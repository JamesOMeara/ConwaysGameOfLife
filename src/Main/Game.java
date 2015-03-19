//James O'Meara
//13715519

package Main;

import javax.swing.JFrame;

public class Game {
 
	public static void main(String[] args) {
        	//create game window
        	//then create the gamepanel inside the window to later call and determine which gamestate we are in
		JFrame window = new JFrame();
        window.setTitle("State Based Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        }  
}

