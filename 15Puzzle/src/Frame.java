import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends JFrame {

	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/images/icon1.jpg")));
		setTitle("15 Puzzle!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Game game = new Game(this);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.setBackground(Color.GRAY);

		JLabel timeLabel = new JLabel();
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setFont(new Font("Serif", Font.BOLD, 18));
		panel.add(timeLabel);

		Chronometer chronometer = new Chronometer(timeLabel);
		chronometer.start();

		JButton resetButton = new JButton("Reset");
		resetButton.setForeground(Color.WHITE);
		resetButton.setBackground(Color.LIGHT_GRAY);
		resetButton.addActionListener(e -> {

			int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the puzzle?", "",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				game.start();
				game.repaint();
				chronometer.start();
			}
		});
		panel.add(resetButton);

		add(panel, BorderLayout.NORTH);
		add(game);

		setSize(new Dimension(272, 330));
		setResizable(false);
		setLocationRelativeTo(null);
	}

}
