package snake;
import java.io.*;
import java.net.*;
public class TCPClient {
    static boolean start=false;
    static boolean readyToSend=false;
    Socket socketClient;
    BufferedReader brInFromUser;
    BufferedReader brInFromServer;
    DataOutputStream dosOutToServer;

    public TCPClient(Socket s) throws Exception{ String strLocal, strSocket;
        socketClient= s;
        brInFromUser= new BufferedReader(new InputStreamReader(System.in));
        brInFromServer= new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        dosOutToServer = new DataOutputStream(socketClient.getOutputStream());
    }


    public void send(String str){
        try{
        	dosOutToServer = new DataOutputStream(socketClient.getOutputStream());
            dosOutToServer.writeBytes(str +"\n");
            System.out.println("send: "+str);
        }catch(Exception e){}

    }
   

    public void close(){
        try {
            socketClient.close();
        }catch(IOException e){}
    }

}
