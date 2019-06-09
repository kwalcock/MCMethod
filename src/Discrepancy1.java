import java.util.ArrayList;

public class Discrepancy1 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected PointGenerator pointGenerator;
	
	public Discrepancy1(double a, double b) {
		this.pointGenerator = new SimplePointGenerator(a, b);
	}
	
	public void clear() {
		points.clear();
	}
	
	public Point next() {
		Point point = pointGenerator.next();
		
		points.add(point);
		point.setD(calcD());
		return point;
	}
	
	public double calcD() {
		int count = points.size();
		double d = 0;
		
		for (Point outerPoint: points) {
			double actualInside = 0;

			for (Point innerPoint: points)
				if (innerPoint.isInside(outerPoint))
					actualInside++;

			double expectedInside = outerPoint.calcArea() * (count - 1);
			double difference = actualInside - expectedInside;
			
			d += difference * difference;
		}
		return Math.sqrt(d / count);
	}
}
