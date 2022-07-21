package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire_Vortex {
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
	
	public Fire_Vortex(double x, double y, double ori, double mag, int lifetime) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		this.lifetime = lifetime;
		this.init_life = lifetime;
		this.size = (int)(60 * (0.207107 * Math.cos(Math.toRadians(4 * ori) + Math.PI) + 1.207107));
		Main.archmage.fire_vortexes.add(this);
		try {
			this.img = Main.r.rotate(ImageIO.read(new File("projectiles\\firevortex.png")), ori);
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
			Main.archmage.fire_vortexes.remove(this);
		}
	}
}
