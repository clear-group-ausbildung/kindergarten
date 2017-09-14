package de.clearit.kindergarten.domain;

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
  public static final String PROPERTY_DELIVERED = "delivered";
  public static final String PROPERTY_DIRTY = "dirty";
  public static final String PROPERTY_FETCHED = "fetched";
  public static final String PROPERTY_RECEIVED_MONEY = "receivedMoney";

  // Fields *****************************************************************

  private Integer id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Boolean delivered;
  private Boolean dirty;
  private Boolean fetched;
  private Boolean receivedMoney;

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
    this(0, firstName, lastName, "", Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
  }

  /**
   * Constructor; creates a new {@link VendorBean} with the given attributes.
   * 
   * @param id
   *          the id
   * @param firstName
   *          the first name
   * @param lastName
   *          the last name
   * @param phoneNumber
   *          the phone number
   * @param delivered
   *          was delivered? true / false
   * @param dirty
   *          was dirty? true / false
   * @param fetched
   *          was fetched? true / false
   * @param receivedMoney
   *          did receive money? true / false
   */
  public VendorBean(Integer id, String firstName, String lastName, String phoneNumber, Boolean delivered, Boolean dirty,
      Boolean fetched, Boolean receivedMoney) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.delivered = delivered;
    this.dirty = dirty;
    this.fetched = fetched;
    this.receivedMoney = receivedMoney;
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

  public Boolean getDelivered() {
    return delivered;
  }

  public void setDelivered(Boolean newValue) {
    Boolean oldValue = getDelivered();
    delivered = newValue;
    firePropertyChange(PROPERTY_DELIVERED, oldValue, newValue);
  }

  public Boolean getDirty() {
    return dirty;
  }

  public void setDirty(Boolean newValue) {
    Boolean oldValue = getDirty();
    dirty = newValue;
    firePropertyChange(PROPERTY_DIRTY, oldValue, newValue);
  }

  public Boolean getFetched() {
    return fetched;
  }

  public void setFetched(Boolean newValue) {
    Boolean oldValue = getFetched();
    fetched = newValue;
    firePropertyChange(PROPERTY_FETCHED, oldValue, newValue);
  }

  public Boolean getReceivedMoney() {
    return receivedMoney;
  }

  public void setReceivedMoney(Boolean newValue) {
    Boolean oldValue = getReceivedMoney();
    receivedMoney = newValue;
    firePropertyChange(PROPERTY_RECEIVED_MONEY, oldValue, newValue);
  }

}