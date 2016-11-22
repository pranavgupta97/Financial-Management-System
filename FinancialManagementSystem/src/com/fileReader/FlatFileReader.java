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

public class FlatFileReader {
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
		
			Scanner sc = null;

			try {
				sc = new Scanner(new File("data/Persons.dat"));

				sc.nextLine(); // reads the number of records from the first line

				// This Person ArrayList stores the Person objects

				while (sc.hasNext()) {
					String line = sc.nextLine(); // reads each line starting from
					// 2nd line

					String data1[] = line.split(";"); // tokenizes the line and
					// stores in a tring array

					// Stores the 4 array elements of each line into strings

					personCode = data1[0];
					// splits name
					String[] fullNamePerson = data1[1].split(",");
					String lastNamePerson = fullNamePerson[0];
					String firstNamePerson = fullNamePerson[1];

					// splits address
					String[] fullAddressPerson = data1[2].split(",");
					String streetPerson = fullAddressPerson[0];
					String cityPerson = fullAddressPerson[1];
					String statePerson = fullAddressPerson[2];

					// checks for addresses with no zipcodes
					String zipPerson = "";
					if (fullAddressPerson[3] == null) {
						zipPerson = "";
					} else {
						zipPerson = fullAddressPerson[3];
					}
					String countryPerson = fullAddressPerson[4];

					// Checks for no emails and splits multiple ones
					ArrayList<String> email = null;
					if (data1.length > 3) {
						String[] allEmails = data1[3].split(",");
						email = new ArrayList<String>();
						for (int i = 0; i < allEmails.length; i++) {
							email.add(allEmails[i]);
						}
					} else {
						email = new ArrayList<String>();
					}

					// Creates an Address object
					Address addressPerson = new Address(streetPerson, cityPerson, statePerson, zipPerson, countryPerson);

					// Creates a Person object
					Person person = new Person(personCode, firstNamePerson, lastNamePerson, addressPerson, email);

					// Adds the Person object into Person ArrayList
					personList.add(person);
				}
				sc.close();

				// clones the personList
				clonedPersons.addAll(personList);

				return personList;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}

		// method for Customers
		public ArrayList<Customers> readCustomers() {
			Scanner scnr = null;
			try {
				scnr = new Scanner(new File("data/Customers.dat"));
				scnr.nextLine();// reads the number of records from the first line

				while (scnr.hasNext()) {
					String line2 = scnr.nextLine();
					String data2[] = line2.split(";");

					// stores necessary data into variables
					String customerCode = data2[0];
					String customerType = data2[1];
					String personCode = data2[2];
					String fullNameCustomer = data2[3];

					// stores address
					String[] fullAddressCustomer = data2[4].split(",");
					String streetCustomer = fullAddressCustomer[0];
					String cityCustomer = fullAddressCustomer[1];
					String stateCustomer = fullAddressCustomer[2];

					// checks for addresses with no zip
					String zipCustomer = "";
					if (fullAddressCustomer[3] == null) {
						zipCustomer = "";
					} else {
						zipCustomer = fullAddressCustomer[3];
					}
					String countryCustomer = fullAddressCustomer[4];

					// compares the person code in Customer.data and Persons.dat and
					// finds the matching person with the customer
					for (int i = 0; i < clonedPersons.size(); i++) {

						if ((personCode).equals(clonedPersons.get(i).getPersonCode())) {

							Address addressCustomer = new Address(streetCustomer, cityCustomer, stateCustomer, zipCustomer,
									countryCustomer);
							Customers customer = new Customers(customerCode, customerType, clonedPersons.get(i),
									fullNameCustomer, addressCustomer);
							customersList.add(customer);

						}

					}
				}

				scnr.close();
				clonedCustomers.addAll(customersList);

				return customersList;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		// method to readProducts
		public ArrayList<Products> readProducts() {
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File("data/Products.dat"));
				scanner.nextLine();

				while (scanner.hasNext()) {
					String line3 = scanner.nextLine();
					String data3[] = line3.split(";");
					String code = data3[0];
					String type = data3[1];
					String nameOfProduct = data3[2];

					// compares type and then uses the three subclasses to store the
					// various types of products
					if (type.equals("E")) {
						String pricePerUnit = data3[3];
						Products equipments = new Equipments(Double.parseDouble(pricePerUnit), code, nameOfProduct, 0.0);
						productList.add(equipments);
					} else if (type.equals("S")) {
						String activationFee = data3[3];
						String annualFee = data3[4];
						Products services = new Services(Double.parseDouble(activationFee), Double.parseDouble(annualFee),
								code, nameOfProduct, 0.0);
						productList.add(services);
					} else {
						// compares technicianPersonCode to find the corresponding
						// person
						String technicianPersonCode = data3[3];
						String hourlyFee = data3[4];
						for (int i = 0; i < clonedPersons.size(); i++) {
							if (technicianPersonCode.equals(clonedPersons.get(i).getPersonCode())) {
								Products consultation = new Consultations(Double.parseDouble(hourlyFee),
										clonedPersons.get(i), code, nameOfProduct, 0.0);
								productList.add(consultation);
							}
						}
					}

				}
				scanner.close();
				clonedProducts.addAll(productList);
				return productList;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
		//method for reading invoice 
		public ArrayList<Invoice> readInvoice() {
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File("data/Invoices.dat"));
				scanner.nextLine();

				while (scanner.hasNextLine()) {
					totalFeeList = new ArrayList<Double>();
					totalTaxList = new ArrayList<Double>();
					totalServiceFeeList = new ArrayList<Double>();
					productType = new ArrayList<Products>();

					String line4 = scanner.nextLine();
					String data4[] = line4.split(";");
					String invoiceCode = data4[0];
					String customerCode = data4[1];
					String invoiceReportDate = data4[2];
					String salesPerson = data4[3];
					String[] productList = data4[4].split(",");
					Invoice invoice = null;
					// loop for splitting invoice.dat and making product objects and invoice objects
					for (int i = 0; i < productList.length; i++) {
						String[] product = productList[i].split(":");
						for (int h = 0; h < product.length; h++) {
						for (int j = 0; j < clonedProducts.size(); j++) {
						if (product[h].equals(clonedProducts.get(j).getCode())) {
						for (int k = 0; k < clonedCustomers.size(); k++) {
						if (customerCode.equals(clonedCustomers.get(k).getCustomerCode())) {
						for (int l = 0; l < clonedPersons.size(); l++) {
						if (salesPerson.equals(clonedPersons.get(l).getPersonCode())) {
													invoice = new Invoice(invoiceCode, clonedCustomers.get(k),
															invoiceReportDate, clonedPersons.get(l), totalFeeList,
															totalTaxList, totalServiceFeeList, clonedProducts.get(j), productType);
												}
											}
										}
									}
									//checking for the type of products
									if (clonedProducts.get(j).getType().equals("E")) {

										for (int k = 0; k < clonedCustomers.size(); k++) {

											if (customerCode.equals(clonedCustomers.get(k).getCustomerCode())) {

												for (int l = 0; l < clonedPersons.size(); l++) {

													if (salesPerson.equals(clonedPersons.get(l).getPersonCode())) {

														Equipments purchasedEquipment = new Equipments(
																clonedProducts.get(j).getPricePerUnit(),
																clonedProducts.get(j).getCode(),
																clonedProducts.get(j).getNameOfProduct(), Double.parseDouble(product[1]));
														invoice.getTotalFeeList().add(
																purchasedEquipment.fee(Double.parseDouble(product[1])));
														invoice.getTotalTaxList().add(purchasedEquipment
																.tax(totalFeeList.get(totalFeeList.size() - 1)));
														invoice.getTotalServiceFeeList().add(0.00);
														invoice.getProductType().add(purchasedEquipment);

													}
												}
											}
										}
									} else if (clonedProducts.get(j).getType().equals("C")) {

										for (int k = 0; k < clonedCustomers.size(); k++) {

											if (customerCode.equals(clonedCustomers.get(k).getCustomerCode())) {

												for (int l = 0; l < clonedPersons.size(); l++) {
													if (salesPerson.equals(clonedPersons.get(l).getPersonCode())) {
														Consultations purchasedConsultation = new Consultations(
																clonedProducts.get(j).getHourlyFee(), clonedPersons.get(l),
																clonedProducts.get(j).getCode(),
																clonedProducts.get(j).getNameOfProduct(), Double.parseDouble(product[1]));
														invoice.getTotalServiceFeeList()
														.add(clonedProducts.get(j).computeFee());
														invoice.getTotalFeeList().add(
																purchasedConsultation.fee(Double.parseDouble(product[1])));
														invoice.getTotalTaxList().add(purchasedConsultation
																.tax(totalFeeList.get(totalFeeList.size() - 1)));
														invoice.getProductType().add(purchasedConsultation);

													}
												}
											}
										}
									} else if (clonedProducts.get(j).getType().equals("S")) {
										//joda time to figure out days in year
										String[] firstDate = product[1].split("-");

										int firstYear = Integer.parseInt(firstDate[0]);
										int firstMonth = Integer.parseInt(firstDate[1]);
										int firstDay = Integer.parseInt(firstDate[2]);

										String[] lastDate = product[2].split("-");

										int lastYear = Integer.parseInt(lastDate[0]);
										int lastMonth = Integer.parseInt(lastDate[1]);
										int lastDay = Integer.parseInt(lastDate[2]);

										DateTime finalDate = new DateTime(lastYear, lastMonth, lastDay, 0, 0);
										DateTime startDate = new DateTime(firstYear, firstMonth, firstDay, 0, 0);

										double daysInYear = (Days.daysBetween(new LocalDate(startDate), new LocalDate(finalDate)).getDays()) + 1;
										for (int k = 0; k < clonedCustomers.size(); k++) {

											if (customerCode.equals(clonedCustomers.get(k).getCustomerCode())) {
												for (int l = 0; l < clonedPersons.size(); l++) {
													if (salesPerson.equals(clonedPersons.get(l).getPersonCode())) {
														Services purchasedService = new Services(
																clonedProducts.get(j).computeFee(),
																clonedProducts.get(j).getAnnualFee(),
																clonedProducts.get(j).getCode(),
																clonedProducts.get(j).getNameOfProduct(), daysInYear);
														invoice.getTotalServiceFeeList()
														.add(clonedProducts.get(j).computeFee());
														invoice.getTotalFeeList().add(purchasedService.fee(daysInYear));
														invoice.getTotalTaxList().add(purchasedService
																.tax(totalFeeList.get(totalFeeList.size() - 1)));
														invoice.getProductType().add(purchasedService);

													}
												}
											}
										}
									}
								}
							}
						}
					}


					//storing the created invoice objects in invoiceList
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
				scanner.close();
				return invoiceList;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

}
