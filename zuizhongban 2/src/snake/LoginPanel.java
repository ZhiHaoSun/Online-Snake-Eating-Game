package snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginPanel extends JFrame {

	protected boolean linked;

	private JButton closeButton;
	private JTextField ipTextField;
    private JTextField portTextField;

	private JLabel jLabel3;
    private JLabel jLabel4;
	private JButton loginButton;

	public LoginPanel() {
		initComponents();
	}

	public boolean isLinked() {
		return linked;
	}

	public void setLinked(boolean linked) {
		this.linked = linked;
	}

	void setLinkIp(String ip) {
		ipTextField.setText(ip);
		ipTextField.setEditable(false);
	}


	private void initComponents() {
        setLayout(null);


        jLabel3 = new JLabel("Server IP");
        jLabel4 = new JLabel("Server port");
		ipTextField = new JTextField();
        portTextField=new JTextField();

		loginButton = new JButton();

		jLabel3.setBounds(20,20,100,25);
        jLabel4.setBounds(20,60,100,25);


        ipTextField.setBounds(90,20,210,40);
        portTextField.setBounds(90,60,210,40);


        loginButton.setText("Start");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        loginButton.setBounds(120,150,70,50);

        add(loginButton);

        add(jLabel3);
        add(jLabel4);
        add(ipTextField);
        add(portTextField);

		setForeground(Color.gray);



        setBounds(100,100,350,250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

	private void loginButtonActionPerformed(ActionEvent evt) {
		try {

			loginButton.setText("Waiting...");

			String ipText = ipTextField.getText();
            int othersPort=Integer.parseInt(portTextField.getText());
			InetAddress ip = InetAddress.getByName(ipText);
			Socket s=new Socket(ip,othersPort);
			Game.socket=s;
			Game.client=new TCPClient(s);

			DataOutputStream test =new DataOutputStream(s.getOutputStream());
			test.writeBytes("88"+'\n');

			String message = new BufferedReader(new InputStreamReader(s.getInputStream())).readLine();
			
			System.out.println("message: "+message);

			int decision=Integer.parseInt(message);
			System.out.println("decision"+decision);


			//int decision=1;
			//System.out.println(new BufferedReader(new InputStreamReader(s.getInputStream())).readLine());

			setVisible(false);
			new SnakeGuide(decision);

			ReceiveThread a =new ReceiveThread(s);
			a.start();

			System.out.println("Game Begin");
		} catch (UnknownHostException ex) {
			Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(this, "Incorrect IP!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Cannot connect!");
		}catch(java.lang.Exception e){}
	}


    private static String getLocalIpAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();

        return address.getHostAddress();
    }

}
