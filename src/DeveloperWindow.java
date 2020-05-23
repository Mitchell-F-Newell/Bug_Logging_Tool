import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
//Class which generated a developer window
public class DeveloperWindow {
//  Database Connections
	private static Connection myConn1, myConn2;
	//Button for updating a bug
	private JButton updateBug;
	//Button for viewing your (a developers) assignments
	private JButton browseAssignment;
	//The name of the developer
	private String developerName;
	//List of products and bugs in the window
    protected JList<String> productList, bugList;
    //Vector of bugs
    protected Vector<Bug> bugs;
    //Vector of products
    protected Vector<Product> products;
    //Buttons used to submit a new bug and to
    //refresh the display window
    protected JButton submitBug, refresh;
    // Mouse Event Listener
    protected MouseAdapter mouseEventListener;
    // Button Event Listener
    protected ActionListener buttonEventListener;
    // Current Product
    protected Product activeProduct;
    
    // Event Listener Class
    private class EventListener implements ActionListener {
        DeveloperWindow display;
        // Event Listener Constructor
        public EventListener (DeveloperWindow d) {
            display = d;
        }
        //Logic for the button actions
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == submitBug) {
                try {
					display.submitNewBug();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == refresh) {
                try {
					display.refresh();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == updateBug) {
                try {
					display.updateBugStatus();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == browseAssignment) {
                try {
					display.displayAssignments();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
    }
    // Developer Window Constructor
    public DeveloperWindow(String username) throws SQLException {
    	myConn1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bug?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        buttonEventListener = new EventListener(this);
        developerName = username;
        this.display();
        refresh();
    }
    // Display Function
    public void display() {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        JFrame main = new JFrame();
        main.setTitle("RAID Bug Tracking System: Developer");
        main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
        main.setSize(800, 900);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Default.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setOpaque(false);
        JLabel username = new JLabel("  " + developerName);
        username.setBorder(new EmptyBorder (9, 0, 80, 0));
        username.setForeground(new Color(0, 0, 0));
        username.setFont(new Font("Britannic Bold", Font.BOLD, 26));
        userPanel.add(username);

        JPanel contentDiv = new JPanel();
        contentDiv.setLayout(new GridLayout(0, 1));
        contentDiv.setOpaque(false);

        // Product Stuff
        JPanel productDiv = new JPanel();
        productDiv.setLayout(new BoxLayout(productDiv, BoxLayout.Y_AXIS));
        productDiv.setOpaque(false);
        productDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        JLabel label = new JLabel("Products:                                                                         ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        productDiv.add(label);
        productList = new JList();
        productList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList productList = (JList)evt.getSource();
                if(evt.getClickCount() == 1) {
                	int index = productList.locationToIndex(evt.getPoint());
                	try {
						showBugsFrom(products.get(index));
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = productList.locationToIndex(evt.getPoint());
                    Product get = products.get(index);
                    viewProduct(get);
                }
            }
        });
        productList.setFont(new Font("Courier", Font.BOLD, 20));
        JScrollPane tempScroll = new JScrollPane(productList);
        productList.setBackground(new Color (162, 154, 154));
        productDiv.add(tempScroll);
        contentDiv.add(productDiv);

        // Bug stuff
        JPanel bugDiv = new JPanel();
        bugDiv.setLayout(new BoxLayout(bugDiv, BoxLayout.Y_AXIS));
        bugDiv.setOpaque(false);
        bugDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        label = new JLabel("Bugs:                                                                                    ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        bugDiv.add(label);
        bugList = new JList();
        bugList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList bugList = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = bugList.locationToIndex(evt.getPoint());
                    Bug get = bugs.get(index);
                    viewBug(get);
                }
            }
        });
        bugList.setFont(new Font("Courier", Font.BOLD, 20));
        tempScroll = new JScrollPane(bugList);
        bugList.setBackground(new Color (162, 154, 154));
        bugDiv.add(tempScroll);
        contentDiv.add(bugDiv);

        // Buttons
        JPanel manyButtons = new JPanel();
        manyButtons.setLayout(new GridLayout(0, 1));
        manyButtons.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 65, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (20, 0, 10, 0));
        submitBug = new CustomJButton(19, 38, "Submit New Bug");
        submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);
        refresh = new CustomJButton(19, 38, "Refresh Results");
        refresh.addActionListener(buttonEventListener);
        buttonPanel.add(refresh);
        updateBug = new CustomJButton(19, 38, "Update This Bug");
        updateBug.addActionListener(buttonEventListener);
        buttonPanel.add(updateBug);
        manyButtons.add(buttonPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 65, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (10, 0, 20, 0));
        browseAssignment = new CustomJButton(26, 38, "Check My Assignments");
        browseAssignment.addActionListener(buttonEventListener);
        buttonPanel.add(browseAssignment);
        manyButtons.add(buttonPanel);

        controlBox.add("South", manyButtons);
        controlBox.add("Center", contentDiv);
        background.add("North", userPanel);
        background.add("Center", controlBox);
        main.add(background);
        main.setVisible(true);
    }
    //Function calls another function which creates a Bug update window
	public void updateBugStatus() throws SQLException {
		generateBugUpdateWindow();
	}
	//Function creates a new bug update window
	private void generateBugUpdateWindow() throws SQLException {
		int index = bugList.getSelectedIndex();
		BugUpdateWindow bugUpdateWin = new BugUpdateWindow(bugs.get(index));
		
	}
	//Function displays the current user/developers assigned bugs
	public void displayAssignments() throws SQLException {
		bugs.removeAllElements();
		
    	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs WHERE assignedDev = ?  AND  status != ?");
    	stmt.setString(1, developerName);
    	stmt.setInt(2, 0);
		ResultSet rs = stmt.executeQuery();
		DefaultListModel<String> model = new DefaultListModel<String>();
		while(rs.next()) {
			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
					  rs.getDate("created"), rs.getString("details"), 
					  rs.getInt("status"), rs.getString("assignedDev"), rs.getDate("dateFixed"));
			bugs.add(bug);
		}
		    for(Bug b : bugs){
		    	String list= b.getName();
		    	list += "  |  ";
		    	if(b.getStatus() == 0) {
		    		list += "REPORTED";
		    	}else if(b.getStatus() == 1) {
		    		list += "APPROVED";
		    	}else {
		    		list += "FIXED";
		    	}
		    	list += "  |  ";
		    	list += b.getProduct();
		         model.addElement(list);
		    }    
		bugList.setModel(model);     
	    bugList.setSelectedIndex(0);
	}
	//Function calls another function that creates a new bug submission window
    protected void submitNewBug() throws SQLException {
        // Display the Bugs from the product argument
    	generateBugSubmissionWindow();
    }
    //Function creates a new bug submission window
    private void generateBugSubmissionWindow() throws SQLException {
    	String uType = "Developer";
    	int index = productList.getSelectedIndex();
  		BugSubmissionWindow bugSubWindow = new BugSubmissionWindow(products.get(index), uType);
  	}
    //Function refreshes the window/GUI
    protected void refresh() throws SQLException {
        // Refresh.
        getBugs();
        getProducts();
    }
    //Functions gets all bugs from the database and places them in a JList
    protected void getBugs() throws SQLException {
    	bugs = new Vector<Bug>();
      	// Get Bugs from the Database
      	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs Where status != ?");
      	stmt.setInt(1, 0);
  		ResultSet rs = stmt.executeQuery();
  		while(rs.next()) {
  			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
  					  rs.getDate("created"), rs.getString("details"), 
  					  rs.getInt("status"), rs.getString("assignedDev"), rs.getDate("dateFixed"));
  			bugs.add(bug);
  			
  			DefaultListModel<String> model = new DefaultListModel<String>();
  		    for(Bug b : bugs){
  		  	String list= b.getName();
	    	list += "  |  ";
	    	if(b.getStatus() == 0) {
	    		list += "REPORTED";
	    	}else if(b.getStatus() == 1) {
	    		list += "APPROVED";
	    	}else {
	    		list += "FIXED";
	    	}
	    	list += "  |  ";
	    	list += b.getProduct();
	         model.addElement(list);
  		    }    
  		    bugList.setModel(model);     
  		    bugList.setSelectedIndex(0);
  		}
    }
    //Function gets all products from the database and displays them in a JList
    protected void getProducts() throws SQLException {
    	products = new Vector<Product>();
        // Get Products from the Database
    	PreparedStatement stmt = myConn2.prepareStatement("SELECT * FROM products");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Product product = new Product(rs.getString("name"), rs.getDate("created"), 
					rs.getString("details"));
			products.add(product);
			
			DefaultListModel<String> model = new DefaultListModel<String>();
			    for(Product p : products){
			    	String list= p.getName();
			    	list += "  |  CREATED: ";
			    	list += p.getCreated();
			         model.addElement(list);
			    }    
			    productList.setModel(model);     
			    productList.setSelectedIndex(0);
			}
    }
    //Function calls a function which creates a bug info window
    protected void viewBug(Bug theBug) {
        // View the Bug.
    	int index = bugList.getSelectedIndex();
    	generateBugInfoWindow(bugs.get(index));
    }
    //Function creates a new bug info window
    private void generateBugInfoWindow(Bug aBug) {
    	BugInfoWindow newWin = new BugInfoWindow(aBug);
	}
    //Function calls a function which creates a product infor window
    protected void viewProduct(Product theProduct) {
        // View the Product
    	int index = productList.getSelectedIndex();
    	generateProductInfoWindow(products.get(index));
    }
    //Function creates a new product info window
    private void generateProductInfoWindow(Product aProduct) {
    	ProductInfoWindow newWin = new ProductInfoWindow(aProduct);
	}
    //Function displays approved bugs of a certain product
    protected void showBugsFrom(Product activeProduct) throws SQLException {
    	 // Display the bugs from the product argument
		bugs.removeAllElements();
	
    	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs WHERE fromProduct = ? AND status != ?");
    	stmt.setString(1, activeProduct.getName());
    	stmt.setInt(2, 0);
		ResultSet rs = stmt.executeQuery();
		DefaultListModel<String> model = new DefaultListModel<String>();
		while(rs.next()) {
			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
					  rs.getDate("created"), rs.getString("details"), 
					  rs.getInt("status"), rs.getString("assignedDev"), rs.getDate("dateFixed"));
			bugs.add(bug);
		}
		    for(Bug b : bugs){
		    	String list= b.getName();
		    	list += "  |  ";
		    	if(b.getStatus() == 0) {
		    		list += "REPORTED";
		    	}else if(b.getStatus() == 1) {
		    		list += "APPROVED";
		    	}else {
		    		list += "FIXED";
		    	}
		    	list += "  |  ";
		    	list += b.getProduct();
		         model.addElement(list);
		    }    
		bugList.setModel(model);     
	    bugList.setSelectedIndex(0);
    }
}
