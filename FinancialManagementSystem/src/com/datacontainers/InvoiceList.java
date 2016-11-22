package com.datacontainers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InvoiceList implements Iterable<Invoice>{
	private Invoice invoices[];
	private Comparator<Invoice> comp;
	private int size;
	
	public InvoiceList(Comparator<Invoice> comp) {
		this.invoices = new Invoice[1];
		this.size = 0;
		this.comp = comp;
	}
	
	public Comparator<Invoice> getComp() {
		return comp;
	}
	
	public void add(Invoice invoice) {
//		InvoiceNode<Invoice> node = new InvoiceNode<Invoice>(invoice, null);
		if(size < invoices.length) {
			invoices[size] = invoice;
//			head = node;
//			size++;
		}
		else {
			Invoice[] original = invoices;
			invoices = new Invoice[size + 1];
			for(int i = 0; i < size; i++) {
				invoices[i] = original[i];
			}
			original = null;
			invoices[size] = invoice;
			for(int j = invoices.length - 2; j >= 0; j--) {
				if(this.comp.compare(invoices[j], invoices[j + 1]) < 0) {
					Invoice temp = invoices[j];
					invoices[j] = invoices[j + 1];
					invoices[j + 1] = temp;
				}
			}
		}
		size++;
//		if(this.comp.compare(head.getInvoice(), node.getInvoice()) < 0) {
//			node.setNext(head);
//			head = node;
//			size++;
//		}
	}
	
	public Invoice get(int index) {
		Invoice retrievedInvoice = null;
		try{
			retrievedInvoice = invoices[index];
		} catch(IndexOutOfBoundsException e) {
			System.out.println("\nException thrown :" + e);
		}
		return ((Invoice)retrievedInvoice);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		boolean empty = false;
		if(size == 0) {
			empty = true;
		}
		return empty;
	}
	
	@Override
	public Iterator<Invoice> iterator() {
		return new IteratorInvoice();
	}
	public class IteratorInvoice implements Iterator<Invoice> {
		int index = 0;
		@Override
		public boolean hasNext() {
			return index < InvoiceList.this.size;
			 }

		@Override
		public Invoice next() {
			Invoice nextInvoice = null;
			if(this.hasNext()) {
				nextInvoice = invoices[index + 1];
				index++;
				return nextInvoice;
			}
			else {
				throw new NoSuchElementException();
			}
		}

	}
}
