import java.sql.Date;
//The class which makes/defines the products
public class Product {
	//name of the product
	private String name;
	//The date that the product was created
	private Date created;
	//The details of the product 
	private String details;
	//The constructor for the product
	public Product(String name, Date created, String details) {
		this.name = name;
		this.created = created;
		this.details = details;
	}
	//Setter for the name of the product
	public void setName(String name) {
		this.name = name;
	}
	//getter for the name of the product
	public String getName() {
		return name;
	}
	//Setter for the date which the product was created
	public void setCreated(Date created) {
		this.created = created;
	}
	//getter for the date which the product was created
	public Date getCreated() {
		return created;
	}
	//Setter for the detail of the product
	public void setDetails( String details) {
		this.details = details;
	}
	//getter for the details of the product
	public String getDetails() {
		return details;
	}
}
