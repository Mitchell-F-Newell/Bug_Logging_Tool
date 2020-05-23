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
//Window to add/update the info of a bug
public class BugInfoWindow {
	//The bug which is being updated
	private Bug theBug;
	//Class constructor for bug window
	public BugInfoWindow(Bug aBug) {
		theBug = aBug;
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
	        JLabel bugName = new JLabel("Bug Name: " + theBug.getName());
	        JLabel fromProduct = new JLabel("Product Bug is in: " + theBug.getProduct());
	        JLabel created = new JLabel("Date Created: " + theBug.getCreated());
	        String stats;
	        if(theBug.getStatus() == 2) {
	        	stats = "Fixed";
	        }else if (theBug.getStatus()==1) {
	        	stats = "Fix in progress.";
	        }else {
	        	stats = "Waiting for aprroval.";
	        }
	        JLabel status = new JLabel ("Bug Status: " +stats);
	        String devel;
	        if(theBug.getAssignedDev() == null) {
	        	devel = "No assigned developer.";
	        }else {
	        	devel = theBug.getAssignedDev();
	        }
	        JLabel dev = new JLabel("Assigned Developer: " + devel);
	        
	        bugName.setBorder(new EmptyBorder (9, 0, 9, 0));
	        bugName.setForeground(new Color(0, 0, 0));
			bugName.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(bugName);
	        
	        fromProduct.setBorder(new EmptyBorder (9, 0, 9, 0));
	        fromProduct.setForeground(new Color(0, 0, 0));
			fromProduct.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(fromProduct);
	        
	        created.setBorder(new EmptyBorder (9, 0, 9, 0));
	        created.setForeground(new Color(0, 0, 0));
			created.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(created);

	        status.setBorder(new EmptyBorder (9, 0, 9, 0));
	        status.setForeground(new Color(0, 0, 0));
			status.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(status);
	        
	        dev.setBorder(new EmptyBorder (9, 0, 9, 0));
	        dev.setForeground(new Color(0, 0, 0));
			dev.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(dev);
	        
	        JTextArea details = new JTextArea("Details:\n" + theBug.getDetails());
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
