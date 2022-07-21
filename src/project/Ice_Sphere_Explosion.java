package project;

import java.awt.image.BufferedImage;

public class Ice_Sphere_Explosion {
	double x;
	double y;
	double init_x;
	double init_y;
	double ori;
	double mag;
	int lifetime;
	int init_life;
	int size;
	int label;
	BufferedImage img;
		
	public Ice_Sphere_Explosion(double x, double y, double ori, double mag, int lifetime, int label) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.ori = ori;
		this.mag = mag;
		this.label = (label+11)%12;
		this.lifetime = lifetime;
		this.init_life = lifetime;
		this.size = (int)(20 * (0.207107 * Math.cos(Math.toRadians(4 * (ori+45)) + Math.PI) + 1.207107));
		Main.archmage.ice_sphere_explosions.add(this);
		this.img = Main.ise_imgs[label * 6];
	}
	
	public void move() {
		this.lifetime -= 1;
		int d = this.init_life - this.lifetime;
		for(int i = 0; i < 6; i++) {
			if(d == 7 * i) {
				this.img = Main.ise_imgs[label * 6 + i];
			}
		}
		this.x = this.init_x + d * mag * Math.cos(Math.toRadians(ori));
		this.y = this.init_y + d * mag * Math.sin(Math.toRadians(ori));
		if(this.lifetime == 0) {
			Main.archmage.ice_sphere_explosions.remove(this);
		}
	}
}
