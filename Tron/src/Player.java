import java.awt.Color;
import java.security.SecureRandom;

public class Player {
	private static final SecureRandom random = new SecureRandom();
	int x, y, dir;
	Color color;

	public Player() {
		this(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
	}

	public Player(Color color) {
		x = random.nextInt(Tron.WIDTH);
		y = random.nextInt(Tron.HEIGHT);
		this.color = color;
		dir = 3;
	}

	public void tick() {
		if (dir == 0)
			y += 1;
		if (dir == 1)
			x -= 1;
		if (dir == 2)
			x += 1;
		if (dir == 3)
			y -= 1;

		if (x >= Tron.WIDTH)
			x = 0;
		else if (x < 0)
			x = Tron.WIDTH - 1;

		if (y >= Tron.HEIGHT)
			y = 0;
		else if (y < 0)
			y = Tron.HEIGHT - 1;
	}

}
