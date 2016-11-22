package com.mwc.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fileReader.DatabaseInfo;

/* NOTE: Donot change the package name or any of the method signatures.
 *  
 * There are 14 methods in total, all of which need to be completed as a 
 * bare minimum as part of the assignment.You can add additional methods 
 * for testing if you feel.
 * 
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your 
 * database.
 * 
 * Donot forget to change your reports generation classes to read from 
 * your database instead of the .dat files.
 */


/**
 * Class containing all methods interacting with the database.
 */
public class InvoiceData {
	
	/**Method that removes every person record from the database. 
	 */
	public static void removeAllPersons() {
	
		Connection conn = DatabaseInfo.getConnection();
		String query1 = "DELETE FROM email";

		try {
		PreparedStatement ps1 = conn.prepareStatement(query1);
		ps1.executeUpdate();

		String query2 = "SELECT personID FROM Person";
		
		PreparedStatement ps2 = conn.prepareStatement(query2);
		ResultSet rs2 = ps2.executeQuery();
		
		int personID = 0;
		
		while (rs2.next()) {
		personID = rs2.getInt("personID");

		// Gets the invoiceID from Invoice using personID
		String query3 = "SELECT invoiceID FROM Invoice WHERE personID = ?";
		
		PreparedStatement ps3 = conn.prepareStatement(query3);
		ps3.setInt(1, personID);
		ResultSet rs3 = ps3.executeQuery();
		
		int invoiceID = 0;
		
		if (rs3.next()) {
		invoiceID = rs3.getInt("invoiceID");
		}
		
		// Deletes every ProductInvoice using the invoiceID
		String query4 = "DELETE FROM ProductListInvoices WHERE invoiceID = ?";
		PreparedStatement ps4 = conn.prepareStatement(query4);
		ps4.setInt(1, invoiceID);
		ps4.executeUpdate();


		// Deletes every Invoice using the personID
		String query5 = "DELETE FROM Invoice WHERE personID = ?";
		PreparedStatement ps5 = conn.prepareStatement(query5);
		ps5.setInt(1, personID);
		ps5.executeUpdate();

		// Deletes every Customer using the personID
		String query6 = "DELETE FROM Customers WHERE personID = ?";
		PreparedStatement ps6 = conn.prepareStatement(query6);
		ps6.setInt(1, personID);
		ps6.executeUpdate();

		// Deletes every Product using the personID
		String query7 = "DELETE FROM Products WHERE personID = ?";
		PreparedStatement ps7 = conn.prepareStatement(query7);
		ps7.setInt(1, personID);
		ps7.executeUpdate();

		//Deletes every Address using personId
		String query8 = "DELETE FROM Address WHERE personID = ?";
		PreparedStatement ps8 = conn.prepareStatement(query8);
		ps8.setInt(1, personID);
		ps8.executeUpdate();
		
		ps3.close();
		ps4.close();
		ps5.close();
		ps6.close();
		ps7.close();
		ps8.close();
	
		
		}

		// Finally deletes every Person
		String query9 = "DELETE FROM Person";
		PreparedStatement ps9 = conn.prepareStatement(query9);
		ps9.executeUpdate();
		ps1.close();
		ps2.close();
		rs2.close();
		ps9.close();
		conn.close();
		
		} catch (SQLException e) {
		System.out.println("SQLException: ");
		e.printStackTrace();
		throw new RuntimeException(e);
		}
	
	
}
	
	/**Method to add a person record to the database with the provided data. 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String street, String city, String state, String zip, String country) {
			Connection conn = DatabaseInfo.getConnection();
			String findPersonID = "SELECT personID FROM Person WHERE personCode = ?";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(findPersonID);
				ps.setString(1, personCode);
				rs = ps.executeQuery();
				while(rs.next()){
				int personID = rs.getInt("personID");
				}
				ps.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String addPerson = "INSERT INTO Person (personCode, firstName, lastName) VALUES (personCode, firstName, lastName) ";
			 try {
				ps = conn.prepareStatement(addPerson);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			addCountry(country);
			
			addState(state);
			
			String getCountryID = "SELECT countryID FROM Country WHERE country = ?";
			 try {
				ps = conn.prepareStatement(getCountryID);
				ps.setString(1, country);
				 rs = ps.executeQuery();
				while(rs.next()){
				int countryID = rs.getInt("countryID");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String getStateID = "SELECT stateID FROM State WHERE state = ?";
			 try {
				ps = conn.prepareStatement(getStateID);
				ps.setString(1, state);
				 rs = ps.executeQuery();
				while(rs.next()){
				int countryID = rs.getInt("countryID");
				}
				ps.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String personAddress = "INSERT INTO Address(street, city, zip, countryID, stateID, personID) VALUES (street, city, zip, countryID, stateID, personID)";
			
			 try {
				ps = conn.prepareStatement(personAddress);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}
	
	/**Method to add an email record to the database with the associated personCode. 
	 */
	public static void addEmail(String personCode, String email) {
	Connection conn = DatabaseInfo.getConnection();
	String findPersonID = "SELECT personID FROM Person WHERE personCode = ?";
	PreparedStatement ps;
	ResultSet rs;
	try {
		ps = conn.prepareStatement(findPersonID);
		ps.setString(1, personCode);
		rs = ps.executeQuery();
		while(rs.next()){
		int personID = rs.getInt("personID");
		}
		ps.close();
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	String addEmail = "INSERT INTO email(personID, email) VALUES (personID, email)";
	 try {
		ps = conn.prepareStatement(findPersonID);
		rs = ps.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	
	}
	
	/**Method that removes every customer record from the database. 
	 */
	public static void removeAllCustomers() {
	String query1 = "SELECT invoiceID FROM Invoice";
	Connection conn = DatabaseInfo.getConnection();

	try {
	PreparedStatement ps1 = conn.prepareStatement(query1);
	ResultSet rs1 = ps1.executeQuery();
	int invoiceID = 0;

	// This loop deletes the invoiceID foreign key from ProductListInvoices

	while (rs1.next()) {
	invoiceID = rs1.getInt("invoiceID");

	String query2 = "DELETE FROM ProductListInvoices WHERE invoiceID = ?";
	PreparedStatement ps2 = conn.prepareStatement(query2);
	ps2.setInt(1, invoiceID);
	ps2.executeUpdate();
	ps2.close();
	}
	ps1.close();
	rs1.close();
	

	String query = "SELECT customerID FROM Customers";
	PreparedStatement ps = conn.prepareStatement(query);
	ResultSet rs = ps.executeQuery();
	int customerID = 0;
	// This loop deletes the Invoice that has a certain CustomerID
	// foreign key
	while (rs.next()) {
	customerID = rs.getInt("customerID");
	

	String query4 = "DELETE FROM Invoice WHERE customerID = ?";
	PreparedStatement ps4 = conn.prepareStatement(query4);
	ps4.setInt(1, customerID);
	ps4.executeUpdate();
	
	String query5 = "DELETE FROM Address WHERE customerID = ?";
	PreparedStatement ps5 = conn.prepareStatement(query5);
	ps5.setInt(1, customerID);
	ps5.executeUpdate();
	
	
	ps4.close();
	ps5.close();
	
	}
	rs.close();
	ps.close();
	// Finally deletes all Customer
	String query6 = "DELETE FROM Customers";
	PreparedStatement ps6 = conn.prepareStatement(query6);
	ps6.executeUpdate();
	ps6.close();
	
	
	conn.close();
	} catch (SQLException e) {
	System.out.println("SQLException: ");
	e.printStackTrace();
	throw new RuntimeException(e);
	}
	
	
	
	}
	
	/**Method to add a customer record to the database with the provided data
	 */
	public static void addCustomer(String customerCode, String type, String primaryContactPersonCode, String name, 
			String street, String city, String state, String zip, String country) {
			Connection conn = DatabaseInfo.getConnection();
			
			String findPersonID = "SELECT personID FROM Person WHERE primaryContactPersonCode = ?";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(findPersonID);
				ps.setString(1, primaryContactPersonCode);
				rs = ps.executeQuery();
				while(rs.next()){
				int personID = rs.getInt("personID");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String addCustomerQuery = "INSERT INTO Customers (customerCode, customerType, personID, name) VALUES (customerCode, type, personID, name)";
			try {
				ps = conn.prepareStatement(addCustomerQuery);
				 rs = ps.executeQuery();
					ps.close();
					rs.close();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			addCountry(country);
			addState(state);
			
			String getCountryID = "SELECT countryID FROM Country WHERE country = ?";
			 try {
				ps = conn.prepareStatement(getCountryID);
				ps.setString(1, country);
				 rs = ps.executeQuery();
				while(rs.next()){
				int countryID = rs.getInt("countryID");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String getStateID = "SELECT stateID FROM State WHERE state = ?";
			 try {
				ps = conn.prepareStatement(getStateID);
				ps.setString(1, state);
				 rs = ps.executeQuery();
				while(rs.next()){
				int countryID = rs.getInt("countryID");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String customerAddress = "INSERT INTO Address(street, city, zip, countryID, stateID, customerCode) VALUES (street, city, zip, countryID, stateID, customerCode)";
			
			 try {
				ps = conn.prepareStatement(customerAddress);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			
	}
	
	/**Removes all product records from the database. 
	 */
	public static void removeAllProducts() {
	Connection conn = DatabaseInfo.getConnection();
	String removeProductQuery = "DELETE FROM Products";
	
	

	try {
		PreparedStatement ps = conn.prepareStatement(removeProductQuery);
		ps.executeUpdate();
		ps.close();
		conn.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	
	
	 
	
	
	}
	/**Adds an equipment record to the database with the provided data.
	 */
	public static void addEquipment(String productCode, String name, Double pricePerUnit) {
	Connection conn = DatabaseInfo.getConnection();
	
	String type = "E";
	String addEquipment = " INSERT INTO Products (code, type, nameOfProduct, pricePerUnit) VALUES (productCode, type, name, pricePerUnit)";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(addEquipment);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

	
	/**Adds a service record to the database with the provided data.
	 */
	public static void addService(String productCode, String name, double activationFee, double annualFee) {
	Connection conn = DatabaseInfo.getConnection();
	
	String type = "S";
	String addService = " INSERT INTO Products (code, type, nameOfProduct, activationFee, annualFee) VALUES (productCode, type, name, activationFee, annualFee)";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(addService);
				 rs = ps.executeQuery();
					ps.close();
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	/**Adds an consultation record to the database with the provided data.
	 */
	public static void addConsultation(String productCode, String name, String consultantPersonCode, Double hourlyFee) {
	Connection conn = DatabaseInfo.getConnection();
	
	String findPersonID = "SELECT personID FROM Person WHERE personCode = ?";
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(findPersonID);
				ps.setString(1, consultantPersonCode);
				rs = ps.executeQuery();
				while(rs.next()){
					int personID = rs.getInt("personID");
					}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
	
	String type = "C";
	String addEquipment = " INSERT INTO Products (code, type, nameOfProduct, personID, hourlyFee) VALUES (productCode, type, name, personID, hourlyFee)";
			 try {
				ps = conn.prepareStatement(addEquipment);
				 rs = ps.executeQuery();
					ps.close();
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	/**Removes all invoice records from the database. 
	 */
	public static void removeAllInvoices() {
	Connection conn = DatabaseInfo.getConnection();

	String query1 = "SELECT invoiceID FROM Invoice";
	

	try {
	PreparedStatement ps1 = conn.prepareStatement(query1);
	ResultSet rs1 = ps1.executeQuery();

	PreparedStatement ps2 = null;
	
	while (rs1.next()) {
	int invoiceID = rs1.getInt("invoiceID");

	String query2 = "DELETE FROM ProductListInvoices WHERE invoiceID = ?";
	ps2 = conn.prepareStatement(query2);
	ps2.setInt(1, invoiceID);
	ps2.executeUpdate();
	ps2.close();
	}
	String query3 = "DELETE FROM Invoice";
	PreparedStatement ps3 = conn.prepareStatement(query3);
	ps3.executeUpdate();

	ps1.close();
	ps3.close();
	rs1.close();
	conn.close();
	} catch (SQLException e) {
	System.out.println("SQLException: ");
	e.printStackTrace();
	throw new RuntimeException(e);
	}
	
	
	}
	
	/**Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String invoiceDate, String salesPersonCode) {
		String findPersonID = "SELECT personID FROM Person WHERE personCode = ?";
		Connection conn = DatabaseInfo.getConnection();
			PreparedStatement ps;
			ResultSet rs ;
			try {
				ps = conn.prepareStatement(findPersonID);
				ps.setString(1, salesPersonCode);
				rs = ps.executeQuery();
				while(rs.next()){
					int salesPersonID = rs.getInt("personID");
					}
					ps.close();
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		String addInvoice = "INSERT INTO INVOICE (invoiceCode, customerCode, invoiceReportDate, personID) VALUES (invoiceCode, customerCode, invoiceDate, salesPersonId)";
		 	try {
				ps = conn.prepareStatement(addInvoice);
				 rs = ps.executeQuery();
					ps.close();
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
	
	
	/**Adds a particular equipment (corresponding to productCode to an 
	 * invoice corresponding to the provided invoiceCode with the given
	 * number of units)
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String productCode, int numUnits) {
	String addEquipmentToInvoice = "INSERT INTO productListInvoices (invoiceCode, productCode, units) VALUES (invoiceCode, productCode, Double.parseDouble(numUnits))";
	Connection conn = DatabaseInfo.getConnection();
	PreparedStatement ps;
	ResultSet rs ;
	try {
		ps = conn.prepareStatement(addEquipmentToInvoice);
		rs = ps.executeQuery();
		ps.close();
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
			
	}
	
	/**Adds a particular service (corresponding to productCode to an 
	 * invoice corresponding to the provided invoiceCode with the given
	 * begin/end dates)
	 */
	public static void addServiceToInvoice(String invoiceCode, String productCode, String startDate, String endDate) {
	//make fucking changes to database
			String addServiceToInvoice = "INSERT INTO productListInvoices (invoiceCode, productCode, startDate, endDate) VALUES (invoiceCode, productCode, startDate, endDate)";
			Connection conn = DatabaseInfo.getConnection();
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(addServiceToInvoice);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	/**Adds a particular consultation (corresponding to productCode to an 
	 * invoice corresponding to the provided invoiceCode with the given
	 * number of billable hours.)
	 */
	public static void addConsultationToInvoice(String invoiceCode, String productCode, double numHours) {
		
		String addConsultationToInvoice = "INSERT INTO productListInvoices (invoiceCode, productCode, units) VALUES (invoiceCode, productCode, numHours)";
		Connection conn = DatabaseInfo.getConnection();
			PreparedStatement ps;
			ResultSet rs;
			try {
				ps = conn.prepareStatement(addConsultationToInvoice);
				rs = ps.executeQuery();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
	public static void addCountry(String country){
	String addCountry = "INSERT INTO Country(country) VALUES(country)";
	Connection conn = DatabaseInfo.getConnection();
	try{
			PreparedStatement ps = conn.prepareStatement(addCountry);
			ResultSet rs = ps.executeQuery();
			ps.close();
			rs.close();
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public static void addState(String state){
		String addState = "INSERT INTO State (state) VALUES (state)";
		Connection conn = DatabaseInfo.getConnection();
			try{
				PreparedStatement ps = conn.prepareStatement(addState);
			
			ResultSet rs = ps.executeQuery();
			ps.close();
			rs.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}