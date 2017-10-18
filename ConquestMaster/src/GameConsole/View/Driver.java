package GameConsole.View;


import javax.swing.JFrame;
/**
 *  The main entrance of the application
 *
 */
public class Driver extends JFrame{
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("all")
	public static void main(String[] args) {
		try {
			WindowMain w = new WindowMain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}