package project;

import java.awt.Color;

public class Pop_Text {
	
	double x;
	double y;
	double init_x;
	double init_y;
	double x_offset;
	int lifetime;
	int length;
	String text;
	Color c;
	
	public Pop_Text(double x, double y, String text, Color c) {
		this.x = x;
		this.y = y;
		this.init_x = x;
		this.init_y = y;
		this.x_offset = -0.5 + 1 * Math.random();
		this.text = text;
		this.lifetime = 0;
		this.length = text.length();
		this.c = c;
		Main.sorc.poptext.add(this);
	}
	
	public void move() {
		this.lifetime += 1;
		this.x = this.init_x + x_offset * lifetime - this.length * 9;
		this.y = this.init_y + 0.85 * (this.lifetime) * (this.lifetime - 21);
		if(this.lifetime == 11) {
			Main.sorc.poptext.remove(this);
		}
	}
}
