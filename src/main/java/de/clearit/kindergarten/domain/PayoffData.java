package de.clearit.kindergarten.domain;

import java.util.HashMap;

/**
 * The class PayoffData
 *
 * @author Marco Jaeger
 */
public class PayoffData {

	private Integer vendorId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Double turnover;
	private Double profit;
	private Double payment;
	private Integer totalSoldItems;
	private HashMap<Integer, Double> soldItemNumbersPricesMap;

	public PayoffData(Integer pVendorId, String pFirstName, String pLastName, String pPhoneNumber, Double pTurnover,
			Double pProfit, Double pPayment, Integer pTotalSoldItems, HashMap<Integer, Double> pSoldItemNumbersPricesMap) {
		vendorId = pVendorId;
		firstName = pFirstName;
		lastName = pLastName;
		phoneNumber = pPhoneNumber;
		turnover = pTurnover;
		profit = pProfit;
		payment = pPayment;
		totalSoldItems = pTotalSoldItems;
		soldItemNumbersPricesMap = pSoldItemNumbersPricesMap;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Double getTurnover() {
		return turnover;
	}

	public Double getProfit() {
		return profit;
	}

	public Double getPayment() {
		return payment;
	}

	public Integer getTotalSoldItems() {
		return totalSoldItems;
	}

	public HashMap<Integer, Double> getSoldItemNumbersPricesMap() {
		return soldItemNumbersPricesMap;
	}
	
	
}