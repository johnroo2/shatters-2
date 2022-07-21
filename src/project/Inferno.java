package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inferno {
	double x;
	double y;
	double spd = 0.25;
	int gate;
	String movement;
	double[] health;
	boolean vulnerable;
	BufferedImage[] imgs;
	BufferedImage img;
	
	public Inferno() {
		this.x = 800;
		this.y = 800;
		this.gate = 0;
		this.movement = "hidden";
		this.vulnerable = false;
		this.health = new double[] {30000, 30000};
		try {
			this.imgs = new BufferedImage[] {ImageIO.read(new File("inferno\\inferno1.png")),
					ImageIO.read(new File("inferno\\inferno2.png"))};
			}
		catch(Exception e) {
			System.out.println("Image cannot be found!");
		}
		this.img = this.imgs[0];
	}
	
	public void animate() {
		if(Main.frame % 40 < 20) {
			this.img = this.imgs[0];
		}
		else {
			this.img = this.imgs[1];
		}
	}
	
	public void move() {
		if(this.movement.equals("inner_circle_l")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * 1.2 * Math.PI/180)-iy*Math.sin(spd * 1.2 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * 1.2 * Math.PI/180)+ix*Math.sin(spd * 1.2 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 150 * 150) {
				this.x -= 2.5; 
			}
		}
		else if(this.movement.equals("middle_circle_l")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * 1 * Math.PI/180)-iy*Math.sin(spd * 1 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * 1 * Math.PI/180)+ix*Math.sin(spd * 1 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 400 * 400) {
				this.x -= 3; 
			}
		}
		else if(this.movement.equals("outer_circle_l")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * 0.7 * Math.PI/180)-iy*Math.sin(spd * 0.7 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * 0.7 * Math.PI/180)+ix*Math.sin(spd * 0.7 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 650 * 650) {
				this.x -= 4.5; 
			}
		}
		else if(this.movement.equals("inner_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -1.2 * Math.PI/180)-iy*Math.sin(spd * -1.2 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -1.2 * Math.PI/180)+ix*Math.sin(spd * -1.2 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 150 * 150) {
				this.x -= 2.5; 
			}
		}
		else if(this.movement.equals("middle_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -1 * Math.PI/180)-iy*Math.sin(spd * -1 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -1 * Math.PI/180)+ix*Math.sin(spd * -1 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 400 * 400) {
				this.x -= 3; 
			}
		}
		else if(this.movement.equals("outer_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -0.7 * Math.PI/180)-iy*Math.sin(spd * -0.7 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -0.7 * Math.PI/180)+ix*Math.sin(spd * -0.7 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 650 * 650) {
				this.x -= 4.5; 
			}
		}
		else if(this.movement.equals("cross")) {
			double cross_spd = 2.35;
			if(this.gate == 0) {
				this.y -= cross_spd;
				if(this.y < 100) {
					this.gate = 1;
				}
			}
			else if(this.gate == 1) {
				this.y += cross_spd;
				if(this.y > 800) {
					this.gate = 2;
				}
			}
			else if(this.gate == 2) {
				this.y += cross_spd;
				if(this.y > 1500) {
					this.gate = 3;
				}
			}
			else if(this.gate == 3) {
				this.y -= cross_spd;
				if(this.y < 800) {
					this.gate = 4;
				}
			}
			else if(this.gate == 4) {
				this.x -= cross_spd;
				if(this.x < 100) {
					this.gate = 5;
				}
			}
			else if(this.gate == 5) {
				this.x += cross_spd;
				if(this.x > 800) {
					this.gate = 6;
				}
			}
			else if(this.gate == 6) {
				this.x += cross_spd;
				if(this.x > 1500) {
					this.gate = 7;
				}
			}
			else if(this.gate == 7) {
				this.x -= cross_spd;
				if(this.x < 800) {
					this.gate = 0;
				}
			}
		}
	}
	
	public void inner_blast() {
		for(int i = 0; i < 12; i++) {
			Fire_Bolt b = new project.Fire_Bolt(this.x, this.y, 30 * i + Main.frame * 0.8, 7, 200);
		}
	}
	
	public void diagonal_blast() {
		for(int i = 0; i < 4; i++) {
			Fire_Wave b = new project.Fire_Wave(this.x, this.y, 45 + 90 * i + Main.frame * 1.2, 9, 150);
		}
	}
	
	public void hex_blast() {
		for(int i = 0; i < 6; i++) {
			Fire_Bolt b = new project.Fire_Bolt(this.x, this.y, 60 * i + Main.frame * 0.5, 14, 100);
		}
	}
	
	public void x_blast() {
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
			Fire_Dagger b = new project.Fire_Dagger(this.x, this.y, rot - 20, 5, 250, 30);
			Fire_Dagger b1 = new project.Fire_Dagger(this.x, this.y, rot + 20, 5, 250, 30);
			Fire_Dagger b2 = new project.Fire_Dagger(this.x, this.y, rot + 160, 5, 250, 30);
			Fire_Dagger b3 = new project.Fire_Dagger(this.x, this.y, rot - 160, 5, 250, 30);
		}
		catch(Exception e){
			
		}
	}
	
	public void pulse_blast() {
		for(int i = 0; i < 10; i++) {
			Fire_Wave b = new project.Fire_Wave(this.x, this.y, 36 * i + Main.frame, 8, 150);
		}
	}
}
