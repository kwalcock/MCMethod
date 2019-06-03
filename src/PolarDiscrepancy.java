import java.util.ArrayList;

public class PolarDiscrepancy implements Discrepancy {
	
	public static class PolarPoint extends Point {
		protected double area;
		
		public PolarPoint(double x, double y, double area) {
			super(x, y);
			this.area = area;
		}
		
		public double getArea() {
			return area;
		}
	}
	
	public static class PolarPointGenerator extends BasePointGenerator {
		
		public PolarPointGenerator(double a, double b) {
			super(a, b);
		}
		
		protected double calcArea(double r) {
			double r2 = r * r;
			double quarter = Math.PI * r2 / 4;
			if (r <= 1)
				return quarter;
			double theta = Math.acos(1D / r);
			double overflow = r2 * theta - Math.sqrt(r2 - 1);
			return quarter - overflow;
		}
		
		public PolarPoint next() {
			double x = nextX();
			double y = nextY();
			double area = calcArea(Math.sqrt(x * x + y * y));
			
			return new PolarPoint(x, y, area);	
		}
	}
	
	protected ArrayList<PolarPoint> points = new ArrayList<PolarPoint>();
	protected PolarPointGenerator pointGenerator;
	
	protected PolarDiscrepancy() {
		this(0d, 0d);
	}
	
	public PolarDiscrepancy(double a, double b) {
		this.pointGenerator = new PolarPointGenerator(a, b);
	}
	
	public void clear() {
		points.clear();
	}
			
	public Point next() {
		PolarPoint point = pointGenerator.next();
		
		points.add(point);
		point.setD(calcD());
		return point;
		
	}
	
	public double calcD() {
		int count = points.size();
		double d = 0;
		
		for (int i = 0; i < count; i++) {
			double area = points.get(i).area;
			double actualInside = 0;
			for (int j = 0; j < count; j++)
				if (points.get(j).area < area)
					actualInside++;
			double expectedInside = area * (count - 1);
			double difference = actualInside - expectedInside;
			d += difference * difference;
		}
		return Math.sqrt(d) / count;
	}
	
	public static void main(String[] args) {
		double[] radii = new double[] {
			0.0d,
			0.1d,
			0.2d,
			0.3d,
			0.4d,
			0.5d,
			0.6d,
			0.7d,
			0.8d,
			0.9d,
			1.0d,
			1.1d,
			1.2d,
			1.3d,
			1.4d,
			Math.sqrt(2)				
		};
		PolarDiscrepancy polarDiscrepancy = new PolarDiscrepancy();
		PolarPointGenerator polarPointGenerator = polarDiscrepancy.pointGenerator;
		
		for (double r: radii)
			System.out.println("" + r + "\t" + polarPointGenerator.calcArea(r));
	}
}
