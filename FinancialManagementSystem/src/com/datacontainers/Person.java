package com.datacontainers;

import java.util.ArrayList;

public class Person {
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address; // Person class owns Address class as a field
	private ArrayList<String> email;
	
	
	// Constructor
	
	
	public Person(String personCode, String firstName, String lastName, Address address, ArrayList<String> email) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
	}

	// Getter and Setter methods
	public Address getAddress() {
		return this.address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	public String getFullName() {
		return lastName + ", " + firstName;
	}
}
