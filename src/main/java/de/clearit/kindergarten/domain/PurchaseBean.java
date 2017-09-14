package de.clearit.kindergarten.domain;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Purchase resource.
 */
public class PurchaseBean extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_ITEM_QUANTITY = "itemQuantity";
  public static final String PROPERTY_ITEM_NUMBER = "itemNumber";
  public static final String PROPERTY_ITEM_PRICE = "itemPrice";
  public static final String PROPERTY_TOTAL_PRICE = "totalPrice";
  public static final String PROPERTY_PROFIT = "profit";
  public static final String PROPERTY_PAYMENT = "payment";
  public static final String PROPERTY_VENDOR_ID = "vendorId";

  // Fields *****************************************************************

  private Integer id;
  private Integer itemQuantity;
  private Integer itemNumber;
  private Double itemPrice;
  private Double totalPrice;
  private Double profit;
  private Double payment;
  private Integer vendorId;

  // Instance Creation ******************************************************

  /**
   * Default constructor; creates a new {@link PurchaseBean} with default (empty
   * (String), zero (Integer) or false(Boolean)) values for the attributes.
   */
  public PurchaseBean() {
    this(null, null, null, null, null, null, null);
  }

  /**
   * Constructor, creates a new {@link PurchaseBean} with the given attributes and
   * default (empty (String), zero (Integer) or false(Boolean)) values for the
   * remaining attributes.
   * 
   * @param itemQuantity
   *          the count of items
   * @param itemNumber
   *          the article number
   * @param itemPrice
   *          the price for a single item
   * @param totalPrice
   *          the total price (itemQuantity * itemPrice)
   * @param profit
   *          the profit
   * @param payment
   *          the payment amount
   * @param vendorId
   *          the vendor id
   */
  public PurchaseBean(Integer itemQuantity, Integer itemNumber, Double itemPrice, Double totalPrice, Double profit,
      Double payment, Integer vendorId) {
    this(null, itemQuantity, itemNumber, itemPrice, totalPrice, profit, payment, vendorId);
  }

  /**
   * Constructor; creates a new {@link PurchaseBean} with the given attributes.
   * 
   * @param id
   *          the id
   * @param itemQuantity
   *          the count of items
   * @param itemNumber
   *          the article number
   * @param itemPrice
   *          the price for a single item
   * @param totalPrice
   *          the total price (itemQuantity * itemPrice)
   * @param profit
   *          the profit
   * @param payment
   *          the payment amount
   * @param vendorId
   *          the vendor id
   */
  public PurchaseBean(Integer id, Integer itemQuantity, Integer itemNumber, Double itemPrice, Double totalPrice,
      Double profit, Double payment, Integer vendorId) {
    super();
    this.id = id;
    this.itemQuantity = itemQuantity;
    this.itemNumber = itemNumber;
    this.itemPrice = itemPrice;
    this.totalPrice = totalPrice;
    this.profit = profit;
    this.payment = payment;
    this.vendorId = vendorId;
  }

  // Accessors **************************************************************

  public Integer getId() {
    return id;
  }

  public void setId(Integer newValue) {
    Integer oldValue = getId();
    id = newValue;
    firePropertyChange(PROPERTY_ID, oldValue, newValue);
  }

  public Integer getItemQuantity() {
    return itemQuantity;
  }

  public void setItemQuantity(Integer newValue) {
    Integer oldValue = getItemQuantity();
    itemQuantity = newValue;
    firePropertyChange(PROPERTY_ITEM_QUANTITY, oldValue, newValue);
  }

  public Integer getItemNumber() {
    return itemNumber;
  }

  public void setItemNumber(Integer newValue) {
    Integer oldValue = getItemNumber();
    itemNumber = newValue;
    firePropertyChange(PROPERTY_ITEM_NUMBER, oldValue, newValue);
  }

  public Double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(Double newValue) {
    Double oldValue = getItemPrice();
    itemPrice = newValue;
    firePropertyChange(PROPERTY_ITEM_PRICE, oldValue, newValue);
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double newValue) {
    Double oldValue = getTotalPrice();
    totalPrice = newValue;
    firePropertyChange(PROPERTY_TOTAL_PRICE, oldValue, newValue);
  }

  public Double getProfit() {
    return profit;
  }

  public void setProfit(Double newValue) {
    Double oldValue = getProfit();
    profit = newValue;
    firePropertyChange(PROPERTY_PROFIT, oldValue, newValue);
  }

  public Double getPayment() {
    return payment;
  }

  public void setPayment(Double newValue) {
    Double oldValue = getPayment();
    payment = newValue;
    firePropertyChange(PROPERTY_PAYMENT, oldValue, newValue);
  }

  public Integer getVendorId() {
    return vendorId;
  }

  public void setVendorId(Integer newValue) {
    Integer oldValue = getVendorId();
    vendorId = newValue;
    firePropertyChange(PROPERTY_VENDOR_ID, oldValue, newValue);
  }

}
