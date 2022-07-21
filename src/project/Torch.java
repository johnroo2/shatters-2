package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Torch {
	int x;
	int y;
	BufferedImage[] imgs = new BufferedImage[4];
	BufferedImage img;
	
	public Torch(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		this.imgs = new BufferedImage[] {ImageIO.read(new File("torch\\torch0000.png")),
				ImageIO.read(new File("torch\\torch0001.png")),
				ImageIO.read(new File("torch\\torch0002.png")),
				ImageIO.read(new File("torch\\torch0003.png"))};
		this.img = this.imgs[0];
	}
	
	public void animate() {
		this.img = this.imgs[(Main.frame % 60)/15];
	}
}
