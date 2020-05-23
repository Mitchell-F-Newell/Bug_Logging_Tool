//Class creates developers
public class Developer {
	//The name of the developer
	private String name;
	//The username of the developer
	private String username;
	//The details of the developer
	private String details;
	
	//Developer constructor
	public Developer(String name, String username, String details) {
		this.name = name;
		this.username = username;
		this.details = details;
	}
	//Set the name of the developer
	public void setName(String name) {
		this.name = name;
	}
	//returns the name of the developer
	public String getName() {
		return name;
	}
	//Functions sets the username of the developer
	public void setUsername(String username) {
		this.username = username;
	}
	//Returns the name of the developer
	public String getUsername() {
		return username;
	}
	//Funciton sets the detals of the developers
	public void setDetails( String details) {
		this.details = details;
	}
	//gets the string of deta ils on a developer
	public String getDetails() {
		return details;
	}
}

