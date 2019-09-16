import java.awt.Color;
import java.security.SecureRandom;

public class Player {
	private final SecureRandom random = new SecureRandom();
	int x, y, dir;
	Color color;

	public Player(Color color) {
		x = random.nextInt(Tron.WIDTH);
		x = random.nextInt(Tron.HEIGHT);
		this.color = color;
		dir = random.nextInt(4);

	}

	public void tick() {
		if (dir == 0)
			y += 1;
		else if (dir == 1)
			x -= 1;
		else if (dir == 1)
			x += 1;
		else if (dir == 1)
			y -= 1;

		if (x >= Tron.WIDTH)
			x = 0;

		if (x < 0)
			x = Tron.WIDTH - 1;

		if (y >= Tron.WIDTH)
			y = 0;

		if (y < 0)
			y = Tron.WIDTH - 1;
	}

}
