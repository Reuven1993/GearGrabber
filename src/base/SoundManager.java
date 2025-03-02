package base;

import java.io.File;
// import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// import javax.sound.sampled.LineUnavailableException;
// import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
    private static SoundManager instance;
    private ExecutorService soundExecutor = Executors.newSingleThreadExecutor();
    private Map<String, Clip> cachedClips = new HashMap<>();
    private boolean enabled = true;
    
    private SoundManager() {
        // Preload common sounds
        preloadSound("drill", "resources/audio/mi.wav.wav");
        preloadSound("pickup", "resources/audio/do.wav.wav");
        preloadSound("error", "resources/audio/Buzz.wav.wav");
    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void preloadSound(String key, String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            cachedClips.put(key, clip);
        } catch (Exception e) {
            System.err.println("Error preloading sound: " + filePath);
            e.printStackTrace();
        }
    }
    
    public void playSound(String key) {
        if (!enabled) return;
        
        soundExecutor.submit(() -> {
            try {
                Clip clip = cachedClips.get(key);
                if (clip == null) {
                    System.err.println("Sound not preloaded: " + key);
                    return;
                }
                
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.start();
                
                // Wait for the sound to finish
                Thread.sleep(500); // Give it at least 500ms
            } catch (Exception e) {
                System.err.println("Error playing sound: " + key);
                e.printStackTrace();
            }
        });
    }
    
    public void playSound(String filePath, boolean blocking) {
        if (!enabled) return;
        
        soundExecutor.submit(() -> {
            try {
                File soundFile = new File(filePath);
                if (!soundFile.exists()) {
                    System.err.println("Sound file not found: " + filePath);
                    return;
                }
                
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                
                if (blocking) {
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                } else {
                    Thread.sleep(100); // Small delay to ensure sound starts
                }
            } catch (Exception e) {
                System.err.println("Error playing sound: " + filePath);
                e.printStackTrace();
            }
        });
    }
    
    public void shutdown() {
        for (Clip clip : cachedClips.values()) {
            clip.close();
        }
        soundExecutor.shutdown();
    }
}