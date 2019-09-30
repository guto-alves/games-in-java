import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Chronometer implements ActionListener {
	private Timer timer = new Timer(1000, this);

	private JLabel label;

	private int minutes;
	private int seconds;

	public Chronometer(JLabel label) {
		this.label = label;
	}

	public void start() {
		minutes = 0;
		seconds = 0;

		label.setText(String.format("%02d:%02d", minutes, seconds));

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		++seconds;

		if (seconds >= 60) {
			seconds = 0;
			++minutes;
		}

		if (minutes >= 60) {
			timer.stop();
		}

		label.setText(String.format("%02d:%02d", minutes, seconds));
	}
}
