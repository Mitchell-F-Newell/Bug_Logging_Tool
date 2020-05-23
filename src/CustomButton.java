// CustomButton.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 *This class contains the CustomButton logic and construction.
 * @author Code created by: Jared Brintnell, Mitchell Newell & Davis Roman
 * ID: 30002066, 30006529, 30000179
 * @version 1.0
 */
public class CustomButton extends JButton {
	
	/**
	 * The custom shape for the button.
	 */
	private Shape buttonShape;
	
	/**
	 * The constructor for the custom button which creates/initializes the button.
	 * @param size Sets the size(width) of the button.
	 * @param text Sets a default text to present within the button.
	 */
	public CustomButton (int size, String text) {
		super(text);
		setOpaque(false);
		setPreferredSize(new Dimension(size * 10, 30));
		setBackground(new Color(223, 217, 226));
		setFont(new Font("Candara", Font.PLAIN, 16));
		super.setContentAreaFilled(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 *Sets the colour of the button and repaints the button to that color.
	 *@param g the graphics of the button.
	 */
	 @Override
	protected void paintComponent (Graphics g) {
		g.setColor(new Color(223, 217, 226));
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
		super.paintComponent(g);
	}
	
	/**
	 * Changes the colour of the border of the button if the button is pressed.
	 * @param g the graphics of the button.
	 */
	protected void paintBorder (Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(new Color(79, 193, 0));
		} else {
			g.setColor(new Color(53, 50, 48));
		}
		g.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 1, 5, 5);
		g.drawRoundRect(1, 0, getWidth() - 2, getHeight() - 1, 5, 5);
		g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 5, 5);
	}
	
	/**
	 * Tell the frame what the container size of the object is.
	 * @param x the length of the container size
	 * @param y the the height of the container size.
	 */
	public boolean contains (int x, int y) {
        if (buttonShape == null || !buttonShape.getBounds().equals(getBounds())) {
            buttonShape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        return buttonShape.contains(x, y);
    }
	
	/**   
	 * {@inheritDoc}  
	 */
	@Override
	public void setContentAreaFilled (boolean b) {	
	}
}