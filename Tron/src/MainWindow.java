import javax.swing.JFrame;

public class MainWindow extends JFrame {

	public MainWindow() {
		setTitle("The Tron Game!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(618, 518);
		setLocationRelativeTo(null);

		Tron tron = new Tron(this);
		add(tron);
	}
}
