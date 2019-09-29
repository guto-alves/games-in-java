import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	private BufferedImage image15;
	private BufferedImage[] numbers = new BufferedImage[16];

	private int width = 64;

	private int[][] grid = new int[6][6];

	private int x, y;

	public Game(JFrame frame) {
		try {
			image15 = ImageIO.read(new File("./src/images/15.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				x = e.getX() / width + 1;
				y = e.getY() / width + 1;

				int dx = 0;
				int dy = 0;

				if (grid[x + 1][y] == 15) {
					dx = 1;
					dy = 0;
				} else if (grid[x][y + 1] == 15) {
					dx = 0;
					dy = 1;
				} else if (grid[x][y - 1] == 15) {
					dx = 0;
					dy = -1;
				} else if (grid[x - 1][y] == 15) {
					dx = -1;
					dy = 0;
				}

				int n = grid[x][y];
				grid[x][y] = 15;
				grid[x + dx][y + dy] = n;

				repaint();
			}
		});

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					startGame();
					repaint();
				}
			}
		});

		setBackground(Color.WHITE);

		startGame();
	}

	protected void startGame() {
		int n = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				numbers[n] = image15.getSubimage(i * width, j * width, width, width);
				grid[i + 1][j + 1] = n;
				n++;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int n = grid[i + 1][j + 1];
				g2d.drawImage(numbers[n], i * width, j * width, null);
			}
		}
	}

}
