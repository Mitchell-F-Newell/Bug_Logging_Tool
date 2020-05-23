// CustomJTextField.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.geom.Rectangle2D;
//Class to create a custom JTextField
public class CustomJTextField extends JTextField implements FocusListener {
	//CustomJTextField Inset
	private Insets fieldInsets;
	//CustomJTextField shape
	private Shape fieldShape;
	//Class constructor
	public CustomJTextField (int size) {
		super(size);
		setForeground(new Color(54, 51, 51));
		setPreferredSize(new Dimension(size * 10, 50));
		setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		Border b = UIManager.getBorder("TextField.border");
		JTextField field = new JTextField(size);
		fieldInsets = b.getBorderInsets(field);
		setOpaque(false);
		addFocusListener(this);
	}
	//Paints the Text Field
	protected void paintComponent (Graphics g) {
		g.setColor(new Color(162, 154, 154));
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		super.paintComponent(g);
		setMargin(new Insets(2, 6, 2, 2));
	}
	// Paints the Border of the Text Field
	protected void paintBorder (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(5));
		if (hasFocus()) {
			g2.setColor(new Color(144, 21, 21));
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		} else {
			g2.setColor(new Color(54, 51, 51));
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
        g2.setStroke(oldStroke);
    }
	// Returns True of False
	public boolean contains (int x, int y) {
        if (fieldShape == null || !fieldShape.getBounds().equals(getBounds())) {
            fieldShape = new Rectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1);
        }
        return fieldShape.contains(x, y);
    }
	 // Repaints if Focus Gained
	public void focusGained (FocusEvent e) {
		repaint();
	}
	 // repaints if Focus Lost
	public void focusLost (FocusEvent e) {
		repaint();
	}
}
