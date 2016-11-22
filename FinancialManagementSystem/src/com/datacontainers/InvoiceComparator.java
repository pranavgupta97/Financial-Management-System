package com.datacontainers;

import java.util.Comparator;

public class InvoiceComparator implements Comparator<Invoice>{

	@Override
	public int compare(Invoice invoice1, Invoice invoice2) {
		if((invoice1.getInvoiceTotal()) < (invoice2.getInvoiceTotal())) {
			return -1;
		}
		else if((invoice1.getInvoiceTotal()) > (invoice2.getInvoiceTotal())) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
