package snake;

import javax.swing.*;

import sun.audio.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ReceiveThread extends Thread{
    Socket socketClient;
    BufferedReader brInFromServer;

    ReceiveThread(Socket s) throws Exception{
        socketClient=s;
        brInFromServer= new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

    }

    public void run() {
            String temp1;
            int temp2;
            int temp3;
            int a;
            int order = 0;
            int decision = 0;
            AudioPlayer MGP = AudioPlayer.player;
    		AudioStream BGM;
    		AudioData MD = null;
    		ContinuousAudioDataStream loop = null;
    		
    		try{
    			BGM = new AudioStream(new FileInputStream("src/bgm.wav"));
    			MD = BGM.getData();
    			System.out.println(MD.toString());
    			
    		}catch(IOException error){
    			error.printStackTrace();
    		}
            while(order != Integer.MAX_VALUE){
            	System.out.println("Receive Thread running");
            	
            	if(MD != null) {
            		loop = new ContinuousAudioDataStream(MD);
        			MGP.start(loop);
            	}
            	
            	try {
					temp1 = brInFromServer.readLine();
					
					
					 String[] strs = temp1.split(",");
					 
					 if(strs.length < 2)
						 	continue;
					
					order = Integer.parseInt(strs[0]);
					decision = Integer.parseInt(strs[1]);
					
	            	System.out.println("ReceiveOrder: "+order);
	            	
	                if(order<20){                    
	                    Game.response(order , decision);
	                } else {
	                	int type = order / 10000;
	                	order = order % 10000;
	                	
	                	int food_x = (order / 100)-30;
	                	int food_y = (order % 100)-20;
	                	
	                	Game.response(type, food_x, food_y, decision);
	                }
	                
				} catch (Exception e) {
					e.printStackTrace();
				}
                
            	

            }

            System.out.println("Receive Thread closed");
            try {
				socketClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }


}

