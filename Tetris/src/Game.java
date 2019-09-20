import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel {

	private BufferedImage tiles;

	public Game() {
		try {
			tiles = ImageIO.read(new File("./src/images/tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setBackground(Color.BLACK);

		setPreferredSize(new Dimension(320, 480));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(tiles, 0, 0, null);
	}

}
