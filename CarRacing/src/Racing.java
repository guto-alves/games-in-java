import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Racing extends JPanel implements ActionListener {
	private BufferedImage backgroundImage;
	private BufferedImage carImage;

	private float x = 300, y = 300;
	private float speed = 0, angle = 0;
	private float maxSpeed = 12.0f;
	private float acc = 0.2f, dec = 0.3f;
	private float turnSpeed = 0.08f;

	private int offsetX = 0, offsetY = 0;

	private boolean up, right, down, left;

	private Timer timer = new Timer(10, this);

	private final int TOTAL_CARS = 5;
	private Car[] cars = new Car[TOTAL_CARS];

	public Racing(JFrame frame) {
		try {
			backgroundImage = ImageIO.read(new File("./src/images/background.png"));
			carImage = ImageIO.read(new File("./src/images/car.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					up = true;

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					right = true;

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					down = true;

				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = true;

				}
			}
		});

		for (int i = 0; i < TOTAL_CARS; i++) {
			cars[i] = new Car();
			cars[i].x = 300 + i * 50;
			cars[i].y = 1700 + i * 80;
			cars[i].speed = 7 + i;
		}

		setPreferredSize(new Dimension(640, 480));

		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		// car movement
		if (up && speed < maxSpeed)
			if (speed < 0)
				speed += dec;
			else
				speed += acc;

		if (down && speed > -maxSpeed)
			if (speed > 0)
				speed -= dec;
			else
				speed -= acc;

		if (!up && !down)
			if (speed - dec > 0)
				speed -= dec;
			else if (speed + dec < 0)
				speed += dec;
			else
				speed = 0;

		if (right && speed != 0)
			angle += turnSpeed * speed / maxSpeed;
		if (left && speed != 0)
			angle -= turnSpeed * speed / maxSpeed;

		x += Math.sin(angle) * speed;
		y += Math.cos(angle) * speed;

		if (x > 320)
			offsetX = (int) (x - 320);
		if (y > 240)
			offsetY = (int) (y - 240);

		g2d.drawImage(backgroundImage, -offsetX, -offsetY, null);

		g2d.drawImage(carImage, (int) x - offsetX, (int) y - offsetY, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
