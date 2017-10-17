package GameConsole.Window;


import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GameConsole.Player.Player;
import GameConsole.World.Country;

/**
 * This class is attach a button to each country
 */
public class CountryButton{
	public String name;
	//public BufferedImage image;
	public JButton b;
	public Country country;
	private JLabel label;
	
//	public CountryButton(BufferedImage i, String name, Country c){
//		this.image = i;
//		this.country = c;
//		c.setButton(this);
//		this.name = name;
//		
//		this.label = new JLabel(1 + ""); // defining the label that will say the number of troops
//		this.label.setHorizontalAlignment(SwingConstants.CENTER);
//		this.label.setForeground(Color.RED);
//		this.label.setFont(new Font("Dialog", Font.PLAIN, 20));
//		
//		this.b = new JButton(new ImageIcon(image)) {
//	        @Override
//	        public boolean contains(int x, int y) {
//	            Rectangle viewRect = getBounds();
//	            Insets insets = getInsets();
//	            viewRect.x = insets.left;
//	            viewRect.y = insets.top;
//	            viewRect.width -= insets.left + insets.right;
//	            viewRect.height -= insets.top + insets.bottom;
//	            Rectangle iconR = new Rectangle();
//	            SwingUtilities.layoutCompoundLabel(this, this.getFontMetrics(this.getFont()), this.getText(), this.getIcon(),
//	                    this.getVerticalAlignment(), this.getHorizontalAlignment(), this.getVerticalTextPosition(),
//	                    this.getHorizontalTextPosition(), viewRect, iconR, new Rectangle(), this.getIconTextGap());
//	            if (!iconR.contains(x, y)) {
//	                return false;
//	            }
//	            x -= iconR.x;
//	            y -= iconR.y;
//	            Color c = new Color(image.getRGB(x, y), true);
//	            return c.getAlpha() != 0 && (c.getRed() < 255 || c.getGreen() < 255 || c.getBlue() < 255);
//	        }
//	    };
//	    b.setOpaque(false);
//	    b.setContentAreaFilled(false);
//	    b.setBorderPainted(false);
//	    /*
//	    b.addActionListener(new ActionListener(){
//	    	@Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(b, "You clicked on the " + country.getName() + " button");
//            }
//	    });
//	    */
//	}

	/**
	 * Constructor method with incoming parameters
	 * @param i the image with BufferedImage type
	 * @param name the button name with String type
	 * @param c the country will be attached with the button with Country type
	 */
	public CountryButton(BufferedImage i, String name, Country c){
		//this.image = i;
		this.country = c;
		c.setButton(this);
		this.name = name;
		
		this.label = new JLabel(1 + ""); // defining the label that will say the number of troops
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setForeground(Color.RED);
		this.label.setFont(new Font("Dialog", Font.PLAIN, 20));
		//label.setBounds(c.getXLoc(), c.getYLoc(), 20, 20);
		//label.setBounds(100, 100, 20, 20);
		
		this.b = new JButton() {
//	        @Override
//	        public boolean contains(int x, int y) {
//	            Rectangle viewRect = getBounds();
//	            Insets insets = getInsets();
//	            viewRect.x = insets.left;
//	            viewRect.y = insets.top;
//	            viewRect.width -= insets.left + insets.right;
//	            viewRect.height -= insets.top + insets.bottom;
//	            Rectangle iconR = new Rectangle();
//	            SwingUtilities.layoutCompoundLabel(this, this.getFontMetrics(this.getFont()), this.getText(), this.getIcon(),
//	                    this.getVerticalAlignment(), this.getHorizontalAlignment(), this.getVerticalTextPosition(),
//	                    this.getHorizontalTextPosition(), viewRect, iconR, new Rectangle(), this.getIconTextGap());
//	            if (!iconR.contains(x, y)) {
//	                return false;
//	            }
//	            x -= iconR.x;
//	            y -= iconR.y;
//	            Color c = new Color(i.getRGB(x, y), true);
//	            return c.getAlpha() != 0 && (c.getRed() < 255 || c.getGreen() < 255 || c.getBlue() < 255);
//	        }
	    };
	    b.setOpaque(false);
	    b.setContentAreaFilled(false);
	    b.setBorderPainted(false);
//	    b.setText("i am a button");
	    /*
	    b.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(b, "You clicked on the " + country.getName() + " button");
            }
	    });
	    */
	}


	/**
	 * To set the bounds of the button and label
	 * @param x x coordinate location with int type
	 * @param y y coordinate location with int type
	 * @param w the width with int type
	 * @param h the height with int type
	 */
	public void setDoubleBounds(int x, int y, int w, int h) {
		this.b.setBounds(x, y, w, h);
		this.label.setBounds(x, y, w, h);
	}
	
	public void fixLabelBounds(int x, int y, int w, int h) {
		this.label.setBounds(x, y, w, h);
	}

	/**
	 * To get the label
	 * @return the label with JLabel type
	 */
	public JLabel getLabel()  {
		return this.label;
	}

	/**
	 * Method to update the troop size on the label
	 * @param p the player who owns the corresponding country of the button with Player type
	 */
	public void updateLabel(Player p) {
		this.label.setText("" + this.country.getTroops().size());
		this.label.setForeground(p.getColor());
	}
	
	
}