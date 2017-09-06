package de.clearit.kindergarten.domain;

import com.google.common.base.CaseFormat;
import com.jgoodies.binding.beans.Model;

import de.clearit.kindergarten.domain.entity.Vendor;

/**
 * The bean class for the Vendor entity.
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

  /**
   * Creates a new {@link VendorBean} from the given {@link Vendor} entity.
   * 
   * @param entity
   *          the entity to create the bean from
   * @return the created bean
   */
  public static VendorBean fromEntity(Vendor entity) {
    VendorBean bean = new VendorBean();
    bean.setId(entity.getInteger(PROPERTY_ID));
    bean.setFirstName(entity.getString(toSnakeCase(PROPERTY_FIRST_NAME)));
    bean.setLastName(entity.getString(toSnakeCase(PROPERTY_LAST_NAME)));
    bean.setPhoneNumber(entity.getString(toSnakeCase(PROPERTY_PHONE_NUMBER)));
    bean.setDelivered(entity.getInteger(PROPERTY_DELIVERED) != 0);
    bean.setDirty(entity.getInteger(PROPERTY_DIRTY) != 0);
    bean.setFetched(entity.getInteger(PROPERTY_FETCHED) != 0);
    bean.setReceivedMoney(entity.getInteger(toSnakeCase(PROPERTY_RECEIVED_MONEY)) != 0);
    return bean;
  }

  /**
   * Creates a new {@link Vendor} entity from the given {@link VendorBean}.
   * 
   * @param bean
   *          the bean to create the entity from
   * @return the created entity
   */
  public static Vendor toEntity(VendorBean bean) {
    Vendor entity = Vendor.findById(bean.getId());
    if (entity == null) {
      entity = new Vendor();
    }
    entity.setString(toSnakeCase(PROPERTY_FIRST_NAME), bean.getFirstName());
    entity.setString(toSnakeCase(PROPERTY_LAST_NAME), bean.getLastName());
    entity.setString(toSnakeCase(PROPERTY_PHONE_NUMBER), bean.getPhoneNumber());
    entity.setInteger(PROPERTY_DELIVERED, bean.getDelivered() ? 1 : 0);
    entity.setInteger(PROPERTY_DIRTY, bean.getDirty() ? 1 : 0);
    entity.setInteger(PROPERTY_FETCHED, bean.getFetched() ? 1 : 0);
    entity.setInteger(toSnakeCase(PROPERTY_RECEIVED_MONEY), bean.getReceivedMoney() ? 1 : 0);
    return entity;
  }

  /**
   * Converts the given camelCase String to a snake_case String. The database
   * column names use snake_case names for columns, e.g. {@code first_name}. The
   * bean properties however use camelCase names for properties, e.g.
   * {@code firstName}. So with this method, the property constant names can be
   * used to map to the database columns for property names with multiple words.
   * <p>
   * <b>Example:</b>
   * </p>
   * <p>
   * {@code toSnakeCase("firstName");  // becomes "first_name"}
   * </p>
   * 
   * @param camelCase
   *          the camelCase formatted String to be converted
   * @return the snake_case converted String
   */
  private static String toSnakeCase(String camelCase) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
  }

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

  // Misc API ***************************************************************

  /**
   * Converts {@code this} bean to a {@link Vendor} entity.
   * 
   * @return the converted entity
   */
  public Vendor toEntity() {
    return toEntity(this);
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