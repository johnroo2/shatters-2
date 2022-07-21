package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blue_Shield {
	double x;
	double y;
	double init_x;
	double init_y;
	double ori;
	double mag;
	int lifetime;
	int init_life;
	int size;
	BufferedImage img;
	
	public Blue_Shield(double x, double y, double ori, double mag, int lifetime) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		this.lifetime = lifetime;
		this.init_life = lifetime;
		this.size = (int)(20 * (0.207107 * Math.cos(Math.toRadians(4 * (ori+45)) + Math.PI) + 1.207107));
		Main.archmage.blue_shields.add(this);
		try {
			this.img = Main.r.rotate(ImageIO.read(new File("projectiles\\blueshield.png")), ori+45);
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		}
	}
	
	public void move() {
		this.lifetime -= 1;
		int d = this.init_life - this.lifetime;
		this.x = this.init_x + d * mag * Math.cos(Math.toRadians(ori));
		this.y = this.init_y + d * mag * Math.sin(Math.toRadians(ori));
		if(this.lifetime == 0) {
			Main.archmage.blue_shields.remove(this);
		}
	}
}
