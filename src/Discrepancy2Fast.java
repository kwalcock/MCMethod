public class Discrepancy2Fast implements Discrepancy {
	protected PointGenerator pointGenerator;
	protected int lattice = 12;
	protected double[][] actualInsides = new double[lattice][lattice];
	protected int count = 0;
	
	public Discrepancy2Fast(double a, double b) {
		this.pointGenerator = new SimplePointGenerator(a, b);
	}
	
	public void clear() {
		count = 0;
	}
		
	public Point next() {
		Point point = pointGenerator.next();
		count++;

		int x = (int) (point.x * lattice);
		int y = (int) (point.y * lattice);
		for (int j = 0; j <= x; j++)
			for (int k = 0; k <= y; k++)
				actualInsides[k][j]++;
		
		point.setD(calcD());
		return point;
	}
	
	public double calcD() {
		double d = 0;
		double factor = 1d * count / lattice / lattice;
		
		for (int i = 0; i < lattice; i++) {
			for (int j = 0; j < lattice; j++) {
				double actualInside = actualInsides[i][j];
				double expectedInside = factor * (i + 1) * (j + 1);
				double difference = actualInside - expectedInside;

				d += difference * difference;
			}
		}
		return Math.sqrt(d) / (count * lattice * lattice);
	}	
}