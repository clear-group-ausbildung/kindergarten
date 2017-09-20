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
    this("", "");
  }

  /**
   * Constructor, creates a new {@link VendorBean} with the given attributes and
   * default (empty (String), zero (Integer) or false(Boolean)) values for the
   * remaining attributes.
   * 
   * @param firstName
   *          the first name
   * @param lastName
   *          the last name
   */
  public VendorBean(String firstName, String lastName) {
    this(0, null, firstName, lastName, "");
  }

  /**
   * Constructor; creates a new {@link VendorBean} with the given attributes.
   * 
   * @param id
   *          the id
   * @param vendorNumber
   *          the vendor number
   * @param firstName
   *          the first name
   * @param lastName
   *          the last name
   * @param phoneNumber
   *          the phone number
   */
  public VendorBean(Integer id, Integer vendorNumber, String firstName, String lastName, String phoneNumber) {
    super();
    this.id = id;
    this.vendorNumber = vendorNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
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

}