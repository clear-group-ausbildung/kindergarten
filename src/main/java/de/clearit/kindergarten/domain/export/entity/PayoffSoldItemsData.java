package de.clearit.kindergarten.domain.export.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * The class PayoffSoldItemsData
 *
 * @author Marco Jaeger
 */
public class PayoffSoldItemsData {

	private final Integer vendorNumber;
	private final HashMap<Integer, Double> soldItemNumbersPricesMap;
	private final Double soldItemSum;

	public PayoffSoldItemsData(Integer pVendorNumber, HashMap<Integer, Double> pSoldItemNumbersPricesMap,
			Double pSoldItemSum) {
		vendorNumber = pVendorNumber;
		soldItemNumbersPricesMap = pSoldItemNumbersPricesMap;
		soldItemSum = pSoldItemSum;
	}

	public Integer getVendorNumber() {
		return vendorNumber;
	}

	public Map<Integer, Double> getSoldItemNumbersPricesMap() {
		return soldItemNumbersPricesMap;
	}

	public Double getSoldItemSum() {
		return soldItemSum;
	}
}