//James O'Meara
//13715519

package StateManager;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import States.MenuState;
import States.PlayState;

public class GameStateManager {

	//here is where we will manage which state we are in and what update and draw methods we will use
	private ArrayList<GameState> gameStates;
	private int currentState;

	//create a final variable or each state
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	
	public GameStateManager(){
		//make a arraylist to hold the states
		gameStates = new ArrayList<GameState>();
		
		//add thes states in the asme order as the static final ints
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));

		//and set a current state as default
		currentState = MENUSTATE;
	}
	
	//a method for when we wish to change the current state
	public void setCurrentState(int i ){
		currentState = i;
	}
	
	//update a particular state
	//use the methods of the state
	public void update(){
		gameStates.get(currentState).update();			}
	public void draw(Graphics g){
		gameStates.get(currentState).draw(g);			}
	public void mousePressed(MouseEvent m){
		gameStates.get(currentState).mousePressed(m);	}

}
