
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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener {
	private final SecureRandom random = new SecureRandom();

	private final int rows = 30, columns = 20;
	private final int size = 16;
	private int width = size * rows;
	private int height = size * columns;

	private BufferedImage whiteImage;
	private BufferedImage redImage;
	private BufferedImage greenImage;

	private final int MAX_LENGTH = 100;
	private Point[] snake = new Point[MAX_LENGTH];
	private int direction, currentLength;

	private Point fruit = new Point();

	private Timer timer = new Timer(100, this);

	public Snake(JFrame frame) {
		setPreferredSize(new Dimension(width, height));

		try {
			whiteImage = ImageIO.read(new File("./src/images/white.png"));
			redImage = ImageIO.read(new File("./src/images/red.png"));
			greenImage = ImageIO.read(new File("./src/images/green.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 2)
					direction = 1;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 1)
					direction = 2;
				if (e.getKeyCode() == KeyEvent.VK_UP && direction != 0)
					direction = 3;
				if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 3)
					direction = 0;
			}
		});

		start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
				g.drawImage(whiteImage, i * size, j * size, null);
		}

		g.drawImage(greenImage, fruit.x * size, fruit.y * size, null);

		for (int i = 0; i < currentLength; i++)
			g.drawImage(redImage, snake[i].x * size, snake[i].y * size, null);
	}

	public void tick() {
		for (int i = currentLength; i > 0; i--) {
			snake[i].x = snake[i - 1].x;
			snake[i].y = snake[i - 1].y;
		}

		if (direction == 0)
			snake[0].y += 1;
		else if (direction == 1)
			snake[0].x -= 1;
		else if (direction == 2)
			snake[0].x += 1;
		else if (direction == 3)
			snake[0].y -= 1;

		if (snake[0].x == rows)
			snake[0].x = 0;

		if (snake[0].x < 0)
			snake[0].x = rows - 1;

		if (snake[0].y == columns)
			snake[0].y = 0;

		if (snake[0].y < 0)
			snake[0].y = columns - 1;

		if ((snake[0].x == fruit.x) && (snake[0].y == fruit.y)) {
			currentLength++;
			fruit.x = random.nextInt(rows);
			fruit.y = random.nextInt(columns);
		}

		for (int i = 1; i < currentLength; i++) {
			if (snake[0].x == snake[i].x && snake[0].y == snake[i].y) {
				timer.stop();
				start();
			}
		}
	}

	public void start() {
		for (int i = 0; i < MAX_LENGTH; i++)
			snake[i] = new Point();

		currentLength = 4;

		direction = 0;

		fruit.x = 10;
		fruit.y = 10;

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tick();
		repaint();
	}

}
