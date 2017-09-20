package de.clearit.kindergarten.domain;

import java.util.HashMap;

/**
 * The class PayoffData
 *
 * @author Marco Jaeger
 */
public class PayoffData {

	private Integer vendorNumber;
	private String firstName;
	private String lastName;
	private Double turnover;
	private Double profit;
	private Double payment;
	private Integer totalSoldItems;
	private HashMap<Integer, Double> soldItemNumbersPricesMap;

	public PayoffData(Integer pVendorNumber, String pFirstName, String pLastName, Double pTurnover, Double pProfit,
			Double pPayment, Integer pTotalSoldItems, HashMap<Integer, Double> pSoldItemNumbersPricesMap) {
		vendorNumber = pVendorNumber;
		firstName = pFirstName;
		lastName = pLastName;
		turnover = pTurnover;
		profit = pProfit;
		payment = pPayment;
		totalSoldItems = pTotalSoldItems;
		soldItemNumbersPricesMap = pSoldItemNumbersPricesMap;
	}

	public Integer getVendorNumber() {
		return vendorNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
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