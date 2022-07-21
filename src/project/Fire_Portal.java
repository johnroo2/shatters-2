package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire_Portal {
	double x;
	double y;
	String label;
	BufferedImage[] imgs;
	BufferedImage img;
	
	public Fire_Portal(String label) {
		this.x = -1000;
		this.y = -1000;
		this.label = label;
		if(this.label.substring(0, 10).equals("firewalls_")) {
			this.x = 800 + 625 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(10))));
			this.y = 800 + 625 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(10))));
		}
		else if(this.label.substring(0, 11).equals("firevomits_")) {
			this.x = 800 + 400 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(11))));
			this.y = 800 + 400 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(11))));
		}
		else if(this.label.substring(0, 11).equals("firefinale_")) {
			this.x = 800 + 650 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(11))));
			this.y = 800 + 650 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(11))));
		}
		else if(this.label.substring(0, 12).equals("fireflowers_")) {
			this.x = 800 + 700 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(12))));
			this.y = 800 + 700 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(12))));
		}
		else if(this.label.substring(0, 13).equals("firepopcorns_")) {
			this.x = 800 + 700 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(13))));
			this.y = 800 + 700 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(13))));
		}
		try {
			this.imgs = new BufferedImage[] {ImageIO.read(new File("projectiles\\fireportal1.png")),
					ImageIO.read(new File("projectiles\\fireportal2.png")),
					ImageIO.read(new File("projectiles\\fireportal3.png"))};
			}
		catch(Exception e) {
			System.out.println("Image cannot be found!");
		}
		this.img = this.imgs[0];
		Main.archmage.fire_portals.add(this);
	}
	
	public void animate() {
		if(this.label.substring(0, 10).equals("firewalls_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(Math.PI/180)-iy*Math.sin(Math.PI/180) + 800;
				this.y = iy*Math.cos(Math.PI/180)+ix*Math.sin(Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 11).equals("firevomits_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(1.5 * Math.PI/180)-iy*Math.sin(1.5 * Math.PI/180) + 800;
				this.y = iy*Math.cos(1.5 * Math.PI/180)+ix*Math.sin(1.5 * Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 11).equals("firefinale_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(1.8 * Math.PI/180)-iy*Math.sin(1.8 * Math.PI/180) + 800;
				this.y = iy*Math.cos(1.8 * Math.PI/180)+ix*Math.sin(1.8 * Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 12).equals("fireflowers_")) {
			if(Main.frame % 5 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(Math.PI/180)-iy*Math.sin(Math.PI/180) + 800;
				this.y = iy*Math.cos(Math.PI/180)+ix*Math.sin(Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 13).equals("firepopcorns_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(2 * Math.PI/180)-iy*Math.sin(2 * Math.PI/180) + 800;
				this.y = iy*Math.cos(2 * Math.PI/180)+ix*Math.sin(2 * Math.PI/180) + 800;
			}
		}
		
		if(Main.frame % 30 < 10) {
			this.img = this.imgs[0];
		}
		else if(Main.frame % 30 < 20) {
			this.img = this.imgs[1];
		}
		else {
			this.img = this.imgs[2];
		}
	}
	
	public void inner_blast() {
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
			Fire_Wave b = new project.Fire_Wave(this.x, this.y, rot+125, 7, 150);
			Fire_Wave b2 = new project.Fire_Wave(this.x, this.y, rot-125, 7, 150);
			Fire_Wave b3 = new project.Fire_Wave(this.x, this.y, rot+55, 7, 150);
			Fire_Wave b4 = new project.Fire_Wave(this.x, this.y, rot-55, 7, 150);
			
			Fire_Bolt b5 = new project.Fire_Bolt(this.x, this.y, rot+125, 9, 150);
			Fire_Bolt b6 = new project.Fire_Bolt(this.x, this.y, rot-125, 9, 150);
			Fire_Bolt b7 = new project.Fire_Bolt(this.x, this.y, rot+55, 9, 150);
			Fire_Bolt b8 = new project.Fire_Bolt(this.x, this.y, rot-55, 9, 150);
		}
		catch(Exception e) {
			
		}
	}
}
