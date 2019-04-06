import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

public class MCDraw extends JPanel implements KeyListener, ActionListener{

	Timer t = new Timer(50, this);
	ArrayList<Double[]> bars = new ArrayList<Double[]>();
	ArrayList<int[]> height = new ArrayList<int[]>();
	double[][] lowest = new double[10][3];
	double[][] highest = new double[10][3];
	int bracket = 10;
	boolean start = false;
	
	public MCDraw() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		for(int i = 0; i < 10; i++) {
			double[] fill = {1000, 0, 0};
			lowest[i] = fill;
			double[] fill2 = {0, 0, 0};
			highest[i] = fill2;
		}
	}
	
	public void produce() {
		for(int i = 0; i < 100; i++) {
			generate();
		}
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		paintDis(g2);
	}
	
	public void paintBars(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawLine(10, 10, 10, 810);
		g2.drawLine(820,  10,  820,  810);
		g2.drawLine(10, 810, 810, 810);
		g2.drawLine(820,  810,  1620, 810);
		for(int i = 0; i < bars.size(); i++) {
			if(bars.get(i)[0] < 150) g2.setColor(Color.YELLOW);
			else if(bars.get(i)[0] < 500) g2.setColor(Color.GREEN);
			else if(bars.get(i)[0] < 2000) g2.setColor(Color.BLUE);
			else g2.setColor(Color.RED);
			g2.fillRect(10 + (int)(800 * bars.get(i)[1]), 810 - (int)(100*Math.log(bars.get(i)[0])), 1, (int)(100*Math.log(bars.get(i)[0])));
			g2.fillRect(820 + (int)(800 * bars.get(i)[2]), 810 - (int)(100*Math.log(bars.get(i)[0])), 1, (int)(100*Math.log(bars.get(i)[0])));
		}
	}
	
	public void paintDis(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawLine(10, 10, 10, 810);
		g2.drawLine(10, 810, 1610, 810);
		int greatest = 0; 
		for(int i = 0; i < height.size(); i++) {
			if(height.get(i)[1] > greatest) greatest = height.get(i)[1];
		}
		for(int i = 0; i < height.size(); i++) {
			if(height.get(i)[0] < 20) g2.setColor(Color.YELLOW);
			else if(height.get(i)[0] < 50) g2.setColor(Color.GREEN);
			else if(height.get(i)[0] < 200) g2.setColor(Color.BLUE);
			else g2.setColor(Color.RED);
			g2.fillRect(10 + bracket*height.get(i)[0] - bracket/2, 810 - (800*height.get(i)[1]/greatest), bracket, 800*height.get(i)[1]/greatest);
		}
	}
	
	public void generate() {
		MCPoints a = new MCPoints();
		//method1(a);
		method2(a);
		method3(a);
	}
	
	public void method1(MCPoints a) {
		Double recip = a.getA() / a.getB();
		if(recip > 1) recip = 1/recip;
		Double diff = Math.abs(a.getA() - a.getB());
		Double[] nums = {a.getDev(), recip, diff};
		bars.add(nums);
	}
	
	public void method2(MCPoints a) {
		int brac = (int) (a.getDev() / bracket);
		for(int i = 0; i < height.size(); i++) {
			if(height.get(i)[0] == brac) {
				height.get(i)[1]++;
				return;
			}
		}
		int[] nheight = {brac, 1};
		height.add(nheight);
	}
	
	public void method3(MCPoints a) {
		int l = 0; 
		int g = 0;
		for(int i = 1; i < 10; i++) {
			if(lowest[i][0] > lowest[l][0]) l = i;
			if(highest[i][0] < highest[g][0]) g = i;
		}
		if(a.getDev() < lowest[l][0]) {
			lowest[l][0] = a.getDev();
			lowest[l][1] = a.getA();
			lowest[l][2] = a.getB();
		}
		if(a.getDev() > highest[g][0]) {
			highest[g][0] = a.getDev();
			highest[g][1] = a.getA();
			highest[g][2] = a.getB();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			start = !start;
			if(start == true) t.start();
			else {
				t.stop();
				for(int i = 0; i < 10; i++) {
					System.out.println(lowest[i][0] + " " + lowest[i][1] + " " + lowest[i][2]);
				}
				for(int i = 0; i < 10; i++) {
					System.out.println(highest[i][0] + " " + highest[i][1] + " " + highest[i][2]);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		produce();
	}

}
