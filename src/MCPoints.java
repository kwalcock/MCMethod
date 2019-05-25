import java.awt.Color;
import java.awt.Font;
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

	Timer t = new Timer(5, this);
	int currPoints = 0;
	ArrayList<double[]> points = new ArrayList<double[]>();
	double greatest = 0;
	double expectation;
	double distance = 0.05;
	int latice = 12;
	long startTime, endTime;
	boolean show = false;
	boolean circle = false;
	int inCirc = 0;
	double a = /*1.0/3;*/ /*2/(1 + Math.sqrt(5))*/ Math.random();
	double b = /*a/3 + */ 1.0/3; /*(a * 1.0/2) + 1.0/36*/ /*Math.sqrt(2) - 1*//* Math.random();*/
	Font f = new Font("serif", Font.PLAIN, 20);
	Color nGreen = new Color(50, 200, 50);
	
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
				if(Math.pow(points.get(i)[0], 2) + Math.pow(points.get(i)[1],  2) < 1) g2.setColor(nGreen);
				else g2.setColor(Color.BLUE);
				g2.fillOval((int)(points.get(i)[0] * 800) + 7, (int)(800 - points.get(i)[1] * 800) + 17, 6, 6);
			}
		}
		else {
			for(int i = 0; i < currPoints; i++) {
				g2.fillOval((int)(points.get(i)[0] * 800) + 7, (int)(800 - points.get(i)[1] * 800) + 17, 6, 6);
			}
		}
		g2.setColor(Color.BLACK);
		for(int i = 0; i < currPoints; i++) {
			g2.drawLine(820 + 1000*i/currPoints, 820 - (int)(points.get(i)[3]*800/greatest), 820 + 1000*(i)/currPoints, 810);
		}
		g2.drawString("" + a, 10, 20);
		g2.drawString("" + b, 820, 20);
		g2.drawString("" + currPoints, 10, 830);
		g2.drawString("" + greatest, 1820, 20);
		g2.drawString("" + greatest/2, 1820, 420);
		if(currPoints != 0)	{
			g2.drawString("" + points.get(currPoints-1)[3], 1820, 820 - (int)(points.get(currPoints-1)[3]*800/greatest));
			g2.setColor(Color.RED);
			g2.fillOval((int)(points.get(currPoints-1)[0] * 800) + 5, (int)(800 - points.get(currPoints-1)[1] * 800) + 15, 10, 10);
			if(show == true) {
				String x = "" + points.get(currPoints-1)[0];
				String y = "" + points.get(currPoints-1)[1];
				g2.drawString("(" + x.substring(0, 5) + ", " + y.substring(0, 5) + ")", (int)(points.get(currPoints-1)[0] * 800) + 5, 
				(int)(800 - points.get(currPoints-1)[1] * 800) + 15);
			}
			g2.drawString("" + 4 * (double)inCirc/currPoints, 410,830);
		}
	}
	
	public void generate(double irr1, double irr2, int mul) {
		double[] e = {0, 0, 0, 0};
		points.add(e);
		points.get(mul)[0] = (irr1*mul) % 1;
		points.get(mul)[1] = (irr2*mul) % 1;
		for(int i = 0; i < mul; i++) {
			if(points.get(mul)[0] < points.get(i)[0] && points.get(mul)[1] < points.get(i)[1]) points.get(i)[2]++;
			else if(points.get(mul)[0] > points.get(i)[0] && points.get(mul)[1] > points.get(i)[1]) points.get(mul)[2]++;
			points.get(i)[2] -= points.get(i)[0]*points.get(i)[1];
			points.get(mul)[2] -= points.get(mul)[0]*points.get(mul)[1];
		}
	}
	
	public void generate(int i) {
		double[] e = {0, 0, 0, 0};
		points.add(e);
		points.get(i)[0] = Math.random();
		points.get(i)[1] = Math.random();
		for(int j = 0; j < i; j++) {
			if(points.get(i)[0] < points.get(j)[0] && points.get(i)[1] < points.get(j)[1]) points.get(j)[2]++;
			else if(points.get(i)[0] > points.get(j)[0] && points.get(i)[1] > points.get(j)[1]) points.get(i)[2]++;
			points.get(j)[2] -= points.get(j)[0]*points.get(j)[1];
			points.get(i)[2] -= points.get(i)[0]*points.get(i)[1];
		}
	}
	
	public double discrepancy1(int p) {
		double d = 0;
		for(int i = 0; i < p; i ++) {
			d += Math.pow(points.get(i)[2],  2);
		}
		return Math.sqrt(d)/p;
	}
	
	public double discrepancy2(int p) {
		double d = 0;
		double[][] past = new double[latice][latice];
		for(int i = 0; i < p; i++) {
			int x = (int)(points.get(i)[0] * latice);
			int y = (int)(points.get(i)[0] * latice);
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
	
	public double discrepancy3(int p) {
		double d = 0;
		double[][] in = new double[latice][latice];
		for(int i = 0; i < p; i++) {
			int x = (int)(points.get(i)[0] * latice);
			int y = (int)(points.get(i)[0] * latice);
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
	
	/*public void within(int num) {
		for(int i = 0; i < totalPoints; i++) {
			double a = Math.sqrt(Math.pow(Math.abs(points[num][0] - points[i][0]), 2) + Math.pow(Math.abs(points[num][1] - points[i][1]), 2));
			if(a < distance) points[num][2]++;
		}
	}*/
	
	public double getDev() {
		return discrepancy1(currPoints);
	}
	
	public double avgDev() {
		double end = 0;
		for(int i = 0; i < 10; i++) {
			end += discrepancy1(currPoints - i*20);
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
		/*generate(a, b, currPoints);*/
		generate(currPoints);
		points.get(currPoints)[3] = discrepancy1(currPoints);
		if(points.get(currPoints)[3] > greatest) greatest = points.get(currPoints)[3];
		if(Math.pow(points.get(currPoints)[0], 2) + Math.pow(points.get(currPoints)[1],  2) < 1) inCirc++;
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
			inCirc = 0;
			repaint();
			break;
		case(KeyEvent.VK_Q):
			currPoints = 0;
			points = new ArrayList<double[]>();
			greatest = 0;
			inCirc = 0;
			repaint();
			break;
		case(KeyEvent.VK_RIGHT):
			/*generate(a, b, currPoints);*/
			generate(currPoints);
			points.get(currPoints)[2] = discrepancy1(currPoints);
			if(points.get(currPoints)[2] > greatest) greatest = points.get(currPoints)[2];
			currPoints++;
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
			points = new ArrayList<double[]>();
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
