package snake;


import java.awt.*;


public class Snake extends Thread{
   protected char direction = 'l';
   protected Body head = null;
   protected Body tail = null;
   protected boolean life = true;
   
    static boolean pausing = false;
    static boolean moving = false;
   protected static class Body {
       Point p;
       Color c;
       Body last;
       Body next;

       public Body(int i) {
           if (i == 1)
               this.c = Color.CYAN;
           else
               this.c = Color.PINK;
       }
   }

   Snake(int d) {
       head = new Body(d);
       if (d == 1)
           head.c = Color.BLUE;
       else
           head.c = Color.RED;
       head.p = new Point(-1, 2 * d - 3);
       head.next = new Body(d);
       head.next.p = new Point(0, 2 * d - 3);
       tail = head;
       while (tail.next != null) {
           tail.next.last = tail;
           tail = tail.next;
       }
   }

   static void draw(Body temp) {
       StdDraw.setPenColor(temp.c);
       StdDraw.filledCircle(temp.p.x, temp.p.y, 0.6);
   }

   public void drawSnake() {
       Body temp = head;
       do {
           draw(temp);
           
           temp = temp.next;
       } while (temp != null);
   }

   public boolean alive(boolean b, Snake another) {
       Body temp = head.next;
      
       do {
           if (head.p.equals(temp.p)) {
               life = false;
               return life;
           }
           temp = temp.next;
       } while (temp != null);
       if (!b) {
           if (head.p.x >= Map.width || head.p.x <= -Map.width
                   || head.p.y >= Map.height || head.p.y <= -Map.height) {
               life = false;
               return life;
           }
       }
       if (another != null) {
           temp = another.head;
           temp = temp.next;
           do {
               if (head.p.equals(temp.p)) {
                   life = false;
                   return life;
               }
               temp = temp.next;   
           } while (temp != null);
       }
      
       if (head.p.equals(Game.rock1.p))
           life = false;
       
       if (head.p.equals(Game.rock2.p))
           life = false;
      
       if (head.p.equals(Game.rock3.p))
           life = false;
       
       return life;
   }
   public boolean isequalto (boolean b, Snake another){
	  
           if (head.p.equals(another.head.p)) {
               life = false;
               another.life = false;
               return false;
           }
           return life;
   }

   public void move(char in, boolean b) {
       if (direction == 'u' || direction == 'd') {
           if (in == 'l') {
               moveleft(b);
               
               return;
           }
           if (in == 'r') {
               moveright(b);
               
               return;
           }
           movestill(b);
           return;
       } else {
           if (in == 'u') {
               moveup(b);
               return;
          
           }
           if (in == 'd') {
               movedown(b);
               
               return;
           }
           movestill(b);
           return;
       }
   }

   private void moveup(boolean b) {
       Body temp = tail;
       
       while (temp != head&& pausing==false) {
           temp.p = temp.last.p;
           temp = temp.last;
       }
       if (head.next.p.y + 1 >= Map.height && b)
           head.p = new Point(head.next.p.x, -Map.height + 1);
       else
           head.p = new Point(head.next.p.x, head.next.p.y + 1);
       direction = 'u';
       return;
   }

   private void movedown(boolean b) {
       Body temp = tail;
       while (temp != head&& pausing==false) {
           temp.p = temp.last.p;
           temp = temp.last;
       }
       if (head.next.p.y - 1 <= -Map.height && b)
           head.p = new Point(head.next.p.x, Map.height - 1);
       else
           head.p = new Point(head.next.p.x, head.next.p.y - 1);
       direction = 'd';
       return;
   }

   private void moveleft(boolean b) {
       Body temp = tail;
    
       while (temp != head&& pausing==false) {
           temp.p = temp.last.p;
           temp = temp.last;
       }
       if (head.next.p.x - 1 <= -Map.width && b)
           head.p = new Point(Map.width - 1, head.next.p.y);
       else
           head.p = new Point(head.next.p.x - 1, head.next.p.y);
       direction = 'l';
       return;
   }

   private void moveright(boolean b) {
       Body temp = tail;
       while (temp != head&& pausing==false) {
           temp.p = temp.last.p;
           temp = temp.last;
       }
       if (head.next.p.x + 1 >= Map.width && b)
           head.p = new Point(-Map.width + 1, head.next.p.y);
       else
           head.p = new Point(head.next.p.x + 1, head.next.p.y);
       direction = 'r';
       return;
   }

   void movestill(boolean b) {
       switch (direction) {
       case 'u':
           moveup(b);
           return;
       case 'd':
           movedown(b);
           return;
       case 'l':
           moveleft(b);
           return;
       case 'r':
           moveright(b);
           return;
       }
   }
   public static void pauseMove() {
       pausing =  true;
   }

   public static void resumeMove() {
       pausing =  false;
   }

   public void stopMove() {
       moving = false;

   }
}
