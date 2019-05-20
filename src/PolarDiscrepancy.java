import java.util.ArrayList;

public class PolarDiscrepancy implements Discrepancy {
	protected ArrayList<Double> radii = new ArrayList<Double>();
	
	public void addPoint(double a, double b) {
		radii.add(Math.sqrt(a * a + b * b));
	}
	
	public double area(double r) {
		double quarter = Math.PI * r * r / 4;
		if (r <= 1)
			return quarter;
		double theta = Math.acos(1D / r);
		double overflow = r * r * theta - Math.sqrt(r * r - 1);
		return quarter - overflow;
	}
	
	public double calculate(int p) {
		double d = 0;
		for (int i = 0; i < p; i++) {
			double r = radii.get(i);
			double actualInside = 0;
			for (int j = 0; j < p; j++)
				if (radii.get(j) < r)
					actualInside++;
			double expectedInside = area(r) * (p - 1);
			double difference = actualInside - expectedInside;
			d += difference * difference;
		}
		return Math.sqrt(d) / p;
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
		
		for (double r: radii)
			System.out.println("" + r + "\t" + polarDiscrepancy.area(r));
	}
}
