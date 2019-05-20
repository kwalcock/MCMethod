import java.util.ArrayList;

public class Discrepancy2 {
	protected ArrayList<Point> points = new ArrayList<Point>();
	protected int latice = 12;
	
	public void addPoint(double a, double b) {
		points.add(new Point(a, b));
	}
	
	public double calculate(int p) {
		double d = 0;
		double[][] past = new double[latice][latice];
		for(int i = 0; i < p; i++) {
			int x = (int)(points.get(i).x * latice);
			int y = (int)(points.get(i).y * latice);
			for(int j = 0; j <= x; j++) {
				for(int k = 0; k <= y; k++) {
					past[k][j]++;
				}
			}
		}
		for(int i = 0; i < latice; i++) {
			for(int j = 0; j < latice; j++) {
				past[i][j] -= ((i + 1)/latice) * ((j + 1)/latice) * p;
				past[i][j] = Math.pow(past[i][j], 2);
				d += past[i][j];
			}
		}
		return Math.sqrt(d)/(p*latice*latice);
	}	
}
