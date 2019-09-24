import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class Tron extends JPanel implements ActionListener {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 480;

	private int speed = 4;

	private boolean[][] fields = new boolean[WIDTH][HEIGHT];

	private BufferedImage backgroundImage;

	private Player player1;
	private Player player2;

	private boolean game = true;

	private Color winnerColor;

	private Timer timer = new Timer(60, this);

	public Tron(JFrame frame) {
		try {
			backgroundImage = ImageIO.read(new File("./src/images/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		player1 = new Player(Color.RED);
		player2 = new Player(Color.GREEN);

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (player1.dir != 2)
						player1.dir = 1;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (player1.dir != 1)
						player1.dir = 2;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (player1.dir != 0)
						player1.dir = 3;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (player1.dir != 3)
						player1.dir = 0;
				}

				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (player2.dir != 2)
						player2.dir = 1;
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					if (player2.dir != 1)
						player2.dir = 2;
				} else if (e.getKeyCode() == KeyEvent.VK_W) {
					if (player2.dir != 0)
						player2.dir = 3;
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					if (player2.dir != 3)
						player2.dir = 0;
				}

			}
		});

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(backgroundImage, 0, 0, null);

		if (!game) {
			g2d.setFont(new Font("Serif", Font.PLAIN, 35));
			g2d.setColor(winnerColor);
			g2d.drawString("YOU WIN!", WIDTH / 2 - 80, 50);
			timer.stop();
		}

		for (int i = 0; i < player1.points.size(); i++) {
			g2d.setColor(player1.color);
			g2d.fillRect(player1.points.get(i).x, player1.points.get(i).y, 3, 3);

			g2d.setColor(player2.color);
			g2d.fillRect(player2.points.get(i).x, player2.points.get(i).y, 3, 3);
		}

		for (int i = 0; i < speed; i++) {
			player1.tick();
			player2.tick();

			if (fields[player1.x][player1.y] == true) {
				winnerColor = player2.color;
				game = false;
			}

			if (fields[player2.x][player2.y] == true) {
				winnerColor = player1.color;
				game = false;
			}

			fields[player1.x][player1.y] = true;
			fields[player2.x][player2.y] = true;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
