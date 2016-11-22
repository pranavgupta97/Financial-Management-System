package com.datacontainers;

public class Equipments extends Products {
	private double pricePerUnit = 0.0;
	private double EquipmentsTax = 0.0;
	private double fee = 0.0;
	private double units = 0.0;
	
	
	//Constructer
	public Equipments( double pricePerUnit, String code, String nameOfProduct, double units){
		super(code, "E", nameOfProduct);
		 this.pricePerUnit = pricePerUnit;
		 this.units = units;
	}

	//Getters and Setters
	@Override
	public double getPricePerUnit() {
		return pricePerUnit;
	}


	public double tax(double fee) {
		EquipmentsTax = (fee) * (0.07);
		return EquipmentsTax;
	}

	@Override
	public double computeFee() {  
		return 0;
	}

	public double fee(double quantity) {
		fee = pricePerUnit * quantity;
		return fee;
	}
//overidden abstract methods
	@Override
	public double getHourlyFee() {
		return 0;
	}

	@Override
	public double getAnnualFee() {
		return 0;
	}

	@Override
	public double getUnits() {
		return units;
	}

}