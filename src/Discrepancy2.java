import java.util.ArrayList;

public class Discrepancy2 {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected PointGenerator pointGenerator;
	
	protected int latice = 12;
	
	public Discrepancy2(double a, double b) {
		this.pointGenerator = new SimplePointGenerator(a, b);
	}
	
	public Point next() {
		Point point = pointGenerator.next();
			
		points.add(point);
		point.setD(calcD());
		return point;
	}
	
	public double calcD() {
		double[][] past = new double[latice][latice];
		int count = points.size();
		double d = 0;
		
		for (int i = 0; i < count; i++) {
			int x = (int)(points.get(i).x * latice);
			int y = (int)(points.get(i).y * latice);
			for (int j = 0; j <= x; j++) {
				for (int k = 0; k <= y; k++) {
					past[k][j]++;
				}
			}
		}
		for (int i = 0; i < latice; i++) {
			for (int j = 0; j < latice; j++) {
				past[i][j] -= ((i + 1)/latice) * ((j + 1)/latice) * count;
				past[i][j] = Math.pow(past[i][j], 2);
				d += past[i][j];
			}
		}
		return Math.sqrt(d)/(count * latice * latice);
	}	
}
