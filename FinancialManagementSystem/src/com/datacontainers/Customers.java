package com.datacontainers;

public class Customers {
	private String customerCode;
	private Person contact;
	private String name;
	private Address address;
	private String customerType;
	private boolean determineTax;
	private double complianceFee;
	
	// Constructor
	public Customers(String customerCode, String customerType, Person contact, String name, Address address) {
		super();
		this.customerCode = customerCode;
		this.customerType = customerType;
		this.contact = contact;
		this.name = name;
		this.address = address;
		this.setDetermineTax(determineTax);
		this.setComplianceFee(complianceFee);
	}
	
	// Getter & Setter methods
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public boolean isDetermineTax() {
		return determineTax;
	}

	public void setDetermineTax(boolean determineTax) {
		if(getCustomerType().equals("B")){
			determineTax = true;
		}
		else {
			setComplianceFee(125.0);
		}
		this.determineTax = determineTax;
	}

	public double getComplianceFee() {
		return complianceFee;
	}

	public void setComplianceFee(double complianceFee) {
		this.complianceFee = complianceFee;
	}
	
	
}
