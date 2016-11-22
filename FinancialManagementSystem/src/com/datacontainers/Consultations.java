package com.datacontainers;

public class Consultations extends Products {
	private double hourlyFee;
	private Person consultant;
	private double ConsultationsTax;
	private double computeFee;
	private double fee = 0.0;
	private double units = 0.0;
	
	//Constructer
	public Consultations(double hourlyFee, Person consultant, String code, String nameOfProduct, double units){
		super(code, "C", nameOfProduct);
		this.hourlyFee = hourlyFee;
		this.consultant = consultant;
		this.units = units;
	}
	
	//Getters and Setters
	@Override
	public double getHourlyFee() {
		return hourlyFee;
	}



	public Person getConsultant() {
		return consultant;
	}

	public void setConsultant(Person consultant) {
		this.consultant = consultant;
	}

	public double tax(double fee) {
		ConsultationsTax = (fee) * (0.0425);
		return ConsultationsTax;
	}

	//overidden methods
	@Override
	public double computeFee() {
		computeFee = 150.0;
		return computeFee;
	}

	
	public double fee(double consultationHours) {
		fee = hourlyFee * consultationHours;
		return fee;
	}

	@Override
	public double getPricePerUnit() {
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
