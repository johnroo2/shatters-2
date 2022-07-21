package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blizzard {
	double x;
	double y;
	double spd = 0.22;
	double[] health;
	int gate;
	boolean vulnerable;
	String movement;
	BufferedImage[] imgs;
	BufferedImage img;
	
	public Blizzard() {
		this.x = 800;
		this.y = 800;
		this.gate = 0;
		this.movement = "hidden";
		this.vulnerable = false;
		this.health = new double[] {35000, 35000};
		try {
			this.imgs = new BufferedImage[] {ImageIO.read(new File("blizzard\\blizzard1.png")),
					ImageIO.read(new File("blizzard\\blizzard2.png"))};
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
				this.x += 1.5; 
			}
		}
		else if(this.movement.equals("middle_circle_l")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * 1 * Math.PI/180)-iy*Math.sin(spd * 1 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * 1 * Math.PI/180)+ix*Math.sin(spd * 1 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 400 * 400) {
				this.x += 3; 
			}
		}
		else if(this.movement.equals("outer_circle_l")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * 0.7 * Math.PI/180)-iy*Math.sin(spd * 0.7 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * 0.7 * Math.PI/180)+ix*Math.sin(spd * 0.7 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 650 * 650) {
				this.x += 4.5; 
			}
		}
		else if(this.movement.equals("inner_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -1.2 * Math.PI/180)-iy*Math.sin(spd * -1.2 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -1.2 * Math.PI/180)+ix*Math.sin(spd * -1.2 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 150 * 150) {
				this.x += 1.5; 
			}
		}
		else if(this.movement.equals("middle_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -1 * Math.PI/180)-iy*Math.sin(spd * -1 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -1 * Math.PI/180)+ix*Math.sin(spd * -1 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 400 * 400) {
				this.x += 3; 
			}
		}
		else if(this.movement.equals("outer_circle_r")) {
			double ix = this.x - 800;
			double iy = this.y - 800;
			this.x = ix*Math.cos(spd * -0.7 * Math.PI/180)-iy*Math.sin(spd * -0.7 * Math.PI/180) + 800;
			this.y = iy*Math.cos(spd * -0.7 * Math.PI/180)+ix*Math.sin(spd * -0.7 * Math.PI/180) + 800;
			if(ix * ix + iy * iy < 650 * 650) {
				this.x += 4.5; 
			}
		}
		else if(this.movement.equals("cross")) {
			double cross_spd = 2.25;
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
		for(int i = 0; i < 8; i++) {
			Ice_Twirl b = new project.Ice_Twirl(this.x, this.y, 45 * i + Main.frame * 0.5, 5, 200, "icebombs_90");
		}
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
		}
		catch(Exception e){
			
		}
	}
	
	public void diagonal_blast() {
		for(int i = 0; i < 4; i++) {
			Ice_Wave b = new project.Ice_Wave(this.x, this.y, 45 + 90 * i + Main.frame * 1.35, 8, 150);
		}
	}
	
	public void hex_blast() {
		for(int i = 0; i < 6; i++) {
			Ice_Bolt b = new project.Ice_Bolt(this.x, this.y, 60 * i + Main.frame * 0.5, 14, 100);
		}
	}
	
	public void x_blast() {
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
			Ice_Dagger b = new project.Ice_Dagger(this.x, this.y, rot - 20, 5, 250, 30);
			Ice_Dagger b1 = new project.Ice_Dagger(this.x, this.y, rot + 20, 5, 250, 30);
			Ice_Dagger b2 = new project.Ice_Dagger(this.x, this.y, rot + 160, 5, 250, 30);
			Ice_Dagger b3 = new project.Ice_Dagger(this.x, this.y, rot - 160, 5, 250, 30);
		}
		catch(Exception e){
			
		}
	}
	
	public void pulse_blast() {
		for(int i = 0; i < 10; i++) {
			Ice_Wave b = new project.Ice_Wave(this.x, this.y, 36 * i + Main.frame, 8, 150);
		}
	}
}
