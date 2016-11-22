package com.reports;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.datacontainers.Customers;
import com.datacontainers.Invoice;
import com.datacontainers.InvoiceComparator;
import com.datacontainers.InvoiceList;
import com.datacontainers.Person;
import com.datacontainers.Products;
import com.fileReader.DataBaseReader;
import com.fileReader.FlatFileReader;
import org.apache.log4j.Logger;


public class InvoiceReport {
	public static void main(String[] args) {
		org.apache.log4j.BasicConfigurator.configure();
		//FlatFileReader Object
//		FlatFileReader fr = new FlatFileReader();
		//Lists of objects
//		List<Person> personList = fr.readPersons();
//		List<Customers> customersList = fr.readCustomers();
//		List<Products> productList = fr.readProducts();
//		List<Invoice> invoiceList = fr.readInvoice();
		
		DataBaseReader dr = new DataBaseReader();
		List<Person> personList = dr.readPersons();
		List<Customers> customersList = dr.readCustomers();
		List<Products> productList = dr.readProducts();
		List<Invoice> invoiceList = dr.readInvoice();
		
		InvoiceList invoiceOrderedList = new InvoiceList(new InvoiceComparator());
		for(int i = 0; i < invoiceList.size(); i++) {
			invoiceOrderedList.add(invoiceList.get(i));
		}
		
		//variables
			double sum1 = 0.0;
			double sum2 = 0.0;
			double sum3 = 0.0;
			double totalSum1 = 0.0;
			double totalSum2 = 0.0;
			double totalSum3 = 0.0;
			double taxSum = 0.00;
			String type = "";
			boolean determineTax = false;
			
			//print statements for the totals of subtotals, fees, and taxes
			System.out.printf("%s \n", "INVOICE SUMMARY REPORT");
			System.out.printf("%s \n", "=================================");
			System.out.printf("%-10s %-40s %-25s %-16s %-12s %-12s %-15s \n", "Invoice", "Customer", "SalesPerson",
					"SubTotal", "Fees", "Taxes", "Total");

			for (int i = 0; i < invoiceOrderedList.size(); i++) {
				invoiceOrderedList.get(i).getCustomer().setDetermineTax(determineTax);
				if ((invoiceOrderedList.get(i).getCustomer().isDetermineTax()) == true) {
					determineTax = true;

				}

				for (int a = 0; a < invoiceOrderedList.get(i).getTotalFeeList().size(); a++) {
					sum1 = sum1 + invoiceOrderedList.get(i).getTotalFeeList().get(a);

				}
				for (int b = 0; b < invoiceOrderedList.get(i).getTotalTaxList().size(); b++) {
					sum2 = sum2 + invoiceOrderedList.get(i).getTotalTaxList().get(b);

				}
				for (int c = 0; c < invoiceOrderedList.get(i).getTotalServiceFeeList().size(); c++) {
					sum3 = sum3 + invoiceOrderedList.get(i).getTotalServiceFeeList().get(c);
				}
				if (determineTax == true) {
					System.out.printf("%-10s %-40s %-23s $ %-11.2f $ %-12.2f $ %-8.2f $ %-15.2f \n",
							invoiceOrderedList.get(i).getInvoiceCode(), invoiceOrderedList.get(i).getCustomer().getName(),
							invoiceOrderedList.get(i).getPerson().getFullName(),
							sum1, sum3, sum2, sum1 + sum3 + sum2);
				} else {
					System.out.printf("%-10s %-40s %-23s $ %-11.2f $ %-12.2f $ %-8.2f $ %-15.2f \n",
							invoiceOrderedList.get(i).getInvoiceCode(), invoiceOrderedList.get(i).getCustomer().getName(),
							invoiceOrderedList.get(i).getPerson().getFullName(),
							sum1, (sum3 + (invoiceOrderedList.get(i).getCustomer().getComplianceFee())), taxSum, sum1 + (sum3 + (invoiceOrderedList.get(i).getCustomer().getComplianceFee())) + taxSum);
					sum3 += invoiceOrderedList.get(i).getCustomer().getComplianceFee();
					sum2 = taxSum;
				}
				totalSum1 = totalSum1 + sum1;
				totalSum2 = totalSum2 + sum2;
				totalSum3 = totalSum3 + sum3;
				sum1 = 0.0;
				sum2 = 0.0;
				sum3 = 0.0;
				determineTax = false;

			}
			System.out.printf("%s \n",
					"===================================================================================================================================");
			System.out.printf("%-75s $ %-11.2f $ %-12.2f $ %-8.2f $ %-15.2f \n\n", "Totals", totalSum1, totalSum3,
					totalSum2, totalSum1 + totalSum3 + totalSum2);
			
			// Printing the invoice objects
			System.out.printf("%s \n", "=================================");
			System.out.printf("%s \n", "INVOICE DETAILS REPORTS");
			System.out.printf("%s \n\n", "=================================");
			for (int q = 0; q < invoiceOrderedList.size(); q++) {
				if (invoiceOrderedList.get(q).getCustomer().getCustomerType().equals("B")) {
					type = "Business";
					invoiceOrderedList.get(q).setDate();
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s %s \n", "Invoice:", invoiceOrderedList.get(q).getInvoiceCode());
					System.out.printf("%s %s \n", "Date: ", invoiceOrderedList.get(q).getDate());
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s %s %s \n", "Saleperson: ", invoiceOrderedList.get(q).getPerson().getLastName(),
							invoiceOrderedList.get(q).getPerson().getFirstName());
					System.out.printf("%s \n", "Customer: ");
					System.out.printf("    %s %s %s %s  \n", invoiceOrderedList.get(q).getCustomer().getName(), "(",
							invoiceOrderedList.get(q).getCustomer().getCustomerCode(), ")");
					System.out.printf("    %s %s %s \n", "(", type, ")");
					System.out.printf("    %s \n", "[" + invoiceOrderedList.get(q).getCustomer().getContact().getLastName()
							+ ", " + invoiceOrderedList.get(q).getCustomer().getContact().getFirstName() + "]");
					System.out.printf("    %s \n", invoiceOrderedList.get(q).getCustomer().getAddress().getStreet());
					System.out.printf("   %s \n", invoiceOrderedList.get(q).getCustomer().getAddress().getCity() + "," + invoiceOrderedList.get(q).getCustomer().getAddress().getState() + invoiceOrderedList.get(q).getCustomer().getAddress().getZip() + " " + invoiceOrderedList.get(q).getCustomer().getAddress().getCountry());
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s \n", "Code     Item                                                            SubTotal       Taxes        Fees           Total");

					for(int t =0; t< invoiceOrderedList.get(q).getTotalFeeList().size(); t++){
						if(invoiceOrderedList.get(q).getProductType().get(t).getType().equals("E")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " units at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/unit)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));

						}
						else if (invoiceOrderedList.get(q).getProductType().get(t).getType().equals("C")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " hours at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/hr)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));
						}
						else if(invoiceOrderedList.get(q).getProductType().get(t).getType().equals("S")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " days at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/yr)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));
						}
					}
					for (int a = 0; a < invoiceOrderedList.get(q).getTotalFeeList().size(); a++) {
						sum1 = sum1 + invoiceOrderedList.get(q).getTotalFeeList().get(a);

					}
					for (int b = 0; b < invoiceOrderedList.get(q).getTotalTaxList().size(); b++) {
						sum2 = sum2 + invoiceOrderedList.get(q).getTotalTaxList().get(b);

					}
					for (int c = 0; c < invoiceOrderedList.get(q).getTotalServiceFeeList().size(); c++) {
						sum3 = sum3 + invoiceOrderedList.get(q).getTotalServiceFeeList().get(c);
					}
					System.out.printf("%119s \n", "===============================================");
					System.out.printf("%-71s $ %-10.2f $ %-11.2f $ %-9.2f $ %.2f \n", "SUB-TOTALS", sum1, sum2, sum3, sum1 + sum2 + sum3 );
					System.out.printf("%-110s $ %.2f \n", "COMPLIANCE FEE", invoiceOrderedList.get(q).getCustomer().getComplianceFee() );
					System.out.printf("%-110s $ %.2f \n\n\n","FINAL TOTAL", sum1 + sum2 + sum3 );
					sum1 = 0;
					sum2 = 0;
					sum3 = 0;

				} else {
					type = "Residential";
					invoiceOrderedList.get(q).setDate();
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s %s \n", "Invoice:", invoiceOrderedList.get(q).getInvoiceCode());
					System.out.printf("%s %s \n", "Date: ", invoiceOrderedList.get(q).getDate());
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s %s %s \n", "Saleperson: ", invoiceOrderedList.get(q).getPerson().getLastName(),
							invoiceOrderedList.get(q).getPerson().getFirstName());
					System.out.printf("%s \n", "Customer: ");
					System.out.printf("    %s %s %s %s  \n", invoiceOrderedList.get(q).getCustomer().getName(), "(",
							invoiceOrderedList.get(q).getCustomer().getCustomerCode(), ")");
					System.out.printf("    %s  \n", "(" + type + ")");
					System.out.printf("    %s \n", "[" + invoiceOrderedList.get(q).getCustomer().getContact().getLastName()
							+ ", " + invoiceOrderedList.get(q).getCustomer().getContact().getFirstName() + "]");
					System.out.printf("    %s \n", invoiceOrderedList.get(q).getCustomer().getAddress().getStreet());
					System.out.printf("    %s \n", invoiceOrderedList.get(q).getCustomer().getAddress().getCity() + "," + invoiceOrderedList.get(q).getCustomer().getAddress().getState() + invoiceOrderedList.get(q).getCustomer().getAddress().getZip() + " " + invoiceOrderedList.get(q).getCustomer().getAddress().getCountry());
					System.out.printf("%s \n", "----------------------------------");
					System.out.printf("%s \n", "Code     Item                                                            SubTotal       Taxes        Fees       Total");
					for(int t = 0; t < invoiceOrderedList.get(q).getTotalFeeList().size(); t++){
						if(invoiceOrderedList.get(q).getProductType().get(t).getType().equals("E")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " units at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/unit)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));

						}
						else if (invoiceOrderedList.get(q).getProductType().get(t).getType().equals("C")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " hours at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/hr)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));
						}
						else if(invoiceOrderedList.get(q).getProductType().get(t).getType().equals("S")){
							System.out.printf("%-8s %-62s $ %-10.2f $ %-11.2f $ %.2f \n", (invoiceOrderedList.get(q).getProductType().get(t).getCode()),(invoiceOrderedList.get(q).getProductType().get(t).getNameOfProduct()+ "  (" + invoiceOrderedList.get(q).getProductType().get(t).getUnits() + " days at " + invoiceOrderedList.get(q).getProductType().get(t).getPricePerUnit() +"/yr)"), (invoiceOrderedList.get(q).getTotalFeeList().get(t)), (invoiceOrderedList.get(q).getTotalTaxList().get(t)), (invoiceOrderedList.get(q).getTotalServiceFeeList().get(t)));
						}
					}
					for (int a = 0; a < invoiceOrderedList.get(q).getTotalFeeList().size(); a++) {
						sum1 = sum1 + invoiceOrderedList.get(q).getTotalFeeList().get(a);

					}
					for (int b = 0; b < invoiceOrderedList.get(q).getTotalTaxList().size(); b++) {
						sum2 = sum2 + invoiceOrderedList.get(q).getTotalTaxList().get(b);

					}
					for (int c = 0; c < invoiceOrderedList.get(q).getTotalServiceFeeList().size(); c++) {
						sum3 = sum3 + invoiceOrderedList.get(q).getTotalServiceFeeList().get(c);
					}
					System.out.printf("%119s \n", "===============================================");
					System.out.printf("%-71s $ %-10.2f $ %-11.2f $ %-9.2f $ %.2f \n", "SUB-TOTALS", sum1, taxSum, sum3, sum1 + taxSum + sum3 );
					System.out.printf("%-110s $ %.2f \n", "COMPLIANCE FEE", invoiceOrderedList.get(q).getCustomer().getComplianceFee() );
					System.out.printf("%-110s $ %.2f \n\n\n","FINAL TOTAL", sum1 + taxSum + invoiceOrderedList.get(q).getCustomer().getComplianceFee() + sum3 );
					sum1 = 0;
					sum2 = 0;
					sum3 = 0;

				}
			}
	}
}
