import java.util.ArrayList;

public class Discrepancy2 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected PointGenerator pointGenerator;
	protected int lattice = 12;
	
	public Discrepancy2(double a, double b) {
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
		double[][] past = new double[lattice][lattice];
		int count = points.size();
		double d = 0;
		
		for (int i = 0; i < count; i++) {
			int x = (int) (points.get(i).x * lattice);
			int y = (int) (points.get(i).y * lattice);
			past[x][y]++;
		}

		for (int x = 0; x < lattice; x++) {
			for (int y = 0; y < lattice; y++) {
				double actualInside = 0;

				for (int j = 0; j <= x; j++)
					for (int k = 0; k <= y; k++)
						actualInside += past[j][k];
				
				double area = (1d + x) * (1d + y);
				double expectedInside = area / lattice / lattice * count;
				double difference = actualInside - expectedInside;

				d += difference * difference;
			}
		}
		return Math.sqrt(d) / (count * lattice * lattice);
	}	
}
