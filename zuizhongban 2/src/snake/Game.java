package snake;


import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import snake.Food;
import snake.Snake.Body;
 
public class Game implements Runnable {
	protected static int num = 0;
	public static int result = 0;
	static boolean choice;
	static boolean speed;
	static Snake[] snake = new Snake[2];
	protected static Food general1 = new Food(1);
	protected static Food general2 = new Food(1);
	protected static Food special = new Food(2);
	protected static Food grass = new Food(3);
	protected static Food rock1 = new Food(4);
	protected static Food rock2 = new Food(5);
	protected static Food rock3 = new Food(6);
	int w, h;
	public int decision;
	public static Socket socket;
    public static TCPClient client;
    public static int nextDecision = 0;

    private static int[] rank;
    char in = 'n'; char in1= 'n';
	public Game(int decision,boolean speed, boolean choice, int w, int h) {
		super();
		this.decision= decision;
		this.choice = choice;
		this.speed= speed;
		
		this.w = w;
		this.h = h;
		snake[0] = new Snake(1);
		
		snake[1] = new Snake(2);	
		
		setGeneralFood(snake , decision);
		Map.drawmap(w, h);
		Map.draw(choice, snake);
	}
	
	public void run() {
	
		rank=new int[5];
		
        try{
            Scanner rin = new Scanner(Game.class.getResourceAsStream("src/rank.txt"));
            int i=0;
            while(rin.hasNext()){
                rank[i]=rin.nextInt();
                i++;
            }
        }catch(Exception re){}
			snake[0].drawSnake();
			snake[1].drawSnake();
			System.out.println("��ʼ�����ҵ�");
			
			while (snake[0].alive(choice, snake[1]) && snake[1].alive(choice, snake[0])) {
				
				if (this.decision==0){
					if (StdDraw.hasNextKeyTyped()) {
						switch (StdDraw.nextKeyTyped()) {
						case 'w':
							in = 'u';
							client.send("1" + "," + this.decision);
							break;
						case 'a':
							in = 'l';
							client.send("2" + "," + this.decision);
							break;
						case 's':
							in = 'd';
							client.send("3" + "," + this.decision);
							break;
						case 'd':
							in = 'r';
							client.send("4" + "," + this.decision);
							break;
						case ' ':
							pauseGame();
							client.send("5" + "," + this.decision);
							break;
						case '0':
							resumeGame();
							client.send("18" + "," + this.decision);
							break;
						default:
							in = 'n';
							break;
						}
					}
				}
				else{
					if (StdDraw.hasNextKeyTyped()) {
						if (StdDraw.hasNextKeyTyped()) {
							switch (StdDraw.nextKeyTyped()) {
								case '8':
							
									in = 'u';
									client.send("1" + "," + this.decision);
									break;
								
								case '4':
									in= 'l';
									client.send("2" + "," + this.decision);
									break;
								
								case '5':
									in = 'd';
									client.send("3" + "," + this.decision);
									break;
								
								case '6':
									in = 'r';
									client.send("4" + "," + this.decision);
									break;
								case ' ':
									pauseGame();
									client.send("5" + "," + this.decision);
									break;
								case '0':
									resumeGame();
									client.send("18" + "," + this.decision);
									break;
								default:
									in = 'n';
									break;
							}
						}
					}
				}
			  
				eatFood(snake, this.decision);
				
				snake[decision].move(in, choice);
				snake[1-decision].movestill(true);
				Map.draw(choice, snake);
				
				sleep(speed,snake[0]);
				sleep(speed,snake[1]);
			
			}
			
			StdDraw.show(1000);
			StdDraw.show();
			StdDraw.clear();

			
				   
			if( snake[0].head.p.x == snake[1].head.p.x && snake[0].head.p.y == snake[0].head.p.y){
		    	System.out.println("1111");
		    	StdDraw.text(0, 2, "No one Win! ");
				 if(isNewRank()){
                
                     reportNewRank();

                 }else{
                     reportRank();
                 }
				client.send("8" + "," + this.decision);
				
			} else if (!snake[this.decision].alive(choice, snake[1-this.decision])){
				StdDraw.text(0, 2, "You Lose! The score is " + result);
				 if(isNewRank()){       

                    reportNewRank();

                }else{
                    reportRank();
                }
				client.send("6" + "," + this.decision);
			} else {
				StdDraw.text(0, 2, "You Win! The score is " + result);
				 if(isNewRank()){       
                   reportNewRank();

               }else{
                   reportRank();
               }
				client.send("7" + "," + this.decision);
			}
				
			StdDraw.show();
		
//		SnakeGuide.snakeGuide.frameend.setVisible(true);
	}
	
	 public static void response(int i, int decision){
		 
			
		 	if(i==1){
				snake[decision].move('u', choice);
				
				
			}
		 	else if(i==2){
				snake[decision].move('l', choice);
			}
		 	else if(i==3){
				snake[decision].move('d', choice);
			}
		 	else if(i==4){
				snake[decision].move('r', choice);
			}
		 	else if(i==5){
				
				pauseGame();
			}
		 	else if(i==6){
//		 		System.out.println("�Է�����");
//		 		StdDraw.text(0, 2, " You Win! The score is " +result);
				
		 		snake[decision].life = false;
		 		
				if(isNewRank()){
					reportNewRank();

                  }else{
                      reportRank();
                  }
				 
             }
		 	else if(i==6){
//		 		System.out.println("�Է�����");
//		 		StdDraw.text(0, 2, " You Win! The score is " +result);
				
		 		snake[1-decision].life = false;
		 		
				if(isNewRank()){
					reportNewRank();

                  }else{
                      reportRank();
                  }
				 
             }
			
		 
		 else if(i==8){
//		 		System.out.println("toupengtou");
//				StdDraw.text(0, 2, "No one Win! ");
				
				snake[decision].life = false;
				snake[1-decision].life = false;
				
				 if(isNewRank()){
                     
                     reportNewRank();

                 }else{
                     reportRank();
                 }
			}
			else if(i == 9) {
				System.out.println("Increase " + decision);
				Body temp = new Body(decision+1);
				temp.p = new Point(snake[decision].tail.p.x, snake[decision].tail.p.y);
				temp.last = snake[decision].tail;
				snake[decision].tail.next = temp;
				snake[decision].tail = temp;
				snake[decision].drawSnake();
			}
			else if(i==11){
				Body temp = new Body(decision+1);
				temp.p = new Point(snake[decision].tail.p.x, snake[decision].tail.p.y);
				temp.last = snake[decision].tail;
				snake[decision].tail.next = temp;
				snake[decision].tail = temp;
				Snake.draw(temp);
			}
			else if(i==14){
				Body temp = new Body(decision+1);
				temp.p = new Point(snake[decision].tail.p.x, snake[decision].tail.p.y);
				temp.last = snake[decision].tail;
				snake[decision].tail.next = temp;
				snake[decision].tail = temp;
				Snake.draw(temp);
			}
			else if(i==16){
				SnakeGuide.isstart= true;
			}
			else if(i==17){
				SnakeGuide.follow(decision);
			}
			else if(i==18){
				resumeGame();
				
			}
		 	
		}
	 
	 public static void response(int a,int i,int j, int decision){
		
		 switch(a) {
		 	case 1:	
		 		general1.p= new Point(i , j);
		 		StdDraw.setPenColor(general1.c);				
				StdDraw.filledSquare(general1.p.x,general1.p.y, 0.5);
		 		break;
		 	case 2:
		 		special.p = new Point(i , j);
		 		StdDraw.setPenColor(special.c);				
				StdDraw.filledCircle(special.p.x, special.p.y, 0.6);
		 		break;
		 	case 3:
		 		grass.p = new Point(i , j);
		 		StdDraw.setPenColor(grass.c);				
				StdDraw.filledCircle(grass.p.x,grass.p.y, 0.6);
		 		break;
		 	case 4:
		 		System.out.println("��������ʯ4");
		 		rock1.p = new Point(i , j);
		 		StdDraw.setPenColor(rock1.c);				
				StdDraw.filledSquare(rock1.p.x,rock1.p.y, 0.5);
		 		break;
		 	case 5: 
		 		System.out.println("��������ʯ5");
		 		rock2.p= new Point(i , j);
		 		StdDraw.setPenColor(rock2.c);				
				StdDraw.filledSquare(rock2.p.x,rock2.p.y, 0.5);
		 		break;
		 	case 6:
		 		System.out.println("��������ʯ6");
		 		rock3.p = new Point(i ,j);
		 		StdDraw.setPenColor(rock3.c);				
				StdDraw.filledSquare(rock3.p.x,rock3.p.y, 0.5);
		 		break;
		 	case 7:
		 		general2.p =new Point(i ,j);
		 		StdDraw.setPenColor(general2.c);				
				StdDraw.filledSquare(general2.p.x,general2.p.y, 0.5);
		 		break;
		 }
		 
	 }
	 
	public static void eatFood(Snake[] snake , int decision)  {
		
		if (snake[decision].head.p.equals(general1.p)) {
			
			Body temp = new Body(decision+1);
				temp.p = new Point(snake[decision].tail.p.x, snake[decision].tail.p.y);
				temp.last = snake[decision].tail;
				snake[decision].tail.next = temp;
				snake[decision].tail = temp;
				result += general1.score;
				num++;
				setGeneralFood(snake , decision);
					client.send("9" + "," + decision);
				if (num % 7 == 0){
					setSpecialFood(snake , decision);
					}
				
				if (num % 3 == 0){
					setGrassFood(snake , decision);
				}
				
				if (num % 2 == 0|| num % 5==0) {
					setRockFood(snake , decision);
				}
				
			}
			else if (snake[decision].head.p.equals(special.p)) {
				client.send("12"+","+ decision);
				result += special.score;
				special.p = null;
				client.send("10" + "," + decision);
			}
			else if (snake[decision].head.p.equals(grass.p)) {
				client.send("13"+","+ decision);
				result += grass.score;
				grass.p = null;
				client.send("11" + "," + decision);
			}

//				if (snake[1-decision].head.p.equals(general1.p)) {
//					Body temp = new Body(2-decision);
//					temp.p = new Point(snake[1-decision].tail.p.x, snake[1-decision].tail.p.y);
//					temp.last = snake[1-decision].tail;
//					snake[1-decision].tail.next = temp;
//					snake[1-decision].tail = temp;
//					result += general1.score;
//					num++;
//					
//					setGeneralFood(snake , decision);
//					if (num % 7 == 0){
//						setSpecialFood(snake , decision);
//						setRockFood(snake , decision);}
//					if (num % 3 == 0){
//						setGrassFood(snake , decision);
//						setRockFood(snake , decision);}
//					if (num % 2 == 0 &&num % 5 == 0)
//						setRockFood(snake , decision);
//					if (snake[1-decision].head.p.equals(special.p)) {
//						client.send("12"+","+ decision);
//						result += special.score;
//						special.p = null;
//						client.send("10" + "," + decision);
//					}
//					if (snake[1-decision].head.p.equals(grass.p)) {
//						client.send("13"+","+decision);
//						result += grass.score;
//						grass.p = null;
//						client.send("11" + "," + decision);
//					}
//					
//				}
			else if (snake[decision].head.p.equals(general2.p)) {
				
					Body temp = new Body(decision+1);
						temp.p = new Point(snake[decision].tail.p.x, snake[decision].tail.p.y);
						temp.last = snake[decision].tail;
						snake[decision].tail.next = temp;
						snake[decision].tail = temp;
						result += general1.score;
						num++;
						client.send("9" + "," + decision);
							setGeneralFood(snake , decision);
						if (num % 7 == 0){
							setSpecialFood(snake , decision);
							}
						
						if (num % 3 == 0){
							setGrassFood(snake , decision);
						}
						
						if (num % 2 == 0|| num % 5==0) {
							setRockFood(snake , decision);
						}
						
					}
			else if(snake[decision].head.p.equals(rock1.p)) {
				snake[decision].life = false;
				client.send("6," + decision);
			}
			else if(snake[decision].head.p.equals(rock2.p)) {
				snake[decision].life = false;
				client.send("6," + decision);
			}else if(snake[decision].head.p.equals(rock3.p)) {
				snake[decision].life = false;
				client.send("6," + decision);
			}
				

			if (special.p != null) {
				special.score--;
				if (special.score == 0) {
					special.p = null;
				}
			}

		}
	public void sleep(Boolean speed, Snake snake) {
		int sum = result;
			sum += result;
			if (speed){
				StdDraw.show(200 - sum > 50 ? 200 - sum : 50);
			}
			else{
				StdDraw.show(100 - sum > 50 ? 100 - sum : 50);
			}
		}
	
	public static void setGeneralFood(Snake[] snake ,int decision) {
		general1 = new Food(1);
		general1.p = snake[0].head.p;
			Body temp = snake[0].head;
			while (temp != null) {
				if (temp.p.equals(general1.p)) {
						general1.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp = snake[0].head;

				} else if (temp == snake[0].tail)
					temp = snake[1].head;
				else
					temp = temp.next;
			}
		client.send((1*10000 + (general1.p.x+30) * 100 +20+general1.p.y) + "," + decision);
		general2 = new Food(1);
		general2.p = snake[0].head.p;
			Body temp8 = snake[0].head;
			while (temp8 != null) {
				if (temp8.p.equals(general2.p)) {
						general2.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp8 = snake[0].head;

				} else if (temp8 == snake[0].tail)
					temp8 = snake[1].head;
				else
					temp8 = temp8.next;
			}
		client.send((7*10000 + (general2.p.x+30) * 100 +20+general2.p.y) + "," + decision);
		
	
	}
	

	public static void setSpecialFood(Snake[] snake , int decision) {
		special = new Food(2);
		special.p = snake[0].head.p;
		
			Body temp = snake[0].head;
			while (temp != null) {
				if (temp.p.equals(special.p)) {
					special.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp = snake[0].head;

				} else if (temp == snake[0].tail)
					temp = snake[1].head;
				else
					temp = temp.next;
			}
			client.send((2*10000+(30+special.p.x)*100+special.p.y+20) + "," + decision);
			
		}
	

	public static void setGrassFood(Snake[] snake , int decision) {
		grass = new Food(3);
		grass.p = snake[0].head.p;
		
			Body temp = snake[0].head;
			while (temp != null) {
				if (temp.p.equals(grass.p)) {
					grass.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp = snake[0].head;

				} else if (temp == snake[0].tail)
					temp = snake[1].head;
				else
					temp = temp.next;
			}
			client.send((3*10000+(30+grass.p.x)*100+20+grass.p.y) + "," + decision);
		}
	

	public static void setRockFood(Snake[] snake , int decision) {
		
		    rock1.p = snake[0].head.p;
		
			Body temp = snake[0].head;
			while (temp != null) {
				if (temp.p.equals(rock1.p)) {
					rock1.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp = snake[0].head;

				} else if (temp == snake[0].tail)
					temp = snake[1].head;
				else
					temp = temp.next;
			}
			client.send((4*10000+(30+rock1.p.x)*100+20+rock1.p.y) + "," + decision);
			
			rock2.p = snake[0].head.p;
			
			Body temp2 = snake[0].head;
			while (temp2 != null) {
				if (temp2.p.equals(rock2.p)) {
					rock2.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp2 = snake[0].head;

				} else if (temp2 == snake[0].tail)
					temp2 = snake[1].head;
				else
					temp2 = temp2.next;
			}
			client.send((5*10000+(30+rock1.p.x)*100+20+rock1.p.y) + "," + decision);
			
			rock3.p = snake[0].head.p;
			
			Body temp3 = snake[0].head;
			while (temp3 != null) {
				if (temp3.p.equals(rock3.p)) {
					rock3.p = new Point(
							(int) ((Map.width - 1) * 2.0 * (Math.random() - 0.5)),
							(int) ((Map.height - 1) * 2.0 * (Math.random() - 0.5)));
					temp3 = snake[0].head;

				} else if (temp3 == snake[0].tail)
					temp3 = snake[1].head;
				else
					temp3 = temp3.next;
			}
			client.send((6*10000+(30+rock1.p.x)*100+20+rock1.p.y) + "," + decision);
			
		}


	public static void drawFood() {
		StdDraw.setPenColor(general1.c);
		
		StdDraw.filledSquare(general1.p.x, general1.p.y, 0.5);
		StdDraw.setPenColor(general2.c);
		
		StdDraw.filledSquare(general2.p.x, general2.p.y, 0.5);
//		System.out.println("aaaaa");
		if (special.p != null) {
			StdDraw.setPenColor(special.c);
			
			StdDraw.filledCircle(special.p.x,special.p.y, 0.5);
		
		}
//		System.out.println("aaaaa");
		if (grass.p != null) {
			StdDraw.setPenColor(grass.c);
			
			StdDraw.filledCircle(grass.p.x, grass.p.y, 0.5);
			
		}
			if (rock1.p != null){
				StdDraw.setPenColor(rock1.c);
			StdDraw.filledSquare(rock1.p.x, rock1.p.y, 0.5);
			}
		if (rock2.p != null){
			StdDraw.setPenColor(rock2.c);
			StdDraw.filledSquare(rock2.p.x, rock2.p.y, 0.5);
			}
		if (rock3.p != null){
				StdDraw.setPenColor(rock3.c);
				StdDraw.filledSquare(rock3.p.x, rock3.p.y, 0.5);
			}
		
	}

	
	private static void reportNewRank(){
        int i;
        for(i=4;i>=0;i--){
            if(result<=rank[i])
                break;
        }

        i++;
        
        JOptionPane.showMessageDialog(null,"New High Rank!\nYou're Rank "+i+"!\n"+"1: "+rank[0]+
        "\n2: "+rank[1]+ "\n3: "+rank[2]+ "\n4: "+rank[3]+ "\n5: "+rank[4]);
    }

    private static void reportRank(){
        JOptionPane.showMessageDialog(null,"You didn't beat old recods :P\nOld Records: \n"+"1: "+rank[0]+
                "\n2: "+rank[1]+ "\n3: "+rank[2]+ "\n4: "+rank[3]+ "\n5: "+rank[4]);
    }
    private static boolean isNewRank(){
        if(result>rank[4]){
            int i;
            for(i=4;i>=0;i--){
                if(result<=rank[i])
                    break;
            }

            i++;
            for(int j=i+1;j<5;j++)
                rank[j]=rank[j-1];
            rank[i]=result;
            try{
                PrintStream rankout = new PrintStream("src/rank.txt");
                for(i=0;i<5;i++)
                    rankout.println(rank[i]);
            }catch (Exception e){}



            return true;
        }else
            return false;

    }
    public static void pauseGame(){
        
        Snake.pauseMove();

    }

    public static void resumeGame(){     
        Snake.resumeMove();

    }


    

}

