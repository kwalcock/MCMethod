import javax.swing.JFrame;

public class MCGui {

	public static void main(String args[]) {	
		JFrame f = new JFrame();
		//MCDraw m = new MCDraw();
		MCPoints m = new MCPoints();
		f.add(m);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1880, 880);
	}
}
