
package com.datacontainers;

public class Services extends Products {
	private double activationFee;
	private double annualFee;
	private double ServicesTax;
	private double fee = 0.0;
	private double units = 0.0;
	
	//Constructer
	public Services(double activationFee,  double annualFee, String code, String nameOfProduct, double units){
		super(code, "S", nameOfProduct);
		this.activationFee = activationFee;
		this.annualFee = annualFee;
		this.units = units;
	}

	//Getters and Setters
	public double getActivationFee() {
		return activationFee;
	}

	public void setActivationFee(double activationFee) {
		this.activationFee = activationFee;
	}

	@Override
	public double getAnnualFee() {
		return annualFee;
	}

	public double tax(double fee) {
		ServicesTax = (fee) * (0.0425);
		return ServicesTax;
	}

	@Override
	public double computeFee() {
		return getActivationFee();
	}

	public double fee(double daysInYear) {
		fee = annualFee * (daysInYear / 365.0);
		return fee;
	}

	@Override
	public double getPricePerUnit() {
		return 0;
	}

	@Override
	public double getHourlyFee() {
		return 0;
	}

	@Override
	public double getUnits() {
		return units;
	}
	
}