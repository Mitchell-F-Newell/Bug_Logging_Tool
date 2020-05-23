// LoginWindow.java
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.SQLException;


public class LoginWindow {
    private static Authenticator anAuthenticator;
    private JFrame main;
    private JButton loginButton;
    private JButton defaultButton;
    private JTextField username;
    private JTextField password;
    private ActionListener buttonEventListener;

    private class EventListener implements ActionListener {
		LoginWindow display;
		public EventListener (LoginWindow d) {
			display = d;
		}
		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == loginButton) {
				try {
					display.login();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == defaultButton) {
				try {
					display.useAsDefault();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

    public LoginWindow () throws SQLException {

    	anAuthenticator = new Authenticator();
        buttonEventListener = new EventListener(this);
        this.display();
    }

    private void display () {
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
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Login.png");

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
        JLabel label = new JLabel("Username:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        username = new CustomJTextField(60);
        content.add(username);

        // Password stuff
        label = new JLabel("Password:");
        label.setBorder(new EmptyBorder (12, 0, 12, 0));
		label.setForeground(defaultColor);
		label.setFont(defaultFont);
		content.add(label);
        password = new CustomJPasswordField(40);
        content.add(password);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 46, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 20, 0));
        loginButton = new CustomJButton(19, 38, "Login");
        loginButton.addActionListener(buttonEventListener);
        buttonPanel.add(loginButton);
        defaultButton = new CustomJButton(19, 38, "Default User");
        defaultButton.addActionListener(buttonEventListener);
        buttonPanel.add(defaultButton);

        // Add elements and display
        controlBox.add("Center", content);
        controlBox.add("South", buttonPanel);
        background.add("South", controlBox);
        main.add(background);
        main.setVisible(true);
    }

    private void login () throws SQLException {
    	String uName = username.getText();
    	String pass = password.getText();
    	boolean login = anAuthenticator.authenticate(uName, pass);
    	if(login) {
    		main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    	}else {
    		 JOptionPane.showMessageDialog(null, "Incorrect Username and/or Password", "Error",
                     JOptionPane.ERROR_MESSAGE);
    	}
    }

    private void useAsDefault () throws SQLException {
        DefaultWindow dWindow = new DefaultWindow();
        System.out.println("Default Window Generated");
        main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
    }

    public static void main (String[] args) throws SQLException {
        LoginWindow temp = new LoginWindow();
    }
}
