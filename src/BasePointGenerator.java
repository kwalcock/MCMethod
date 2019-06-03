public abstract class BasePointGenerator implements PointGenerator {
	protected double a;
	protected double b;
	
	protected double currentX = 0;
	protected double currentY = 0;

	public BasePointGenerator(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	protected double nextX() {
		return currentX = (currentX + a) % 1;
	}
	
	protected double nextY() {
		return currentY = (currentY + b) % 1;
	}
	
	public abstract Point next();
}
