package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire_Dagger {
	double x;
	double y;
	double init_x;
	double init_y;
	double ori;
	double mag;
	int lifetime;
	int init_life;
	int size;
	int amp;
	BufferedImage img;
	
	public Fire_Dagger(double x, double y, double ori, double mag, int lifetime, int amp) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		this.amp = amp;
		this.lifetime = lifetime;
		this.init_life = lifetime;
		this.size = (int)(24 * (0.207107 * Math.cos(Math.toRadians(4 * (ori+45)) + Math.PI) + 1.207107));
		Main.archmage.fire_daggers.add(this);
		try {
			this.img = Main.r.rotate(ImageIO.read(new File("projectiles\\firedagger.png")), ori+45);
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		}
	}
	
	public void move() {
		this.lifetime -= 1;
		int d = this.init_life - this.lifetime;
		double c = Math.cos(Math.toRadians(this.ori));
		double s = Math.sin(Math.toRadians(this.ori));
		this.x = this.init_x + d * mag * c - amp * Math.sin(d/4.0) * s;
		this.y = this.init_y + d * mag * s + amp * Math.sin(d/4.0) * c;
		if(this.lifetime == 0) {
			Main.archmage.fire_daggers.remove(this);
		}
	}
}
