package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire_Finale_Wave {
	double x;
	double y;
	int lifetime;
	int turntime;
	int label;
	int turn;
	int stop;
	int use_index;
	int init_life;
	int size;
	int dir;
	double spd = 13.5;
	BufferedImage img;
	
	public Fire_Finale_Wave(int stop, int label, int dir) {
		this.x = 800;
		this.y = 800;
		this.lifetime = 4 * Math.abs(label);
		this.turntime = 0;
		this.label = label;
		this.stop = stop;
		this.turn = Math.abs(this.label);
		this.use_index = 0;
		this.dir = dir;
		if(this.label < 0) {
			this.turn = 18;
		}
		else {
			this.turn = 0;
		};
		this.size = (int) Main.ffw_sizes[this.turn];
		Main.archmage.fire_finale_waves.add(this);
		this.img = Main.ffw[this.turn];
	}
	
	public void move() {
		if(this.lifetime > 0) {
			this.lifetime -= 1;
			if(this.label < 0) {
				this.x -= 12;
			}
			else {
				this.x += 12;
			}
			if(this.lifetime == 0) {
				if(this.dir == 0) {
					this.turn = 16;
					this.img = Main.ffw[(this.turn) % 32];
					this.size = (int) Main.ffw_sizes[(this.turn) % 32];
				}
				else {
					this.turn = 0;
					this.img = Main.ffw[(this.turn) % 32];
					this.size = (int) Main.ffw_sizes[(this.turn) % 32];
				}
			}
		}
		else {
			this.turntime += 1;
			if(this.label > 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				if(this.dir == 0) {
					this.x = ix*Math.cos(spd/(double)Math.abs(label) * Math.PI/180)-iy*Math.sin(spd/(double)Math.abs(label) * Math.PI/180) + 800;
					this.y = iy*Math.cos(spd/(double)Math.abs(label) * Math.PI/180)+ix*Math.sin(spd/(double)Math.abs(label) * Math.PI/180) + 800;
				}
				else {
					this.x = ix*Math.cos(-spd/(double)Math.abs(label) * Math.PI/180)-iy*Math.sin(-spd/(double)Math.abs(label) * Math.PI/180) + 800;
					this.y = iy*Math.cos(-spd/(double)Math.abs(label) * Math.PI/180)+ix*Math.sin(-spd/(double)Math.abs(label) *Math.PI/180) + 800;
				}
				try {
					double rot = Math.toDegrees(Math.atan((this.y - 800)/(this.x - 800)));
					if(this.x > 800 && this.y >= 800) {
						this.use_index = (int) Math.floor(rot/10);
					}
					else if(this.x > 800 && this.y < 800) {
						this.use_index = 36 + (int) Math.floor(rot/10);
					}
					else if(this.x < 800) {
						this.use_index = 18 + (int) Math.floor(rot/10);
					}
				}
				catch(Exception e) {
					if(this.y > 800) {
						this.use_index = 9;
					}
					else {
						this.use_index = 27;
					}
				}
				if(this.dir == 0) {
					this.img = Main.ffw[(this.use_index+9)%36];
					this.size = (int) Main.ffw_sizes[(this.use_index+9)%36];
				}
				else {
					this.img = Main.ffw[(this.use_index+27)%36];
					this.size = (int) Main.ffw_sizes[(this.use_index+27)%36];
				}
				if(this.turntime >= this.stop) {
					Main.archmage.fire_finale_waves.remove(this);
				}
			}
			else {
				double ix = this.x - 800;
				double iy = this.y - 800;
				if(this.dir != 0) {
					this.x = ix*Math.cos(spd/(double)Math.abs(label) * Math.PI/180)-iy*Math.sin(spd/(double)Math.abs(label) * Math.PI/180) + 800;
					this.y = iy*Math.cos(spd/(double)Math.abs(label) * Math.PI/180)+ix*Math.sin(spd/(double)Math.abs(label) * Math.PI/180) + 800;
				}
				else {
					this.x = ix*Math.cos(-spd/(double)Math.abs(label) * Math.PI/180)-iy*Math.sin(-spd/(double)Math.abs(label) * Math.PI/180) + 800;
					this.y = iy*Math.cos(-spd/(double)Math.abs(label) * Math.PI/180)+ix*Math.sin(-spd/(double)Math.abs(label) *Math.PI/180) + 800;
				}
				try {
					double rot = Math.toDegrees(Math.atan((this.y - 800)/(this.x - 800)));
					if(this.x > 800 && this.y >= 800) {
						this.use_index = (int) Math.floor(rot/10);
					}
					else if(this.x > 800 && this.y < 800) {
						this.use_index = 36 + (int) Math.floor(rot/10);
					}
					else if(this.x < 800) {
						this.use_index = 18 + (int) Math.floor(rot/10);
					}
				}
				catch(Exception e) {
					if(this.y > 800) {
						this.use_index = 9;
					}
					else {
						this.use_index = 27;
					}
				}
				if(this.dir == 0) {
					this.img = Main.ffw[(this.use_index+27)%36];
					this.size = (int) Main.ffw_sizes[(this.use_index+27)%36];
				}
				else {
					this.img = Main.ffw[(this.use_index+9)%36];
					this.size = (int) Main.ffw_sizes[(this.use_index+9)%36];
				}
				if(this.turntime >= this.stop) {
					Main.archmage.fire_finale_waves.remove(this);
				}
			}
		}
	}
}
