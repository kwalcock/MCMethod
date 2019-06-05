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
			for (int j = 0; j <= x; j++) {
				for (int k = 0; k <= y; k++) {
					past[k][j]++;
				}
			}
		}
		for (int i = 0; i < lattice; i++) {
			for (int j = 0; j < lattice; j++) {
				past[i][j] -= ((1d + i) / lattice) * ((1d + j) / lattice) * count;
				past[i][j] = Math.pow(past[i][j], 2);
				d += past[i][j];
			}
		}
		return Math.sqrt(d)/(count * lattice * lattice);
	}	
}
