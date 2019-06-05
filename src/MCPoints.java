import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MCPoints extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	Random randomA = new Random(1234);
	Random randomB = new Random(4321);
	
	Timer t = new Timer(5, this);
	int currPoints = 0;
	ArrayList<Point> points = new ArrayList<Point>();
	double greatest = 0;
	double expectation;
	double distance = 0.05;
	long startTime, endTime;
	boolean show = false;
	boolean circle = false;
	int inCirc = 0;
	double a = /*1.0/3;*/ /*2/(1 + Math.sqrt(5))*/ randomA.nextDouble();
	double b = /*a/3 + *//* 1.0/3;*/ /*(a * 1.0/2) + 1.0/36*/ /*Math.sqrt(2) - 1*/ randomB.nextDouble();
	Font f = new Font("serif", Font.PLAIN, 20);
	Color nGreen = new Color(50, 200, 50);
	
//	Discrepancy discrepancy = new Discrepancy1(a, b);
//	Discrepancy discrepancy = new Discrepancy1Fast(a, b);
//	Discrepancy discrepancy = new Discrepancy2(a, b);
	Discrepancy discrepancy = new Discrepancy2Fast(a, b);
//	Discrepancy discrepancy = new Discrepancy3(a, b);
//	Discrepancy discrepancy = new Discrepancy3Fast(a, b);
//	Discrepancy discrepancy = new PolarDiscrepancy(a, b);
//	Discrepancy discrepancy = new PolarDiscrepancySorted(a, b);
//	Discrepancy discrepancy = new PolarDiscrepancyLinked(a, b);
	
	public MCPoints() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(f);
		if(circle) {
			g2.drawOval(-790, 20, 1600, 1600);
			for(int i = 0; i < currPoints; i++) {
				if(Math.pow(points.get(i).x, 2) + Math.pow(points.get(i).y,  2) < 1) g2.setColor(nGreen);
				else g2.setColor(Color.BLUE);
				g2.fillOval((int)(points.get(i).x * 800) + 7, (int)(800 - points.get(i).y * 800) + 17, 6, 6);
			}
		}
		else {
			for(int i = 0; i < currPoints; i++) {
				g2.fillOval((int)(points.get(i).x * 800) + 7, (int)(800 - points.get(i).y * 800) + 17, 6, 6);
			}
		}
		g2.setColor(Color.BLACK);
		for(int i = 0; i < currPoints; i++) {
			g2.drawLine(820 + 1000*i/currPoints, 820 - (int)(points.get(i).getD()*800/greatest), 820 + 1000*(i)/currPoints, 810);
		}
		g2.drawString("" + a, 10, 20);
		g2.drawString("" + b, 820, 20);
		g2.drawString("" + currPoints, 10, 830);
		g2.drawString("" + greatest, 1820, 20);
		g2.drawString("" + greatest/2, 1820, 420);
		if(currPoints != 0)	{
			g2.drawString("" + points.get(currPoints-1).getD(), 1820, 820 - (int)(points.get(currPoints-1).getD()*800/greatest));
			g2.setColor(Color.RED);
			g2.fillOval((int)(points.get(currPoints-1).x * 800) + 5, (int)(800 - points.get(currPoints-1).y * 800) + 15, 10, 10);
			if(show == true) {
				String x = "" + points.get(currPoints-1).x;
				String y = "" + points.get(currPoints-1).y;
				g2.drawString("(" + x.substring(0, 5) + ", " + y.substring(0, 5) + ")", (int)(points.get(currPoints-1).x * 800) + 5, 
				(int)(800 - points.get(currPoints-1).y * 800) + 15);
			}
			g2.drawString("" + 4 * (double)inCirc/currPoints, 410,830);
		}
	}
	
	public double getDev() {
		return points.get(currPoints).d;
	}
	
	public double avgDev() {
		double end = 0;
		
		for (int i = 0; i < 10; i++) {
			end += points.get(currPoints - i * 20).d;
		}
		return end/10;
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Point point = discrepancy.next(); 
		points.add(point);
		currPoints++;
		greatest = Math.max(point.getD(), greatest);
		if (point.isInside(1))
			inCirc++;
		repaint();
		if(currPoints%100 == 0) {
			startTime = System.nanoTime();
		}if(currPoints%100 == 99) {
			endTime = System.nanoTime();
			System.out.println("" + currPoints + "\t" + (endTime - startTime));
			
//			for (Point p: points) {
//				System.out.println(p.getD());
//			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code){
		case(KeyEvent.VK_SPACE):
			if(t.isRunning()) t.stop();
			else t.start();
			break;
		case(KeyEvent.VK_R):
			a = randomA.nextDouble();
			b = randomB.nextDouble();
			// Need a new thing
			currPoints = 0;
			points = new ArrayList<Point>();
			greatest = 0;
			inCirc = 0;
			repaint();
			break;
		case(KeyEvent.VK_Q):
			currPoints = 0;
			points = new ArrayList<Point>();
			greatest = 0;
			inCirc = 0;
			repaint();
			break;
		case(KeyEvent.VK_RIGHT):
			Point point = discrepancy.next(); 
			points.add(point);
			greatest = Math.max(point.getD(), greatest);
			repaint();
			break;
		case(KeyEvent.VK_LEFT):
			points.remove(currPoints-1);
			currPoints--;
			repaint();
			break;
		case(KeyEvent.VK_S):
			show = !show;
			repaint();
			break;
		case(KeyEvent.VK_C):
			circle = !circle;
			currPoints = 0;
			points = new ArrayList<Point>();
			greatest = 0;
			repaint();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
