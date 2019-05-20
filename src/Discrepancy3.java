import java.util.ArrayList;

public class Discrepancy3 implements Discrepancy {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected int latice = 12;
	
	public void addPoint(double a, double b) {
		points.add(new Point(a, b));
	}
	
	public double calculate(int p) {
		double d = 0;
		double[][] in = new double[latice][latice];
		for(int i = 0; i < p; i++) {
			int x = (int)(points.get(i).x * latice);
			int y = (int)(points.get(i).y * latice);
			in[x][y]++;
		}
		for(int i = 0; i < latice; i++) {
			for(int j = 0; j < latice; j++) {
				in[i][j] -= p/(latice*latice);
				in[i][j] = Math.pow(in[i][j], 2);
				d += in[i][j];
			}
		}
		return Math.sqrt(d)/(p*latice*latice);
	}
}
