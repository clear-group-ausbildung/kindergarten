package de.clearit.kindergarten.domain;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * The class PayoffData
 *
 * @author Marco Jaeger
 */
public class PayoffData {

	private final Integer vendorNumber;
	private final String firstName;
	private final String lastName;
	private final BigDecimal turnover;
	private final BigDecimal profit;
	private final BigDecimal payment;
	private final Integer totalSoldItems;
	private final HashMap<Integer, BigDecimal> soldItemNumbersPricesMap;

	public PayoffData(Integer pVendorNumber, String pFirstName, String pLastName, BigDecimal pTurnover, BigDecimal pProfit,
			BigDecimal pPayment, Integer pTotalSoldItems, HashMap<Integer, BigDecimal> pSoldItemNumbersPricesMap) {
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

	public BigDecimal getTurnover() {
		return turnover;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public Integer getTotalSoldItems() {
		return totalSoldItems;
	}

	public HashMap<Integer, BigDecimal> getSoldItemNumbersPricesMap() {
		return soldItemNumbersPricesMap;
	}

}