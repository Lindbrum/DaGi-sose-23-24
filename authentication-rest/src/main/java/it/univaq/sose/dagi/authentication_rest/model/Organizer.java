package it.univaq.sose.dagi.authentication_rest.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="organizer")
public class Organizer {
	private Long id;
	private String username;
	private String password;
	private String name;
	
	public Organizer(Long id, String username, String password, String name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public Organizer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
