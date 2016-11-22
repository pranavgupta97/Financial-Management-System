package com.datacontainers;

public abstract class Products implements Calculations{
	private String code = "";
	private String type = "";
	private String nameOfProduct = "";
	
	//Constructer
	public Products(String code, String type, String nameOfProduct){
		this.code = code;
		this.type = type;
		this.nameOfProduct = nameOfProduct;
	}

	//Getters and Setters
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getNameOfProduct() {
		return nameOfProduct;
	}


	public void setNameOfProduct(String nameOfProduct) {
		this.nameOfProduct = nameOfProduct;
	}
	//abstract methods
	public abstract double computeFee();
	
	public abstract double getPricePerUnit();
	
	public abstract double getHourlyFee();
	
	public abstract double getAnnualFee();
	
	public abstract double getUnits();
	
}