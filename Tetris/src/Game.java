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

	private final int ROWS = 22;
	private final int COLUMNS = 12;

	private int[][] fields = new int[ROWS][COLUMNS];

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

	private BufferedImage tilesImage;
	private BufferedImage backgroundImage;

	private int dx, colorNum;
	private boolean rotate = false;
	private float timer = 0f, delay = 0.3f;

	private Timer timer2 = new Timer(50, this);

	public Game(JFrame frame) {
		try {
			tilesImage = ImageIO.read(new File("./src/images/tiles.png"));
			backgroundImage = ImageIO.read(new File("./src/images/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 4; i++) {
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

		genereteFigure();

		timer2.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(backgroundImage, 0, 0, this);

		timer += 0.1f;

		// move
		for (int i = 0; i < 4; i++) {
			b[i].x = a[i].x;
			b[i].y = a[i].y;
			a[i].x += dx;
		}

		if (!isValidFigure()) {
			for (int i = 0; i < 4; i++) {
				a[i].x = b[i].x;
				a[i].y = b[i].y;
			}
		}

		// rotate figure
		if (rotate) {
			Point p = a[1]; // center of rotation
			for (int i = 0; i < 4; i++) {
				int x = a[i].y - p.y;
				int y = a[i].x - p.x;
				a[i].x = p.x - x;
				a[i].y = p.y + y;
			}

			if (!isValidFigure()) {
				for (int i = 0; i < 4; i++) {
					a[i].x = b[i].x;
					a[i].y = b[i].y;
				}
			}
		}

		// tick
		if (timer > delay) {
			for (int i = 0; i < a.length; i++) {
				b[i].x = a[i].x;
				b[i].y = a[i].y;
				a[i].y += 1;
			}

			if (!isValidFigure()) {
				for (int i = 0; i < 4; i++)
					fields[b[i].y][b[i].x] = colorNum;

				genereteFigure();
			}

			if (!isValidFigure()) {
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COLUMNS; j++)
						fields[i][j] = 0;
				}
				genereteFigure();
			}

			timer = 0f;
		}

		// check lines
		int k = ROWS - 1;
		for (int i = ROWS - 1; i > 0; i--) {
			int count = 0;
			for (int j = 1; j < COLUMNS - 2; j++) {
				if (fields[i][j] != 0)
					count++;
				fields[k][j] = fields[i][j];
			}
			if (count < COLUMNS - 3)
				k--;
		}

		dx = 0;
		rotate = false;
		delay = 0.3f;

		// draw stopped figures
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (fields[i][j] == 0)
					continue;

				BufferedImage s = tilesImage.getSubimage(fields[i][j] * 18, 0, 18, 18);
				g2d.drawImage(s, j * 18, i * 18, this);
			}
		}

		// draw current figure
		for (int i = 0; i < 4; i++) {
			BufferedImage s = tilesImage.getSubimage(colorNum * 18, 0, 18, 18);
			g2d.drawImage(s, a[i].x * 18, a[i].y * 18, this);
		}
	}

	private void genereteFigure() {
		colorNum = 1 + random.nextInt(7);

		int numberOfFigure = random.nextInt(7);

		int initialPosition = random.nextInt(COLUMNS - 2) + 1;
		for (int i = 0; i < 4; i++) {
			a[i].x = figures[numberOfFigure][i] % 2 + initialPosition;
			a[i].y = figures[numberOfFigure][i] / 2;
		}
	}

	private boolean isValidFigure() {
		for (int i = 0; i < 4; i++)
			if (a[i].x < 1 || a[i].x >= COLUMNS || a[i].y >= ROWS)
				return false;
			else if (fields[a[i].y][a[i].x] != 0)
				return false;

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
