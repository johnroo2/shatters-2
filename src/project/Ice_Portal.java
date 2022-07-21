package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ice_Portal {
	double x;
	double y;
	String label;
	BufferedImage[] imgs;
	BufferedImage img;
	
	public Ice_Portal(String label) {
		this.x = -1000;
		this.y = -1000;
		this.label = label;
		if(this.label.substring(0, 9).equals("icebombs_")) {
			this.x = 800 + 600 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(9))));
			this.y = 800 + 600 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(9))));
		}
		else if(this.label.substring(0, 10).equals("icearenas_")) {
			this.x = 800 + 725 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(10))));
			this.y = 800 + 725 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(10))));
		}
		else if(this.label.substring(0, 10).equals("icefinale_")) {
			this.x = 800 + 720 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(10))));
			this.y = 800 + 720 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(10))));
		}
		else if(this.label.substring(0, 11).equals("iceportals_")) {
			this.x = 800;
			this.y = 800;
		}
		else if(this.label.substring(0, 11).equals("icerotates_")) {
			int xm = 0;
			int ym = 0;
			if(this.label.charAt(11) == 'u') {
				ym = -1;
			}
			if(this.label.charAt(11) == 'd') {
				ym = 1;
			}
			if(this.label.charAt(11) == 'l') {
				xm = -1;
			}
			if(this.label.charAt(11) == 'r') {
				xm = 1;
			}
			this.x = 800 + xm * Integer.valueOf(this.label.substring(13));
			this.y = 800 + ym * Integer.valueOf(this.label.substring(13));
		}
		try {
			this.imgs = new BufferedImage[] {ImageIO.read(new File("projectiles\\iceportal1.png")),
					ImageIO.read(new File("projectiles\\iceportal2.png")),
					ImageIO.read(new File("projectiles\\iceportal3.png"))};
			}
		catch(Exception e) {
			System.out.println("Image cannot be found!");
		}
		this.img = this.imgs[0];
		Main.archmage.ice_portals.add(this);
	}
	
	public void animate() {
		if(this.label.substring(0, 9).equals("icebombs_")) {
			if(Main.frame % 4 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(Math.PI/180)-iy*Math.sin(Math.PI/180) + 800;
				this.y = iy*Math.cos(Math.PI/180)+ix*Math.sin(Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 10).equals("icearenas_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(Math.PI/180)-iy*Math.sin(Math.PI/180) + 800;
				this.y = iy*Math.cos(Math.PI/180)+ix*Math.sin(Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 10).equals("icefinale_")) {
			if(Main.frame % 3 == 0) {
				double ix = this.x - 800;
				double iy = this.y - 800;
				this.x = ix*Math.cos(Math.PI/180)-iy*Math.sin(Math.PI/180) + 800;
				this.y = iy*Math.cos(Math.PI/180)+ix*Math.sin(Math.PI/180) + 800;
			}
		}
		else if(this.label.substring(0, 11).equals("iceportals_")) {
			if(Main.frame % 3 == 0) {
				this.x += 4 * Math.cos(Math.toRadians(Integer.valueOf(this.label.substring(11))));
				this.y += 4 * Math.sin(Math.toRadians(Integer.valueOf(this.label.substring(11))));
			}
			if((this.x - 800)*(this.x - 800) + (this.y - 800) * (this.y - 800) > 700 * 700) {
				Main.archmage.ice_portals.remove(this);
			}
		}
		else if(this.label.substring(0, 11).equals("icerotates_")) {
			if(Main.frame % 3 == 0) {
				if(this.label.charAt(12) == 'r') {
					double ix = this.x - 800;
					double iy = this.y - 800;
					double rspd = 5/Double.valueOf(this.label.substring(13));
					this.x = ix*Math.cos(rspd)-iy*Math.sin(rspd) + 800;
					this.y = iy*Math.cos(rspd)+ix*Math.sin(rspd) + 800;
					
				}
				if(this.label.charAt(12) == 'l') {
					double ix = this.x - 800;
					double iy = this.y - 800;
					double rspd = -5/Double.valueOf(this.label.substring(13));
					this.x = ix*Math.cos(rspd)-iy*Math.sin(rspd) + 800;
					this.y = iy*Math.cos(rspd)+ix*Math.sin(rspd) + 800;
					
				}
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
	
	public void cardinal_blast() {
		Ice_Wave b = new project.Ice_Wave(this.x, this.y - 40, -90, 7, 150);
		Ice_Wave b2 = new project.Ice_Wave(this.x - 30, this.y - 20, -90, 7, 150);
		Ice_Wave b3 = new project.Ice_Wave(this.x + 30, this.y - 20, -90, 7, 150);
		Ice_Wave b4 = new project.Ice_Wave(this.x, this.y + 40, 90, 7, 150);
		Ice_Wave b5 = new project.Ice_Wave(this.x - 30, this.y + 20, 90, 7, 150);
		Ice_Wave b6 = new project.Ice_Wave(this.x + 30, this.y + 20, 90, 7, 150);
		Ice_Wave b7 = new project.Ice_Wave(this.x - 40, this.y, 180, 7, 150);
		Ice_Wave b8 = new project.Ice_Wave(this.x - 20, this.y - 30, 180, 7, 150);
		Ice_Wave b9 = new project.Ice_Wave(this.x - 20, this.y + 30, 180, 7, 150);
		Ice_Wave b10 = new project.Ice_Wave(this.x + 40, this.y, 0, 7, 150);
		Ice_Wave b11 = new project.Ice_Wave(this.x + 20, this.y - 30, 0, 7, 150);
		Ice_Wave b12 = new project.Ice_Wave(this.x + 20, this.y + 30, 0, 7, 150);
	}
	
	public void diagonal_blast() {
		Ice_Wave b = new project.Ice_Wave(this.x - 30, this.y - 30, -135, 7, 150);
		Ice_Wave b2 = new project.Ice_Wave(this.x + 5, this.y - 35, -135, 7, 150);
		Ice_Wave b3 = new project.Ice_Wave(this.x - 35, this.y + 5, -135, 7, 150);
		Ice_Wave b4 = new project.Ice_Wave(this.x + 30, this.y + 30, 45, 7, 150);
		Ice_Wave b5 = new project.Ice_Wave(this.x - 5, this.y + 35, 45, 7, 150);
		Ice_Wave b6 = new project.Ice_Wave(this.x + 35, this.y - 5, 45, 7, 150);		
		Ice_Wave b7 = new project.Ice_Wave(this.x + 30, this.y - 30, -45, 7, 150);
		Ice_Wave b8 = new project.Ice_Wave(this.x - 5, this.y - 35, -45, 7, 150);
		Ice_Wave b9 = new project.Ice_Wave(this.x + 35, this.y + 5, -45, 7, 150);
		Ice_Wave b10 = new project.Ice_Wave(this.x - 30, this.y + 30, 135, 7, 150);
		Ice_Wave b11 = new project.Ice_Wave(this.x + 5, this.y + 35, 135, 7, 150);
		Ice_Wave b12 = new project.Ice_Wave(this.x - 35, this.y - 5, 135, 7, 150);
	}
	
	public void flank_blast() {
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
			Ice_Wave b = new project.Ice_Wave(this.x, this.y, rot+105, 5, 250);
			Ice_Wave b2 = new project.Ice_Wave(this.x, this.y, rot-105, 5, 250);
			Ice_Wave b3 = new project.Ice_Wave(this.x, this.y, rot+75, 5, 250);
			Ice_Wave b4 = new project.Ice_Wave(this.x, this.y, rot-75, 5, 250);
			
			Ice_Bolt b5 = new project.Ice_Bolt(this.x, this.y, rot+105, 7, 250);
			Ice_Bolt b6 = new project.Ice_Bolt(this.x, this.y, rot-105, 7, 250);
			Ice_Bolt b7 = new project.Ice_Bolt(this.x, this.y, rot+75, 7, 250);
			Ice_Bolt b8 = new project.Ice_Bolt(this.x, this.y, rot-75, 7, 250);
		}
		catch(Exception e) {
			
		}
	}
	
	public void pulse_blast() {
		try {
			double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
			Ice_Wave b = new project.Ice_Wave(this.x, this.y, rot+45, 5, 250);
			Ice_Wave b2 = new project.Ice_Wave(this.x, this.y, rot-45, 5, 250);
			Ice_Wave b3 = new project.Ice_Wave(this.x, this.y, rot+135, 5, 250);
			Ice_Wave b4 = new project.Ice_Wave(this.x, this.y, rot-135, 5, 250);
			
			Ice_Bolt b5 = new project.Ice_Bolt(this.x, this.y, rot+45, 7, 250);
			Ice_Bolt b6 = new project.Ice_Bolt(this.x, this.y, rot-45, 7, 250);
			Ice_Bolt b7 = new project.Ice_Bolt(this.x, this.y, rot+135, 7, 250);
			Ice_Bolt b8 = new project.Ice_Bolt(this.x, this.y, rot-135, 7, 250);
		}
		catch(Exception e) {
			
		}
	}
	
	public void outer_blast() {
		for(int i = 0; i < 18; i++) {
			Ice_Bolt b = new project.Ice_Bolt(this.x, this.y, 20 * i, 4, 50);
		}
	}
	
	public void square_blast() {
		try {
			for(int i = 0; i < 2; i++) {
				double rot = Math.toDegrees(Math.atan((this.y-800)/(this.x-800)));
				double r = 40 * Math.random();
				if(i % 2 == 0) {
					Ice_Twirl b = new project.Ice_Twirl(this.x - 20, this.y - 20, 180 * i + rot + r, 4, 120, "icebombs_30");
					Ice_Twirl b1 = new project.Ice_Twirl(this.x - 20, this.y + 20, 180 * i + rot + r, 4, 120, "icebombs_30");
					Ice_Twirl b2 = new project.Ice_Twirl(this.x + 20, this.y - 20, 180 * i + rot + r, 4, 120, "icebombs_30");
					Ice_Twirl b3 = new project.Ice_Twirl(this.x + 20, this.y + 20, 180 * i + rot + r, 4, 120, "icebombs_30");
				}
				else {
					Ice_Twirl b = new project.Ice_Twirl(this.x - 20, this.y - 20, 180 * i + rot + r, 4, 120, "icebombs_-30");
					Ice_Twirl b1 = new project.Ice_Twirl(this.x - 20, this.y + 20, 180 * i + rot + r, 4, 120, "icebombs_-30");
					Ice_Twirl b2 = new project.Ice_Twirl(this.x + 20, this.y - 20, 180 * i + rot + r, 4, 120, "icebombs_-30");
					Ice_Twirl b3 = new project.Ice_Twirl(this.x + 20, this.y + 20, 180 * i + rot + r, 4, 120, "icebombs_-30");
				}
			}
		}
		catch(Exception e) {
			
		}
	}
}
