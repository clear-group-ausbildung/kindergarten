package de.clearit.kindergarten.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

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
	private final Map<Integer, BigDecimal> soldItemNumbersPricesMap;

	public PayoffData(Integer pVendorNumber, String pFirstName, String pLastName, BigDecimal pTurnover, BigDecimal pProfit,
			BigDecimal pPayment, Integer pTotalSoldItems, Map<Integer, BigDecimal> pSoldItemNumbersPricesMap) {
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

	public String getTurnover() {
		return turnover.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}

	public String getProfit() {
		return profit.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}

	public String getPayment() {
		return payment.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}

	public Integer getTotalSoldItems() {
		return totalSoldItems;
	}

	public Map<Integer, String> getSoldItemNumbersPricesMap() {
		
		return convertMapValuesFromBigDecimalToString(soldItemNumbersPricesMap);
	}

	public Map<Integer, String> convertMapValuesFromBigDecimalToString(Map<Integer, BigDecimal> mapToBeConverted) {
		Map<Integer, String> result = mapToBeConverted.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> value.toString()));
		return result;
    }
}