public class SimplePointGenerator extends BasePointGenerator {
	
	SimplePointGenerator(double a, double b) {
		super(a, b);
	}
	
	public Point next() {
		double newX = nextX();
		double newY = nextY();
		
		return new Point(newX, newY);
	}
}
