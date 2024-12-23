package app;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private static Clip clipPlaying;
    private Clip clip;
    
    public BackgroundMusic(String soundFilePath) {
    	
        try {
            // Load the sound file and open it as a Clip
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void play() {
        	
    	if (clipPlaying != null) {
    		clipPlaying.setFramePosition(0);
    		clipPlaying.stop();
    	} 
    	
    	clipPlaying = clip;
    	clipPlaying.start();
        	
    	clipPlaying.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void stop() {
    	if (clipPlaying != null && clipPlaying.isRunning()) {
    		clipPlaying.stop();
    	}
    }
    
    public void close() {
        clip.close();  // Close the Clip when done
    }
}
