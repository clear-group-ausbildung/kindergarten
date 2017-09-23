package de.clearit.kindergarten.domain;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Vendor resource.
 */
public final class VendorBean extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_VENDOR_NUMBER = "vendorNumber";
  public static final String PROPERTY_FIRST_NAME = "firstName";
  public static final String PROPERTY_LAST_NAME = "lastName";
  public static final String PROPERTY_PHONE_NUMBER = "phoneNumber";

  // Fields *****************************************************************

  private Integer id;
  private Integer vendorNumber;
  private String firstName;
  private String lastName;
  private String phoneNumber;

  // Instance Creation ******************************************************

  /**
   * Default constructor; creates a new {@link VendorBean} with default (empty
   * (String), zero (Integer) or false(Boolean)) values for the attributes.
   */
  public VendorBean() {
    super();
    this.id = 0;
    this.vendorNumber = null;
    this.firstName = "";
    this.lastName = "";
    this.phoneNumber = "";
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

  public Integer getVendorNumber() {
    return vendorNumber;
  }

  public void setVendorNumber(Integer newValue) {
    Integer oldValue = getVendorNumber();
    vendorNumber = newValue;
    firePropertyChange(PROPERTY_VENDOR_NUMBER, oldValue, newValue);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String newValue) {
    String oldValue = getFirstName();
    firstName = newValue;
    firePropertyChange(PROPERTY_FIRST_NAME, oldValue, newValue);
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String newValue) {
    String oldValue = getLastName();
    lastName = newValue;
    firePropertyChange(PROPERTY_LAST_NAME, oldValue, newValue);
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String newValue) {
    String oldValue = getPhoneNumber();
    phoneNumber = newValue;
    firePropertyChange(PROPERTY_PHONE_NUMBER, oldValue, newValue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
    VendorBean other = (VendorBean) obj;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
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
    return "VendorBean [id=" +
        id +
        ", vendorNumber=" +
        vendorNumber +
        ", firstName=" +
        firstName +
        ", lastName=" +
        lastName +
        ", phoneNumber=" +
        phoneNumber +
        "]";
  }

}