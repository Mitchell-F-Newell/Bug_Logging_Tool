import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

//The window to add/submit a new bug
public class BugSubmissionWindow {
	//Connection to the mySQL database
	private static Connection myConn;
	//Main JFrame for the windodw
    private JFrame main;
    //Type of User
    private String userType;
    //TextFields for the bugs title and bugs Details
    private JTextField bugDetails, title;
    //Jbutton for submitting the bug submission and updateing the SQL server
    //and Jbutton for cancelling the submission
    private JButton cancelSubmission, submitBug;
    //The product that the new bug is a part of
    private Product theProduct;
    // The listener for the buttons
    private ActionListener buttonEventListener;

    // The event listener to make the buttons call methods
    private class EventListener implements ActionListener {
		BugSubmissionWindow display;
		public EventListener (BugSubmissionWindow d) {
			display = d;
		}
		//Track the button presses and includes the logic
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == submitBug) {
				try {
					display.submit(userType);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == cancelSubmission) {
					display.cancel();
			}
		}
	}
    //Class constructor
    public BugSubmissionWindow(Product product,String uType) throws SQLException {
    	myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bug?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        theProduct = product;
        userType = uType;
        buttonEventListener = new EventListener(this);
        this.display();
    }
    //Sets up the window to display correctly
    public void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        main = new JFrame();
		main.setTitle("RAID Bug Tracking System: New Bug");
		main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
		main.setSize(700, 500);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Extras.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        //JPanel setup
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder (0, 50, 0, 50));

        // Username stuff
        JLabel label = new JLabel("Bug Title:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        title = new CustomJTextField(60);
        content.add(title);

        // Password stuff
        label = new JLabel("Bug Information:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        bugDetails = new CustomJTextField(40);
        content.add(bugDetails);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        submitBug = new CustomJButton(18, 38, "Submit");
        submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);
        cancelSubmission = new CustomJButton(18, 38, "Cancel");
        cancelSubmission.addActionListener(buttonEventListener);
        buttonPanel.add(cancelSubmission);

        JLabel title = new JLabel("Add A New Bug");
        title.setBorder(new EmptyBorder (30, 50, 50, 50));
		title.setForeground(new Color (0, 0, 0));
		title.setFont(new Font ("Eras Bold ITC", Font.PLAIN, 40));

        // Add elements and display
        background.add("North", title);
        controlBox.add("Center", content);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }
    //Cancels the bug submission
    private void cancel() {
    	
    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
 // Submit Button, takes data and submits to sql to update database
    private void submit(String userType) throws ParseException, SQLException {
    	String bugTitle = title.getText();
    	String bugDetail = bugDetails.getText();
    	Blob detailBlob = myConn.createBlob();
    	detailBlob.setBytes(1, bugDetail.getBytes());
    	
    	long time = System.currentTimeMillis();
    	java.sql.Date date = new java.sql.Date(time);
    	
    	if(userType == "ProjectManager") {
    		
    		PreparedStatement stmt = myConn.prepareStatement("INSERT INTO bugs (name, fromProduct, created,"
    				+ "details, status) VALUES(?, ?, ?, ?, ?)");
    		stmt.setString(1, bugTitle); 
    		stmt.setString(2, theProduct.getName()); //theBug.getName());
    		stmt.setDate(3,date);
    		stmt.setBlob(4, detailBlob);
    		stmt.setInt(5, 1);
    		stmt.executeUpdate();
    	}else {
    		PreparedStatement stmt2 = myConn.prepareStatement("INSERT INTO bugs (name, fromProduct, created,"
    				+ "details, status) VALUES(?, ?, ?, ?, ?)");
    		stmt2.setString(1, bugTitle); 
    		stmt2.setString(2, theProduct.getName()); //theBug.getName());
    		stmt2.setDate(3, date);
    		stmt2.setString(4, bugDetail);
    		stmt2.setInt(5, 0);
    		stmt2.executeUpdate();	
    	}
    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
}
