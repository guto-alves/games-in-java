import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel {
	private final int M = 20;
	private final int N = 10;

	private int[][] fields = new int[M][N];

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

	public Game() {
		try {
			tiles = ImageIO.read(new File("./src/images/tiles.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < a.length; i++) {
			a[i] = new Point();
			b[i] = new Point();
		}

		setBackground(Color.BLACK);

		setPreferredSize(new Dimension(320, 480));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		int n = 3;
		for (int i = 0; i < 4; i++) {
			a[i].x = figures[n][i] % 2;
			a[i].y = figures[n][i] / 2;
		}

		g2d.drawImage(tiles, 0, 0, 18, 18, 0, 0, 18, 18, null);
	}

}
