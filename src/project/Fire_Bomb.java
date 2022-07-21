package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire_Bomb {
	double x;
	double y;
	int init_life;
	int size;
	int lifetime;
	BufferedImage img;
	
	public Fire_Bomb(double x, double y) {
		this.x = x;
		this.y = y;
		this.lifetime = 194;
		this.size = 60;
		Main.archmage.fire_bombs.add(this);
		this.img = Main.fb_imgs[0];
	}
	
	public void animate() {
		for(int i = 0; i < 13; i++) {
			if(this.lifetime == 156 - i * 12) {
				this.img = Main.fb_imgs[i];
			}
		}
		this.lifetime -= 1;
		if(this.lifetime == 0) {
			Main.archmage.fire_bombs.remove(this);
			for(int i = 0; i < 8; i++) {
				Fire_Bomb_Explosion b = new Fire_Bomb_Explosion(this.x-5, this.y-5, -90 + 45 * i, 8, 15, i);
			}
		}
	}
}
