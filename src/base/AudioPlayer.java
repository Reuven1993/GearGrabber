package base;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public enum MusicStatus{
		PLAYING,
		STOPPED
	}

	private Clip clip;
	private AudioInputStream audioInputStream;
	private String musicPath;
	private MusicStatus status = MusicStatus.STOPPED;
	
	
	/*
	 * Play a sound file for numIterations times.
	 * To play in a continuous loop, use numIterations = 0;
	 */	
	public void play(String musicPath, int numIterations) {
		this.musicPath = musicPath;
		File musicFile = new File(this.musicPath);
		try {
			audioInputStream = AudioSystem.getAudioInputStream(musicFile.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(numIterations - 1);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clip.start();
		status = MusicStatus.PLAYING;
	}
	
	public void stop() {
		clip.stop();
		status = MusicStatus.STOPPED;
	}
	
	public void close() {
		clip.close();
	}
	
	
	public MusicStatus getStatus() {
		return status;
	}
	
}
