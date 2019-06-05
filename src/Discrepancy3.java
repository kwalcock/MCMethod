import java.util.ArrayList;

public class Discrepancy3 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected PointGenerator pointGenerator;
	protected int lattice = 12;
	
	public Discrepancy3(double a, double b) {
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
		double[][] in = new double[lattice][lattice];
		
		for (int i = 0; i < count; i++) {
			int x = (int) (points.get(i).x * lattice);
			int y = (int) (points.get(i).y * lattice);
			
			in[x][y]++;
		}
		for (int x = 0; x < lattice; x++) {
			for (int y = 0; y < lattice; y++) {
				in[x][y] -= (double) count / (lattice * lattice);
				in[x][y] = Math.pow(in[x][y], 2);
				d += in[x][y];
			}
		}
		return Math.sqrt(d) / (count * lattice * lattice);
	}
}
