import java.util.ArrayList;

public class Discrepancy1Fast implements Discrepancy {
	
	protected class FastPoint extends Point {
		// How many of the other points are actually inside this one
		protected double actualInside = 0;
		
		public FastPoint(double x, double y) {
			super(x,y);
		}
	}
	
	protected class FastPointGenerator extends BasePointGenerator {
		
		public FastPointGenerator(double a, double b) {
			super(a, b);
		}
		
		public FastPoint next() {
			return new FastPoint(nextX(), nextY());
		}
	}
	
	protected ArrayList<FastPoint> points = new ArrayList<FastPoint>();
	protected FastPointGenerator pointGenerator;
	
	public Discrepancy1Fast(double a, double b) {
		this.pointGenerator = new FastPointGenerator(a, b);
	}
	
	public Point next() {
		FastPoint newPoint = pointGenerator.next();		
		
		for (FastPoint point: points)
			if (newPoint.isInside(point))
				point.actualInside++;
			else if (point.isInside(newPoint))
				newPoint.actualInside++;
		points.add(newPoint);
		newPoint.setD(calcD());
		return newPoint;
	}
	
	public double calcD() {
		int count = points.size();
		double d = 0;
		
		for (FastPoint point: points) {
			double actualInside = point.actualInside;
			double expectedInside =  point.calcArea() * (count - 1); // Leave out self.
			double difference = actualInside - expectedInside;
			
			d += difference * difference;
		}
		return Math.sqrt(d) / count;
	}
}
