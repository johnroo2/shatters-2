package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Catalyst {
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
	
	public Catalyst(double x, double y, double ori, double mag, int lifetime) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		if(Main.poggers) {
			this.size = (int)(72 * (0.207107 * Math.cos(Math.toRadians(4 * (ori + 90)) + Math.PI) + 1.207107));
		}
		else {
			this.size = (int)(24 * (0.207107 * Math.cos(Math.toRadians(4 * (ori + 45)) + Math.PI) + 1.207107));
		}
		this.lifetime = lifetime;
		this.init_life = lifetime;
		
		Main.sorc.catashots.add(this);
		try {
			if(Main.poggers) {
				this.img = Main.r.rotate(ImageIO.read(new File("wand\\pag.png")), ori + 90);
			}
			else {
				this.img = Main.r.rotate(ImageIO.read(new File("wand\\lavender.png")), ori + 45);
			}
		}
		
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		}
	}
	
	public void move() {
		this.lifetime -= 1;
		int d = this.init_life - this.lifetime;
		double s = Math.sin(Math.toRadians(ori));
		double c = Math.cos(Math.toRadians(ori));
		/*this.x = this.init_x + d * mag * c - 20 * Math.sin(d/2.5) * s;
		this.y = this.init_y + d * mag * s + 20 * Math.sin(d/2.5) * c;*/
		this.x = this.init_x + c *  d * mag;
		this.y = this.init_y + s * d * mag;
		if(this.lifetime == 0) {
			Main.sorc.catashots.remove(this);
		}
	}
}
