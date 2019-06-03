import java.util.LinkedList;
import java.util.ListIterator;

public class PolarDiscrepancyLinked implements Discrepancy {
	protected LinkedList<PolarDiscrepancy.PolarPoint> points = new LinkedList<PolarDiscrepancy.PolarPoint>();
	protected PolarDiscrepancy.PolarPointGenerator pointGenerator;
	protected PolarDiscrepancySorted.PolarPointComparator comparator = new PolarDiscrepancySorted.PolarPointComparator();
	
	public PolarDiscrepancyLinked(double a, double b) {
		this.pointGenerator = new PolarDiscrepancy.PolarPointGenerator(a, b);
	}

	public void clear() {
		points.clear();
	}
			
	@Override
	public Point next() {
		PolarDiscrepancy.PolarPoint point = pointGenerator.next();
		
		point.setD(calcD(point));
		return point;
	}

	protected double calcD(double actualInside, int count, PolarDiscrepancy.PolarPoint point) {
		double area = point.area;
		double expectedInside = area * (count - 1);
		double difference = actualInside - expectedInside;
		
		return difference * difference;			
	}
	
	public double calcD(PolarDiscrepancy.PolarPoint newPoint) {
		int count = points.size() + 1; // It hasn't been added yet.
		ListIterator<PolarDiscrepancy.PolarPoint> iterator = points.listIterator();
		int index = -1;
		double d = 0;
		boolean added = false;
		
		while (iterator.hasNext()) {
			PolarDiscrepancy.PolarPoint point = iterator.next();
			index++;
			
			if (!added && comparator.compare(newPoint, point) <= 0) {
				iterator.previous();
				iterator.add(newPoint);
				point = newPoint;
				added = true;
			}
			d += calcD(index, count, point);			
		}
		if (!added) {
			points.add(newPoint);
			d += calcD(count - 1, count, newPoint);
		}
		return Math.sqrt(d) / count;
	}
}
