import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Car Racing Game!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new Racing(frame));
		frame.setSize(new Dimension(640, 480));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
