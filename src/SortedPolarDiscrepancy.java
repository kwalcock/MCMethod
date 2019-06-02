import java.util.ArrayList;
import java.util.Collections;

public class SortedPolarDiscrepancy implements Discrepancy {
	protected ArrayList<Double> areas = new ArrayList<Double>();
	
	public void addPoint(double a, double b) {
		double area = calculateArea(Math.sqrt(a * a + b * b));
		int index = Collections.binarySearch(areas, area);
		
		if (index < 0)
			index = -index - 1;
		areas.add(index, area);
	}
	
	public double calculateArea(double r) {
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
			double actualInside = i;
			double area = areas.get(i);
			double expectedInside = area * (p - 1);
			double difference = actualInside - expectedInside;
			d += difference * difference;
		}
		return Math.sqrt(d) / p;
	}
}
