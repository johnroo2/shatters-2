package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ice_Star {
	double x;
	double y;
	double init_x;
	double init_y;
	double ori;
	double mag;
	String label;
	int lifetime;
	int size;
	BufferedImage img;
	
	public Ice_Star(double x, double y, double ori, double mag, int lifetime, String label) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		this.label = label;
		this.lifetime = lifetime;
		this.size = (int)(28 * (0.207107 * Math.cos(Math.toRadians(4 * ori) + Math.PI) + 1.207107));
		Main.archmage.ice_stars.add(this);
		try {
			this.img = Main.r.rotate(ImageIO.read(new File("projectiles\\icestar.png")), ori);
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		}
	}
	
	public void move() {
		this.lifetime -= 1;
		if(this.mag > 0) {
			this.mag -= 0.2;
			this.x += mag * Math.cos(Math.toRadians(ori));
			this.y += mag * Math.sin(Math.toRadians(ori));
		}
		else {
			this.mag = 0;
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(Math.toRadians(Double.valueOf(this.label.substring(10))))
					-iy*Math.sin(Math.toRadians(Double.valueOf(this.label.substring(10)))) + 800;
			this.y = iy*Math.cos(Math.toRadians(Double.valueOf(this.label.substring(10))))
					+ix*Math.sin(Math.toRadians(Double.valueOf(this.label.substring(10)))) + 800;
		}
		if(this.lifetime == 0) {
			Main.archmage.ice_stars.remove(this);
		}
	}
}
