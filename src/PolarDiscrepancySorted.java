import java.util.Collections;
import java.util.Comparator;

public class PolarDiscrepancySorted extends PolarDiscrepancy {

	protected Comparator<PolarPoint> comparator = new Comparator<PolarPoint>() {
		public int compare(PolarPoint left, PolarPoint right) {
			double difference = left.area - right.area;
			
			if (difference < 0)
				return -1;
			else if (difference > 0)
				return 1;
			else return 0;
		}
	};
	
	public PolarDiscrepancySorted(double a, double b) {
		super(a, b);
	}

	@Override
	public Point next() {
		PolarPoint point = pointGenerator.next();
		int index = Collections.binarySearch(points, point, comparator);
		
		if (index < 0)
			index = -index - 1;
		points.add(index, point);
		point.setD(calcD());
		return point;
	}

	public double calcD() {
		int count = points.size();
		double d = 0;
		
		for (int i = 0; i < count; i++) {
			double actualInside = i;
			double area = points.get(i).area;
			double expectedInside = area * (count - 1);
			double difference = actualInside - expectedInside;
			
			d += difference * difference;
		}
		return Math.sqrt(d) / count;
	}
}
