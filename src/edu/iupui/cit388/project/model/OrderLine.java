package edu.iupui.cit388.project.model;

public class OrderLine {

	private String itemDescription;
	private int quantity;
	private double unitPrice;
	
	public OrderLine(String itemDescription, int quantity, double unitPrice) {
		super();
		this.itemDescription = itemDescription;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "OrderLine [itemDescription=" + itemDescription + ", quantity=" + quantity + ", unitPrice=" + unitPrice
				+ "]";
	}
	
	
}
