//James O'Meara
//13715519

package Main;

import javax.swing.JPanel;

import StateManager.GameStateManager;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, MouseListener {
     
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Thread thread;
	private BufferedImage image;
	private Graphics g;
	//variabel to see if the game is running or not,
	private boolean running;
	private GameStateManager gsm;

	public GamePanel() {
              super();
              
              setPreferredSize(new Dimension(WIDTH, HEIGHT));
              setFocusable(true);
              requestFocus();
      }
     
      public void addNotify() {
              super.addNotify();
              if(thread == null) {
                      addMouseListener(this);
                      //start thread
                      thread = new Thread(this);
                      thread.start();
              }
              
      }
     
      // initializes variables
      private void init() {
             //initalize the buffered image to get a smooth image
              image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
              g = image.getGraphics();
             
              running = true;
              
              //create the game state manager
              //will automatically select main menu 
              gsm = new GameStateManager();
             
      }
     
      // the "main" function
      public void run() {
             
      	//initalise the thread
      	init();
      	

      	// simple game loop
      	while(running) {
     
      		update();
      		draw();
      		drawToScreen();

      		try {
      			Thread.sleep(250);
	        }catch(Exception e) {	e.printStackTrace();	}
      		}
      	}
     
      // updates the game
      private void update() {
      	gsm.update();
      }
     
      // draws the game onto an off-screen buffered image
      private void draw() {
      	gsm.draw(g);
      }
     
      // draws the off-screen buffered image to the screen
      private void drawToScreen() {
              Graphics g = getGraphics();
              g.drawImage(image, 0, 0, null);
              g.dispose();
      }
     


	public void mouseClicked(MouseEvent m) {		
	}

	public void mouseEntered(MouseEvent m) {
	}

	public void mouseExited(MouseEvent m) {		
	}

	public void mousePressed(MouseEvent m) {	
		gsm.mousePressed(m);
	}

	public void mouseReleased(MouseEvent m) {
		
	}
  
}