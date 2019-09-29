import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
		setTitle("15 Puzzle!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new Game(this));

		setSize(new Dimension(272, 295));
		setLocationRelativeTo(null);
		setResizable(false);
	}

}
