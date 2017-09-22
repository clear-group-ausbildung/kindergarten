package de.clearit.kindergarten.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Purchase resource.
 */
@JsonIgnoreProperties({ "id", "propertyChangeListeners", "vetoableChangeListeners" })
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((itemNumber == null) ? 0 : itemNumber.hashCode());
    result = prime * result + ((itemPrice == null) ? 0 : itemPrice.hashCode());
    result = prime * result + ((vendorNumber == null) ? 0 : vendorNumber.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PurchaseBean other = (PurchaseBean) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (itemNumber == null) {
      if (other.itemNumber != null)
        return false;
    } else if (!itemNumber.equals(other.itemNumber))
      return false;
    if (itemPrice == null) {
      if (other.itemPrice != null)
        return false;
    } else if (!itemPrice.equals(other.itemPrice))
      return false;
    if (vendorNumber == null) {
      if (other.vendorNumber != null)
        return false;
    } else if (!vendorNumber.equals(other.vendorNumber))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PurchaseBean [id=");
    builder.append(id);
    builder.append(", itemNumber=");
    builder.append(itemNumber);
    builder.append(", itemPrice=");
    builder.append(itemPrice);
    builder.append(", vendorNumber=");
    builder.append(vendorNumber);
    builder.append("]");
    return builder.toString();
  }

}
