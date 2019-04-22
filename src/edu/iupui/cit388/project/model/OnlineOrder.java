package edu.iupui.cit388.project.model;

import java.util.Calendar;

public class OnlineOrder extends Order {

	private String shipAddress;
	private CreditCard creditCardInfo;
	
	public OnlineOrder(long id, Calendar dateTime) {
		super(id, dateTime);
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public CreditCard getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(CreditCard creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	@Override
	public String getAdditionalInfo() {
		return shipAddress + "\n";
	}
}
