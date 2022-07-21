package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Magi_Generator {
	int x;
	int y; 
	int element;
	boolean sealed;
	boolean vulnerable;
	double[] health;
	BufferedImage img;
	BufferedImage[] imgs;
	
	public Magi_Generator(int x, int y) {
		this.x = x;
		this.y = y;
		this.element = 0;
		this.sealed = false;
		this.vulnerable = false;
		this.health = new double[] {25000, 25000};
		try {
			this.imgs = new BufferedImage[] {ImageIO.read(new File("generator\\generator_normal0.png")),
					ImageIO.read(new File("generator\\generator_normal1.png")),
					ImageIO.read(new File("generator\\generator_normal2.png")),
					ImageIO.read(new File("generator\\generator_fire0.png")),
					ImageIO.read(new File("generator\\generator_fire1.png")),
					ImageIO.read(new File("generator\\generator_fire2.png")),
					ImageIO.read(new File("generator\\generator_ice0.png")),
					ImageIO.read(new File("generator\\generator_ice1.png")),
					ImageIO.read(new File("generator\\generator_ice2.png")),
					ImageIO.read(new File("generator\\generator_sfire0.png")),
					ImageIO.read(new File("generator\\generator_sfire1.png")),
					ImageIO.read(new File("generator\\generator_sfire2.png")),
					ImageIO.read(new File("generator\\generator_sice0.png")),
					ImageIO.read(new File("generator\\generator_sice1.png")),
					ImageIO.read(new File("generator\\generator_sice2.png"))};
			}
		catch(Exception e) {
			System.out.println("Image cannot be found!");
		}
		this.img = this.imgs[0];
	}
	
	public void animate() {
		if(Main.cd == 0) {
			if(this.element == 0) {
				if(Main.frame % 45 < 15) {
					this.img = this.imgs[0];
				}
				else if(Main.frame % 45 < 30) {
					this.img = this.imgs[1];
				}
				else if(Main.frame % 45 < 45) {
					this.img = this.imgs[2];
				}
			}
			else if(this.element == 1) {
				if(!this.sealed) {
					if(Main.frame % 45 < 15) {
						this.img = this.imgs[3];
					}
					else if(Main.frame % 45 < 30) {
						this.img = this.imgs[4];
					}
					else if(Main.frame % 45 < 45) {
						this.img = this.imgs[5];
					}
				}
				else {
					if(Main.frame % 45 < 15) {
						this.img = this.imgs[9];
					}
					else if(Main.frame % 45 < 30) {
						this.img = this.imgs[10];
					}
					else if(Main.frame % 45 < 45) {
						this.img = this.imgs[11];
					}
				}
			}
			else if(this.element == 2) {
				if(!this.sealed) {
					if(Main.frame % 45 < 15) {
						this.img = this.imgs[6];
					}
					else if(Main.frame % 45 < 30) {
						this.img = this.imgs[7];
					}
					else if(Main.frame % 45 < 45) {
						this.img = this.imgs[8];
					}
				}
				else {
					if(Main.frame % 45 < 15) {
						this.img = this.imgs[12];
					}
					else if(Main.frame % 45 < 30) {
						this.img = this.imgs[13];
					}
					else if(Main.frame % 45 < 45) {
						this.img = this.imgs[14];
					}
				}
			}
		}
		else {
			if(this.element == 0) {
				if(Main.cd % 45 < 15) {
					this.img = this.imgs[0];
				}
				else if(Main.cd % 45 < 30) {
					this.img = this.imgs[1];
				}
				else if(Main.cd % 45 < 45) {
					this.img = this.imgs[2];
				}
			}
			else if(this.element == 1) {
				if(!this.sealed) {
					if(Main.cd % 45 < 15) {
						this.img = this.imgs[3];
					}
					else if(Main.cd % 45 < 30) {
						this.img = this.imgs[4];
					}
					else if(Main.cd % 45 < 45) {
						this.img = this.imgs[5];
					}
				}
				else {
					if(Main.cd % 45 < 15) {
						this.img = this.imgs[9];
					}
					else if(Main.cd % 45 < 30) {
						this.img = this.imgs[10];
					}
					else if(Main.cd % 45 < 45) {
						this.img = this.imgs[11];
					}
				}
			}
			else if(this.element == 2) {
				if(!this.sealed) {
					if(Main.cd % 45 < 15) {
						this.img = this.imgs[6];
					}
					else if(Main.cd % 45 < 30) {
						this.img = this.imgs[7];
					}
					else if(Main.cd % 45 < 45) {
						this.img = this.imgs[8];
					}
				}
				else {
					if(Main.cd % 45 < 15) {
						this.img = this.imgs[12];
					}
					else if(Main.cd % 45 < 30) {
						this.img = this.imgs[13];
					}
					else if(Main.cd % 45 < 45) {
						this.img = this.imgs[14];
					}
				}
			}
		}
	}
	
	public void choose_element() {
		if(!this.sealed) {
			if(Math.random() < 0.5) {
				this.element = 1;
			}
			else {
				this.element = 2;
			}
		}
	}
}
