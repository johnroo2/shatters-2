package project;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio_Player {
	Clip c;
	
	public Audio_Player() {
		
	}
	
	public void PlayMusic(String path) throws UnsupportedAudioFileException, IOException {
		File musicPath = new File(path);
		
		if(musicPath.exists()) {
			try {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				c = AudioSystem.getClip();
				c.open(audioInput);
				c.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (LineUnavailableException e) {
				System.out.println("unhandled audio error!");
			}
		}
	}
}
