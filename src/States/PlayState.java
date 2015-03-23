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

public class PlayState extends GameState {

	//boolean, to only automatically update moves if simulate is selected
	private boolean animate = true;
	//hold the images
	private Image stepIM;
	private Image simulateIM;
	private Image stopIM;
	private Image menuIM;

	public PlayState(GameStateManager gsm){
		this.gsm = gsm;
		//ge all images
		stopIM = new ImageIcon("res/stop.png").getImage();
		simulateIM = new ImageIcon("res/simulate.png").getImage();
		stepIM = new ImageIcon("res/step.png").getImage();
		menuIM = new ImageIcon("res/menu.png").getImage();
	}
	
	public void update() {
		//only simulate if told other wise wait for step to be pressed
		if(animate){
			step();
		}
	}

	//do ne iteration of the simulation
	public void step(){
		//create a new grid, as to not modify the one you are 
		//checking now
		boolean newGrid[][] = new boolean[gridSize][gridSize];
		//go through all cells and count #neighbors
		//apply the rules and make a new grid 
		for (int x=0;x<gridSize;x++) {
			for (int y=0;y<gridSize;y++) {
				int number = 0;
				//===========top row above selected cell====================================
				if(y > 0 && x > 0 && grid[x-1][y-1] == true){
					number++;		}	//top left
				if(y > 0 && grid[x][y-1] == true){
					number++;		}	//top square
				if(y > 0 && x < (grid.length -1) && grid[x+1][y-1] == true){
					number++;		}	//top right square
				//===============middle row above selected cell=============================
				if(x > 0 && grid[x-1][y] == true){
					number++;		}  //left square	
				if(x < (grid.length -1) && grid[x+1][y] == true){
					number++;		}	//right square
				//================bottom row above selected cell============================
				if(y < (grid.length -1) && x > 0 && grid[x-1][y+1] == true){
					number++;	}	//bottom left
				if(y < (grid.length -1) && grid[x][y+1] == true){
					number++;	}	//bottom
				if(y < (grid.length -1) && x < (grid.length -1) && grid[x+1][y+1] == true){
					number++;		}		//bottom right
				//==========================================================================
				
				
				//apply the rules
				//first check if the cell is alive
				if ( grid[x][y] == true ){
					//so if it is alive and have 2 or 3 neighbors 
					//this means it will stay alive
					if(number == 2 || number == 3){
						//stay alive
						newGrid[x][y] = true;
					}else{
						//else it will die
						newGrid[x][y] = false;
					}
				}else{
					//so if the cell is not alive to begin with
					//check if it has 3 neighbors if it does it will come to life
					if(number == 3){
						//bring back to life
						newGrid[x][y] =true;
					}else{//otherwise
						//it is dead
						newGrid[x][y] = false;
					}
				}
			}
		}
		//set the new grid to the old one
		grid = newGrid;
	}
	
	//change the boolean value so auto simulation will occur
	public void startAnimation(){
		animate = true;
	}

	//this is what wil be drawn to screen
	public void draw(Graphics g) {		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);
		
		// redraw all game objects
		g.setColor(Color.RED);
		for (int x=0;x<gridSize;x++) {
			for (int y=0;y<gridSize;y++) {
				if (grid[x][y]) {
					g.fillRect(x*box, y*box, box, box);
				}	
			}
		}
		//draw the buttons
		g.drawImage(stepIM, 10,10,50,30, null);
		g.drawImage(simulateIM, 70,10,60,30, null);
		g.drawImage(stopIM, 140,10,50,30, null);
		g.drawImage(menuIM, 200,10,50,30, null);

	}

	//detect mouse clicks and where they are
	public void mousePressed(MouseEvent m) {
		//if mouse is detected in any button
		//get the rectangle of the button and check does it contains the mouse pointer
		//when the mouse is clicked
		if (getBoundsStep().contains(m.getX(),m.getY())){
			step();
			return;
		}
		if (getBoundsSimulate().contains(m.getX(),m.getY())){
			animate = true;
			return;
		}
		if (getBoundsStop().contains(m.getX(),m.getY())){
			animate = false;
			return;
		}
		if (getBoundsMenu().contains(m.getX(),m.getY())){
			animate = false;
			gsm.setCurrentState(GameStateManager.MENUSTATE);
			return;
		}
	}

	
	//returns a rectangle the same dimensions as the image 
	//so we can test if the mouse is clicked inside it
	protected Rectangle getBoundsStep(){
		return new Rectangle(10,10,50,30);		}
	protected Rectangle getBoundsSimulate(){
		return new Rectangle(70,10,60,30);		}
	protected Rectangle getBoundsStop(){
		return new Rectangle(140,10,50,30);		}
	protected Rectangle getBoundsMenu(){
		return new Rectangle(200,10,50,30);		}

	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

}
