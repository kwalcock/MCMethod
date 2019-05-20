import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MCPoints extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	Timer t = new Timer(5, this);
	int currPoints = 0;
	ArrayList<double[]> points = new ArrayList<double[]>();
	double greatest = 0;
	double expectation;
	double distance = 0.05;
	int latice = 12;
	long startTime, endTime;
	double a = 22.0/29 + 17.0/174 /*2/(1 + Math.sqrt(5))*/ /*Math.random()*/, b = 11.0/29 + (8.0*17)/(29.0*9) /*(a * 1.0/2) + 1.0/36*/ /*Math.sqrt(2) - 1*/ /*Math.random()*/;
//	Discrepancy discrepancy = new Discrepancy1();
//	Discrepancy discrepancy = new Discrepancy2();
//	Discrepancy discrepancy = new Discrepancy3();
	Discrepancy discrepancy = new PolarDiscrepancy();
	
	public MCPoints() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i < currPoints; i++) {
			g2.fillOval((int)(points.get(i)[0] * 800) + 8, (int)(800 - points.get(i)[1] * 800) + 8, 4, 4);
		}
		for(int i = 0; i < currPoints; i++) {
			g2.drawLine(820 + 1000*i/currPoints, 810 - (int)(points.get(i)[2]*800/greatest), 820 + 1000*(i)/currPoints, 810);
		}
		g2.drawString("" + a, 10, 10);
		g2.drawString("" + b, 820, 10);
		g2.drawString("" + currPoints, 10, 810);
		g2.drawString("" + greatest, 1820, 10);
		g2.drawString("" + greatest/2, 1820, 410);
		if(currPoints != 0)	{
			g2.drawString("" + points.get(currPoints-1)[2], 1820, 810 - (int)(points.get(currPoints-1)[2]*800/greatest));
			g2.setColor(Color.RED);
			g2.fillOval((int)(points.get(currPoints-1)[0] * 800) + 7, (int)(800 - points.get(currPoints-1)[1] * 800) + 7, 6, 6);
		}
	}
	
	public void generate(double irr1, double irr2, int mul) {
		double[] e = {(irr1 * mul) % 1, (irr2 * mul) % 1, 0};
		points.add(e);
		discrepancy.addPoint(e[0], e[1]);
	}
	
	public void generate(int i) {
		double[] e = {Math.random(), Math.random(), 0};
		points.add(e);
		discrepancy.addPoint(e[0], e[1]);
	}
	
	/*public void within(int num) {
		for(int i = 0; i < totalPoints; i++) {
			double a = Math.sqrt(Math.pow(Math.abs(points[num][0] - points[i][0]), 2) + Math.pow(Math.abs(points[num][1] - points[i][1]), 2));
			if(a < distance) points[num][2]++;
		}
	}*/
	
	public double getDev() {
		return discrepancy.calculate(currPoints);
	}
	
	public double avgDev() {
		double end = 0;
		for(int i = 0; i < 10; i++) {
			end += discrepancy.calculate(currPoints - i * 20);
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
		generate(a, b, currPoints);
		//generate();
		points.get(currPoints)[2] = discrepancy.calculate(currPoints);
		if(points.get(currPoints)[2] > greatest) greatest = points.get(currPoints)[2];
		currPoints++;
		repaint();
		if(currPoints%100 == 0) {
			startTime = System.nanoTime();
		}if(currPoints%100 == 99) {
			endTime = System.nanoTime();
			System.out.println(endTime - startTime);
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
			a = Math.random();
			b = Math.random();
			currPoints = 0;
			points = new ArrayList<double[]>();
			greatest = 0;
			repaint();
			break;
		case(KeyEvent.VK_Q):
			currPoints = 0;
			points = new ArrayList<double[]>();
			greatest = 0;
			repaint();
			break;
		case(KeyEvent.VK_RIGHT):
			generate(a, b, currPoints);
			points.get(currPoints)[2] = discrepancy.calculate(currPoints);
			if(points.get(currPoints)[2] > greatest) greatest = points.get(currPoints)[2];
			currPoints++;
			repaint();
			break;
		case(KeyEvent.VK_LEFT):
			points.remove(currPoints-1);
			currPoints--;
			repaint();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
