//James O'Meara
//13715519

package States;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import StateManager.GameState;
import StateManager.GameStateManager;

public class MenuState extends GameState {

	//hold the images for the menu here
	private Image startIM;
	private Image randomIM;
	private Image exitIM;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		//get all images
		startIM = new ImageIcon("res/start.png").getImage();
		randomIM = new ImageIcon("res/random.png").getImage();
		exitIM = new ImageIcon("res/exit.png").getImage();
		
		// Initialize the game grid to all false
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				grid[x][y]=false;
			}
		}
	}
	
	
	//update the menu
	public void update() {
		//Nothing to update continually here
	}

	
	//draw this onto screen
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);
		
		// redraw all game objects
		g.setColor(Color.GREEN);
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				if (grid[x][y]) {
					g.fillRect(x*box, y*box, box, box);
				}	
			}
		}
		
		//draw the menu buttons
		g.drawImage(startIM, 10,10,50,30, null);
		g.drawImage(randomIM, 70,10,90,30, null);
		g.drawImage(exitIM, 170,10,50,30, null);

	}
	
	
	//generate a random number of live cells, in random places
	public void generate(){
		for (int x=0;x<gridSize;x++) {
			for (int y=0;y<gridSize;y++) {
				
				int t = (int)(Math.random()*5) + 1;
				if (t == 1){
					grid[x][y]=true;
				}
				
			}
		}
	}


	//dectect mouse clickes on the images 1st, as not to create live cells over images
	//then if not clicked on image but clicked elsewhere create a live cell there
	public void mousePressed(MouseEvent m) {
		//again get the rectangle bounds fo the image and check if the mouse was 
		//clicked inside it.
		if (getBoundsStart().contains(m.getX(),m.getY())){
			gsm.setCurrentState(GameStateManager.PLAYSTATE);
			return;
		}
		if (getBoundsRandom().contains(m.getX(),m.getY())){
			generate();			
			return;
		}
		if (getBoundsExit().contains(m.getX(),m.getY())){
			System.exit(0);		
			return;
		}
		
		//if no click on an image determine where it was clicked
		//eg: game screen size = 800 and 40 elements
		//divide the x position by the number of elements then by 2 to determine 
		//what relevant cell it is in the grid
		int x = m.getX()/(gridSize/2);
		int y = m.getY()/(gridSize/2);
		// toggle the state of the cell to the opposite of what it currently is
		grid[x][y] = !grid[x][y];
	}
	
	
	//returns a rectangle the same dimensions as the image 
	//so we can test if the mouse is clicked inside it
	protected Rectangle getBoundsStart(){
		return new Rectangle(10,10,50,30);		}
	protected Rectangle getBoundsRandom(){
		return new Rectangle(70,10,90,30);		}
	protected Rectangle getBoundsExit(){
		return new Rectangle(170,10,50,30);		}

}
