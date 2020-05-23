// BackgroundPanel.java
import java.awt.*;
import javax.swing.*;

//Background Panel Class
public class BackgroundPanel extends JPanel {
	//Image Used for the Background of the Program
    ImageIcon backgroundImage;

    //Constructor for the Class
    public BackgroundPanel (LayoutManager layout, String image) {
		super(layout);
		backgroundImage = new ImageIcon(image);
	}
    // Paints the Image onto the back panel
    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
	}
}
