import java.util.ArrayList;

public class Discrepancy1 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	
	public void addPoint(double a, double b) {
		points.add(new Point(a, b));
	}
	
	protected double area(Point p) {
		return p.x * p.y;
	}
	
	public double calculate(int p) {
		double d = 0;
		for (int i = 0; i < p; i++) {
			double x = points.get(i).x;
			double y = points.get(i).y;
			double actualInside = 0;
			for (int j = 0; j < p; j++)
				if (points.get(j).x < x && points.get(j).y < y)
					actualInside++;
			double expectedInside =  area(points.get(i)) * (p - 1);
			double difference = actualInside - expectedInside;
			d += difference * difference;
		}
		return Math.sqrt(d)/p;
	}
}
