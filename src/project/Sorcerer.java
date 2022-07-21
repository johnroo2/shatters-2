
package project;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Sorcerer{
	double x;
	double y; 
	int spd;
	
	//cooldowns
	int[] motion;
	int[] fire;
	int animate;
	
	BufferedImage[] imgs;
	BufferedImage img;
	
	//face direction
	int dir;
	
	double[] health;
	//mod health, atk, default is 1, 1
	double[] multiplier = new double[] {1, 1};
	int dmg[];
	
	//0: armorbreak, 1: burn, 2: darkness, 3: sicken, 4: slow, 5: curse
	int status[];
	
	ArrayList<Catalyst> catashots;
	ArrayList<Pop_Text> poptext;
	
	public Sorcerer(int x, int y) {
		this.x = x;
		this.y = y;
		this.spd = 5;
		this.motion = new int[]{0, 2};
		this.fire = new int[] {0, 8};
		this.animate = 0;
		this.health = new double[] {1500 * multiplier[0], 1500 * multiplier[0]};
		this.dmg = new int[] {(int) (325 * multiplier[1]), (int) (385 * multiplier[1])};
		this.catashots = new ArrayList<Catalyst>();
		this.poptext = new ArrayList<Pop_Text>();
		this.status = new int[] {0, 0, 0, 0, 0, 0};
		try {
			this.imgs = new BufferedImage[]{
					ImageIO.read(new File("sorc\\sorcb1.png")),ImageIO.read(new File("sorc\\sorcb2.png")),ImageIO.read(new File("sorc\\sorcb3.png")),
					ImageIO.read(new File("sorc\\sorcb4.png")),ImageIO.read(new File("sorc\\sorcb5.png")),ImageIO.read(new File("sorc\\sorcl1.png")),
					ImageIO.read(new File("sorc\\sorcl2.png")),ImageIO.read(new File("sorc\\sorcl1.png")),ImageIO.read(new File("sorc\\sorcl2.png")),
					ImageIO.read(new File("sorc\\sorcl3.png")),ImageIO.read(new File("sorc\\sorcf1.png")),ImageIO.read(new File("sorc\\sorcf2.png")),
					ImageIO.read(new File("sorc\\sorcf3.png")),ImageIO.read(new File("sorc\\sorcf4.png")),ImageIO.read(new File("sorc\\sorcf5.png")),
					ImageIO.read(new File("sorc\\sorcr1.png")),ImageIO.read(new File("sorc\\sorcr2.png")),ImageIO.read(new File("sorc\\sorcr1.png")),
					ImageIO.read(new File("sorc\\sorcr2.png")),ImageIO.read(new File("sorc\\sorcr3.png"))};
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
		this.img = this.imgs[0];
		this.dir = 0;
	}
	
	//movement
	public void move(boolean[] keys) {
        if (this.motion[0] > 0) {
            this.motion[0] -= 1;
        }
        
        if(keys[0] || keys[1] || keys[2] || keys[3]) {
	        if (this.motion[0] == 0) {
	        	this.motion[0] = this.motion[1];
				boolean dg = false;
				
				//W
				if(keys[0]) {
					this.dir = 0;
					if(Main.gamestate.equals("carpet_close")) {
		                if (this.y > 20 && this.x + this.y > 425 && this.x - this.y < 1175) {
		                    if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }
		                    this.y -= this.spd;
		                }
					}
					else if(Main.gamestate.equals("carpet_open")) {
						if (dg == false) {
	                        dg = true;
	                    }
	                    else {
	                    	this.motion[0] += 1;
	                    }
	                    this.y -= this.spd;
	                    if(this.y < 1570) {
	                    	Main.gamestate = "carpet_close";
	                    	Main.frame = 0;
	                    }
					}
				}
				//A
				if(keys[1]) {
					this.dir = 1;
					if(Main.gamestate.equals("carpet_close")) {
		                if (this.x > 20 && this.x + this.y > 425 && this.y - this.x < 1175) {
		                    if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }
		                    this.x -= this.spd;
		                }
					}
					else if(Main.gamestate.equals("carpet_open")) {
						if(this.x > 650) {
							if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }
		                    this.x -= this.spd;
						}
					}
				}
				//S
				if(keys[2]) {
					this.dir = 2;
					if(Main.gamestate.equals("carpet_close")) {
		                if (this.y < 1570 && this.y - this.x < 1175 && this.x + this.y < 2775) {
		                    if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }                    
		                    this.y += this.spd;
		                }
					}
					else if(Main.gamestate.equals("carpet_open")) {
						if(this.y < 2370) {
							if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }                    
		                    this.y += this.spd;
						}
					}
				}
				//D
				if(keys[3]) {
					this.dir = 3;
					if(Main.gamestate.equals("carpet_close")) {
		                if (this.x < 1570 && this.x - this.y < 1175  && this.x + this.y < 2775) {
		                    if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }
		                    this.x += this.spd;
		                }
					}
					else if(Main.gamestate.equals("carpet_open")) {
						if(this.x < 940) {
							if (dg == false) {
		                        dg = true;
		                    }
		                    else {
		                    	this.motion[0] += 1;
		                    }
		                    this.x += this.spd;
						}
					}
				}
	        }
		}
	}
	
	//shooting
	public void shoot(boolean[] keys) {
		if (this.fire[0] > 0) {
            this.fire[0] -= 1;
        }
		
		if(this.fire[0] == 0) {
			if(keys[4] || keys[5]) {
				try {
	                double rot = Math.toDegrees(Math.atan((double)(400 - Main.mousecoords[1]) / (double)(400 - Main.mousecoords[0])));
	                if(400 < Main.mousecoords[0]) {
	                	this.makeshot(rot);
	                	if(rot <= -50) {
	                		this.dir = 0;
	                	}
	                	else if(rot >= 50) {
	                		this.dir = 2;
	                	}
	                	else {
	                		this.dir = 3;
	                	}
						this.fire[0] = this.fire[1];;
	                }
	                else {
	                	this.makeshot(rot+180);
	                	if(rot >= 50) {
	                		this.dir = 0;
	                	}
	                	else if(rot <= -50) {
	                		this.dir = 2;
	                	}
	                	else {
	                		this.dir = 1;
	                	}
						this.fire[0] = this.fire[1];;
	                }
	            }
	            catch(Exception e) {
	                if (400 < Main.mousecoords[1]) {
	                	this.makeshot(90);
	                	this.dir = 2;
						this.fire[0] = this.fire[1];;
	                }
	                else {
	                	this.makeshot(-90);
	                	this.dir = 0;
						this.fire[0] = this.fire[1];;
	                }
	            }
			}
		}
	}
	
	//status loop
	public void check_status() {
		//armor break
		if(this.status[0] > 0) {
			this.status[0] -= 1;
		}
		//burn
		if(this.status[1] > 0) {
			if(Main.frame % 4 == 0 && this.health[0] > 1) {
				this.health[0] -= 1;
				Main.write_list[8] += 1;
			}
			this.status[1] -= 1;
		}
		//darkness
		if(this.status[2] > 0) {
			this.status[2] -= 1;
		}
		//sicken
		if(this.status[3] > 0) {
			this.status[3] -= 1;
		}
		//slow
		if(this.status[4] > 0) {
			this.status[4] -= 1;
			this.spd = 2;
			if(this.status[4] == 0) {
				this.spd = 5;
			}
		}
		//curse
		if(this.status[5] > 0) {
			this.status[5] -= 1;
		}
	}
	
	public void damage(double dmg, int status, int duration) {
		//calculate curse after armor break
		double sum = dmg;
		if(this.status[0] > 0) {
			sum += 50;
		}
		if(this.status[5] > 0) {
			sum *= 1.25;
		}
		this.health[0] -= sum;
		Main.write_list[8] += sum;
		
		//Pop_Text q = new project.Pop_Text(this.x, this.y-10, String.valueOf(sum), new Color(255, 40, 40));
		
		if(status != -1) {
			if(this.status[status] == 0) {
				if(status == 0) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Exposed", new Color(255, 40, 40));
				}
				else if(status == 1) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Scorched", new Color(255, 40, 40));
				}
				else if(status == 2) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Darkness", new Color(255, 40, 40));
				}
				else if(status == 3) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Sick", new Color(255, 40, 40));
				}
				else if(status == 4) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Slowed", new Color(255, 40, 40));
				}
				else if(status == 5) {
					Pop_Text p = new project.Pop_Text(this.x, this.y-15, "Curse", new Color(255, 40, 40));
				}
			}
			if(this.status[status] < duration) {
				this.status[status] = duration;
			}
		}
	}
	
	//shooting
	public void makeshot(double ori) {
		if(Main.poggers) {
			Catalyst c1 = new project.Catalyst(this.x - 46, this.y - 32, ori, 3, 160);
		}
		else {
			Catalyst c1 = new project.Catalyst(this.x - 14, this.y, ori, 12, 40);
		}
	}
	
	//walking
	public void animate() {
		this.animate += 1;
		if(this.fire[0] > this.fire[1]/2) {
			this.img = this.imgs[5 * dir + 4];
		}
		else {
			if(this.motion[0] > 0) {
				for(int i = 0; i < 4; i++) {
					if(this.animate % 80 <= 20 * i) {
						this.img = this.imgs[5 * dir + i];
						break;
					}
				}
			}
			else {
				this.img = this.imgs[5 * dir];
			}		
		}
	}
}