import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("The Game!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new Game());
		frame.setSize(new Dimension(350, 500));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
