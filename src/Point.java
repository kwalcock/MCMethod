public class Point {
	protected double x;
	protected double y;
	protected double d; // discrepancy of point when it was added
	
	public Point(double x, double y) {
		this(x, y, 0d);
	}
	
	public Point(double x, double y, double d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getD() {
		return d;
	}
	
	public void setD(double d) {
		this.d = d;
	}
	
	public boolean isInside(double r) {
		return x * x + y * y < r * r;
	}
	
	public boolean isInside(Point other) {
		return this.x < other.x && this.y < other.y;
	}
	
	protected double calcArea() {
		return x * y;
	}
}
