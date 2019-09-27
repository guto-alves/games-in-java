import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private final SecureRandom random = new SecureRandom();

	private final int M = 20;
	private final int N = 10;

	private boolean[][] fields = new boolean[M][N];

	private int[][] figures = { { 1, 3, 5, 7 }, // I
			{ 2, 4, 5, 7 }, // Z
			{ 3, 5, 4, 6 }, // S
			{ 3, 5, 4, 7 }, // T
			{ 2, 3, 5, 7 }, // L
			{ 3, 5, 7, 6 }, // J
			{ 2, 3, 4, 5 } // O
	};

	private Point[] a = new Point[4];
	private Point[] b = new Point[4];

	private BufferedImage tiles;
	private BufferedImage backgroundImage;
	private BufferedImage frameImage;

	private boolean rotate = false;
	private int dx = 0, colorNum = 1;
	private float timer = 0, delay = 0.3f;

	private Timer timer2 = new Timer(50, this);

	public Game(JFrame frame) {
		try {
			tiles = ImageIO.read(new File("./src/images/tiles.png"));
			backgroundImage = ImageIO.read(new File("./src/images/background.png"));
			frameImage = ImageIO.read(new File("./src/images/frame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < a.length; i++) {
			a[i] = new Point();
			b[i] = new Point();
		}

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
					rotate = true;
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					dx = -1;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					dx = 1;

				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					delay = 0.05f;
			}
		});

		setBackground(Color.WHITE);

		setPreferredSize(new Dimension(320, 480));

		timer2.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(backgroundImage, 0, 0, null);
		g2d.drawImage(frameImage, 0, 0, null);

		timer += 0.1;

		// Move
		for (int i = 0; i < 4; i++) {
			b[i] = a[i];
			a[i].x += dx;
		}

		if (!check())
			for (int i = 0; i < 4; i++)
				a[i] = b[i];

		if (!check())
			for (int i = 0; i < 4; i++)
				a[i] = b[i];

		// Rotate
		if (rotate) {
			Point p = a[1]; // center of rotation
			for (int i = 0; i < 4; i++) {
				int x = a[i].y - p.y;
				int y = a[i].x - p.x;
				a[i].x = p.x - x;
				a[i].y = p.y + y;
			}
			if (!check())
				for (int i = 0; i < 4; i++)
					a[i] = b[i];
		}

		// Tick
		if (timer > delay) {
			for (int i = 0; i < 4; i++) {
				b[i] = a[i];
				a[i].y += 1;
			}

			if (!check()) {
				for (int i = 0; i < 4; i++)
					//fields[b[i].y][b[i].x] = colorNum == 1 ? true : false;

				colorNum = 1 + random.nextInt(7);
				int n = random.nextInt(7);
				for (int i = 0; i < 4; i++) {
					a[i].x = figures[n][i] % 2;
					a[i].y = figures[n][i] / 2;
				}
			}

			timer = 0;
		}

		// check lines
		int k = M - 1;
		for (int i = M - 1; i > 0; i--) {
			int count = 0;

			for (int j = 0; j < N; j++) {
				if (fields[i][j])
					count++;
				fields[k][j] = fields[i][j];
			}

			if (count < N)
				k--;
		}

		dx = 0;
		rotate = false;
		delay = 0.3f;

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (fields[i][j] == false)
					continue;

				BufferedImage s = tiles.getSubimage(18, 0, 18, 18);
				g2d.drawImage(s, j * 18, i * 18, null);
			}
		}

		for (int i = 0; i < 4; i++) {
			BufferedImage s = tiles.getSubimage(18, 0, 18, 18);
			g2d.drawImage(s, a[i].x * 18, a[i].y * 18, null);
		}
	}

	private boolean check() {
		for (int i = 0; i < 4; i++)
			if (a[i].x < 0 || a[i].x >= N || a[i].y >= M)
				return false;
			else if (fields[a[i].y][a[i].x])
				return false;

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
