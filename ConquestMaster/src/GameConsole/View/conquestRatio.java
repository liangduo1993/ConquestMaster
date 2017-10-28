package GameConsole.View;


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * 
 *	this class is responsible for displaying conquestRatio of a player. Based on one player own countries/total countries.
 *
 */
public class conquestRatio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lb1;
	private JLabel lb2;

	/**
	 * constructor method, conquestRatio extends JPanel and input parameters.
	 */
	public conquestRatio() {
		this.setLayout(new GridLayout(1, 1));
		this.setBounds(0, 0, 500, 100);

		lb1 = new JLabel("p1");
		lb1.setHorizontalAlignment(SwingConstants.CENTER);
		lb1.setBounds(0, 0, this.getWidth() / 2, 100);

		lb2 = new JLabel("p2");
		lb2.setBounds(this.getWidth() / 2, 0, this.getWidth() / 2, 100);
		lb2.setHorizontalAlignment(SwingConstants.CENTER);

		this.add(lb1);
		this.add(lb2);
	}
	
	/**
	 * this method is using to override paintComponent method and draw rectangles to show the ratio.
	 */
	public void paintComponent(Graphics g) {
		g.drawRect(0, 0, this.getWidth(), 100);
		g.drawRect(0, 0, this.getWidth() / 2, 100);
	}

	public static void main(String[] args) {
		conquestRatio panel = new conquestRatio();
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 500, 140);
		frame.setVisible(true);
		frame.getContentPane().add(panel);
	}
}
