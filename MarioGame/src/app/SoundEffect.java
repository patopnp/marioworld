package app;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect {

    private Clip clip;
    private String soundFilePath;
    
    public SoundEffect(String soundFilePath) {
    	 this.soundFilePath = soundFilePath;
    	 
    	 
    	 
        try {
            // Load the sound file and open it as a Clip
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void playSingle() {
    	clip.setFramePosition(0);
    	clip.start();
    }
    
    public void playConsecutiveSounds(SoundEffect secondClip) {

    	try {
    	    // Create a new AudioInputStream for each playback
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            
            // Create a new Clip
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            clip.start();  // Play the sound
            
            // Add a listener to close the Clip after playback
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                	secondClip.play();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void play() {
        try {
            // Create a new AudioInputStream for each playback
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            
            // Create a new Clip
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            clip.start();  // Play the sound
            
            // Add a listener to close the Clip after playback
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();  // Free resources after sound completes
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
    	if (clip != null && clip.isRunning()) {
    		clip.stop();
    	}
    }
/*
    public void play() {
    	
    	new Thread(() -> {
            
            	if (clip.isRunning()) {
		            clip.stop();  // Stop if it's already playing
		        }
		        clip.setFramePosition(0);  // Rewind to the beginning
		        clip.start();              // Play the sound
		        //close();
        }).start();
    	
    }
*/
    public void close() {
        clip.close();  // Close the Clip when done
    }
}
