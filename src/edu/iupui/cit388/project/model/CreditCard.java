package edu.iupui.cit388.project.model;

public class CreditCard {

	private String number;
	private int expireMonth;
	private int expireYear;
	
	public CreditCard(String number, int expireMonth, int expireYear) {
		super();
		this.number = number;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getExpireMonth() {
		return expireMonth;
	}

	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}

	public int getExpireYear() {
		return expireYear;
	}

	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}

	
}
