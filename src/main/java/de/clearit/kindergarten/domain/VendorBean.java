package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.Model;

/**
 * The bean class for the Vendor resource.
 */
public final class VendorBean extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_FIRST_NAME = "firstName";
  public static final String PROPERTY_LAST_NAME = "lastName";
  public static final String PROPERTY_PHONE_NUMBER = "phoneNumber";
  public static final String PROPERTY_VENDOR_NUMBERS = "vendorNumbers";

  // Fields *****************************************************************

  private Integer id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private List<VendorNumberBean> vendorNumbers;

  // Instance Creation ******************************************************

  /**
   * Default constructor; creates a new {@link VendorBean} with default (empty
   * (String), zero (Integer) or false(Boolean)) values for the attributes. Also
   * initialises the vendor numbers with an empty list.
   */
  public VendorBean() {
    this(0, "", "", "", new ArrayList<>());
  }

  /**
   * Constructor, creates a new {@link VendorBean} with the given Attributes.
   *
   * @param id
   *          the id
   * @param firstName
   *          the first name
   * @param lastName
   *          the last name
   * @param phoneNumber
   *          the phone number
   * @param vendorNumbers
   *          the list of vendor numbers
   */
  public VendorBean(Integer id, String firstName, String lastName, String phoneNumber,
      List<VendorNumberBean> vendorNumbers) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.vendorNumbers = vendorNumbers;
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

  public List<VendorNumberBean> getVendorNumbers() {
    return vendorNumbers;
  }

  public void setVendorNumbers(List<VendorNumberBean> newValue) {
    List<VendorNumberBean> oldValue = getVendorNumbers();
    vendorNumbers = newValue;
    firePropertyChange(PROPERTY_VENDOR_NUMBERS, oldValue, newValue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((vendorNumbers == null) ? 0 : vendorNumbers.hashCode());
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
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
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
    if (vendorNumbers == null) {
      return other.vendorNumbers == null;
    } else return vendorNumbers.equals(other.vendorNumbers);
  }

  @Override
  public String toString() {
    return "VendorBean [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
        + phoneNumber + ", vendorNumbers=" + vendorNumbers + "]";
  }

}
