package com.datacontainers;

import java.awt.List;
import java.util.ArrayList;

public class Invoice {
	private String invoiceCode;
	private Customers customer;
	private String invoiceReportDate;
	private Person person;
	private Products products;
	//	private ArrayList<Calculations> productsList = new ArrayList<Calculations>();
	private ArrayList<Double> totalFeeList = new ArrayList<Double>();
	private ArrayList<Double> totalTaxList = new ArrayList<Double>();
	private ArrayList<Double> totalServiceFeeList = new ArrayList<Double>();
	private ArrayList<Products> productType = new ArrayList<Products>();
	private String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private String date = "";
	private double invoiceTotal;
	
	public Invoice(String invoiceCode, Customers customer, String invoiceReportDate, Person person, ArrayList<Double> totalFeeList, ArrayList<Double> totalTaxList, ArrayList<Double> totalServiceFeeList, Products products, ArrayList<Products> productType) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.invoiceReportDate = invoiceReportDate;
		this.person = person;
		this.totalFeeList = totalFeeList;
		this.totalTaxList = totalTaxList;
		this.totalServiceFeeList = totalServiceFeeList;
		this.products = products;
		this.productType = productType;

	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getInvoiceReportDate() {
		return invoiceReportDate;
	}

	public void setInvoiceReportDate(String invoiceReportDate) {
		this.invoiceReportDate = invoiceReportDate;
	}

	//	public ArrayList<Calculations> getProductsList() {
	//		return productsList;
	//	}
	//
	//	public void setProductsList(ArrayList<Calculations> productsList) {
	//		this.productsList = productsList;
	//	}

	public ArrayList<Double> getTotalFeeList() {
		return totalFeeList;
	}

	public void setTotalFeeList(ArrayList<Double> totalFeeList) {
		this.totalFeeList = totalFeeList;
	}

	public ArrayList<Double> getTotalTaxList() {
		return totalTaxList;
	}

	public void setTotalTaxList(ArrayList<Double> totalTaxList) {
		this.totalTaxList = totalTaxList;
	}

	public ArrayList<Double> getTotalServiceFeeList() {
		return totalServiceFeeList;
	}

	public void setTotalServiceFeeList(ArrayList<Double> totalServiceFeeList) {
		this.totalServiceFeeList = totalServiceFeeList;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public ArrayList<Products> getProductType() {
		return productType;
	}

	public void setProductType(ArrayList<Products> productType) {
		this.productType = productType;
	}

	public String [] getMonths() {
		return months;
	}

	public void setMonths(String [] months) {
		this.months = months;
	}

	public String getDate() {
		return date;
	}

	public void setDate() {
		String[] currentDate = invoiceReportDate.split("-");
		String currentYear = currentDate[0];
		String[] day = currentDate[1].split("");
		String currentMonth ="";
		if(day[0].equals("0")){
			currentMonth = months[Integer.parseInt(day[1]) - 1];
			this.date = currentMonth + " " + currentDate[2] + ", " + currentYear;
		}
		else {
			if(day[1].equals("0")) {
				currentMonth = months[9];
			}
			else if(day[1].equals("1")) {
				currentMonth = months[10];
			}
			else {
				currentMonth = months[11];
			}
			this.date = currentMonth + " " + currentDate[2] + ", " + currentYear;
		}
	}

	public double getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	
	public void reset() {
		this.invoiceCode = null;
		this.customer = null;
		this.invoiceReportDate = null;
		this.person = null;
		this.totalFeeList = null;
		this.totalTaxList = null;
		this.totalServiceFeeList = null;
		this.products = null;
		this.productType = null;
	}

}
