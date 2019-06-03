import java.util.ArrayList;

public class Discrepancy3 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected PointGenerator pointGenerator;
	protected int latice = 12;
	
	public Discrepancy3(double a, double b) {
		this.pointGenerator = new SimplePointGenerator(a, b);
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
		double[][] in = new double[latice][latice];
		
		for (int i = 0; i < count; i++) {
			int x = (int)(points.get(i).x * latice);
			int y = (int)(points.get(i).y * latice);
			
			in[x][y]++;
		}
		for (int i = 0; i < latice; i++) {
			for (int j = 0; j < latice; j++) {
				in[i][j] -= count/(latice*latice);
				in[i][j] = Math.pow(in[i][j], 2);
				d += in[i][j];
			}
		}
		return Math.sqrt(d) / (count * latice * latice);
	}
}
