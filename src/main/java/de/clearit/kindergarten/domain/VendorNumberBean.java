package de.clearit.kindergarten.domain;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Vendor Number resource.
 */
public final class VendorNumberBean extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_VENDOR_NUMBER = "vendorNumber";
  public static final String PROPERTY_VENDOR_ID = "vendorId";

  // Fields *****************************************************************

  private Integer id;
  private int vendorNumber;
  private int vendorId;

  // Instance Creation ******************************************************

  /**
   * Default constructor; creates a new {@link VendorNumberBean} with default
   * (empty (String), zero (Integer) or false(Boolean)) values for the attributes.
   */
  public VendorNumberBean() {
    super();
    this.id = null;
    this.vendorNumber = 0;
    this.vendorId = 0;
  }

  /**
   * Constructor, creates a new {@link VendorNumberBean} with the given
   * attributes.
   *
   * @param id
   *          the id
   * @param vendorNumber
   *          the vendor number
   * @param vendorId
   *          the vendor id
   */
  public VendorNumberBean(Integer id, int vendorNumber, int vendorId) {
    super();
    this.id = id;
    this.vendorNumber = vendorNumber;
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

  public int getVendorNumber() {
    return vendorNumber;
  }

  public void setVendorNumber(int newValue) {
    int oldValue = getVendorNumber();
    vendorNumber = newValue;
    firePropertyChange(PROPERTY_VENDOR_NUMBER, oldValue, newValue);
  }

  public int getVendorId() {
    return vendorId;
  }

  public void setVendorId(int newValue) {
    int oldValue = getVendorId();
    vendorId = newValue;
    firePropertyChange(PROPERTY_VENDOR_ID, oldValue, newValue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + vendorId;
    result = prime * result + vendorNumber;
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
    VendorNumberBean other = (VendorNumberBean) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (vendorId != other.vendorId)
      return false;
    return vendorNumber == other.vendorNumber;
  }

  @Override
  public String toString() {
    return "VendorNumberBean [id=" + id + ", vendorNumber=" + vendorNumber + ", vendorId=" + vendorId + "]";
  }

}
