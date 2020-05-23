import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//The window to update a instance of a bug
public class BugUpdateWindow {
	//The bug that is being updated
    private Bug theBug;
    //The window that is displayed
    private JFrame main;
    //The text field which updates the details of the bug
    private JTextField statusUpdate;
    //The button to submit the bug update to the SQL server and
    //The button to cancel the bug update submission
    private JButton submitUpdate, cancelUpdate;
    // The listener for the buttons
    private ActionListener buttonEventListener;
    // The connection to the sql database
	private static Connection myConn;
	//The checkbox to detail that the bug has been fixed
    public JCheckBox fixed;

    // The event listener to make the buttons call methods
    private class EventListener implements ActionListener {
		BugUpdateWindow display;
		public EventListener (BugUpdateWindow d) {
			display = d;
		}
		//Track the button presses and includes the logic
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == submitUpdate) {
				try {
					display.submit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == cancelUpdate) {
				display.cancel();
			}
		}
	}
    //Class constructor
    public BugUpdateWindow(Bug aBug) throws SQLException{

    	myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bug?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        buttonEventListener = new EventListener(this);
        theBug = aBug;
        this.display();
    }

    // Sets up the window to display correctly
    private void display() {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        main = new JFrame();
		main.setTitle("RAID Bug Tracking System");
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

        // Status Update stuff
        JLabel label = new JLabel("Bug is fixed:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
         statusUpdate= new CustomJTextField(60);
        content.add(statusUpdate);

        //CheckBox
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        checkBoxPanel.setOpaque(false);
        JLabel fix = new JLabel("Fixed:");
        fix.setBorder(new EmptyBorder (12, 0, 12, 0));
		fix.setForeground(defaultColor);
		fix.setFont(defaultFont);
		checkBoxPanel.add(label);
        fixed = new JCheckBox();
        checkBoxPanel.add(fixed);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        submitUpdate = new CustomJButton(19, 38, "Submit");
        submitUpdate.addActionListener(buttonEventListener);
        buttonPanel.add(submitUpdate);
        cancelUpdate = new CustomJButton(19, 38, "Cancel");
        cancelUpdate.addActionListener(buttonEventListener);
        buttonPanel.add(cancelUpdate);

        JLabel title = new JLabel("Update: " + theBug.getName());
        title.setBorder(new EmptyBorder (30, 50, 50, 50));
		title.setForeground(new Color (0, 0, 0));
		title.setFont(new Font ("Eras Bold ITC", Font.PLAIN, 40));

        // Add elements and display
        background.add("North", title);
        controlBox.add("North", content);
        controlBox.add("Center", checkBoxPanel);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }
    //Cancels the bug update submission
    private void cancel() {

    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
 // Submit Button, takes data and submits to sql to update database
    private void submit() throws ParseException, SQLException {
    	String details = null;
    	String oldDetails = null;
    	String addToDetails = statusUpdate.getText();

    	long time = System.currentTimeMillis();
    	java.sql.Date date = new java.sql.Date(time);
    	
    	String newDetails = date + "-" + addToDetails;

    	PreparedStatement stmt = myConn.prepareStatement("SELECT details FROM bugs WHERE name = ?");
		stmt.setString(1, theBug.getName());  
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			oldDetails = rs.getString("details");
		}

		//System.out.println(oldDetails);
		details = oldDetails +"\n" + newDetails;
		Blob detailBlob = myConn.createBlob();
    	detailBlob.setBytes(1, details.getBytes());
		//System.out.println(details);

		PreparedStatement stmt2 = myConn.prepareStatement("UPDATE bugs SET details = ? WHERE name = ?");
		stmt2.setBlob(1, detailBlob);
		stmt2.setString(2, theBug.getName());
		stmt2.executeUpdate();

		if(fixed.isSelected()) {
			PreparedStatement stmt3 = myConn.prepareStatement("UPDATE bugs SET status = ?, dateFixed = ? WHERE name = ?");
			stmt3.setInt(1, 2);
			stmt3.setDate(2, date);
			stmt3.setString(3, theBug.getName());
			stmt3.executeUpdate();
		}

    	main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }
}
