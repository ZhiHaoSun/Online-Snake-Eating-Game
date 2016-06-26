package snake;
import javax.swing.*;
import sun.audio.*;
import java.awt.event.*;
import java.io.*;
public class BgMusic implements Runnable{

	public void run(){
		
		System.out.println("wwww");
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD = null;
		ContinuousAudioDataStream loop = null;
		try{
			BGM = new AudioStream(Game.class.getResourceAsStream("./bgm.wav"));
			MD = BGM.getData();
		}catch(IOException error){
			System.out.print("file not found");
		}
			
		while(true) {
			loop = new ContinuousAudioDataStream(MD);
			MGP.start(loop);
		}
		
	}
}