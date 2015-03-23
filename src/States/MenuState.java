//James O'Meara
//13715519

package States;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;

import StateManager.GameState;
import StateManager.GameStateManager;

public class MenuState extends GameState {

	//hold the images for the menu here
	private Image startIM;
	private Image randomIM;
	private Image saveIM;
	private Image openIM;
	private Image resetIM;


	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		//get all images
		startIM = new ImageIcon("res/start.png").getImage();
		randomIM = new ImageIcon("res/random.png").getImage();
		saveIM = new ImageIcon("res/save.png").getImage();
		openIM = new ImageIcon("res/open.png").getImage();
		resetIM = new ImageIcon("res/reset.png").getImage();


		
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
		g.drawImage(saveIM, 170,10,50,30, null);
		g.drawImage(openIM, 230,10,50,30, null);
		g.drawImage(resetIM, 290,10,50,30, null);


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
	private int x;
	private int y;
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
		if (getBoundsSave().contains(m.getX(),m.getY())){	
			writeGridToFile("Save1");		
			System.out.println("current grid saved");
			return;
		}
		if (getBoundsOpen().contains(m.getX(),m.getY())){
			readFile("save1");
			System.out.println("opened file");
			return;
		}
		if (getBoundsReset().contains(m.getX(),m.getY())){
			blank();
			System.out.println("grid reset");
			return;
		}
		
		//if no click on an image determine where it was clicked
		//eg: game screen size = 800 and 40 elements
		//divide the x position by the number of elements then by 2 to determine 
		//what relevant cell it is in the grid
		
		x = m.getX()/(gridSize/2);
		y = m.getY()/(gridSize/2);
		// toggle the state of the cell to the opposite of what it currently is
		

		grid[x][y] = !grid[x][y];
		
		return;
		
	}
	
	public void mouseMoved(MouseEvent m) {
		//not realy neeeded
	}


	public void mouseDragged(MouseEvent m) {
		x = getX(m);
		y = getY(m);
		
		//set the position to true to draw a live cell
		grid[x][y] = true;


	}

	public void mouseReleased(MouseEvent m) {
		//not needed
	}
	
	//returns a rectangle the same dimensions as the image 
	//so we can test if the mouse is clicked inside it
	protected Rectangle getBoundsStart(){
		return new Rectangle(10,10,50,30);		}
	protected Rectangle getBoundsRandom(){
		return new Rectangle(70,10,90,30);		}
	protected Rectangle getBoundsSave(){
		return new Rectangle(170,10,50,30);		}
	protected Rectangle getBoundsOpen(){
		return new Rectangle(230,10,50,30);		}
	protected Rectangle getBoundsReset(){
		return new Rectangle(290,10,50,30);		}
	
	//get position x and y
	public int getX(MouseEvent m){
		return m.getX()/(gridSize/2);
	}
	public int getY(MouseEvent m){
		return m.getY()/(gridSize/2);
	}

	//create a blank screen with no live cells
	public void blank(){
		for (int i = 0; i<gridSize; i++){
			for (int j = 0; j< gridSize; j++){
				grid[i][j] = false;
			}
		}
	}
	
	//write current grid to file
	public void writeGridToFile(String fileName){
		String filename = fileName;
		String text = "";
		for (int i = 0; i < gridSize; i++){
			for (int j = 0; j< gridSize; j++){
				if (grid[i][j]){
					text += "1";
				}else{
					text += "0";
				}
			}
		}
		try{
		BufferedWriter writer = new BufferedWriter(new FileWriter("res/" + filename));
		writer.write(text);
		writer.close();
		}
		catch(IOException e) { }
	}

	//read file and draw to screen
	public void readFile(String fileName){
		String filename = fileName;
		boolean[][] newGrid = new boolean[gridSize][gridSize];
		//read from file 
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/" + filename));
			
			//get the text and change to char array
			String line = reader.readLine();
			char[] c = line.toCharArray();

			//go through the text and create a new grid with the values from the file
			int m = 0;
			for (int i = 0; i < gridSize; i++){
				for (int j = 0; j< gridSize; j++){
					if (c[m] == '1'){
						newGrid[i][j] = true;
					}else{
						newGrid[i][j] = false;
					}
					m++;
				}
			}
			
			//set current grid to this new grid
			grid = newGrid;
	
		} catch (IOException e) {	}
	}
}





