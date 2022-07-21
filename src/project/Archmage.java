package project;
import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Archmage {
	double x;
	double y;
	
	//popcorns and arenas
	double chain;
	int chaincount;
	
	//firewall direction
	int fwdir;
	
	String action;
	int ticks;
	
	//staggers
	double stagger_cap;
	
	//flash animation
	int flash;
	
	boolean vulnerable;
	double[] health;
	
	//attack name
	String attack;
	
	//phase number
	int phase;
	
	BufferedImage[] imgs = new BufferedImage[44];
	BufferedImage img;
	
	//oh brother!! :rolling_eyes:
	ArrayList<Ice_Twirl> ice_twirls;
	ArrayList<Ice_Vortex> ice_vortexes;
	ArrayList<Fire_Twirl> fire_twirls;
	ArrayList<Fire_Vortex> fire_vortexes;
	ArrayList<Ice_Wave> ice_waves;
	ArrayList<Fire_Wave> fire_waves;
	ArrayList<Blue_Ball> blue_balls;
	ArrayList<Fire_Bolt> fire_bolts;
	ArrayList<Ice_Bolt> ice_bolts;
	ArrayList<Fire_Portal> fire_portals;
	ArrayList<Ice_Portal> ice_portals;
	ArrayList<Ice_Star> ice_stars;
	ArrayList<Pink_Ball> pink_balls;
	ArrayList<Ice_Shield> ice_shields;
	ArrayList<Ice_Spear> ice_spears;
	ArrayList<Fire_Bomb> fire_bombs;
	ArrayList<Fire_Bomb_Explosion> fire_bomb_explosions;
	ArrayList<Ice_Sphere> ice_spheres;
	ArrayList<Ice_Sphere_Explosion> ice_sphere_explosions;
	ArrayList<Fire_Finale_Wave> fire_finale_waves;
	ArrayList<Pink_Shield> pink_shields;
	ArrayList<Blue_Shield> blue_shields;
	ArrayList<Ice_Dagger> ice_daggers;
	ArrayList<Fire_Dagger> fire_daggers;
	
	ArrayList<String> taunts; 
	
	public Archmage() {
		this.x = 800;
		this.y = 800;
		this.chain = 0;
		this.chaincount = 0;
		if(Math.random() < 0.5) {
			this.fwdir = 0;
		}
		else {
			this.fwdir = 1;
		}
		this.action = "";
		this.ticks = 0;
		this.vulnerable = false;
		this.phase = 0;
		this.stagger_cap = 0;
		this.health = new double[] {375000, 375000};
		
		this.flash = 0;
		
		//oh spell nah! :cow:
		this.ice_twirls = new ArrayList<Ice_Twirl>();
		this.fire_twirls = new ArrayList<Fire_Twirl>();
		this.ice_vortexes = new ArrayList<Ice_Vortex>();
		this.fire_vortexes = new ArrayList<Fire_Vortex>();
		this.ice_waves = new ArrayList<Ice_Wave>();
		this.fire_waves = new ArrayList<Fire_Wave>();
		this.blue_balls = new ArrayList<Blue_Ball>();
		this.fire_bolts = new ArrayList<Fire_Bolt>();
		this.ice_bolts = new ArrayList<Ice_Bolt>();
		this.ice_stars = new ArrayList<Ice_Star>();
		this.pink_balls = new ArrayList<Pink_Ball>();
		this.fire_portals = new ArrayList<Fire_Portal>();
		this.ice_portals = new ArrayList<Ice_Portal>();
		this.ice_shields = new ArrayList<Ice_Shield>();
		this.ice_spears = new ArrayList<Ice_Spear>();
		this.fire_bombs = new ArrayList<Fire_Bomb>();
		this.fire_bomb_explosions = new ArrayList<Fire_Bomb_Explosion>();
		this.ice_spheres = new ArrayList<Ice_Sphere>();
		this.ice_sphere_explosions = new ArrayList<Ice_Sphere_Explosion>();
		this.fire_finale_waves = new ArrayList<Fire_Finale_Wave>();
		this.pink_shields = new ArrayList<Pink_Shield>();
		this.blue_shields = new ArrayList<Blue_Shield>();
		this.ice_daggers = new ArrayList<Ice_Dagger>();
		this.fire_daggers = new ArrayList<Fire_Dagger>();
		this.attack = "";
		
		this.taunts = new ArrayList<String>();
		
		try {
			//0: Default
			//1-4 Default Channel
			//5-8 Ice Channel
			//9-12 Fire Channel
			//13-16 Fire Nuke
			//17-20 Ice Nuke
			//21-24 Sicken Channel
			//25-32 Finale Animation
			//33-36 Finale Hover
			//37-40 Stagger
			//41-43 Flashes

			for(int i = 0; i < 44; i++) {
				imgs[i] = ImageIO.read(new File("archmage\\archmage"+String.format("%04d", i)+".png"));
			}
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		}
		this.img = this.imgs[0];
	}
	
	public void phase() {
		double pc = this.health[0]/this.health[1];
		//phase 0: no birds
		if(this.phase == 0 && pc <= 0.7) {
			this.health[0] = 0.7 * this.health[1];
			this.phase = 1;
			Main.reset();
			this.vulnerable = false;
			this.taunts.add("Twilight Archmage: Oooh, admirable indeed! You’ve put up more fight than those pompous lords ever did.");
		}
		
		//phase 1: first sicken
		else if(this.phase == 1 && this.sealed_gens() == 1) {
			Main.reset();
			this.phase = 2;
			for(int i = 0; i < Main.gens.length; i++) {
				if(!Main.gens[i].sealed) {
					Main.gens[i].vulnerable = false;
					Main.gens[i].health[0] = Main.gens[i].health[1];
				}
			}
			Main.inferno.health[0] = Main.inferno.health[1];
			Main.blizzard.health[0] = Main.blizzard.health[1];
			this.taunts.add("Twilight Archmage: You… you impetuous oaf, you sealed the generator! Fine then, let us do this the slow way…");
		}
		
		//phase 2: one bird
		else if(this.phase == 2 && pc <= 0.4 && this.health[0] > this.stagger_cap) {
			this.flash = 35;
			this.health[0] = 0.4 * this.health[1];
			this.phase = 3;
			Main.reset();
			this.vulnerable = false;
			this.taunts.add("Twilight Archmage: Hmph, you would have made a more competent force than the royal guard!");
		}
		
		//phase 3: second sicken
		else if(this.phase == 3 && this.sealed_gens() == 2) {
			Main.reset();
			this.phase = 4;
			for(int i = 0; i < Main.gens.length; i++) {
				if(!Main.gens[i].sealed) {
					Main.gens[i].vulnerable = false;
					Main.gens[i].health[0] = Main.gens[i].health[1];
				}
			}
			Main.inferno.health[0] = Main.inferno.health[1];
			Main.blizzard.health[0] = Main.blizzard.health[1];
			this.taunts.add("Twilight Archmage: You really are dragging this out, you know. So much for making it painless! Eheheeeeh!");
		}
		
		//phase 4: two birds
		else if(this.phase == 4 && pc < 0.1  && this.health[0] > this.stagger_cap) {
			this.flash = 35;
			this.health[0] = 0.1 * this.health[1];
			this.phase = 5;
			Main.reset();
			this.vulnerable = false;
			this.taunts.add("Twilight Archmage: You have far overstayed your welcome! My master can wait no longer…");
		}
		
		//phase 5: third sicken
		else if(this.phase == 5 && this.sealed_gens() == 3) {
			Main.reset();
			this.phase = 6;
			if(Main.g1.element + Main.g2.element + Main.g3.element <= 4) {
				this.attack = "firefinale_prep";
			}
			else if(Main.g1.element + Main.g2.element + Main.g3.element >= 5) {
				this.attack = "icefinale_prep";
			}
			for(int i = 0; i < Main.gens.length; i++) {
				if(!Main.gens[i].sealed) {
					Main.gens[i].vulnerable = false;
					Main.gens[i].health[0] = Main.gens[i].health[1];
				}
			}
			Main.am_state = 4;
			Main.inferno.health[0] = Main.inferno.health[1];
			Main.blizzard.health[0] = Main.blizzard.health[1];
			this.taunts.add("Twilight Archmage: NO!");
		}
		
		//phase 6: finale
		else if(this.phase == 6) {
			if(pc <= 0) {
				this.flash = 35;
				Main.reset();
				this.vulnerable = true;
				this.taunts.add("Twilight Archmage: Im... Impossible...");
				Main.am_state = 5;
				this.attack = "death";
				this.phase = 7;
			}
		}
		
		//phase 7: death
	}
	
	//returns the number of sealed generators
	public int sealed_gens() {
		int count = 0;
		for(int i = 0; i < Main.gens.length; i++) {
			if(Main.gens[i].sealed) {
				count++;
			}
		}
		return count;
	}
	
	//animations
	public void d_channel() {
		if(!this.action.equals("d_channel")) {
			this.ticks = 0;
			this.action = "d_channel";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[1];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[2];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[3];
			}
			else {
				this.img = this.imgs[4];
			}
		}
	}
	
	public void i_channel() {
		if(!this.action.equals("i_channel")) {
			this.ticks = 0;
			this.action = "i_channel";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[5];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[6];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[7];
			}
			else {
				this.img = this.imgs[8];
			}
		}
	}
	
	public void f_channel() {
		if(!this.action.equals("f_channel")) {
			this.ticks = 0;
			this.action = "f_channel";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[9];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[10];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[11];
			}
			else {
				this.img = this.imgs[12];
			}
		}
	}
	
	public void s_channel() {
		if(!this.action.equals("s_channel")) {
			this.ticks = 0;
			this.action = "s_channel";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[21];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[22];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[23];
			}
			else {
				this.img = this.imgs[24];
			}
		}
	}
	
	public void f_nuke() {
		if(!this.action.equals("f_nuke")) {
			this.ticks = 0;
			this.action = "f_nuke";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[13];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[14];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[15];
			}
			else {
				this.img = this.imgs[16];
			}
		}
	}

	public void i_nuke() {
		if(!this.action.equals("i_nuke")) {
			this.ticks = 0;
			this.action = "i_nuke";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[17];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[18];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[19];
			}
			else {
				this.img = this.imgs[20];
			}
		}
	}
	
	public void stagger() {
		if(!this.action.equals("stagger")) {
			this.ticks = 0;
			this.action = "stagger";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[37];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[38];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[39];
			}
			else {
				this.img = this.imgs[40];
			}
		}
	}
	
	public void finale_hover() {
		if(!this.action.equals("finale_hover")) {
			this.ticks = 0;
			this.action = "finale_hover";
		}
		if(this.ticks == 0) {
			this.ticks = 60;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 45) {
				this.img = this.imgs[33];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[34];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[35];
			}
			else {
				this.img = this.imgs[36];
			}
		}
	}
	
	public void finale_animation() {
		if(!this.action.equals("finale_animation")) {
			this.ticks = 0;
			this.action = "finale_animation";
		}
		if(this.ticks == 0) {
			this.ticks = 120;
		}
		else {
			this.ticks -= 1;
			if(this.ticks > 105) {
				this.img = this.imgs[25];
			}
			else if(this.ticks > 90) {
				this.img = this.imgs[26];
			}
			else if(this.ticks > 75) {
				this.img = this.imgs[27];
			}
			else if(this.ticks > 60) {
				this.img = this.imgs[28];
			}
			else if(this.ticks > 45) {
				this.img = this.imgs[29];
			}
			else if(this.ticks > 30) {
				this.img = this.imgs[30];
			}
			else if(this.ticks > 15) {
				this.img = this.imgs[31];
			}
			else {
				this.img = this.imgs[32];
			}
		}
	}
	
	//clears portals
	//(and stars)
	public void clear_portals() {
		int fs = this.fire_portals.size();
		int is = this.ice_portals.size();
		int ss = this.ice_stars.size();
		for(int i = 0; i < fs; i++) {
			this.fire_portals.remove(0);
		}
		for(int i = 0; i < is; i++) {
			this.ice_portals.remove(0);
		}
		for(int i = 0; i < ss; i++) {
			this.ice_stars.remove(0);
		}
	}
	
	//animation
	public void main() {
		if(this.ticks > 0) {
			if(this.action.equals("d_channel")) {
				this.d_channel();
			}
			else if(this.action.equals("i_channel")) {
				this.i_channel();
			}
			else if(this.action.equals("f_channel")) {
				this.f_channel();
			}
			else if(this.action.equals("s_channel")) {
				this.s_channel();
			}
			else if(this.action.equals("f_nuke")) {
				this.f_nuke();
			}
			else if(this.action.equals("i_nuke")) {
				this.i_nuke();
			}
			else if(this.action.equals("stagger")) {
				this.stagger();
			}
			else if(this.action.equals("finale_hover")) {
				this.finale_hover();
			}
			else if(this.action.equals("finale_animation")) {
				this.finale_animation();
			}
		}
		else {
			this.img = this.imgs[0];
		}
		if(this.flash >= 0) {
			if(this.flash < 7) {
				this.img = this.imgs[43];
			}
			else if(this.flash < 14) {
				this.img = this.imgs[38];
			}
			else if(this.flash < 21) {
				this.img = this.imgs[43];
			}
			else if(this.flash < 28) {
				this.img = this.imgs[38];
			}
			else if(this.flash < 35) {
				this.img = this.imgs[43];
			}
			else if(this.flash < 42) {
				this.img = this.imgs[42];
			}
			else if(this.flash < 49) {
				this.img = this.imgs[41];
			}
			this.flash -= 1;
		}
	}
	
	//attacks
	public void fireblasts() {
		if(Main.frame % 60 == 0) {
			this.f_channel();
		}
		
		if(Main.frame % 15 == 0) {
			for(int i = 0; i < 5; i++) {
				Fire_Bolt b = new project.Fire_Bolt(800 + 700 * Math.cos(Math.toRadians(Main.frame/5 + 72 * i)), 
						800 + 700 * Math.sin(Math.toRadians(Main.frame/5 + 72 * i)), 180 + Main.frame/5 + 72 * i, 7, 100);
			}
		}
		if(Main.frame % 60 == 0) {
			for(int i = 0; i < 5; i++) {
				for(int j = -1; j < 2; j++) {
					Fire_Bolt b = new project.Fire_Bolt(800 + 700 * Math.cos(Math.toRadians(Main.frame/5 + 72 * i)), 
							800 + 700 * Math.sin(Math.toRadians(Main.frame/5 + 72 * i)), Main.frame/5 + 72 * i + 40 * j, 10, 30);
				}
			}
		}
		if(Main.frame % 480 == 0) {
			for(int i = 0; i < 8; i++) {
				Fire_Bomb b = new project.Fire_Bomb(800 + 150 * Math.cos(Math.toRadians(45*i)), 800 + 175 * Math.sin(Math.toRadians(45*i)));
			}
		}
		if(Main.frame % 480 == 160) {
			for(int i = 0; i < 12; i++) {
				Fire_Bomb b = new project.Fire_Bomb(800 + 425 * Math.cos(Math.toRadians(30*i)), 800 + 425 * Math.sin(Math.toRadians(30*i)));
			}
		}
		if(Main.frame % 480 == 320) {
			for(int i = 0; i < 15; i++) {
				Fire_Bomb b = new project.Fire_Bomb(800 + 700 * Math.cos(Math.toRadians(24*i)), 800 + 675 * Math.sin(Math.toRadians(24*i)));
			}
		}
		
		if(Main.frame % 100 == 0) {
			for(int i = 0; i < 10; i++) {
				Fire_Vortex b = new project.Fire_Vortex(800, 800, Main.frame/3 + 36 * i, 10, 85);
			}
		}
		
		if(this.fire_portals.size() == 0) {
			for(int i = 0; i < 5; i++) {
				Fire_Portal f = new project.Fire_Portal("fireflowers_"+String.valueOf(72*i));
			}
		}
	}
	
	public void firewalls() {
		if(Main.frame % 60 == 0) {
			this.f_nuke();
		}
		
		if(Main.frame % 1200 < 200) {
			if(Main.frame % 1400 == 0) {
				if(Math.random() < 0.5) {
					this.fwdir = 0;
				}
				else {
					this.fwdir = 1;
				}
			}
			if(Main.frame % 25 == 0) {
				if(this.fwdir == 0) {
					Fire_Wave b = new project.Fire_Wave(800, 800, 0, 10, 80);
					Fire_Wave b2 = new project.Fire_Wave(800, 800, 180, 10, 80);
				}
				else {
					Fire_Wave b = new project.Fire_Wave(800, 800, 90, 10, 80);
					Fire_Wave b2 = new project.Fire_Wave(800, 800, -90, 10, 80);
				}
			}
		}
		else if(Main.frame % 1200 < 1000) {
			if(Main.frame % 25 == 0 && Main.frame % 250 < 135) {
				for(int i = 0; i < 16; i++) {
					if(this.fwdir == 0) {
						Fire_Wave b = new project.Fire_Wave(0, 160 + 80 * i, 0, 10, 160);
					}
					else {
						Fire_Wave b = new project.Fire_Wave(160 + 80 * i, 0, 90, 10, 160);
					}
				}
			}
			else if(Main.frame % 25 == 12 && Main.frame % 250 < 135) {
				for(int i = 0; i < 16; i++) {
					if(this.fwdir == 0) {
						Fire_Wave b2 = new project.Fire_Wave(1600, 200 + 80 * i, 180, 10, 160);
					}
					else {
						Fire_Wave b2 = new project.Fire_Wave( 200 + 80 * i, 1600, -90, 10, 160);
					}
				}
			}
		}
		else {
			if(Main.frame % 1200 >= 1096 && Main.frame % 1200 < 1100) {
				double rx = 300 + 500 * Math.random();
				double ry = 300 + 500 * Math.random();
				Fire_Bomb b = new project.Fire_Bomb(rx, ry);
				
				rx = 800 + 500 * Math.random();
				ry = 300 + 500 * Math.random();
				Fire_Bomb b1 = new project.Fire_Bomb(rx, ry);
				
				rx = 300 + 500 * Math.random();
				ry = 800 + 500 * Math.random();
				Fire_Bomb b2 = new project.Fire_Bomb(rx, ry);
				
				rx = 800 + 500 * Math.random();
				ry = 800 + 500 * Math.random();
				Fire_Bomb b3 = new project.Fire_Bomb(rx, ry);
			}
		}
		
		if(Main.frame % 60 == 0) {
			for(int i = 0; i < 8; i++) {
				for(int j = -3; j < 4; j++) {
					Fire_Bolt b = new project.Fire_Bolt(800 + 625 * Math.cos(Math.toRadians(Main.frame/3 + 45 * i)), 
							800 + 625 * Math.sin(Math.toRadians(Main.frame/3 + 45 * i)), Main.frame/3 + 45 * i + 24 * j, 15, 20);
				}
			}
		}
		
		if(Main.frame % 150 == 0) {
			for(int i = 0; i < 6; i++) {
				Blue_Ball b = new project.Blue_Ball(800, 800, Main.frame/4 + 60 * i, 6, 150);
			}
		}
		
		if(this.fire_portals.size() == 0) {
			for(int i = 0; i < 8; i++) {
				Fire_Portal b = new project.Fire_Portal("firewalls_"+String.valueOf(45*i));
			}
		}
	}
	
	public void iceportals() {
		if(Main.frame % 60 == 0) {
			this.i_channel();
		}
		
		if(Main.frame % 250 == 0) {
			if(ThreadLocalRandom.current().nextInt(0, 2) == 0) {
				Ice_Portal b = new project.Ice_Portal("iceportals_0");
				Ice_Portal b1 = new project.Ice_Portal("iceportals_90");
				Ice_Portal b2 = new project.Ice_Portal("iceportals_180");
				Ice_Portal b3 = new project.Ice_Portal("iceportals_270");
			}
			else {
				Ice_Portal b = new project.Ice_Portal("iceportals_45");
				Ice_Portal b1 = new project.Ice_Portal("iceportals_135");
				Ice_Portal b2 = new project.Ice_Portal("iceportals_225");
				Ice_Portal b3 = new project.Ice_Portal("iceportals_315");
			}
		}
		if(Main.frame % 375 == 0) {
			for(int i = 0; i < this.ice_portals.size(); i++) {
				Ice_Portal q = this.ice_portals.get(i);
				if((q.x - 800) * (q.x - 800) + (q.y - 800) * (q.y - 800) > 200 * 200) {
					if(ThreadLocalRandom.current().nextInt(0, 2) == 0) {
						q.diagonal_blast();
					}
					else {
						q.cardinal_blast();
					}
				}
			}
		}
		if(Main.frame % 350 == 50) {
			for(int i = 0; i < 20; i++) {
				Ice_Twirl b = new project.Ice_Twirl(800, 800, 18 * i, 8, 110, "iceportals_50");
			}
		}
		if(Main.frame % 350 == 100) {
			for(int i = 0; i < 20; i++) {
				Ice_Twirl b = new project.Ice_Twirl(800, 800, 18 * i, 8, 110, "iceportals_-50");
			}
		}
		if(Main.frame % 350 == 150) {
			for(int i = 0; i < 20; i++) {
				Ice_Twirl b = new project.Ice_Twirl(800, 800, 18 * i + 9, 8, 110, "iceportals_50");
			}
		}
	}
	
	public void icerotates() {
		if(Main.frame % 60 == 0) {
			this.i_channel();
		}
		
		if(this.ice_portals.size() == 0) {
			Ice_Portal b = new project.Ice_Portal("icerotates_ur200");
			Ice_Portal b2 = new project.Ice_Portal("icerotates_ul375");
			Ice_Portal b3 = new project.Ice_Portal("icerotates_ur550");
			Ice_Portal b4 = new project.Ice_Portal("icerotates_ul725");
			
			Ice_Portal b6 = new project.Ice_Portal("icerotates_dr200");
			Ice_Portal b7 = new project.Ice_Portal("icerotates_dl375");
			Ice_Portal b8 = new project.Ice_Portal("icerotates_dr550");
			Ice_Portal b9 = new project.Ice_Portal("icerotates_dl725");
			
			Ice_Portal b10 = new project.Ice_Portal("icerotates_rr200");
			Ice_Portal b11 = new project.Ice_Portal("icerotates_rl375");
			Ice_Portal b12 = new project.Ice_Portal("icerotates_rr550");
			Ice_Portal b13 = new project.Ice_Portal("icerotates_rl725");
			
			Ice_Portal b14 = new project.Ice_Portal("icerotates_lr200");
			Ice_Portal b15 = new project.Ice_Portal("icerotates_ll375");
			Ice_Portal b16 = new project.Ice_Portal("icerotates_lr550");
			Ice_Portal b17 = new project.Ice_Portal("icerotates_ll725");
		}
		if(Main.frame % 500 == 230) {
			for(int i = 0; i < this.ice_portals.size(); i+=2) {
				Ice_Portal q = this.ice_portals.get(i);
				if(ThreadLocalRandom.current().nextInt(0, 2) == 0) {
					q.flank_blast();
				}
				else {
					q.pulse_blast();
				}
			}
		}
		if(Main.frame % 500 == 480) {
			for(int i = 1; i < this.ice_portals.size(); i+=2) {
				Ice_Portal q = this.ice_portals.get(i);
				if(ThreadLocalRandom.current().nextInt(0, 2) == 0) {
					q.flank_blast();
				}
				else {
					q.pulse_blast();
				}
			}
		}
		
		if(Main.frame % 125 == 0) {
			double q = 40 * Math.random();
			for(int i = 0; i < 6; i++) {
				Ice_Vortex b = new project.Ice_Vortex(800, 800, 60 * i + Main.frame/4.2 + q, 7, 135);
			}
		}
	}
	
	public void firepopcorns() {
		if(Main.frame % 60 == 0) {
			this.f_nuke();
		}
		
		if(Main.frame % 25 == 0) {
			double q = ThreadLocalRandom.current().nextInt(8, 11);
			for(int i = 0; i < q; i++) {
				double r = -50 + 100 * Math.random();
				Fire_Twirl b = new project.Fire_Twirl(800, 800, Main.frame/1.5 + i * 360/q, 8, 125, "firepopcorns_"+String.valueOf((int)r));
			}
		}
		if(Main.frame % 50 == 0) {
			for(int i = 0; i < 5; i++) {
				Fire_Vortex b = new project.Fire_Vortex(800, 800, Main.frame/1.8 + 72 * i, 10, 85);
			}
		}
		
		if(Main.frame % 50 == 0) {
			for(int i = 0; i < 8; i++) {
				for(int j = -1; j < 2; j++) {
					Fire_Bolt b = new project.Fire_Bolt(800 + 700 * Math.cos(Math.toRadians(Main.frame/3 + 45 * i)), 
							800 + 700 * Math.sin(Math.toRadians(Main.frame/3 + 45 * i)), 180 + Main.frame/3 + 45 * i + 40 * j, 6, 20);
				}
				for(int j = -1; j < 2; j++) {
					Fire_Bolt b = new project.Fire_Bolt(800 + 700 * Math.cos(Math.toRadians(Main.frame/3 + 45 * i)), 
							800 + 700 * Math.sin(Math.toRadians(Main.frame/3 + 45 * i)), Main.frame/3 + 45 * i + 40 * j, 6, 20);
				}
			}
		}
	
		if(Main.frame % 400 == 0) {
			for(int i = 0; i < 3; i++) {
				Pink_Ball b = new project.Pink_Ball(800 + 800 * Math.cos(Math.toRadians(Main.frame/2 + 120 * i)), 
						770 + 800 * Math.sin(Math.toRadians(Main.frame/2 + 120 * i)), 180 + Main.frame/2 + 120 * i, 5, 100, 44);
				this.chain = Main.frame/2;
			}
		}
		for(int j = 1; j < 4; j++) {
			if(Main.frame % 400 == 20 * j) {
				for(int i = 0; i < 3; i++) {
					Pink_Ball b = new project.Pink_Ball(800 + 800 * Math.cos(Math.toRadians(this.chain + 120 * i)), 
							770 + 800 * Math.sin(Math.toRadians(this.chain + 120 * i)), 180 + this.chain + 120 * i, 5, 100, 44);
				}
			}
		}
		
		if(this.fire_portals.size() == 0) {
			for(int i = 0; i < 6; i++) {
				Fire_Portal b = new project.Fire_Portal("firewalls_"+String.valueOf(60*i));
			}
		}
	}
	
	public void icearenas() {
		if(Main.frame % 60 == 0) {
			this.i_nuke();
		}
		
		if(Main.frame % 600 == 300) {
			for(int i = 0; i < 24; i++) {
				Ice_Star b = new project.Ice_Star(800 + 600 * Math.cos(Math.toRadians(15 * i + Main.frame/2.2)),
						800 + 600 * Math.sin(Math.toRadians(15 * i + Main.frame/2.2)), 180 + 15 * i + Main.frame/2.2, 
						6, 205, "icearenas_1");
			}
		}
		if(Main.frame % 600 == 310) {
			for(int i = 0; i < 24; i++) {
				Ice_Star b = new project.Ice_Star(800 + 650 * Math.cos(Math.toRadians(15 * i + Main.frame/2.2)),
						800 + 650 * Math.sin(Math.toRadians(15 * i + Main.frame/2.2)), 180 + 15 * i + Main.frame/2.2, 
						6, 205, "icearenas_1.1");
			}
		}
		if(Main.frame % 600 == 320) {
			for(int i = 0; i < 24; i++) {
				Ice_Star b = new project.Ice_Star(800 + 700 * Math.cos(Math.toRadians(15 * i + Main.frame/2.2)),
						800 + 700 * Math.sin(Math.toRadians(15 * i + Main.frame/2.2)), 180 + 15 * i + Main.frame/2.2, 
						6, 205, "icearenas_1.2");
			}
		}
		
		if(Main.frame % 600 == 0) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 3; j++) {
					Ice_Star b = new project.Ice_Star(800, 800, 14 * i + 120 * j + Main.frame/2.2, 10, 205, "icearenas_2.647");
				}
			}
		}
		if(Main.frame % 600 == 10) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 3; j++) {
					Ice_Star b = new project.Ice_Star(800, 800, 14 * i + 120 * j + Main.frame/2.2, 9.4, 205, "icearenas_2.647");
				}
			}
		}
		if(Main.frame % 600 == 20) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 3; j++) {
					Ice_Star b = new project.Ice_Star(800, 800, 14 * i + 120 * j + Main.frame/2.2, 8.8, 205, "icearenas_2.647");
				}
			}
		}
		if(this.ice_portals.size() == 0) {
			for(int i = 0; i < 6; i++) {
				Ice_Portal b = new project.Ice_Portal("icearenas_"+String.valueOf(60*i));
			}
		}
		
		if(Main.frame % 150 == 0) {
			for(int i = 0; i < this.ice_portals.size(); i++) {
				this.ice_portals.get(i).outer_blast();
			}
			for(int i = 0; i < 6; i++) {
				Pink_Ball b = new project.Pink_Ball(800 + 700 * Math.cos(Math.toRadians(60 * i + Main.frame/3)),
						800 + 700 * Math.sin(Math.toRadians(60 * i + Main.frame/3)), 180 + 60 * i + Main.frame/3, 
						2, 75, 36);
			}
		}
		if(Main.frame % 40 == 0) {
			if(Math.random() < 0.4) {
				this.chaincount = 8;
				for(int i = 0; i < 8; i++) {
					Ice_Shield b = new project.Ice_Shield(800, 800, 45 * i + Main.frame/2.3, 12, 50);
					this.chain = Main.frame/2.3;
				}
			}
			else {
				if(Math.random() < 0.5) {
					this.chaincount = 6;
					for(int i = 0; i < 6; i++) {						
						Ice_Shield b = new project.Ice_Shield(800, 800, 60 * i + Main.frame/2.3, 10, 70);
						this.chain = Main.frame/2.3;
					}
				}
				else {
					this.chaincount = 3;
					for(int i = 0; i < 3; i++) {
						Ice_Shield b = new project.Ice_Shield(800, 800, 120 * i + Main.frame/2.3, 9, 100);
						this.chain = Main.frame/2.3;
					}
				}
			}
		}
		for(int j = 1; j < 4; j++) {
			if(Main.frame % 40 == 7 * j + 4) {
				for(int i = 0; i < this.chaincount; i++) {
					//Lagrange interpolation of (3, 9), (6, 10), (8, 12)
					double v = (2*this.chaincount * this.chaincount - 13*this.chaincount + 156)/15.0;
					Ice_Spear b = new project.Ice_Spear(800, 800, i * 360/this.chaincount + this.chain, 
							v, (int)(600.0/v));
				}
			}
		}
	}
	
	public void firevomits() {
		if(Main.frame % 60 == 0) {
			this.f_channel();
		}
		if(Main.frame % 55 == 0) {
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 2; j++) {
					Fire_Wave b = new project.Fire_Wave(800, 800, 21 * i + 180 * j + Main.frame - 45, 5, 200);
				}
			}
		}
		if(Main.frame % 11 == 0) {
			double rx = 100 + 1400 * Math.random();
			double ry = 100 + 1400 * Math.random();
			Fire_Bomb b = new project.Fire_Bomb(rx, ry);
		}
		
		if(this.fire_portals.size() == 0) {
			for(int i = 0; i < 3; i++) {
				Fire_Portal b = new project.Fire_Portal("firevomits_"+String.valueOf(120*i));
			}
		}
		
		if(Main.frame % 100 == 0) {
			for(int i = 0; i < this.fire_portals.size(); i++) {
				this.fire_portals.get(i).inner_blast();
			}
		}
	}
	
	public void icebombs() {
		if(Main.frame % 60 == 0) {
			this.i_nuke();
		}
		if(Main.frame % 200 == 0) {
			double rx = 200 + 600 * Math.random();
			double ry = 200 + 600 * Math.random();
			Ice_Sphere b = new project.Ice_Sphere(rx, ry);
		}
		if(Main.frame % 200 == 50) {			
			double rx = 200 + 600 * Math.random();
			double ry = 800 + 600 * Math.random();
			Ice_Sphere b1 = new project.Ice_Sphere(rx, ry);
		}
		if(Main.frame % 200 == 100) {
			double rx = 800 + 600 * Math.random();
			double ry = 200 + 600 * Math.random();
			Ice_Sphere b2 = new project.Ice_Sphere(rx, ry);
		}
		if(Main.frame % 200 == 150) {	
			double rx = 800 + 600 * Math.random();
			double ry = 800 + 600 * Math.random();
			Ice_Sphere b3 = new project.Ice_Sphere(rx, ry);
		}
		
		if(this.ice_portals.size() == 0) {
			for(int i = 0; i < 8; i++) {
				Ice_Portal b = new project.Ice_Portal("icebombs_"+String.valueOf(45*i));
			}
		}
		for(int i = 0; i < 4; i++) {
			if(Main.frame % 240 == 60 * i) {
				double r = 40 * Math.random();
				if(i % 2 == 0) {
					Ice_Twirl b = new project.Ice_Twirl(780, 780, 90 * i + Main.frame + r, 3, 150, "icebombs_20");
					Ice_Twirl b1 = new project.Ice_Twirl(780, 820, 90 * i + Main.frame + r, 3, 150, "icebombs_20");
					Ice_Twirl b2 = new project.Ice_Twirl(820, 780, 90 * i + Main.frame + r, 3, 150, "icebombs_20");
					Ice_Twirl b3 = new project.Ice_Twirl(820, 820, 90 * i + Main.frame + r, 3, 150, "icebombs_20");
				}
				else {
					Ice_Twirl b = new project.Ice_Twirl(780, 780, 90 * i + Main.frame + r, 3, 150, "icebombs_-20");
					Ice_Twirl b1 = new project.Ice_Twirl(780, 820, 90 * i + Main.frame + r, 3, 150, "icebombs_-20");
					Ice_Twirl b2 = new project.Ice_Twirl(820, 780, 90 * i + Main.frame + r, 3, 150, "icebombs_-20");
					Ice_Twirl b3 = new project.Ice_Twirl(820, 820, 90 * i + Main.frame + r, 3, 150, "icebombs_-20");
				}
			}
		}
		if(Main.frame % 320 == 0) {
			for(int i = 0; i < this.ice_portals.size(); i++) {
				this.ice_portals.get(i).square_blast();
			}
		}
	}
	
	public void icefinale() {
		if(Main.frame % 60 == 0) {
			this.i_nuke();
		}
		
		if(Main.frame < 1000) {
			this.vulnerable = false;
		}
		else {
			this.vulnerable = true;
		}
		
		if(Main.frame >= 480) {
			int if_frame = Main.frame - 480;
			if(this.ice_portals.size() == 0) {
				for(int i = 0; i < 8; i++) {
					Ice_Portal b = new project.Ice_Portal("icefinale_"+String.valueOf(45*i));
				}
			}
			
			if(if_frame % 30 == 0) {
				for(int i = 0; i < 8; i++) {
					for(int j = -3; j < 4; j++) {
						Ice_Bolt b = new project.Ice_Bolt(800 + 720 * Math.cos(Math.toRadians(if_frame/3 + 45 * i)), 
								800 + 720 * Math.sin(Math.toRadians(if_frame/3 + 45 * i)), if_frame/3 + 45 * i + 20 * j, 9, 20);
					}
				}
			}
			
			for(int i = 0; i < 8; i++) {
				if(if_frame % 560 == 70 * i) {
					double r = -40 + 80 * Math.random();
					Ice_Vortex b = new project.Ice_Vortex(800 + 720 * Math.cos(Math.toRadians(if_frame/3 + 45 * i)), 
							800 + 720 * Math.sin(Math.toRadians(if_frame/3 + 45 * i)), 180 + if_frame/3 + 45 * i + r, 10, 160);
				}
			}
			if(if_frame % 50 == 0) {
				for(int i = 0; i < 8; i++) {
					double r = -40 + 80 * Math.random();
					double t = -15 + 30 * Math.random();
					Ice_Twirl b = new project.Ice_Twirl(800 + 720 * Math.cos(Math.toRadians(if_frame/3 + 45 * i)), 
							800 + 720 * Math.sin(Math.toRadians(if_frame/3 + 45 * i)), 180 + if_frame/3 + 45 * i + r, 7, 250,
							"icefinale_"+String.valueOf(t));
				}
			}
			
			if(if_frame % 700 == 0) {
				double rx = 200 + 600 * Math.random();
				double ry = 200 + 600 * Math.random();
				Ice_Sphere b = new project.Ice_Sphere(rx, ry);
			}
			if(if_frame % 700 == 175) {			
				double rx = 200 + 600 * Math.random();
				double ry = 800 + 600 * Math.random();
				Ice_Sphere b1 = new project.Ice_Sphere(rx, ry);
			}
			if(if_frame % 700 == 350) {
				double rx = 800 + 600 * Math.random();
				double ry = 200 + 600 * Math.random();
				Ice_Sphere b2 = new project.Ice_Sphere(rx, ry);
			}
			if(if_frame % 700 == 525) {	
				double rx = 800 + 600 * Math.random();
				double ry = 800 + 600 * Math.random();
				Ice_Sphere b3 = new project.Ice_Sphere(rx, ry);
			}
	
			if(if_frame % 800 == 100) {
				double rx = -250 + 500 * Math.random() + Main.sorc.x;
				double ry = -250 + 500 * Math.random() + Main.sorc.y;
				Ice_Sphere b = new project.Ice_Sphere(rx, ry);
			}
		}
	}
	
	public void firefinale() {
		if(Main.frame % 60 == 0) {
			this.f_nuke();
		}
		
		if(Main.frame < 1000) {
			this.vulnerable = false;
		}
		else {
			this.vulnerable = true;
		}
		
		if(Main.frame >= 420) {
			int ff_frame = Main.frame - 420;
			for(int i = 0; i < 14; i++) {
				if(ff_frame % 1400 > 45 * i && ff_frame % 1400 < 45 * i + 150 + 5 * i && ff_frame % 14 == 0) {
					Fire_Finale_Wave b = new project.Fire_Finale_Wave(465, -3 - i, i % 2);
				}
			}
			if(ff_frame % 14 == 0 && ff_frame % 1400 < 45 * 13 + 150) {
				Fire_Wave b = new project.Fire_Wave(800, 800, 0, 12, 56);
				Fire_Wave b1 = new project.Fire_Wave(800, 800, 180, 12, 56);
			}
			
			if(ff_frame % 280 == 0) {
				double rx = 200 + 600 * Math.random();
				double ry = 200 + 600 * Math.random();
				Fire_Bomb b = new project.Fire_Bomb(rx, ry);
			}
			if(ff_frame % 280 == 70) {			
				double rx = 800 + 600 * Math.random();
				double ry = 200 + 600 * Math.random();
				Fire_Bomb b1 = new project.Fire_Bomb(rx, ry);
			}
			if(ff_frame % 280 == 140) {
				double rx = 200 + 600 * Math.random();
				double ry = 800 + 600 * Math.random();
				Fire_Bomb b2 = new project.Fire_Bomb(rx, ry);
			}
			if(ff_frame % 280 == 210) {	
				double rx = 800 + 600 * Math.random();
				double ry = 800 + 600 * Math.random();
				Fire_Bomb b3 = new project.Fire_Bomb(rx, ry);
			}
			
			if(ff_frame % 100 == 0) {
				double rx = -150 + 300 * Math.random() + Main.sorc.x;
				double ry = -150 + 300 * Math.random() + Main.sorc.y;
				Fire_Bomb b = new project.Fire_Bomb(rx, ry);
				
				rx = -40 + 80 * Math.random();
				ry = -40 + 80 * Math.random();
				Fire_Bomb b1 = new project.Fire_Bomb(800 + rx, 775 + ry);
			}
		
			if(this.fire_portals.size() == 0) {
				for(int i = 0; i < 8; i++) {
					Fire_Portal b = new project.Fire_Portal("firefinale_"+String.valueOf(45*i));
				}
			}
			
			if(ff_frame % 25 == 0) {
				for(int i = 0; i < 8; i++) {
					for(int j = -3; j < 4; j++) {
						Fire_Bolt b = new project.Fire_Bolt(800 + 650 * Math.cos(Math.toRadians(ff_frame/3 + 45 * i)), 
								800 + 650 * Math.sin(Math.toRadians(ff_frame/3 + 45 * i)), ff_frame/3 + 45 * i + 24 * j, 18, 20);
					}
				}
			}
		}
	}
	
	public void firestagger() {
		if(Main.frame == 1) {
			this.flash = 49;
		}
		if(Main.frame % 60 == 50) {
			this.stagger();
		}
		if(Main.frame == 1) {
			this.stagger_cap = this.health[0] - 0.151 * this.health[1];
		}
		if(Main.frame > 300) {
			if(Main.inferno.movement.equals("hidden")){
				Main.inferno.movement = "middle_circle_r";
				Main.inferno.spd = 0.4;
			}
			if(Main.frame % 50 == 0) {
				Main.inferno.inner_blast();
			}
			if(Main.frame % 40 == 0) {
				Main.inferno.diagonal_blast();
			}
		}
	}
	
	public void icestagger() {
		if(Main.frame == 1) {
			this.flash = 49;
		}
		if(Main.frame % 60 == 50) {
			this.stagger();
		}
		if(Main.frame == 1) {
			this.stagger_cap = this.health[0] - 0.151 * this.health[1];
		}
		if(Main.frame > 300) {
			if(Main.blizzard.movement.equals("hidden")){
				Main.blizzard.movement = "middle_circle_l";
				Main.blizzard.spd = 0.36;
			}
			if(Main.frame % 45 == 0) {
				Main.blizzard.inner_blast();
			}
			if(Main.frame % 35 == 0) {
				Main.blizzard.diagonal_blast();
			}
		}
	}
	
	public void firedoublestagger() {
		if(Main.frame == 1) {
			this.flash = 49;
		}
		if(Main.frame == 40 || Main.frame == 60 || Main.frame == 80) {
			this.flash = 10;
		}
		if(Main.frame % 60 == 50) {
			this.stagger();
		}
		if(Main.frame == 1) {
			this.stagger_cap = this.health[0] - 0.151 * this.health[1];
		}
		if(Main.frame > 300) {
			if(Main.inferno.movement.equals("hidden")){
				Main.inferno.movement = "inner_circle_r";
				Main.inferno.spd = 0.4;
			}
			if(Main.frame % 100 == 0) {
				Main.inferno.inner_blast();
			}
			if(Main.frame % 50 == 0) {
				Main.inferno.diagonal_blast();
			}
			if(Main.blizzard.movement.equals("hidden")){
				Main.blizzard.movement = "outer_circle_l";
				Main.blizzard.spd = 0.36;
			}
			if(Main.frame % 110 == 0) {
				Main.blizzard.inner_blast();
			}
			if(Main.frame % 75 == 0) {
				Main.blizzard.diagonal_blast();
			}
		}
	}
	
	public void icedoublestagger() {
		if(Main.frame == 1) {
			this.flash = 49;
		}
		if(Main.frame == 40 || Main.frame == 60 || Main.frame == 80) {
			this.flash = 10;
		}
		if(Main.frame % 60 == 50) {
			this.stagger();
		}
		if(Main.frame == 1) {
			this.stagger_cap = this.health[0] - 0.151 * this.health[1];
		}
		if(Main.frame > 300) {
			if(Main.inferno.movement.equals("hidden")){
				Main.inferno.movement = "outer_circle_r";
				Main.inferno.spd = 0.4;
			}
			if(Main.frame % 125 == 0) {
				Main.inferno.inner_blast();
			}
			if(Main.frame % 85 == 0) {
				Main.inferno.diagonal_blast();
			}
			if(Main.blizzard.movement.equals("hidden")){
				Main.blizzard.movement = "inner_circle_l";
				Main.blizzard.spd = 0.36;
			}
			if(Main.frame % 80 == 0) {
				Main.blizzard.inner_blast();
			}
			if(Main.frame % 50 == 0) {
				Main.blizzard.diagonal_blast();
			}
		}
	}
	
	public void icefinale_prep() {
		if(Main.frame == 1) {
			Main.inferno.health[0] = Main.inferno.health[1];
			Main.blizzard.health[0] = Main.blizzard.health[1];
		}
		
		if(Main.frame == 240) {
			this.taunts.add("Twilight Archmage: I leave the rest to you, my undying hosts. Time is short!");
		}
	
		if(Main.frame == 480) {
			this.finale_animation();
		}
		else if(Main.frame % 60 ==  0 && Main.frame >= 600) {
			this.finale_hover();
		}
		if(Main.frame > 600) {
			if(Main.inferno.movement.equals("hidden")){
				Main.inferno.movement = "middle_circle_r";
				Main.inferno.spd = 0.4;
			}
			if(Main.blizzard.movement.equals("hidden")){
				Main.blizzard.movement = "inner_circle_l";
				Main.blizzard.spd = 0.36;
			}
			
			if(Main.blizzard.health[0] > 0 && Main.inferno.health[0] > 0) {
				Main.inferno.vulnerable = true;
				Main.blizzard.vulnerable = false;
				
				if(Main.frame % 35 == 0) {
					Main.inferno.x_blast();
				}
				
				if(Main.frame % 80 == 0) {
					Main.inferno.pulse_blast();
				}
			}
			
			else if(Main.blizzard.health[0] > 0) {
				Main.blizzard.vulnerable = true;
				
				if(Main.frame % 40 == 0) {
					for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 3; j++) {
							Ice_Wave b = new project.Ice_Wave(800, 800, 17 * i + 120 * j + Main.frame, 10, 100);
						}
					}
				}
				
				if(Main.frame % 160 == 0) {
					double rx = 200 + 600 * Math.random();
					double ry = 200 + 600 * Math.random();
					Ice_Sphere b = new project.Ice_Sphere(rx, ry);
				}
				if(Main.frame % 160 == 40) {			
					double rx = 800 + 600 * Math.random();
					double ry = 200 + 600 * Math.random();
					Ice_Sphere b1 = new project.Ice_Sphere(rx, ry);
				}
				if(Main.frame % 160 == 80) {
					double rx = 200 + 600 * Math.random();
					double ry = 800 + 600 * Math.random();
					Ice_Sphere b2 = new project.Ice_Sphere(rx, ry);
				}
				if(Main.frame % 160 == 120) {	
					double rx = 800 + 600 * Math.random();
					double ry = 800 + 600 * Math.random();
					Ice_Sphere b3 = new project.Ice_Sphere(rx, ry);
				}
			}
			
			else {
				Main.reset();
				this.health[0] = 0.2 * this.health[1];
				this.attack = "icefinale";
				Main.write_list[4] = 1;
				Main.frame = -1;
				Main.am_state = 4;
				this.taunts.add("Twilight Archmage: That’s IT! You brought this fate upon yourselves!");
			}
		}
	}
	
	public void firefinale_prep() {
		if(Main.frame == 1) {
			Main.inferno.health[0] = Main.inferno.health[1];
			Main.blizzard.health[0] = Main.blizzard.health[1];
		}

		if(Main.frame == 240) {
			this.taunts.add("Twilight Archmage: I leave the rest to you, my undying hosts. Time is short!");
		}
	
		if(Main.frame == 480) {
			this.finale_animation();
		}
		else if(Main.frame % 60 ==  0 && Main.frame >= 600) {
			this.finale_hover();
		}
		if(Main.frame > 600) {
			if(Main.inferno.movement.equals("hidden")){
				Main.inferno.movement = "inner_circle_r";
				Main.inferno.spd = 0.4;
			}
			if(Main.blizzard.movement.equals("hidden")){
				Main.blizzard.movement = "middle_circle_l";
				Main.blizzard.spd = 0.36;
			}
			
			if(Main.blizzard.health[0] > 0 && Main.inferno.health[0] > 0) {
				Main.inferno.vulnerable = false;
				Main.blizzard.vulnerable = true;
				
				if(Main.frame % 35 == 0) {
					Main.blizzard.x_blast();
				}
				
				if(Main.frame % 80 == 0) {
					Main.blizzard.pulse_blast();
				}
			}
			
			else if(Main.inferno.health[0] > 0) {
				Main.inferno.vulnerable = true;
				
				if(Main.frame % 50 == 0) {
					for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 3; j++) {
							Fire_Wave b = new project.Fire_Wave(800, 800, -21 + 14 * i + 120 * j + Main.frame * 4, 6, 180);
						}
					}
				}
				
				if(Main.frame % 100 == 0) {
					for(int i = 0; i < 7; i++) {
						Fire_Vortex b = new project.Fire_Vortex(800, 800, 51.4 * i + Main.frame/4, 8, 150);
					}
				}
				
				if(Main.frame % 120 == 0) {
					double rx = 200 + 600 * Math.random();
					double ry = 200 + 600 * Math.random();
					Fire_Bomb b = new project.Fire_Bomb(rx, ry);
				}
				if(Main.frame % 120 == 30) {			
					double rx = 800 + 600 * Math.random();
					double ry = 200 + 600 * Math.random();
					Fire_Bomb b1 = new project.Fire_Bomb(rx, ry);
				}
				if(Main.frame % 120 == 60) {
					double rx = 200 + 600 * Math.random();
					double ry = 800 + 600 * Math.random();
					Fire_Bomb b2 = new project.Fire_Bomb(rx, ry);
				}
				if(Main.frame % 120 == 90) {	
					double rx = 800 + 600 * Math.random();
					double ry = 800 + 600 * Math.random();
					Fire_Bomb b3 = new project.Fire_Bomb(rx, ry);
				}
			}
			
			else {
				Main.reset();
				this.health[0] = 0.2 * this.health[1];
				this.attack = "firefinale";
				Main.write_list[3] = 1;
				Main.frame = -1;
				Main.am_state = 4;
				this.taunts.add("Twilight Archmage: That’s IT! You brought this fate upon yourselves!");
			}
		}
	}
	
	//generator positions: 800, 300; 350, 1150; 1250, 1150
	public void sicken1() {
		for(int i = 0; i < Main.gens.length; i++) {
			if(!Main.gens[i].sealed) {
				Main.gens[i].vulnerable = true;
			}
		}
		
		if(Main.frame == 25) {
			Pop_Text p = new project.Pop_Text(Main.sorc.x, Main.sorc.y-15, "Sick", new Color(255, 40, 40));
		}
		Main.sorc.status[3] = 100;
		
		if(Main.frame % 60 == 0) {
			this.s_channel();
		}
		if(Main.frame % 100 == 0) {
			for(int i = 0; i < 7; i++) {
				Pink_Shield b = new project.Pink_Shield(800, 300, 51 * i + Main.frame * 0.7, 6, 200);
				Pink_Shield b1 = new project.Pink_Shield(350, 1150, 51 * i + Main.frame * 0.7, 6, 200);
				Pink_Shield b2 = new project.Pink_Shield(1250, 1150, 51 * i + Main.frame * 0.7, 6, 200);
			}
		}
	}
	
	public void sicken2() {
		for(int i = 0; i < Main.gens.length; i++) {
			if(!Main.gens[i].sealed) {
				Main.gens[i].vulnerable = true;
			}
		}
		
		if(Main.frame == 25) {
			Pop_Text p = new project.Pop_Text(Main.sorc.x, Main.sorc.y-15, "Sick", new Color(255, 40, 40));
		}
		Main.sorc.status[3] = 100;
		
		if(Main.frame % 60 == 0) {
			this.s_channel();
		}
		
		if(Main.frame % 100 == 0) {
			for(int i = 0; i < 7; i++) {
				Pink_Shield b = new project.Pink_Shield(800, 300, 51 * i + Main.frame * 0.7, 6, 200);
				Pink_Shield b1 = new project.Pink_Shield(350, 1150, 51 * i + Main.frame * 0.7, 6, 200);
				Pink_Shield b2 = new project.Pink_Shield(1250, 1150, 51 * i + Main.frame * 0.7, 6, 200);
			}
		}
		
		if(Main.frame % 100 == 0) {
			for(int i = 0; i < 12; i++) {
				Blue_Shield b = new project.Blue_Shield(800, 800, 30 * i + Main.frame * 1.4, 8, 100);
			}
		}
	}
	
	public void sicken3() {
		for(int i = 0; i < Main.gens.length; i++) {
			if(!Main.gens[i].sealed) {
				Main.gens[i].vulnerable = true;
			}
		}
		
		if(Main.frame == 25) {
			Pop_Text p = new project.Pop_Text(Main.sorc.x, Main.sorc.y-15, "Sick", new Color(255, 40, 40));
		}
		Main.sorc.status[3] = 100;
		
		if(Main.frame % 60 == 0) {
			this.s_channel();
		}
		
		if(Main.frame % 80 == 0) {
			for(int i = 0; i < 7; i++) {
				Pink_Shield b = new project.Pink_Shield(800, 300, 51 * i + Main.frame * 0.55, 6, 200);
				Pink_Shield b1 = new project.Pink_Shield(350, 1150, 51 * i + Main.frame * 0.55, 6, 200);
				Pink_Shield b2 = new project.Pink_Shield(1250, 1150, 51 * i + Main.frame * 0.55, 6, 200);
			}
		}
		
		if(Main.frame % 80 == 0) {
			for(int i = 0; i < 15; i++) {
				Blue_Shield b = new project.Blue_Shield(800, 800, 24 * i + Main.frame * 0.85, 8, 100);
			}
		}
	}
	
	public void death() {
		if(Main.frame % 60 == 0) {
			this.stagger();
		}
		this.health[0] = 0;
		this.vulnerable = true;
		
		if(Main.frame == 500) {
			this.taunts.add("Twilight Archmage: ...? What is-- no, it's too soon!");
		}
		else if(Main.frame == 1000) {
			this.taunts.add("Twilight Archmage: Wait, there's still time! I JUST NEED MORE POWER! WAIT!");
		}
		else if(Main.frame == 1600) {
			Main.gamestate = "win";
			if(Main.g1.element + Main.g2.element + Main.g3.element <= 4) {
				Main.write_list[0] = 1;
			}
			else {
				Main.write_list[1] = 1;
			}
			try {
				Main.write();
			} catch (IOException e) {
				System.out.println("win error");
			}
		}
	}
}
