package de.clearit.kindergarten.domain.export.entity;

import java.util.ArrayList;

/**
 * The class PayoffDataInternal
 *
 * @author Marco Jaeger
 */
public class PayoffDataInternal {

	private final Double turnover;
	private final Double profit;
	private final Double payment;
	private final Integer totalSoldItems;
	private final ArrayList<PayoffDataInternalVendor> payoffDataInternalVendorList;

	public PayoffDataInternal(Double pTurnover, Double pProfit, Double pPayment, Integer pTotalSoldItems,
			ArrayList<PayoffDataInternalVendor> pPayoffDataInternalVendorList) {
		turnover = pTurnover;
		profit = pProfit;
		payment = pPayment;
		totalSoldItems = pTotalSoldItems;
		payoffDataInternalVendorList = pPayoffDataInternalVendorList;
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

	public ArrayList<PayoffDataInternalVendor> getPayoffDataInternalVendor() {
		return payoffDataInternalVendorList;
	}
}