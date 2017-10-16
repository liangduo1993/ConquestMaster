package gameConsole.window;


import javax.swing.JFrame;
/**
 *  The main entrance of the application
 *
 */
public class driver extends JFrame{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WindowMain w = new WindowMain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}