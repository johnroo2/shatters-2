package project;

import java.awt.image.BufferedImage;

public class Ice_Sphere {
	double x;
	double y;
	int init_life;
	int size;
	int lifetime;
	BufferedImage img;
	
	public Ice_Sphere(double x, double y) {
		this.x = x;
		this.y = y;
		this.lifetime = 186;
		this.size = 60;
		Main.archmage.ice_spheres.add(this);
		this.img = Main.is_imgs[0];
	}
	
	public void animate() {
		for(int i = 0; i < 10; i++) {
			if(this.lifetime == 150 - i * 15) {
				this.img = Main.is_imgs[i];
			}
		}
		this.lifetime -= 1;
		if(this.lifetime == 0) {
			Main.archmage.ice_spheres.remove(this);
			for(int i = 0; i < 12; i++) {
				Ice_Sphere_Explosion b = new Ice_Sphere_Explosion(this.x-5, this.y-5, -90 + 30 * i, 10, 42, i);
			}
		}
	}
}
