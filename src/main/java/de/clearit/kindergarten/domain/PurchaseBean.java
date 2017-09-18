package de.clearit.kindergarten.domain;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Purchase resource.
 */
public class PurchaseBean extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_ITEM_NUMBER = "itemNumber";
  public static final String PROPERTY_ITEM_PRICE = "itemPrice";
  public static final String PROPERTY_VENDOR_NUMBER = "vendorNumber";

  // Fields *****************************************************************

  private Integer id;
  private Integer itemNumber;
  private Double itemPrice;
  private Integer vendorNumber;

  // Instance Creation ******************************************************

  /**
   * Default constructor; creates a new {@link PurchaseBean} with default (empty
   * (String), zero (Integer) or false(Boolean)) values for the attributes.
   */
  public PurchaseBean() {
    this(null, null, null, null);
  }

  /**
   * Constructor, creates a new {@link PurchaseBean} with the given attributes and
   * default (empty (String), zero (Integer) or false(Boolean)) values for the
   * remaining attributes.
   * 
   * @param itemNumber
   *          the article number
   * @param itemPrice
   *          the price for a single item
   * @param vendorNumber
   *          the vendor number
   */
  public PurchaseBean(Integer itemNumber, Double itemPrice, Integer vendorNumber) {
    this(null, itemNumber, itemPrice, vendorNumber);
  }

  /**
   * Constructor; creates a new {@link PurchaseBean} with the given attributes.
   * 
   * @param id
   *          the id
   * @param itemNumber
   *          the article number
   * @param itemPrice
   *          the price for a single item
   * @param vendorNumber
   *          the vendor number
   */
  public PurchaseBean(Integer id, Integer itemNumber, Double itemPrice, Integer vendorNumber) {
    super();
    this.id = id;
    this.itemNumber = itemNumber;
    this.itemPrice = itemPrice;
    this.vendorNumber = vendorNumber;
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

  public Integer getVendorNumber() {
    return vendorNumber;
  }

  public void setVendorNumber(Integer newValue) {
    Integer oldValue = getVendorNumber();
    vendorNumber = newValue;
    firePropertyChange(PROPERTY_VENDOR_NUMBER, oldValue, newValue);
  }

}
