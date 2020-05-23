import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
//The window to add a product to the sql database
public class DeveloperInfoWindow {
	private Developer theDeveloper;
	public DeveloperInfoWindow(Developer aDeveloper) {
		theDeveloper = aDeveloper;
		this.display();
	}
    // Sets up the window to display correctly
	public void display() {
		  Font defaultFont = new Font("Candara", Font.BOLD, 20);
	        Color defaultColor = new Color (54, 51, 51);

	        // Create Frame
	        JFrame main = new JFrame();
			main.setTitle("RAID Bug Tracking System: Product Information");
			main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
			main.setSize(700, 500);
			main.setLocationRelativeTo(null);
			main.setResizable(false);
			main.setLayout(new BorderLayout());
	        main.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

	        // Set background image
	        JPanel background = new JPanel(new BorderLayout());

	        // Panel to control the display
	        JPanel controlBox = new JPanel();
	        controlBox.setLayout(new BorderLayout());
	        controlBox.setOpaque(false);

	        JPanel userPanel = new JPanel();
	        userPanel.setLayout(new GridLayout(0, 1));
	        userPanel.setOpaque(false);
	        JLabel name = new JLabel("Developers Name: " + theDeveloper.getName());
	        JLabel username = new JLabel("Developers username: " + theDeveloper.getUsername());
	        //JLabel numBugs = new JLabel("Number of Bugs: " + theProduct.getNumberOfBugs());
	        
	        name.setBorder(new EmptyBorder (9, 0, 9, 0));
	        name.setForeground(new Color(0, 0, 0));
			name.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(name);
	        
	        username.setBorder(new EmptyBorder (9, 0, 9, 0));
	        username.setForeground(new Color(0, 0, 0));
			username.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(username);
	        
	        JTextArea details = new JTextArea("Details: " + theDeveloper.getDetails());
	        details.setEditable(false);
	        details.setLineWrap(true);
	        details.setBorder(new EmptyBorder (9, 0, 9, 0));
	        details.setForeground(new Color(0, 0, 0));
			details.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        background.add("Center", details);
	        controlBox.add(userPanel);
	        
	        background.add("North", controlBox);
	        main.add(background);
	        main.setVisible(true);
	}
}
