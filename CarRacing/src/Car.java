
public class Car {
	float x, y, speed, angle;

	public Car() {
		speed = 2;
		angle = 0;
	}

	public void move() {
		x += Math.sin(angle) * speed;
		y -= Math.cos(angle) * speed;
	}

}
