import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new Game(frame));
		frame.setSize(new Dimension(335, 520));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
