package com.fileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.datacontainers.Address;
import com.datacontainers.Calculations;
import com.datacontainers.Consultations;
import com.datacontainers.Customers;
import com.datacontainers.Equipments;
import com.datacontainers.Person;
import com.datacontainers.Products;
import com.datacontainers.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.datacontainers.Invoice;

public class DataBaseReader {
	// Variables
	public ArrayList<Person> personList = new ArrayList<Person>();
	public ArrayList<Customers> customersList = new ArrayList<Customers>();
	public ArrayList<Products> productList = new ArrayList<Products>();
	public String personCode = "";
	public ArrayList<Person> clonedPersons = new ArrayList<Person>();
	public ArrayList<Customers> clonedCustomers = new ArrayList<Customers>();
	public ArrayList<Products> clonedProducts = new ArrayList<Products>();
	// public ArrayList<Calculations> productsList = new
	// ArrayList<Calculations>();
	public ArrayList<Double> totalFeeList;
	public ArrayList<Double> totalTaxList;
	public ArrayList<Double> totalServiceFeeList;
	public ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
	public ArrayList<Products> productType;

	// readPersons method
	public ArrayList<Person> readPersons() {

		String aquery = "SELECT * FROM Address WHERE personID = ?";
		String cquery = "SELECT country FROM Country WHERE countryID = ?";
		String squery = "SELECT state FROM State WHERE stateID = ? ";
		String pquery = "SELECT * FROM Person";
		String equery = "SELECT email FROM email WHERE personID = ?";

		Connection conn = DatabaseInfo.getConnection();
		try{
			PreparedStatement ps = conn.prepareStatement(pquery);
			ResultSet rs = ps.executeQuery();
			String personCode = null;
			String firstNamePerson = null;
			String lastNamePerson = null; 

			while(rs.next()){

				int personID = rs.getInt("personID");
				personCode = rs.getString("personCode");
				firstNamePerson  = rs.getString("firstName");
				lastNamePerson = rs.getString("lastName");

				PreparedStatement ps1 = conn.prepareStatement(equery);
				ps1.setInt(1, personID);
				ResultSet rs1 = ps1.executeQuery();
				ArrayList <String> emailPerson = new ArrayList<String>();
				while(rs1.next()){

					emailPerson.add(rs1.getString("email"));
				}

				ps1 = conn.prepareStatement(aquery);
				ps1.setInt(1, personID);
				rs1 = ps1.executeQuery();
				int countryID = 0;
				int stateID = 0;
				String streetPerson = null;
				String cityPerson = null;
				String zipPerson = null;
				if(rs1.next()){
					countryID = rs1.getInt("countryID");
					stateID = rs1.getInt("stateID");
					streetPerson = rs1.getString("street");
					cityPerson = rs1.getString("city");
					zipPerson = rs1.getString("zip");
				}
				ps1.close();
				rs1.close();

				ps1 = conn.prepareStatement(cquery);
				ps1.setInt(1, countryID);
				rs1 = ps1.executeQuery();
				String countryPerson = null;
				if(rs1.next()){
					countryPerson = rs1.getString("country");
				}
				ps1.close();
				rs1.close();

				ps1 = conn.prepareStatement(squery);
				ps1.setInt(1, stateID);
				rs1 = ps1.executeQuery();
				String statePerson = null;
				if(rs1.next()){
					statePerson = rs1.getString("state");
				}
				ps1.close();
				rs1.close();

				Address addressPerson = new Address(streetPerson, cityPerson, statePerson, zipPerson, countryPerson);
				Person person = new Person(personCode, firstNamePerson, lastNamePerson, addressPerson, emailPerson);
				personList.add(person);
			}
			ps.close();
			rs.close();
			conn.close();
			// clones the personList
			clonedPersons.addAll(personList);
			return personList;
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


	// method for Customers
	public ArrayList<Customers> readCustomers() {
		String customerQuery = "SELECT * FROM Customers";
		String aquery = "SELECT * FROM Address WHERE customerID = ?";
		String cquery = "SELECT country FROM Country WHERE countryID = ?";
		String squery = "SELECT state FROM State WHERE stateID = ? ";

		Connection conn = DatabaseInfo.getConnection();

		try{
			PreparedStatement ps = conn.prepareStatement(customerQuery);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int customerID = rs.getInt("customerID");
				String customerCode = rs.getString("customerCode");
				int salesPersonID = rs.getInt("personID");
				String customerType = rs.getString("customerType");
				String fullNameCustomer = rs.getString("name");



				PreparedStatement ps1 = conn.prepareStatement(aquery);
				ps1.setInt(1, customerID);
				ResultSet rs1 = ps1.executeQuery();
				int countryID = 0;
				int stateID = 0;
				String streetCustomer = null;
				String cityCustomer = null;
				String zipCustomer = null;

				if(rs1.next()){
					countryID = rs1.getInt("countryID");
					stateID = rs1.getInt("stateID");
					streetCustomer = rs1.getString("street");
					cityCustomer = rs1.getString("city");
					zipCustomer = rs1.getString("zip");
				}
				ps1.close();
				rs1.close();

				ps1 = conn.prepareStatement(cquery);
				ps1.setInt(1, countryID);
				rs1 = ps1.executeQuery();
				String countryCustomer = null;
				if(rs1.next()){
					countryCustomer = rs1.getString("country");
				}
				ps1.close();
				rs1.close();

				ps1 = conn.prepareStatement(squery);
				ps1.setInt(1, stateID);
				rs1 = ps1.executeQuery();
				String stateCustomer = null;	
				if(rs1.next()){
					stateCustomer = rs1.getString("state");
				}
				ps1.close();
				rs1.close();
				Address addressCustomer = new Address(streetCustomer, cityCustomer, stateCustomer, zipCustomer,
						countryCustomer);
				Customers customer = new Customers(customerCode, customerType, clonedPersons.get(salesPersonID-1),fullNameCustomer, addressCustomer);
				customersList.add(customer);

			}
			ps.close();
			rs.close();
			conn.close();

			clonedCustomers.addAll(customersList);

			return customersList;

		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	// method to readProducts
	public ArrayList<Products> readProducts() {
		String productQuery = "SELECT * FROM Products WHERE type = ?";
		Scanner scanner = null;
		Connection conn = DatabaseInfo.getConnection();

		try{
			PreparedStatement ps = conn.prepareStatement(productQuery);
			ps.setString(1, "E");
			ResultSet rs = ps.executeQuery();
			double pricePerUnit; 
			String code;
			String nameOfProduct;
			while(rs.next()){
				pricePerUnit = rs.getDouble("pricePerUnit");
				code = rs.getString("code");
				nameOfProduct = rs.getString("nameOfProduct");
				Products equipments = new Equipments(pricePerUnit, code, nameOfProduct, 0.0);
				productList.add(equipments);
			}
			ps.close();
			rs.close();

			ps = conn.prepareStatement(productQuery);
			ps.setString(1, "C");
			rs = ps.executeQuery();


			Products consultation;
			int consultationPersonCode;
			while(rs.next()){
				double hourlyFee = rs.getDouble("hourlyFee");
				consultationPersonCode = rs.getInt("personID");
				code = rs.getString("code");
				nameOfProduct = rs.getString("nameOfProduct");
				consultation = new Consultations(hourlyFee,clonedPersons.get(consultationPersonCode-1), code, nameOfProduct, 0.0);
				productList.add(consultation);
			}
			ps.close();
			rs.close();

			ps = conn.prepareStatement(productQuery);
			ps.setString(1, "S");
			rs = ps.executeQuery();

			while(rs.next()){
				double activationFee = rs.getDouble("activationFee");
				double annualFee = rs.getDouble("annualFee");
				code = rs.getString("code");
				nameOfProduct = rs.getString("nameOfProduct");

				Products services = new Services(activationFee, annualFee, code, nameOfProduct, 0.0);
				productList.add(services);
			}
			ps.close();
			rs.close();
			conn.close();

			clonedProducts.addAll(productList);
			return productList;

		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	//method for reading invoice 
	public ArrayList<Invoice> readInvoice() {
		

		

		//create customerID, productID
		String invoiceQuery = "SELECT * FROM Invoice";
		String joinQuery = "SELECT code from ProductListInvoices JOIN Invoice ON ProductListInvoices.invoiceID = Invoice.invoiceID WHERE Invoice.invoiceID = ?";
		String unitsQuery = "SELECT units from ProductListInvoices JOIN Products ON ProductListInvoices.code = Products.code WHERE ProductListInvoices.code = ?";
		Connection conn = DatabaseInfo.getConnection();
	
		try{

			PreparedStatement ps = conn.prepareStatement(invoiceQuery);
			ResultSet rs = ps.executeQuery();

			int invoiceID = 0;
			String invoiceCode = null;
			int customerID = 0;
			String invoiceReportDate = null;

			while(rs.next()){
				Invoice invoice = null;
				invoiceID = rs.getInt("invoiceID");
				invoiceCode = rs.getString("invoiceCode");
				customerID = rs.getInt("customerID");
				invoiceReportDate = rs.getString("invoiceReportDate");
				int personID = rs.getInt("personID");
				totalFeeList = new ArrayList<Double>();
				totalTaxList = new ArrayList<Double>();
				totalServiceFeeList = new ArrayList<Double>();
				productType = new ArrayList<Products>();

				PreparedStatement ps1 = conn.prepareStatement(joinQuery);
				ps1.setInt(1, invoiceID);
				ResultSet rs1 = ps1.executeQuery();

				ArrayList<String> productCode = new ArrayList<String>();

				while(rs1.next()){

					productCode.add(rs1.getString("code"));
				}

				ps1.close();
				rs1.close();

				

				for(int z = 0; z < productCode.size(); z++){
//					
					for(int k = 0; k < clonedProducts.size(); k++){
						if(productCode.get(z).equals(clonedProducts.get(k).getCode())){
							invoice = new Invoice(invoiceCode, clonedCustomers.get(customerID-1),invoiceReportDate, clonedPersons.get(personID-1), totalFeeList,totalTaxList, totalServiceFeeList, clonedProducts.get(k), productType);



							ps1 = conn.prepareStatement(unitsQuery);
							ps1.setString(1, productCode.get(z));
							rs1 = ps1.executeQuery();

							double units = 0.0;

							if(rs1.next()){
								if(String.valueOf(rs1.getDouble("units")) != null){
								units = rs1.getDouble("units");
								}
							}

							ps1.close();
							rs1.close();

							if(clonedProducts.get(k).getType().equals("E")){

								Equipments purchasedEquipment = new Equipments(clonedProducts.get(k).getPricePerUnit(), clonedProducts.get(k).getCode(), clonedProducts.get(k).getNameOfProduct(), units);
								invoice.getTotalFeeList().add(purchasedEquipment.fee(units));
								invoice.getTotalTaxList().add(purchasedEquipment.tax(totalFeeList.get(totalFeeList.size() - 1)));
								invoice.getTotalServiceFeeList().add(0.00);
								invoice.getProductType().add(purchasedEquipment);

							}

							else if(clonedProducts.get(k).getType().equals("C")){

								Consultations purchasedConsultation = new Consultations(clonedProducts.get(k).getHourlyFee(), clonedPersons.get(personID-1), clonedProducts.get(k).getCode(), clonedProducts.get(k).getNameOfProduct(), units);
								invoice.getTotalServiceFeeList().add(clonedProducts.get(k).computeFee());
								invoice.getTotalFeeList().add(purchasedConsultation.fee(units));
								invoice.getTotalTaxList().add(purchasedConsultation.tax(totalFeeList.get(totalFeeList.size() - 1)));
								invoice.getProductType().add(purchasedConsultation);
							}


							else if(clonedProducts.get(k).getType().equals("S")){

								//joda time to figure out days in year
								String getFirstDate = "SELECT firstDate, lastDate FROM ProductListInvoices WHERE invoiceID = ? AND code = ?";

								 ps1 = conn.prepareStatement(getFirstDate);
								ps1.setInt(1, invoiceID);
								ps1.setString(2,productCode.get(z));
								 rs1 = ps1.executeQuery();

								while(rs1.next()){
									if(rs1.getString("firstDate") != null && rs1.getString("lastDate") != null){
									String firstDate = rs1.getString("firstDate");
									String lastDate = rs1.getString("lastDate");
									
									String[] first = firstDate.split("-");
									String[] last = lastDate.split("-");
									int firstYear = Integer.parseInt(first[0]);
									int firstMonth = Integer.parseInt(first[1]);
									int firstDay = Integer.parseInt(first[2]);

									int lastYear = Integer.parseInt(last[0]);
									int lastMonth = Integer.parseInt(last[1]);
									int lastDay = Integer.parseInt(last[2]);

									DateTime finalDate = new DateTime(lastYear, lastMonth, lastDay, 0, 0);
									DateTime startDate = new DateTime(firstYear, firstMonth, firstDay, 0, 0);

									double daysInYear = (Days.daysBetween(new LocalDate(startDate), new LocalDate(finalDate)).getDays()) + 1;

									Services purchasedService = new Services(clonedProducts.get(k).computeFee(), clonedProducts.get(k).getAnnualFee(), clonedProducts.get(k).getCode(), clonedProducts.get(k).getNameOfProduct(), daysInYear);
									invoice.getTotalServiceFeeList().add(clonedProducts.get(k).computeFee());
									invoice.getTotalFeeList().add(purchasedService.fee(daysInYear));
									invoice.getTotalTaxList().add(purchasedService.tax(totalFeeList.get(totalFeeList.size() - 1)));
									invoice.getProductType().add(purchasedService);
									}

								}

								ps1.close();
								rs1.close();

							}

						}

					}
					
				}
				
				invoiceList.add(invoice);
		
				double sum1 = 0.0;
				double sum2 = 0.0;
				double sum3 = 0.0;

				for(int i = 0; i < invoiceList.size(); i++) {
					if (invoiceList.get(i).getCustomer().getCustomerType().equals("B")) {
						for (int a = 0; a < invoiceList.get(i).getTotalFeeList().size(); a++) {
							sum1 = sum1 + invoiceList.get(i).getTotalFeeList().get(a);

						}
						for (int b = 0; b < invoiceList.get(i).getTotalTaxList().size(); b++) {
							sum2 = sum2 + invoiceList.get(i).getTotalTaxList().get(b);

						}
						for (int c = 0; c < invoiceList.get(i).getTotalServiceFeeList().size(); c++) {
							sum3 = sum3 + invoiceList.get(i).getTotalServiceFeeList().get(c);
						}
						invoice.setInvoiceTotal(sum1 + sum2 + sum3);
						sum1 = 0.0;
						sum2 = 0.0;
						sum3 = 0.0;
					}
					else {
						for (int a = 0; a < invoiceList.get(i).getTotalFeeList().size(); a++) {
							sum1 = sum1 + invoiceList.get(i).getTotalFeeList().get(a);

						}
						for (int b = 0; b < invoiceList.get(i).getTotalTaxList().size(); b++) {
							sum2 = sum2 + invoiceList.get(i).getTotalTaxList().get(b);

						}
						for (int c = 0; c < invoiceList.get(i).getTotalServiceFeeList().size(); c++) {
							sum3 = sum3 + invoiceList.get(i).getTotalServiceFeeList().get(c);
						}
						invoice.setInvoiceTotal(sum1 + invoiceList.get(i).getCustomer().getComplianceFee() + sum3);
						sum1 = 0.0;
						sum2 = 0.0;
						sum3 = 0.0;
					}
				}
				

			}
			ps.close();
			rs.close();
			conn.close();
			return invoiceList;
		}catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} 

	}
}

