// CustomJButton.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
//Class to create a custom button
public class CustomJButton extends JButton {
	//Shape for the button
	private Shape buttonShape;
	//Constructor for the custom button
	public CustomJButton (int sizeX, int sizeY, String text) {
		super(text);
		setOpaque(false);
		setPreferredSize(new Dimension(sizeX * 10, sizeY));
		setBackground(new Color(54, 51, 51));
		setFont(new Font("Candara", Font.BOLD, 20));
		super.setContentAreaFilled(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	//The paint component/color/graphic of the button
	protected void paintComponent (Graphics g) {
		g.setColor(new Color(162, 154, 154)); // FIX
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        if (getModel().isPressed()) {
			setForeground(new Color(144, 21, 21));
		} else {
			setForeground(new Color(54, 51, 51));
		}
		super.paintComponent(g);
	}
	// Paints the Border
	protected void paintBorder (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(5));
		if (getModel().isPressed()) {
			g2.setColor(new Color(144, 21, 21));
		} else {
			g2.setColor(new Color(54, 51, 51));
		}
		g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2.setStroke(oldStroke);
	}
	 // Returns true or False
	public boolean contains (int x, int y) {
        if (buttonShape == null || !buttonShape.getBounds().equals(getBounds())) {
            buttonShape = new Rectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1);
        }
        return buttonShape.contains(x, y);
    }
	// Sets Content Area Filled
	public void setContentAreaFilled (boolean b) {
	}
}
