import java.util.Date;

//Bug Class
public class Bug {
	//The name of the bug
	private String name;
	//The product the bug is contained in
	private String product;
	//The date the bug was created and fixed
	private Date created, bugFixed;
	//The details of the bug
	private String details;
	//The status of the bug
	private int status;
	//The dev that the bug is assigned to
	private String assignedDev;
	
	// Bug class Constructor
	public Bug(String name, String fromProduct, Date created, 
			   String details, int status,String assignedDev, Date bugFixed) {
		this.name = name;
		this.product = fromProduct;
		this.created = created;
		this.details = details;
		this.status = status;
		this.assignedDev = assignedDev;	
	}
	
	// Function used to set the name of a Bug
	public void setName(String name) {
		this.name = name;
	}
	
	// Function that returns the current name of the Bug
	public String getName() {
		return name;
	}
	
	// Function that sets the product the Bug is from
	public void setProduct(String fromProduct) {
		this.product = fromProduct;
	}
	
	// Returns the product with which the bug is from
	public String getProduct() {
		return product;
	}
	
	// Sets the date for when the Bug was created
	public void setCreated(Date created) {
		this.created = created;
	}
	
	// Returns the Date the Bug was Created
	public Date getCreated() {
		return created;
	}
	
	//Sets the bug details
	public void setDetails(String details) {
		this.details = details;
	}
	
	//Returns the bugs details
	public String getDetails() {
		return details;
	}
	
	// Sets the Status of the Bug
	public void setStatus(int status) {
		this.status = status;
	}
	
	// Returns the Status of the Bug
	public int getStatus() {
		return status;
	}
	
	 // Sets the Assigned Dev for the Bug
	public void setAssignedDev(String assignedDev) {
		this.assignedDev = assignedDev;
	}
	
	// Returns the Developed for which the Bug is Assigned To
	public String getAssignedDev() {
		return assignedDev;
	}
	
	//Sets the date the the bug was fixed on
	public void setDateFixed(Date bugFixed) {
		this.bugFixed = bugFixed;
	}
	
	//returns the date that the bug was fixed on
	public Date getDateFixed() {
		return bugFixed;
	}
	
}
