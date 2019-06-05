public class Discrepancy2Fast implements Discrepancy {
	protected PointGenerator pointGenerator;
	protected int lattice = 12;
	protected int[][] actualInsides = new int[lattice][lattice];
	protected int[][] totalInsides = new int[lattice][lattice];
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
		actualInsides[x][y]++;
		
		point.setD(calcD());
		return point;
	}
	
	public double calcD() {
		double d = 0;
		double factor = 1d * count / lattice / lattice;
		
		for (int x = 0; x < lattice; x++) {
			for (int y = 0; y < lattice; y++) {
				int totalInside = actualInsides[x][y];
				
				if (x > 0)
					totalInside += totalInsides[x - 1][y];
				if (y > 0)
					totalInside += totalInsides[x][y - 1];
				if (x > 0 && y > 0)
					totalInside -= totalInsides[x - 1][y - 1];
				totalInsides[x][y] = totalInside;
				
				double actualInside = totalInside;
				int area = (x + 1) * (y + 1); 
				double expectedInside = factor * area;
				double difference = actualInside - expectedInside;

				d += difference * difference;
			}
		}
		return Math.sqrt(d) / (count * lattice * lattice);
	}	
}
