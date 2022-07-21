package project;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Runtime.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener {
	//keyboard
	public static boolean[] keys = {false, false, false, false, false, false};
	
	//mouse
	public static int[] mousecoords = {400, 400};
	
	//counters, controllers
	public static String gamestate = "0";
	public static int hits = 0;
	public static int frame = 0;
	public static int bframe = 0;
	public static double[] start_bg = new double[] {0, 0, 0, 0};
	public static int cd = 2000;
	public static int am_state = 0;
	public static boolean pause = false;
	public static boolean audio = true;
	public static boolean sicken = false;
	
	//text file streaming
	//0: wins (fire), 1: wins (ice) 2: losses, 3: fire finales, 4: ice finales, 5: inferno kills, 6: blizzard kills, 7: damage dealt, 8: damage taken
	public static double[] read_list = new double[9];
	public static double[] write_list = new double[9];
	
	public static Scanner reader;
	public static PrintWriter writer;
	
	//poggers!
	public static boolean poggers = false;
	public static BufferedImage poggers_img;
	
	//da wizards
	public static Archmage archmage = new project.Archmage();
	public static Sorcerer sorc = new project.Sorcerer(800, 2250);
	
	//da birds
	public static Blizzard blizzard = new project.Blizzard();
	public static Inferno inferno = new project.Inferno();
	
	//the third of the magi !
	public static Magi_Generator g1 = new project.Magi_Generator(800, 300);
	public static Magi_Generator g2 = new project.Magi_Generator(350, 1150);
	public static Magi_Generator g3 = new project.Magi_Generator(1250, 1150);
	public static Magi_Generator[] gens = new Magi_Generator[] {g1, g2, g3};
	
	//torches
	public static Torch[] torches = new Torch[4];
	
	//background
	public static BufferedImage carpet_open;
	public static BufferedImage carpet_close;
	public static BufferedImage carpet_sicken;
	public static BufferedImage lose;
	public static BufferedImage win;
	public static BufferedImage beads;
	
	//buttons
	public static BufferedImage b0_start_0;
	public static BufferedImage b0_start_1;
	public static BufferedImage b0_howto_0;
	public static BufferedImage b0_howto_1;
	public static BufferedImage b0_credits_0;
	public static BufferedImage b0_credits_1;
	public static BufferedImage b12_back_0;
	public static BufferedImage b12_back_1;
	public static BufferedImage b2_stats_0;
	public static BufferedImage b2_stats_1;
	
	//text, titles
	public static BufferedImage b1_howto;
	public static BufferedImage b2_credits;
	public static BufferedImage b3_stats;
	public static BufferedImage[] titles;
	
	public static BufferedImage blackbox;
	
	//status effects
	public static BufferedImage[] status;
	public static BufferedImage dark_cover;
	public static BufferedImage shield;
	
	//rotation
	public static Rotate r = new project.Rotate();
	
	//audio 
	public static Audio_Player a = new project.Audio_Player();
	
	//audio symbol
	public static BufferedImage s_on;
	public static BufferedImage s_off;
	
	
	//storing the images
	public static BufferedImage[] fbe_imgs;
	public static BufferedImage[] ise_imgs;
	public static BufferedImage[] fb_imgs;
	public static BufferedImage[] is_imgs;
	public static BufferedImage[] torch_imgs = new BufferedImage[4];
	public static BufferedImage fireshade;
	public static BufferedImage[] ffw = new BufferedImage[36];
	public static double[] ffw_sizes = new double[36];
	
	public Main() throws IOException, UnsupportedAudioFileException
	{				
		//setting up jframe
		setPreferredSize(new Dimension(800, 800));
		setBackground(new Color(30, 100, 50));
		this.setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		Thread thread = new Thread(this);
		thread.start();
		
		//reading data.txt
		reader = new Scanner(new File("data.txt"));
		for(int i = 0; i < 9; i++) {
			read_list[i] = Double.parseDouble(reader.nextLine());
		}
		reader.close();
		
		//importing the images
		//five copies for each orientation
		
		//audio
		a.PlayMusic("music\\shatters_ost.wav");
		
		try {
			carpet_open = ImageIO.read(new File("background\\carpet0000.png"));
			carpet_close = ImageIO.read(new File("background\\carpet0001.png"));
			carpet_sicken = ImageIO.read(new File("background\\carpet0002.png"));
			beads = ImageIO.read(new File("background\\beads.png"));
			lose = ImageIO.read(new File("background\\lose.png"));
			win = ImageIO.read(new File("background\\win.png"));
			
			status = new BufferedImage[] {ImageIO.read(new File("status\\status_armorbreak.png")),
					ImageIO.read(new File("status\\status_burn.png")),
					ImageIO.read(new File("status\\status_darkness.png")),
					ImageIO.read(new File("status\\status_sicken.png")),
					ImageIO.read(new File("status\\status_slow.png")),
					ImageIO.read(new File("status\\status_curse.png"))};
			
			dark_cover = ImageIO.read(new File("status\\effect_darkness.png"));
			shield = ImageIO.read(new File("status\\shield.png"));
			
			b0_start_0 = ImageIO.read(new File("button\\0_start_0.png"));
			b0_start_1 = ImageIO.read(new File("button\\0_start_1.png"));
			b0_howto_0 = ImageIO.read(new File("button\\0_howto_0.png"));
			b0_howto_1 = ImageIO.read(new File("button\\0_howto_1.png"));
			b0_credits_0 = ImageIO.read(new File("button\\0_credits_0.png"));
			b0_credits_1 = ImageIO.read(new File("button\\0_credits_1.png"));
			b12_back_0 = ImageIO.read(new File("button\\12_back_0.png"));
			b12_back_1 = ImageIO.read(new File("button\\12_back_1.png"));
			b2_stats_0 = ImageIO.read(new File("button\\2_stats_0.png"));
			b2_stats_1 = ImageIO.read(new File("button\\2_stats_1.png"));
			
			b1_howto = ImageIO.read(new File("titles\\1_howto.png"));
			b2_credits = ImageIO.read(new File("titles\\2_credits.png"));
			b3_stats = ImageIO.read(new File("titles\\3_stats.png"));
			
			poggers_img = ImageIO.read(new File("wand\\pag.png"));
			blackbox = ImageIO.read(new File("archmage\\blackbox.png"));
			
			titles = new BufferedImage[] {ImageIO.read(new File("titles\\title0.png")),
					ImageIO.read(new File("titles\\title1.png")),
					ImageIO.read(new File("titles\\title2.png")),
					ImageIO.read(new File("titles\\title3.png"))};
			
			fbe_imgs = new BufferedImage[] {
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 0),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 45),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 45),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 45),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 45),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 45),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 90),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 135),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 135),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 135),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 135),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 135),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 180),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 225),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 225),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 225),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 225),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 225),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 270),
					
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion0.png")), 315),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion1.png")), 315),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion2.png")), 315),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion3.png")), 315),
					r.rotate(ImageIO.read(new File("projectiles\\firebomb_explosion\\firebomb_explosion4.png")), 315)};
			
			//six copies in this case
			ise_imgs = new BufferedImage[] {
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 0),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 0),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 30),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 30),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 30),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 30),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 30),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 30),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 60),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 60),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 60),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 60),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 60),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 60),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 90),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 90),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 120),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 120),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 120),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 120),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 120),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 120),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 150),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 150),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 150),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 150),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 150),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 150),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 180),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 180),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 210),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 210),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 210),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 210),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 210),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 210),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 240),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 240),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 240),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 240),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 240),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 240),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 270),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 270),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 300),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 300),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 300),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 300),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 300),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 300),
					
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion0.png")), 330),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion1.png")), 330),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion2.png")), 330),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion3.png")), 330),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion4.png")), 330),
					r.rotate(ImageIO.read(new File("projectiles\\icesphere_explosion\\icesphere_explosion5.png")), 330)};
			
			//bombs
			fb_imgs = new BufferedImage[] {ImageIO.read(new File("projectiles\\firebomb\\firebomb0.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb1.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb2.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb3.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb4.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb5.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb6.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb7.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb8.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb7.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb8.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb7.png")),
					ImageIO.read(new File("projectiles\\firebomb\\firebomb8.png"))};
			
			is_imgs = new BufferedImage[]{ImageIO.read(new File("projectiles\\icesphere\\icesphere0.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere1.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere2.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere3.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere4.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere5.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere4.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere5.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere4.png")),
					ImageIO.read(new File("projectiles\\icesphere\\icesphere5.png"))};
			
			
			//torch images
			torches = new Torch[] {new project.Torch(670, 1650), 
					new project.Torch(930, 1650), 
					new project.Torch(670, 1850), 
					new project.Torch(930, 1850),
					new project.Torch(670, 2050), 
					new project.Torch(930, 2050),
					new project.Torch(670, 2250), 
					new project.Torch(930, 2250)};
			fireshade = ImageIO.read(new File("torch\\fireshade.png"));
			s_on = ImageIO.read(new File("speaker\\sound_on.png"));
			s_off = ImageIO.read(new File("speaker\\sound_off.png"));
			
			for(int i = 0; i < 36; i++) {
				ffw[i] = r.rotate(ImageIO.read(new File("projectiles\\firewave.png")), 10 * i);
				ffw_sizes[i] = (int)(30 * (0.207107 * Math.cos(Math.toRadians(4 * (10 * i)) + Math.PI) + 1.207107));
			}
		}
		catch(Exception e) {
			
		}
	}
	
	//run method
	public void run()
	{
		while(true)
		{
			repaint();
			try
			{	
				Thread.sleep(8);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//collision function - pythagorean theorem
	public boolean collision(double x1, double y1, double x2, double y2, double radius) {
		return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) < (radius * radius);
	}
	
	public void paintComponent(Graphics g)
	{
		//handles audio
		if(audio) {
			a.c.start();
		}
		else {
			a.c.stop();
		}
		
		if(!pause) {
			//clear screen
			super.paintComponent(g);
			
			if(gamestate.equals("lose")) {
				g.drawImage(lose, 0, 0, 800, 800, this);
			}
			
			else if(gamestate.equals("win")) {
				g.drawImage(win, 0, 0, 800, 800, this);
				if(poggers) {
					if(mousecoords[0] > 270 && mousecoords[0] < 570 && mousecoords[1] > 400 && mousecoords[1] < 670) {
						g.drawImage(poggers_img, 280, 370, 300, 300, this);
					}
				}
			}
			
			//main game
			else if(gamestate.equals("carpet_close")) {
				//add frames
				if(cd > 0) {
					cd -= 1;
					//intro text
					if(cd == 1750) {
						archmage.taunts.add("Twilight Archmage: Eheh…heh…eheheh! Oh, it must've took quite some power to bring me back here!");
					}
					if(cd == 1250) {
						archmage.taunts.add("Twilight Archmage: But if you got yourself here…then that spiteful doorman has dropped his post! SPLENDID!");
					}
					if(cd == 750) {
						archmage.taunts.add("Twilight Archmage: Yes yes, that is just grand! Allow me to show my heartfelt gratitude!");
					}
					if(cd == 250) {
						archmage.taunts.add("Twilight Archmage: After all, you've already brought me the tools...");
					}
				}
				else {
					frame++;
				}
				
				//draw background
				if(sicken) {
					g.drawImage(carpet_sicken, (int)(400 - sorc.x - 800), (int)(400 - sorc.y - 800), 3200, 4000, this);
				}
				else {
					g.drawImage(carpet_close, (int)(400 - sorc.x - 800), (int)(400 - sorc.y - 800), 3200, 4000, this);
				}
				
				//draw generator attacks	
				for(int i = 0; i < archmage.pink_shields.size(); i++) {
					Pink_Shield q = archmage.pink_shields.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.pink_shields.remove(q);
						sorc.damage(50, 5, 375);
						//curse for 3.00 seconds
					}
				}
				for(int i = 0; i < archmage.blue_shields.size(); i++) {
					Blue_Shield q = archmage.blue_shields.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.blue_shields.remove(q);
						sorc.damage(75, 0, 375);
						//armor break for 3.00 seconds
					}
				}
				
				//draw generators
				for(int i = 0; i < gens.length; i++) {
					Magi_Generator q = gens[i];
					q.animate();
					g.drawImage(q.img, (int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y - 40), 80, 80, this);
					
					//healthbars
					if(q.vulnerable && !q.sealed) {
						g.setColor(new Color(0,0,0));
						g.fillRect ((int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y + 50), 80, 5);
						g.setColor(new Color(150, 200, 150));
						g.fillRect((int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y + 50), (int)(80 * q.health[0]/q.health[1]), 5);
					}
					
					if(!q.vulnerable || q.sealed) {
						g.drawImage(shield, (int)(400 - sorc.x + q.x - 8), (int)(400 - sorc.y + q.y - 60), 16, 16, this);
					}
				}
				
				//draw portals
				for(int i = 0; i < archmage.fire_portals.size(); i++) {
					Fire_Portal q = archmage.fire_portals.get(i);
					q.animate();
					g.drawImage(q.img, (int)(400 - sorc.x + q.x - 30), (int)(400 - sorc.y + q.y - 30), 60, 60, this);
				}
				for(int i = 0; i < archmage.ice_portals.size(); i++) {
					Ice_Portal q = archmage.ice_portals.get(i);
					q.animate();
					g.drawImage(q.img, (int)(400 - sorc.x + q.x - 30), (int)(400 - sorc.y + q.y - 30), 60, 60, this);
				}
				
				//draw wand shots
				for(int i = 0; i < sorc.catashots.size(); i++) {
					Catalyst q = sorc.catashots.get(i);
					q.move();
					//bullets dont appear behind player
					if(poggers) {
						if(q.init_life - q.lifetime > 0) {
							g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
						}
					}
					else {
						if(q.init_life - q.lifetime > 1) {
							g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
						}
					}
					if(collision(q.x + q.size/2, q.y + q.size/2, 810, 830, 40)) {
						if(archmage.vulnerable && archmage.phase < 7) {
							//pop text placeholder
							int d = ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							if(archmage.phase != 7) {
								write_list[7] += d;
							}
							archmage.health[0] -= d;
							sorc.catashots.remove(q);
						}
					}
					for(int j = 0; j < gens.length; j++) {
						if(collision(q.x + q.size/2, q.y + q.size/2, gens[j].x, gens[j].y, 40)) {
							if(gens[j].vulnerable && !gens[j].sealed) {
								int d = ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
								write_list[7] += d;
								gens[j].health[0] -= d;
								sorc.catashots.remove(q);
								if(gens[j].health[0] <= 0) {
									gens[j].sealed = true;
								}
							}
						}
					}
					
					if(collision(q.x + q.size/2, q.y + q.size/2, blizzard.x, blizzard.y, 45)) {
						if(blizzard.vulnerable && blizzard.health[0] > 0) {
							int d = ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							write_list[7] += d;
							blizzard.health[0] -= d;
							sorc.catashots.remove(q);
						}
					}
					
					if(collision(q.x + q.size/2, q.y + q.size/2, inferno.x, inferno.y, 45)) {
						if(inferno.vulnerable && inferno.health[0] > 0) {
							int d = ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							write_list[7] += d;
							inferno.health[0] -= d;
							sorc.catashots.remove(q);
						}
					}
				}
				
				//draw projectiles
				for(int i = 0; i < archmage.ice_twirls.size(); i++) {
					Ice_Twirl q = archmage.ice_twirls.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_twirls.remove(q);
						sorc.damage(150, -1, 0);
						
					}
				}
				for(int i = 0; i < archmage.fire_twirls.size(); i++) {
					Fire_Twirl q = archmage.fire_twirls.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_twirls.remove(q);
						sorc.damage(150, -1, 0);
					}
				}
				for(int i = 0; i < archmage.ice_vortexes.size(); i++) {
					Ice_Vortex q = archmage.ice_vortexes.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_vortexes.remove(q);
						sorc.damage(300, -1, 125);
						//sorc.damage(300, 2, 125);
						//darkness for 1.00 seconds + lag
					}
				}
				for(int i = 0; i < archmage.fire_vortexes.size(); i++) {
					Fire_Vortex q = archmage.fire_vortexes.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_vortexes.remove(q);
						sorc.damage(100, 1, 200);
						//burn for 1.60 seconds + 50 damage
					}
				}
				for(int i = 0; i < archmage.ice_waves.size(); i++) {
					Ice_Wave q = archmage.ice_waves.get(i);
					if(frame % 3 == 0) {
						q.move();
						
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_waves.remove(q);
						if(archmage.phase == 6) {
							sorc.damage(200, -1, 0);
						}
						else {
							sorc.damage(200, 4, 200);
						}
						//slow for 1.60 seconds
						//except in phase 6
						
					}
				}
				for(int i = 0; i < archmage.fire_waves.size(); i++) {
					Fire_Wave q = archmage.fire_waves.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_waves.remove(q);
						sorc.damage(200, -1, 0);
					}
				}
				for(int i = 0; i < archmage.blue_balls.size(); i++) {
					Blue_Ball q = archmage.blue_balls.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.blue_balls.remove(q);
						sorc.damage(100, 4, 125);
						//slowed for 1.00 seconds
					}
				}
				for(int i = 0; i < archmage.fire_bolts.size(); i++) {
					Fire_Bolt q = archmage.fire_bolts.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					g.setColor(new Color(255, 255, 255));
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_bolts.remove(q);
						sorc.damage(150, 0, 500);
						//armour broken for 4.00 seconds
					}
				}
				for(int i = 0; i < archmage.ice_bolts.size(); i++) {
					Ice_Bolt q = archmage.ice_bolts.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_bolts.remove(q);
						sorc.damage(175, 5, 375);
						//curse for 3.00 seconds
					}
				}
				for(int i = 0; i < archmage.pink_balls.size(); i++) {
					Pink_Ball q = archmage.pink_balls.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					g.setColor(new Color(255, 255, 255));
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.pink_balls.remove(q);
						sorc.damage(225, -1, 0);
					}
				}
				for(int i = 0; i < archmage.ice_stars.size(); i++) {
					Ice_Star q = archmage.ice_stars.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_stars.remove(q);
						sorc.damage(200, -1, 0);
					}
				}
				for(int i = 0; i < archmage.ice_shields.size(); i++) {
					Ice_Shield q = archmage.ice_shields.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_shields.remove(q);
						sorc.damage(200, 0, 500);
						//armour broken for 4.00 seconds
					}
				}
				for(int i = 0; i < archmage.ice_spears.size(); i++) {
					Ice_Spear q = archmage.ice_spears.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_spears.remove(q);
						sorc.damage(125, -1, 0);
					}
				}
				
				for(int i = 0; i < archmage.ice_daggers.size(); i++) {
					Ice_Dagger q = archmage.ice_daggers.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_daggers.remove(q);
						sorc.damage(200, 5, 300);
						//curse for 2.40 seconds
					}
				}
				
				for(int i = 0; i < archmage.fire_daggers.size(); i++) {
					Fire_Dagger q = archmage.fire_daggers.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_daggers.remove(q);
						sorc.damage(200, 0, 375);
						//armor break for 3.00 seconds
					}
				}
				
				for(int i = 0; i < archmage.fire_finale_waves.size(); i++) {
					Fire_Finale_Wave q = archmage.fire_finale_waves.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_finale_waves.remove(q);
						sorc.damage(200, -1, 0);
					}
				}
				
				//draw bomb explosions
				for(int i = 0; i < archmage.fire_bomb_explosions.size(); i++) {
					Fire_Bomb_Explosion q = archmage.fire_bomb_explosions.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.fire_bomb_explosions.remove(q);
						sorc.damage(275, 0, 250);
						//armour broken for 2.00 seconds
					}
				}
				for(int i = 0; i < archmage.ice_sphere_explosions.size(); i++) {
					Ice_Sphere_Explosion q = archmage.ice_sphere_explosions.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					if(collision(sorc.x, sorc.y, q.x + q.size/2, q.y + q.size/2, q.size/2 + 14)) {
						archmage.ice_sphere_explosions.remove(q);
						sorc.damage(200, 4, 50);
						//slow for 0.40 seconds
					}
				}
				
				//draw birds
				if(!blizzard.movement.equals("hidden")) {
					blizzard.move();
					blizzard.animate();
					if(blizzard.health[0] > 0) {
						g.drawImage(blizzard.img, (int)(400 - sorc.x + blizzard.x) - 45, (int)(400 - sorc.y + blizzard.y) - 45, 90, 90, this);
					}
					
					//healthbar
					if(blizzard.vulnerable && blizzard.health[0] > 0) {
						g.setColor(new Color(0,0,0));
						g.fillRect ((int)(400 - sorc.x + blizzard.x - 40), (int)(400 - sorc.y + blizzard.y + 50), 80, 5);
						g.setColor(new Color(150, 200, 150));
						g.fillRect((int)(400 - sorc.x + blizzard.x - 40), (int)(400 - sorc.y + blizzard.y + 50), (int)(80 * blizzard.health[0]/blizzard.health[1]), 5);
					}						
				}
				if(!inferno.movement.equals("hidden")) {
					inferno.move();
					inferno.animate();
					if(inferno.health[0] > 0) {
						g.drawImage(inferno.img, (int)(400 - sorc.x + inferno.x) - 45, (int)(400 - sorc.y + inferno.y) - 45, 90, 90, this);
					}
					
					//healthbar
					if(inferno.vulnerable && inferno.health[0] > 0) {
						g.setColor(new Color(0,0,0));
						g.fillRect ((int)(400 - sorc.x + inferno.x - 40), (int)(400 - sorc.y + inferno.y + 50), 80, 5);
						g.setColor(new Color(150, 200, 150));
						g.fillRect((int)(400 - sorc.x + inferno.x - 40), (int)(400 - sorc.y + inferno.y + 50), (int)(80 * inferno.health[0]/inferno.health[1]), 5);
					}
				}
				
				g.setColor(new Color(255, 255, 255));
				
				//draw bombs
				for(int i = 0; i < archmage.fire_bombs.size(); i++) {
					Fire_Bomb q = archmage.fire_bombs.get(i);
					q.animate();
					
					if(q.lifetime < 156) {
						g.drawImage(q.img, (int)(400 - sorc.x + q.x) - q.size/2, (int)(400 - sorc.y + q.y) - q.size/2, q.size, q.size, this);
					}
					
					//bomb particles
					if(q.lifetime > 145) {
						for(int j = 0; j < 8; j++) {
							double v = 0.5 * Math.random() + (194.0-(double)q.lifetime)/32.0;
							int s = ThreadLocalRandom.current().nextInt(5, 9);
							g.fillRect((int)(400 - sorc.x + bp_value(q.x-800, q.y-800, v)[0]), (int)(400 - sorc.y + bp_value(q.x-800, q.y-800, v)[1]), s, s);
						}
					}
				}
				
				for(int i = 0; i < archmage.ice_spheres.size(); i++) {
					Ice_Sphere q = archmage.ice_spheres.get(i);
					q.animate();
					if(q.lifetime < 150) {
						g.drawImage(q.img, (int)(400 - sorc.x + q.x) - q.size/2, (int)(400 - sorc.y + q.y) - q.size/2, q.size, q.size, this);
					}
					
					//bomb particles
					if(q.lifetime > 139) {
						for(int j = 0; j < 8; j++) {
							double v = 0.5 * Math.random() + (186.0-(double)q.lifetime)/32.0;
							int s = ThreadLocalRandom.current().nextInt(5, 9);
							g.fillRect((int)(400 - sorc.x + bp_value(q.x-800, q.y-800, v)[0]), (int)(400 - sorc.y + bp_value(q.x-800, q.y-800, v)[1]), s, s);
						}
					}
				}
				
				/*archmage attacks:
				Fire Majority: archmage.fireblasts(), archmage.firevomits()
				Fire Nuke: archmage.firewalls(), archmage.firepopcorns()
				Ice Majority: archmage.iceportals(), archmage.icerotates()
				Ice Nuke: archmage.icearenas(), archmage.icebombs()
				
				archmage.fire/icestagger()
				archmage.fire/icedoublestagger()
				archmage.fire/icefinale_prep()
				archmage.fire/icefinale()
				 */
				
				//archmage
				archmage.main();
				archmage.phase();
				//starting up
				if(am_state == 0) {
					blizzard.movement = "hidden";
					inferno.movement = "hidden";
					if(frame % 60 == 1) {
						if(archmage.phase == 0 || archmage.phase == 2 || archmage.phase == 4) {
							archmage.d_channel();
						}
					}
					if(frame == 1) {
						if(archmage.phase == 0 || archmage.phase == 2 || archmage.phase == 4) {
							if(!g1.sealed) {
								g1.element = 0;
							}
							if(!g2.sealed) {
								g2.element = 0;
							}
							if(!g3.sealed) {
								g3.element = 0;
							}
						}
						
						if(archmage.phase == 4) {
							if(inferno.health[0] <= 0) {
								write_list[5] += 1;
								inferno.health[0] = inferno.health[1];
							}
							if(blizzard.health[0] <= 0) {
								write_list[6] += 1;
								blizzard.health[0] = blizzard.health[1];
							}
						}
					}
					
					if(frame == 300) {
						if(archmage.phase == 0 || archmage.phase == 2 || archmage.phase == 4) {
							g1.choose_element();
							g2.choose_element();
							g3.choose_element();
						}
						
						//3 fire
						if(g1.element + g2.element + g3.element == 3) {
							if(Math.random() < 0.5) {
								archmage.attack = "firewalls";
							}
							else {
								archmage.attack = "firepopcorns";
							}
						}
						//2 fire 1 ice
						else if(g1.element + g2.element + g3.element == 4) {
							if(Math.random() < 0.5) {
								archmage.attack = "fireblasts";
							}	
							else {
								archmage.attack = "firevomits";
							}
						}
						//2 ice 1 fire
						else if(g1.element + g2.element + g3.element == 5) {
							if(Math.random() < 0.5) {
								archmage.attack = "iceportals";
							}
							else {
								archmage.attack = "icerotates";
							}
						}
						//3 ice
						else if(g1.element + g2.element + g3.element == 6) {
							if(Math.random() < 0.5) {
								archmage.attack = "icebombs";
							}
							else {
								archmage.attack = "icearenas";								
							}
						}
						
						//check for other phases
						if(archmage.phase == 1) {
							archmage.attack = "sicken1";
							am_state = 3;
						}
						else if(archmage.phase == 3) {
							archmage.attack = "sicken2";
							am_state = 3;
						}
						else if(archmage.phase == 5) {
							archmage.attack = "sicken3";
							am_state = 3;
						}
					}
					
					if(frame == 350) {
						if(archmage.phase == 0) {
							int d = ThreadLocalRandom.current().nextInt(0, 5);
							if(d == 0) {
								archmage.taunts.add("Twilight Archmage: Ahhhhh yes…let’s see what fate chose for you.");
							}
							else if(d == 1) {
								archmage.taunts.add("Twilight Archmage: Stay put now, you’ll love this next spell!");
							}
							else if(d == 2) {
								archmage.taunts.add("Twilight Archmage: Now, what shall I conjure up with this?");
							}
							else if(d == 3) {
								archmage.taunts.add("Twilight Archmage: Eeeheheheheh! It has been too long since I’ve felt this!");
							}
							else {
								archmage.taunts.add("Twilight Archmage: You’ll find that it was… unwise… to wake me.");
							}
						}
						else if(archmage.phase == 2) {
							if(g1.element + g2.element + g3.element <= 4) {
								if(archmage.taunts.get(archmage.taunts.size()-1).equals("Twilight Archmage: You… you impetuous oaf, you sealed the generator! Fine then, let us do this the slow way…")) {
									archmage.taunts.add("Twilight Archmage: Inferno, incinerate them!");
								}
							}
							else {
								if(archmage.taunts.get(archmage.taunts.size()-1).equals("Twilight Archmage: You… you impetuous oaf, you sealed the generator! Fine then, let us do this the slow way…")) {
									archmage.taunts.add("Twilight Archmage: Freeze the life from our guests, Blizzard!");
								}
							}
						}
						else if(archmage.phase == 4) {
							if(archmage.taunts.get(archmage.taunts.size()-1).equals("Twilight Archmage: You really are dragging this out, you know. So much for making it painless! Eheheeeeh!")) {
								archmage.taunts.add("Twilight Archmage: Inferno, Blizzard, to me!");
							}
						}
					}
					
					//generator particles
					if(frame > 300) {
						generator_particles(15, g);
					}
					
					if(frame == 600) {
						if(archmage.phase == 0) {
							archmage.vulnerable = true;
						}
						am_state = 1;
						frame = -1;
					}					
				}
				//regular attacks
				else if(am_state == 1) {
					
					//funny words, magic man!
					if(frame == 1) {
						if(archmage.attack.equals("fireblasts")) {
							archmage.taunts.add("Twilight Archmage: Firos Xiflis’TOh Dristatiks Quantinoftus!");
						}
						else if(archmage.attack.equals("firevomits")) {
							archmage.taunts.add("Twilight Archmage: Firos Xiflis’TOh Gantsualo Quantinoftus!");
						}
						else if(archmage.attack.equals("firepopcorns")) {
							archmage.taunts.add("Twilight Archmage: Firos Xiflis’TOh Wavunwavun Quantinoftus!");	
						}
						else if(archmage.attack.equals("firewalls")) {
							archmage.taunts.add("Twilight Archmage: Firos Xiflis’TOh Pridigioc Quantinoftus!");
						}
						else if(archmage.attack.equals("iceportals")) {
							archmage.taunts.add("Twilight Archmage: Toahc Xifls’ToH Rigroatah Qualtinoc!");
						}
						else if(archmage.attack.equals("icerotates")) {
							archmage.taunts.add("Twilight Archmage: Toahc Xifls’ToH Puroktis Qualtinoc!");
						}
						else if(archmage.attack.equals("icearenas")) {
							archmage.taunts.add("Twilight Archmage: Toahc Xifls’ToH Nohsdrol Qualtinoc!");
						}
						else if(archmage.attack.equals("icebombs")) {
							archmage.taunts.add("Twilight Archmage: Toahc Xifls’ToH Merilmeril Qualtinoc!");
						}
					}
						
					//lag time
					if(frame < 600) {
						generator_particles((int)(15-frame/40), g);
					}
					
					if(archmage.attack.substring(0, 4).equals("fire")){
						if(archmage.phase == 2) {
							inferno.vulnerable = true;
							if(inferno.health[0] <= 0) {
								reset();
								write_list[5] += 1;
								am_state = 2;
								archmage.attack = "firestagger";
								archmage.vulnerable = true;
								inferno.health[0] = inferno.health[1];
								blizzard.health[0] = blizzard.health[1];
								inferno.movement = "hidden";
								archmage.taunts.add("Twilight Archmage: ...");
							}
						}
						else if(archmage.phase == 4) {
							inferno.vulnerable = true;
							blizzard.vulnerable = true;
							if(inferno.health[0] <= 0 && blizzard.health[0] <= 0) {
								reset();
								am_state = 2;
								write_list[5] += 1;
								write_list[6] += 1;
								archmage.attack = "firedoublestagger";
								archmage.vulnerable = true;
								inferno.health[0] = inferno.health[1];
								blizzard.health[0] = blizzard.health[1];
								inferno.movement = "hidden";
								blizzard.movement = "hidden";
								archmage.taunts.add("Twilight Archmage: ...");
							}
						}			
						
						if(archmage.attack.equals("firewalls")) {
							//1200
							archmage.firewalls();
							//2399 so no extra waves come out
							if(frame == 2399) {
								reset();
							}
							
							if(archmage.phase == 2) {
								inferno.movement = "cross";
							}
							if(archmage.phase == 4) {
								inferno.movement = "cross";
								blizzard.movement = "inner_circle_l";
							}
						}
						else if(archmage.attack.equals("firepopcorns")) {
							//400
							archmage.firepopcorns();
							if(frame == 2800) {
								reset();
							}
							
							if(archmage.phase == 2) {
								inferno.movement = "middle_circle_r";
							}
							if(archmage.phase == 4) {
								inferno.movement = "middle_circle_r";
								blizzard.movement = "cross";
							}
						}
						else if(archmage.attack.equals("firevomits")) {
							//100
							archmage.firevomits();
							if(frame == 2800) {
								reset();
							}
							
							if(archmage.phase == 2) {
								inferno.movement = "outer_circle_r";
							}
							if(archmage.phase == 4) {
								inferno.movement = "outer_circle_r";
								blizzard.movement = "middle_circle_l";
							}
						}
						else if(archmage.attack.equals("fireblasts")) {
							//480
							archmage.fireblasts();
							if(frame == 2880) {
								reset();
							}
							
							//two crosses
							if(archmage.phase == 2) {
								inferno.movement = "inner_circle_r";
							}
							if(archmage.phase == 4) {
								inferno.movement = "inner_circle_r";
								blizzard.movement = "cross";
							}
						}
					}
					else if(archmage.attack.substring(0, 3).equals("ice")) {
						if(archmage.phase == 2) {
							blizzard.vulnerable = true;
							if(blizzard.health[0] <= 0) {
								reset();
								write_list[6] += 1;
								am_state = 2;
								archmage.attack = "icestagger";
								archmage.vulnerable = true;
								inferno.health[0] = inferno.health[1];
								blizzard.health[0] = blizzard.health[1];
								blizzard.movement = "hidden";
								archmage.taunts.add("Twilight Archmage: ...");
							}
						}
						else if(archmage.phase == 4) {
							inferno.vulnerable = true;
							blizzard.vulnerable = true;
							if(inferno.health[0] <= 0 && blizzard.health[0] <= 0) {
								reset();
								write_list[5] += 1;
								write_list[6] += 1;
								am_state = 2;	
								archmage.attack = "icedoublestagger";
								archmage.vulnerable = true;
								inferno.health[0] = inferno.health[1];
								blizzard.health[0] = blizzard.health[1];
								inferno.movement = "hidden";
								blizzard.movement = "hidden";
								archmage.taunts.add("Twilight Archmage: ...");
							}
						}
						
						if(archmage.attack.equals("icearenas")) {
							//600
							archmage.icearenas();
							if(frame == 3000) {
								reset();
							}
							
							if(archmage.phase == 2) {
								blizzard.movement = "inner_circle_l";
							}
							if(archmage.phase == 4) {
								blizzard.movement = "inner_circle_l";
								inferno.movement = "middle_circle_r";
							}
						}
						else if(archmage.attack.equals("icebombs")) {
							//160
							archmage.icebombs();
							if(frame == 3200) {
								reset();
							}
							
							if(archmage.phase == 2) {
								blizzard.movement = "cross";
							}
							if(archmage.phase == 4) {
								blizzard.movement = "cross";
								inferno.movement = "inner_circle_r";
							}
						}
						else if(archmage.attack.equals("iceportals")) {
							//250, 350
							archmage.iceportals();
							if(frame == 3500) {
								reset();
							}
							
							if(archmage.phase == 2) {
								blizzard.movement = "middle_circle_l";
							}
							if(archmage.phase == 4) {
								blizzard.movement = "middle_circle_l";
								inferno.movement = "outer_circle_r";
							}
						}
						else if(archmage.attack.equals("icerotates")) {
							//475
							archmage.icerotates();
							if(frame == 3325) {
								reset();
							}
							
							if(archmage.phase == 2) {
								blizzard.movement = "outer_circle_l";
							}
							if(archmage.phase == 4) {
								blizzard.movement = "outer_circle_l";
								inferno.movement = "cross";
							}
						}
					}
				}
				//staggers
				else if(am_state == 2) {
					if(archmage.attack.equals("firestagger")) {
						archmage.firestagger();
					}
					else if(archmage.attack.equals("icestagger")) {
						archmage.icestagger();
					}
					else if(archmage.attack.equals("firedoublestagger")) {
						archmage.firedoublestagger();
					}
					else if(archmage.attack.equals("icedoublestagger")) {
						archmage.icedoublestagger();
					}
					
					//time limit reached or damage cap reached
					if(frame == 1500) {
						
						//recover stagger
						if(archmage.health[0] < archmage.stagger_cap) {
							archmage.health[0] = archmage.stagger_cap;
						}
						
						//reset stagger cap
						archmage.stagger_cap = 0;
						reset();
					}			
					if(archmage.health[0] < archmage.stagger_cap) {
						//flash
						archmage.flash = 35;
						
						//recover stagger
						if(archmage.health[0] < archmage.stagger_cap) {
							archmage.health[0] = archmage.stagger_cap;
						}
						
						//reset stagger cap
						archmage.stagger_cap = 0;
						reset();
					}
				}
				//sickens (generator phases)
				else if(am_state == 3) {
					sicken = true;
					generator_particles(15, g);
					
					if(frame == 350) {
						if(archmage.phase == 1) {
							archmage.taunts.add("Twilight Archmage: Let’s put that to good use… BEHOLD! Your new place in my king’s forces!");
						}
						if(archmage.phase == 3) {
							archmage.taunts.add("Twilight Archmage: But it’s never too late to change that, wouldn’t you say?");
						}
						if(archmage.phase == 5) {
							archmage.taunts.add("Twilight Archmage: MY LORD! Their souls are yours to reap! COME TO ME!");
						}
					}
					
					if(archmage.attack.equals("sicken1")) {
						archmage.sicken1();
					}
					else if(archmage.attack.equals("sicken2")) {
						archmage.sicken2();
					}
					else if(archmage.attack.equals("sicken3")) {
						archmage.sicken3();
					}
				}
				
				//pre-finales and finales (indefinite phases)
				else if(am_state == 4) {
					if(archmage.attack.equals("firefinale_prep")) {
						archmage.firefinale_prep();
					}
					else if(archmage.attack.equals("icefinale_prep")) {
						archmage.icefinale_prep();
					}
					else if(archmage.attack.equals("firefinale")) {
						archmage.firefinale();
					}
					else if(archmage.attack.equals("icefinale")) {
						archmage.icefinale();
					}
				}
				
				//death
				else if(am_state == 5) {
					if(archmage.attack.equals("death")) {
						archmage.death();
					}
				}
				
				if(archmage.phase != 7 || frame <= 1200) {
					g.drawImage(archmage.img, (int)(400 - sorc.x + archmage.x) - 75, (int)(400 - sorc.y + archmage.y) - 100, 150, 150, this);
				}
				
				//darkness cover
				if(sorc.status[2] > 0) {
					g.drawImage(dark_cover, 0, 0, 800, 800, this);
				}
				
				//archmage health bar
				if(archmage.phase < 7) {
					g.setFont(new Font("Helvetica", Font.PLAIN, 18));
					g.setColor(new Color(70,70,70));
					g.fillRect (480, 35, 300, 24);
					if(g instanceof Graphics2D){
				        Graphics2D g2 = (Graphics2D)g;
				        g2.setColor(new Color(255, 255, 255));
				        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				        RenderingHints.VALUE_ANTIALIAS_ON);
			
				        g2.drawString("Twilight Archmage", 484, 25); 
				    }
					double pc = 300.0 * (double)archmage.health[0]/(double)archmage.health[1];
					if(cd > 1775 && cd < 1900) {
						//two variables bcs arithmetic is hard
						double pd = (double)(cd-1775)/125.0;
						double id = 1 - pd;
						//colour transition
						g.setColor(new Color((int) (60 * id + 70 * pd), (int) (120 * id + 70 * pd), (int) (220 * id + 70 * pd)));
						g.fillRect(480, 35, (int)(300 * id), 24);
					}
					else if(cd >= 1900) {
						//do nothing!
					}
					else {
						if(archmage.vulnerable) {
							g.setColor(new Color(120, 220, 60));
							g.fillRect(480, 35, (int)pc, 24);
						}
						else {
							g.drawImage(shield, 750, 12, 16, 16, this);
							g.setColor(new Color(60, 120, 220));
							g.fillRect(480, 35, (int)pc, 24);
						}
					}
				}
				
				//sorc
				sorc.move(keys);
				sorc.shoot(keys);
				sorc.check_status();
				sorc.animate();
				
				//drawing sorc
				if(sorc.dir != 1 && sorc.dir != 3 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 386, 386, 28, 28, this);
				}
				else if(sorc.dir == 1 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 376, 386, 38, 28, this);
				}
				else if(sorc.dir == 3 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 386, 386, 38, 28, this);
				}
				else {
					g.drawImage(sorc.img, 386, 386, 28, 28, this);
				}
				
				if(frame % 10 == 0 && sorc.status[3] == 0 && sorc.status[1] == 0) {
					sorc.health[0] += sorc.health[1] * 0.004;
					if(sorc.health[0] > sorc.health[1]) {
						sorc.health[0] = sorc.health[1];
					}
				}
				
				//sorc healthbar 
				g.setColor(new Color(0,0,0));
				g.fillRect (382, 420, 32, 5);
				if(sorc.status[1] > 0) {
					g.setColor(new Color(255, 200, 90));
				}
				else {
					g.setColor(new Color(150, 200, 150));
				}
				g.fillRect(382, 420, (int)(32 * sorc.health[0]/sorc.health[1]), 5);
				int r = 0;
				for(int i = 0; i < status.length; i++) {
					if(sorc.status[i] > 0) {
						//account for sicken
						if(i != 3) {
							g.drawImage(status[i], 401 - get_cc() * 7 + 14 * r, 372, 12, 12, this);
						}
						else {
							g.drawImage(status[i], 401 - get_cc() * 7 + 14 * r, 371, 12, 12, this);
						}
						r++;
					}
				}
				
				//draw poptext
				for(int i = 0; i < sorc.poptext.size(); i++) {
					Pop_Text q = sorc.poptext.get(i);
					if(frame % 3 == 0) {
						q.move();
					}
					g.setFont(new Font("Helvetica", Font.BOLD, 18));
					if(g instanceof Graphics2D){
				        Graphics2D g2 = (Graphics2D)g;
				        g.setColor(q.c);
				        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				        RenderingHints.VALUE_ANTIALIAS_ON);
			
				        g2.drawString(q.text, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y)); 
				    }
				}
				
				//draw taunts
				g.drawImage(blackbox, 0, 625, 700, 175, this);
				for(int row = 1; row <= 5; row++) {
					g.setFont(new Font("Helvetica", Font.PLAIN, 14));
					if(g instanceof Graphics2D){
				        Graphics2D g2 = (Graphics2D)g;
				        g.setColor(new Color(255, 255, 255));
				        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				        RenderingHints.VALUE_ANTIALIAS_ON);
				        	        
				        if(archmage.taunts.size() - row >= 0) {
				        	g2.drawString(archmage.taunts.get(archmage.taunts.size() - row), 10, 800 - 27 * row); 
				        }
				    }
				}
				
				//check if sorc dead
				if(sorc.health[0] < 0) {
					gamestate = "lose";
					write_list[2] = 1;
					
					try {
						write();
					} catch (IOException e) {
						System.out.println("loss error");
					}
				}
			}
			
			//hall
			else if(gamestate.equals("carpet_open")) {
				//add frames
				frame++;
				
				//draw background
				g.drawImage(carpet_open, (int)(400 - sorc.x - 800), (int)(400 - sorc.y - 800), 3200, 4000, this);
				
				//draw generators
				for(int i = 0; i < gens.length; i++) {
					Magi_Generator q = gens[i];
					q.animate();
					g.drawImage(q.img, (int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y - 40), 80, 80, this);
					
					//healthbars
					if(q.vulnerable && !q.sealed) {
						g.setColor(new Color(0,0,0));
						g.fillRect ((int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y + 50), 80, 5);
						g.setColor(new Color(150, 200, 150));
						g.fillRect((int)(400 - sorc.x + q.x - 40), (int)(400 - sorc.y + q.y + 50), (int)(80 * q.health[0]/q.health[1]), 5);
					}
				}
				
				for(int i = 0; i < sorc.catashots.size(); i++) {
					Catalyst q = sorc.catashots.get(i);
					q.move();
					//bullets dont appear behind player
					if(q.init_life - q.lifetime > 1) {
						g.drawImage(q.img, (int)(400 - sorc.x + q.x), (int)(400 - sorc.y + q.y), q.size, q.size, this);
					}
					/*if(collision(q.x + q.size/2, q.y + q.size/2, 810, 830, 40)) {
						if(archmage.vulnerable) {
							archmage.health[0] -= ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							sorc.catashots.remove(q);
						}
					}
					for(int j = 0; j < gens.length; j++) {
						if(collision(q.x + q.size/2, q.y + q.size/2, gens[j].x, gens[j].y, 40)) {
							if(gens[j].vulnerable && !gens[j].sealed) {
								gens[j].health[0] -= ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
								sorc.catashots.remove(q);
								if(gens[j].health[0] <= 0) {
									gens[j].sealed = true;
								}
							}
						}
					}
					
					if(collision(q.x + q.size/2, q.y + q.size/2, blizzard.x, blizzard.y, 45)) {
						if(blizzard.vulnerable && blizzard.health[0] > 0) {
							blizzard.health[0] -= ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							sorc.catashots.remove(q);
						}
					}
					
					if(collision(q.x + q.size/2, q.y + q.size/2, inferno.x, inferno.y, 45)) {
						if(inferno.vulnerable && inferno.health[0] > 0) {
							inferno.health[0] -= ThreadLocalRandom.current().nextInt(sorc.dmg[0], sorc.dmg[1]);
							sorc.catashots.remove(q);
						}
					}*/
				}
				//torches
				for(int i = 0; i < torches.length; i++) {
					Torch q = torches[i];
					q.animate();
					g.drawImage(fireshade, (int)(400 - sorc.x + q.x - 35), (int)(400 - sorc.y + q.y - 25), 70, 70, this);
					g.drawImage(q.img, (int)(400 - sorc.x + q.x - 35), (int)(400 - sorc.y + q.y - 35), 70, 70, this);

				}
				
				//archmage
				g.drawImage(archmage.img, (int)(400 - sorc.x + archmage.x) - 75, (int)(400 - sorc.y + archmage.y) - 100, 150, 150, this);	
				
				//archmage healthbar not drawn
				
				//sorc
				sorc.move(keys);
				sorc.shoot(keys);
				sorc.animate();
				
				//drawing sorc
				if(sorc.dir != 1 && sorc.dir != 3 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 386, 386, 28, 28, this);
				}
				else if(sorc.dir == 1 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 376, 386, 38, 28, this);
				}
				else if(sorc.dir == 3 && sorc.fire[0] > sorc.fire[1]/2) {
					g.drawImage(sorc.img, 386, 386, 38, 28, this);
				}
				else {
					g.drawImage(sorc.img, 386, 386, 28, 28, this);
				}
				
				if(frame % 10 == 0) {
					sorc.health[0] += sorc.health[1] * 0.004;
					if(sorc.health[0] > sorc.health[1]) {
						sorc.health[0] = sorc.health[1];
					}
				}
				
				//sorc healthbar 
				g.setColor(new Color(0,0,0));
				g.fillRect (382, 420, 32, 5);
				g.setColor(new Color(150, 200, 150));
				g.fillRect(382, 420, (int)(32 * sorc.health[0]/sorc.health[1]), 5);
				/*int r = 0;
				for(int i = 0; i < 3; i++) {
					if(sorc.status[i] > 0) {
						g.drawImage(status[i], 415 - get_cc() * 6 + 11 * r, 388, 7, 7, this);
						r++;
					}
				}*/
			}
			
			/*
			 Navigation:
			 0 = main -> carpet_open, 1, 2
			 1 = how to -> 0
			 2 = credits -> 0, 2a, carpet_open + poggers -> true
			 2a = stats -> 2
			 
			*/
			
			else {
				bframe++;
				
				//i love physics
				double slope = 0;
				if(mousecoords[0] != 400) {
					slope = (mousecoords[1] - 400.0)/(mousecoords[0] - 400.0);
				}
				if(mousecoords[0] < 400) {
					//cos(arctan(y/x)) = 1/sqrt(y^2/x^2 + 1)
					start_bg[2] -= 0.05/Math.sqrt(slope * slope + 1);
				}
				else if(mousecoords[0] > 400) {
					start_bg[2] += 0.05/Math.sqrt(slope * slope + 1);
				}
				if(mousecoords[1] < 400) {
					//sin(arctan(y/x)) = (y/x)/sqrt(y^2/x^2 + 1)
					if(mousecoords[0] < 400) {
						start_bg[3] -= 0.05 * slope/Math.sqrt(slope * slope + 1);
					}
					else if(mousecoords[0] > 400) {
						start_bg[3] += 0.05 * slope/Math.sqrt(slope * slope + 1);
					}
					else {
						start_bg[3] -= 0.05;
					}
				}
				else if(mousecoords[1] > 400) {
					if(mousecoords[0] < 400) {
						start_bg[3] -= 0.05 * slope/Math.sqrt(slope * slope + 1);
					}
					else if(mousecoords[0] > 400) {
						start_bg[3] += 0.05 * slope/Math.sqrt(slope * slope + 1);
					}
					else {
						start_bg[3] += 0.05;
					}
				}
				
				//speed cap
				/*if(start_bg[2] < -3) {
					start_bg[2] = -3;
				}
				if(start_bg[2] > 3) {
					start_bg[2] = 3;
				}
				if(start_bg[3] < -3) {
					start_bg[3] = -3;
				}
				if(start_bg[3] > 3) {
					start_bg[3] = 3;
				}*/
				start_bg[0] += start_bg[2];
				start_bg[1] += start_bg[3];
				
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						g.drawImage(beads, (int)(800 * i + start_bg[0] % 800), (int)(800 * j + start_bg[1] % 800), 800, 800, this);
					}
				}
				
				//main
				if(gamestate.equals("0")) {
					g.drawImage(titles[(bframe % 28) / 7], 0, 0, 800, 800, this);
					
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 300 && mousecoords[1] < 400) {
						g.drawImage(b0_start_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "carpet_open";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b0_start_0, 0, 0, 800, 800, this);
					}
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 440 && mousecoords[1] < 540) {
						g.drawImage(b0_howto_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "1";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b0_howto_0, 0, 0, 800, 800, this);
					}
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 580 && mousecoords[1] < 680) {
						g.drawImage(b0_credits_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "2";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b0_credits_0, 0, 0, 800, 800, this);
					}
				}
				
				//howto
				else if(gamestate.equals("1")) {
					g.drawImage(b1_howto, 0, 0, 800, 800, this);
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 600 && mousecoords[1] < 700) {
						g.drawImage(b12_back_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "0";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b12_back_0, 0, 0, 800, 800, this);
					}
				}
				
				//credits
				else if(gamestate.equals("2")) {
					g.drawImage(b2_credits, 0, 0, 800, 800, this);
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 600 && mousecoords[1] < 700) {
						g.drawImage(b12_back_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "0";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b12_back_0, 0, 0, 800, 800, this);
					}
					
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 460 && mousecoords[1] < 560) {
						g.drawImage(b2_stats_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "3";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b2_stats_0, 0, 0, 800, 800, this);
					}
					
					//poggers
					if(mousecoords[0] > 565 && mousecoords[0] < 665 && mousecoords[1] > 215 && mousecoords[1] < 265) {
						if(keys[4]) {
							gamestate = "carpet_open";
							sorc.dmg[0] *= 0;
							sorc.dmg[1] *= 7;
							sorc.fire[1] *= 3;
							poggers = true;
							keys[4] = false;
						}
					}
				}
				
				//stats
				else if(gamestate.equals("3")) {
					g.drawImage(b3_stats, 0, 0, 800, 800, this);
					if(mousecoords[0] > 180 && mousecoords[0] < 620 && mousecoords[1] > 600 && mousecoords[1] < 700) {
						g.drawImage(b12_back_1, 0, 0, 800, 800, this);
						if(keys[4]) {
							gamestate = "2";
							keys[4] = false;
						}
					}
					else {
						g.drawImage(b12_back_0, 0, 0, 800, 800, this);
					}
					
					String[] prefix = new String[] {"Wins (Fire)", "Wins (Ice)", "Losses",
							"Fire Finales", "Ice Finales",
							"Inferno Kills", "Blizzard Kills",
							"Damage Dealt", "Damage Taken"};
					Color[] colours = new Color[] {new Color(247, 220, 25), new Color(25, 247, 240), new Color(247, 25, 25),
							new Color(247, 162, 25), new Color(25, 193, 247), new Color(247, 162, 25),
							new Color(25, 193, 247), new Color(255, 255, 255), new Color(255, 255, 255)};
					
					for(int i = 0; i < 9; i++) {
						g.setFont(new Font("Courier", Font.BOLD, 25));
						if(g instanceof Graphics2D){
					        Graphics2D g2 = (Graphics2D)g;
					        g.setColor(colours[i]);
					        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					        RenderingHints.VALUE_ANTIALIAS_ON);
					        g2.drawString(String.format("%-24s %d", prefix[i], (int)read_list[i]), 125, 200 + 35 * i); 
					    }
					}
				}
			}
			
			//draw sound, no matter what
			if(audio) {
				g.drawImage(s_on, 20, 20, 24, 24, this);
			}
			else {
				g.drawImage(s_off, 20, 20, 24, 24, this);
			}
		}
	}
	
	public static void reset() {
		archmage.vulnerable = false;
		blizzard.vulnerable = false;
		inferno.vulnerable = false;
		am_state = 0;
		frame = 0;
		sicken = false;
		archmage.clear_portals();
		blizzard.movement = "hidden";
		inferno.movement = "hidden";
		inferno.spd = 0.25;
		blizzard.spd = 0.22;
		blizzard.x = 800;
		blizzard.y = 800;
		inferno.x = 800;
		inferno.y = 800;
	}
	
	public int get_cc() {
		int count = 0; 
		for(int i = 0; i < sorc.status.length; i++) {
			if(sorc.status[i] > 0) {
				count++;
			}
		}
		return count;
	}
	
	public static double[] bp_value(double dx, double dy, double para) {
		double dist = Math.sqrt(dx * dx + dy * dy)/2;
		double angle;
		try {
			angle = Math.atan(dy/dx);
			if(dx < 0) {
				angle += Math.PI;
			}
		}
		catch(Exception e) {
			if(dy > 0) {
				angle = -Math.PI/2;
			}
			else {
				angle = Math.PI/2;
			}
		}

		return new double[] {800 + dist * ((Math.cos(angle) * para)),
				800 + dist * ((Math.sin(angle) * para) - (Math.abs(Math.cos(angle)) * (-(para * para) + 2 * para)/1.75))};
		// - (Math.sin(angle) * (-(para * para) + 2 * para)/1.75)
	}
	
	public static void generator_particles(int intensity, Graphics g) {
		if(g1.element == 1) {
			g.setColor(new Color(245, 180, 70));
			for(int i = 0; i < intensity; i++) {
				double v = 500 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(5, 9);
				g.fillRect((int)(400 - sorc.x + offset + 800), (int)(400 - sorc.y + v + 300), s, s);
			}
		}
		else if(g1.element == 2) {
			g.setColor(new Color(132, 234, 245));
			for(int i = 0; i < intensity; i++) {
				double v = 500 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(4, 8);
				g.fillRect((int)(400 - sorc.x + offset + 800), (int)(400 - sorc.y + v + 300), s, s);
			}
		}
		
		if(g2.element == 1) {
			g.setColor(new Color(245, 180, 70));
			for(int i = 0; i < intensity; i++) {
				double v = 520 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(5, 9);
				g.fillRect((int)(400 - sorc.x + 0.866*v + 0.5 * offset + 350), (int)(400 - sorc.y - 0.5 * v - 0.866 * offset + 1120), s, s);
			}
		}
		else if(g2.element == 2) {
			g.setColor(new Color(132, 234, 245));
			for(int i = 0; i < intensity; i++) {
				double v = 520 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(4, 8);
				g.fillRect((int)(400 - sorc.x + 0.866*v + 0.5 * offset + 350), (int)(400 - sorc.y - 0.5 * v - 0.87 * offset + 1120), s, s);
			}
		}
		
		if(g3.element == 1) {
			g.setColor(new Color(245, 180, 70));
			for(int i = 0; i < intensity; i++) {
				double v = 520 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(5, 9);
				g.fillRect((int)(400 - sorc.x - 0.866*v - 0.5 * offset + 1250), (int)(400 - sorc.y - 0.5 * v - 0.87 * offset + 1120), s, s);
			}
		}
		else if(g3.element == 2) {
			g.setColor(new Color(132, 234, 245));
			for(int i = 0; i < intensity; i++) {
				double v = 520 * Math.random();
				double offset = -8 + 16 * Math.random();
				int s = ThreadLocalRandom.current().nextInt(4, 8);
				g.fillRect((int)(400 - sorc.x - 0.866*v - 0.5 * offset + 1250), (int)(400 - sorc.y - 0.5 * v - 0.866 * offset + 1120), s, s);
			}
		}
	}
	
	public static void write() throws IOException{
		writer = new PrintWriter(new FileWriter("data.txt"));
		for(int i = 0; i < 9; i++) {
			read_list[i] += write_list[i];
			writer.println(read_list[i]);
		}
		writer.close();
	}
	
	//initializing jframe
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException
	{	
		JFrame nframe = new JFrame(":nicky:");
		Main panel = new Main();
		nframe.add(panel);
		nframe.pack();	
		nframe.setVisible(true);
		nframe.setResizable(false);
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w')
		{
			keys[0] = true;
		}
		if (e.getKeyChar() == 'a')
		{
			keys[1] = true;
		}
		if(e.getKeyChar() == 's')
		{
			keys[2] = true;
		}
		if(e.getKeyChar() == 'd')
		{
			keys[3] = true;
		}	
		if(e.getKeyChar() == ' ')
		{
			keys[4] = true;
		}	
		if(e.getKeyChar() == 'i') 
		{
			keys[5] = !keys[5];
		}
		if(e.getKeyChar() == 'p') 
		{
			pause = !pause;
		}
		if(e.getKeyChar() == 'm') {
			audio = !audio;
		}
		if(e.getKeyChar() == 'o')
		{
			if(cd > 0 && gamestate.equals("carpet_close")) {
				archmage.taunts.removeAll(archmage.taunts);
				archmage.taunts.add("Twilight Archmage: Eheh…heh…eheheh! Oh, it must've took quite some power to bring me back here!");
				archmage.taunts.add("Twilight Archmage: But if you got yourself here…then that spiteful doorman has dropped his post! SPLENDID!");
				archmage.taunts.add("Twilight Archmage: Yes yes, that is just grand! Allow me to show my heartfelt gratitude!");
				archmage.taunts.add("Twilight Archmage: After all, you've already brought me the tools...");
				cd = 1;				
			}
		}	
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'w')
		{
			keys[0] = false;
		}
		if (e.getKeyChar() == 'a')
		{
			keys[1] = false;
		}
		if(e.getKeyChar() == 's')
		{
			keys[2] = false;
		}
		if(e.getKeyChar() == 'd')
		{
			keys[3] = false;
		}	
		if(e.getKeyChar() == ' ')
		{
			keys[4] = false;
		}
	}
	
	//getting mouse coords
	public void mouseClicked(MouseEvent e) {
		mousecoords[0] = e.getX();
		mousecoords[1] = e.getY();
	}
	public void mousePressed(MouseEvent e) {
		mousecoords[0] = e.getX();
		mousecoords[1] = e.getY();
		keys[4] = true;
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
		mousecoords[0] = e.getX();
		mousecoords[1] = e.getY();
		keys[4] = false;
	}
	public void mouseMoved(MouseEvent e) {
		mousecoords[0] = e.getX();
		mousecoords[1] = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		mousecoords[0] = e.getX();
		mousecoords[1] = e.getY();
	}
}


