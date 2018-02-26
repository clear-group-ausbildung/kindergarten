package de.clearit.kindergarten.domain.export.entity;

import java.util.List;

/**
 * The class PayoffData
 *
 * @author Marco Jaeger
 */
public class PayoffDataReceipt {

  private final List<Integer> vendorNumberList;
  private final String firstName;
  private final String lastName;
  private final Double turnover;
  private final Double profit;
  private final Double payment;
  private final Integer totalSoldItems;
  private final List<PayoffSoldItemsData> payoffSoldItemsData;

  public PayoffDataReceipt(List<Integer> pVendorNumbers, String pFirstName, String pLastName, Double pTurnover,
      Double pProfit, Double pPayment, Integer pTotalSoldItems, List<PayoffSoldItemsData> pPayoffSoldItemsData) {
    vendorNumberList = pVendorNumbers;
    firstName = pFirstName;
    lastName = pLastName;
    turnover = pTurnover;
    profit = pProfit;
    payment = pPayment;
    totalSoldItems = pTotalSoldItems;
    payoffSoldItemsData = pPayoffSoldItemsData;
  }

  public List<Integer> getVendorNumberList() {
    return vendorNumberList;
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

  public List<PayoffSoldItemsData> getPayoffSoldItemsData() {
    return payoffSoldItemsData;
  }
}