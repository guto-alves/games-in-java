import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private final SecureRandom random = new SecureRandom();

	private final int WIDTH = 400;
	private final int HEIGHT = 533;

	private BufferedImage backgroundImage;
	private BufferedImage platformImage;
	private BufferedImage persImage;

	private int x, y, h = 150;
	private float dx, dy;

	private Timer timer = new Timer(10, this);

	private enum Status {
		GAME_OVER, PLAYING
	};

	private Status status;

	private final int TOTAL_PLATFORMS = 10;
	private Point[] platforms = new Point[TOTAL_PLATFORMS];

	private long score;
	private Thread displayScoreThread;

	private int direction;

	public Game(JFrame frame) {
		try {
			backgroundImage = ImageIO.read(new File("./src/images/background.png"));
			platformImage = ImageIO.read(new File("./src/images/platform.png"));
			persImage = ImageIO.read(new File("./src/images/doodle_left.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < TOTAL_PLATFORMS; i++)
			platforms[i] = new Point();

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (x + persImage.getWidth() >= WIDTH + 40)
					x = -40;
				else if (x <= -40)
					x = WIDTH - 40;

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					try {
						persImage = ImageIO.read(new File("./src/images/doodle_right.png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					if (direction == 1)
						++dx;
					else
						dx = 3;

					if (dx > 10)
						dx = 10;

					x += dx;

					direction = 1;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					try {
						persImage = ImageIO.read(new File("./src/images/doodle_left.png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					if (direction == 2)
						++dx;
					else
						dx = 3;

					if (dx > 10)
						dx = 10;

					x -= dx;

					direction = 2;
				}
			}
		});

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		startGame();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (status == Status.PLAYING) {
			dy += 0.2;
			y += dy;

			// verifica se enconsta em alguma plataforma
			for (int i = 0; i < TOTAL_PLATFORMS; i++) {
				if ((x + 50 > platforms[i].x) && (x + 20 < platforms[i].x + 68)
						&& (platforms[i].y - (y + 70) <= 5 && platforms[i].y - (y + 70) >= -2) && (dy > 0))
					dy = -10; // faz doodle subir
			}

			// faz plataformas descerem
			if (y < h) {
				score += h - y;
				for (int i = 0; i < TOTAL_PLATFORMS; i++) {
					y = h;
					platforms[i].y = (int) (platforms[i].y - dy);
					if (platforms[i].y > HEIGHT) {
						platforms[i].y = 0;
						platforms[i].x = random.nextInt(WIDTH - 68);
					}
				}
			}

			updateStatus();
		} else {
			y -= 1;

			for (int i = 0; i < TOTAL_PLATFORMS; i++)
				platforms[i].y = (int) (platforms[i].y - dy);

			if (!displayScoreThread.isAlive())
				displayScoreThread.start();
		}

		// draw
		g.drawImage(backgroundImage, 0, 0, null);

		for (int i = 0; i < TOTAL_PLATFORMS; i++)
			g.drawImage(platformImage, platforms[i].x, platforms[i].y, null);

		g.drawImage(persImage, x, y, null);
	}

	private void updateStatus() {
		if (y > 500)
			status = Status.GAME_OVER;
		else
			status = Status.PLAYING;
	}

	private void startGame() {
		status = Status.PLAYING;
		score = 0;
		direction = 0;

		x = 100;
		y = 400;

		platforms[0].x = 100;
		platforms[0].y = 510;

		for (int i = 1; i < TOTAL_PLATFORMS; i++) {
			platforms[i].x = random.nextInt(WIDTH - 68);
			platforms[i].y = random.nextInt(HEIGHT - 14);
		}

		displayScoreThread = new Thread() {
			@Override
			public void run() {
				sleep(3000);

				JOptionPane.showMessageDialog(null, "Your score: " + score, "Game Over",
						JOptionPane.INFORMATION_MESSAGE);
				timer.stop();

				sleep(1500);

				startGame();
			};

			private void sleep(int millis) {
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}

			}
		};

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
