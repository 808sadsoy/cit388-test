package edu.iupui.cit388.project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.iupui.cit388.project.api.ReceiptGenerator;

public abstract class Order implements ReceiptGenerator {

	private long id;
	private Calendar dateTime;	
	private List<OrderLine> orderLineList = new ArrayList<>();
		
	public List<OrderLine> getOrderLines() {
		return orderLineList;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLineList = orderLines;
	}
	
	public void addOrderLine(OrderLine orderLine) {
		this.orderLineList.add(orderLine);
	}

	public void removeOrderLineByItemDescription(String item) {
		
		for (int i = 0;  i < orderLineList.size(); i++) {
			if (orderLineList.get(i).getItemDescription().equals(item)) {
				orderLineList.remove(i);
				break;
			}
		}
	}

	@Override
	public double receiptTotalAmount() {
		
		double total = 0.0;
		
		for (int i = 0;  i < orderLineList.size(); i++) {
			OrderLine orderLine = orderLineList.get(i);
			
			total += orderLine.getQuantity() * orderLine.getUnitPrice();
		}
		
		return total;
	}
	
	public abstract String getAdditionalInfo();
	
	@Override
	public String receiptDetails() {
		
//		System.out.println();
		String details = getAdditionalInfo();

		for (int i = 0;  i < orderLineList.size(); i++) {			
			details += "\n" + orderLineList.get(i).toString(); 
		}
		
//		System.out.println(dateTime.getTime());
		return details;
	}

	
	public Order(long id, Calendar dateTime) {
		super();
		this.id = id;
		this.dateTime = dateTime;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Calendar getDateTime() {
		return dateTime;
	}
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}
	 
	 
}
