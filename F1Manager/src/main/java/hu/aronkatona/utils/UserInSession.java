package hu.aronkatona.utils;

import java.io.Serializable;

public class UserInSession implements Serializable{

	private static final long serialVersionUID = 7252992881895291277L;
	private long id;
	private String name;
	private String email;
	
	public UserInSession(){}

	public UserInSession(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
}
