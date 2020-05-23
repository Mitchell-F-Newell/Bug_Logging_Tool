import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
//Class generates product info window
public class ProductInfoWindow {
	//THe product whose info is being displayed
	private Product theProduct;
	//Constructor for the product info window
	public ProductInfoWindow(Product aProduct) {
		theProduct = aProduct;
		this.display();
	    }
	//The display/GUI code that ensures the window works properly
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
	        JLabel productname = new JLabel("Product Name: " + theProduct.getName());
	        JLabel created = new JLabel("Date Created: " + theProduct.getCreated());
	        //JLabel numBugs = new JLabel("Number of Bugs: " + theProduct.getNumberOfBugs());
	        
	        productname.setBorder(new EmptyBorder (9, 0, 9, 0));
	        productname.setForeground(new Color(0, 0, 0));
			productname.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(productname);
	        
	        created.setBorder(new EmptyBorder (9, 0, 9, 0));
	        created.setForeground(new Color(0, 0, 0));
			created.setFont(new Font("Britannic Bold", Font.BOLD, 26));
	        userPanel.add(created);
	        
	        JTextArea details = new JTextArea("Details: " + theProduct.getDetails());
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
