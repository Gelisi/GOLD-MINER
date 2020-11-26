package goldminer;

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.File;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

 

public class mp3player{
	static Player player = null;
	static String music_name = "Vicetone,Tony Igy - Astronomia.mp3";
	/**
	 * 播放 5 秒并结束播放
	 */
	public static void play() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File file = new File(music_name);
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream stream = new BufferedInputStream(fis);
					player = new Player(stream);
					player.play();
				} catch (Exception e) {

					// TODO: handle exception
				}
			}
		}).start(); 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		player.close();
	}
}
