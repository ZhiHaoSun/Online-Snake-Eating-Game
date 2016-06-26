package snake;
import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

public class SnakeGuide implements ItemListener, AdjustmentListener {

	static SnakeGuide snakeGuide;
	static Runnable game;
	static Thread gamerun;
	
	public static void main(String[] args) {
		try {
            new LoginPanel();

        } catch (Exception e) {
        }
	}
	static Frame framestart; // 开始
	Panel pannum ,pancho, panw, panh, panbtn;
	Checkbox  cblow, cbhigh,cbcro, cbucr; // 单选：可穿墙，不穿墙
	Scrollbar slw, slh; // 滚动条：宽度，高度
	Label lbnum, lbcro, lbwid, lbhei; // 标签：人数，模式，宽度，高度
	Label lbhp1, lbhp2, lbhp3, lbhp4, lbhp5;
	TextField tfw, tfh; // 文本框：宽度，高度
	Button btncfm, btnccl; // 按钮：确认，取消

	Frame frameend; // 结束
	Label msg; // 询问
	Panel panyn;
	Button btny, btnn; // 是，否
	static boolean speed;
	static boolean choice;
	static int w, h;
	boolean isready= false;
	static boolean isstart= false;
	public SnakeGuide(int decision) {
		speed= true;
		choice = true;
		w = 60;
		h = 40;
		
		
		lbhp1 = new Label("......玩家1用w,a,s,d控制方向，玩家2用8,4,5,6控制，空格暂停");
		lbhp2 = new Label("......黄色的是普通食物，每个1分，会使蛇身变长");
		lbhp3 = new Label("......红色是高分食物，每个5分，生成数量较少");
		lbhp4 = new Label("......绿色是草，分数随机");
		lbhp5 = new Label("......黑色是岩石，撞上游戏结束");	

		pannum = new Panel();
		lbnum = new Label("速度");
		CheckboxGroup cbgnum = new CheckboxGroup();
		cblow = new Checkbox("低速", cbgnum, true);
		cblow.addItemListener(this);
		cbhigh = new Checkbox("高速", cbgnum, false);
		cbhigh.addItemListener(this);
		pannum.add(lbnum);
		pannum.add(cblow);
		pannum.add(cbhigh);


		pancho = new Panel();
		lbcro = new Label("模式");
		CheckboxGroup cbgcho = new CheckboxGroup();
		cbcro = new Checkbox("可穿墙", cbgcho, true);
		cbcro.addItemListener(this);
		cbucr = new Checkbox("不可穿墙", cbgcho, false);
		cbucr.addItemListener(this);
		pancho.add(lbcro);
		pancho.add(cbcro);
		pancho.add(cbucr);

		panw = new Panel();
		lbwid = new Label("宽度");
		slw = new Scrollbar(Scrollbar.HORIZONTAL, 60, 5, 10, 65);
		slw.addAdjustmentListener(this);
		tfw = new TextField("60", 5);
		tfw.setEditable(false);
		panw.add(lbwid);
		panw.add(tfw);

		panh = new Panel();
		lbhei = new Label("高度");
		slh = new Scrollbar(Scrollbar.HORIZONTAL, 40, 3, 10, 43);
		slh.addAdjustmentListener(this);
		tfh = new TextField("40", 5);
		tfh.setEditable(false);
		panh.add(lbhei);
		panh.add(tfh);
		
		panbtn = new Panel();
		btncfm = new Button("确定");
		btncfm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isready= true;
				Game.client.send("16"+","+ decision);
				if (SnakeGuide.isstart == true){
					framestart.setVisible(false);
					framestart.dispose();
					game = new Game(decision, speed, choice, w, h);
					gamerun = new Thread(game);
					gamerun.start();
					Game.client.send("17"+","+ (1-decision));
				}
			}
		});
		btnccl = new Button("退出");
		btnccl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framestart.setVisible(false);
				framestart.dispose();
				System.exit(0);
			}
		});
		panbtn.add(btncfm);
		panbtn.add(btnccl);

		framestart = new Frame("选项设置");
		framestart.setLayout(new GridLayout(12, 1));
		framestart.add(lbhp1);
		framestart.add(lbhp2);
		framestart.add(lbhp3);
		framestart.add(lbhp4);
		framestart.add(lbhp5);
		framestart.add(pannum);
		framestart.add(pancho);
		framestart.add(panw);
		framestart.add(slw);
		framestart.add(panh);
		framestart.add(slh);
		framestart.add(panbtn);
		framestart.setSize(400, 360);
		framestart.setResizable(false);
		framestart.setLocationRelativeTo(null);
		framestart.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				framestart.setVisible(false);
				framestart.dispose();
				System.exit(0);
			}
		
		});

		
		msg = new Label("是否继续？");
		panyn = new Panel();
		btny = new Button("是");
		btnn = new Button("否");
		btny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameend.setVisible(false);
				frameend.dispose();
				framestart.setVisible(true);
			}
		});
		btnn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameend.setVisible(false);
				frameend.dispose();
				System.exit(0);
			}
		});
		frameend = new Frame();
		frameend.setLayout(new BorderLayout());
		frameend.add(BorderLayout.NORTH, msg);
		panyn.add(btny);
		panyn.add(btnn);
		frameend.add(BorderLayout.SOUTH, panyn);
		frameend.setSize(100, 100);
		frameend.setResizable(false);
		frameend.setLocationRelativeTo(null);
		frameend.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				frameend.setVisible(false);
				frameend.dispose();
				System.exit(0);
			}
		});

		framestart.setVisible(true);
		frameend.setVisible(false);

	}

	public void adjustmentValueChanged(AdjustmentEvent eve) {
		if (eve.getSource() == slw) {
			tfw.setText(Integer.toString(((Scrollbar) eve.getSource())
					.getValue()));
			w = ((Scrollbar) eve.getSource()).getValue();
		}
		if (eve.getSource() == slh) {
			tfh.setText(Integer.toString(((Scrollbar) eve.getSource())
					.getValue()));
			h = ((Scrollbar) eve.getSource()).getValue();
		}
	}

	public void itemStateChanged(ItemEvent eve) {
		if (eve.getSource() == cblow) {
			speed = true;
			
		}
		if (eve.getSource() == cbhigh) {
			speed = false;
			
		}
		if (eve.getSource() == cbcro) {
			choice = true;
		}
		if (eve.getSource() == cbucr) {
			choice = false;
		}
	}

	 private void reportRank(){
	        int[] rank=new int[5];
	        try{
	            Scanner rin = new Scanner(SnakeGuide.class.getResourceAsStream("rank.txt"));
	            int i=0;
	            while(rin.hasNext()){
	                rank[i]=rin.nextInt();
	                i++;
	            }
	        }catch(Exception re){System.out.println("Rank Wrong!");}

	        JOptionPane.showMessageDialog(null,"Records: \n"+"1: "+rank[0]+
	                "\n2: "+rank[1]+ "\n3: "+rank[2]+ "\n4: "+rank[3]+ "\n5: "+rank[4]);
	    }
	 
	static void follow(int decision){ 
	 	
		framestart.setVisible(false);
		framestart.dispose();
		game = new Game(decision, speed, choice, w, h);
		gamerun = new Thread(game);
		gamerun.start();
	 
	}	

}

