package de.clearit.kindergarten.domain;

import com.jgoodies.binding.beans.Model;

/**
 * The model class for the Address entity.
 */
public class Address extends Model {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_LINE1 = "line1";
  public static final String PROPERTY_LINE2 = "line2";
  public static final String PROPERTY_STREET1 = "street1";
  public static final String PROPERTY_STREET2 = "street2";
  public static final String PROPERTY_ZIP_CODE = "zipCode";
  public static final String PROPERTY_CITY = "city";

  private String line1;
  private String line2;
  private String street1;
  private String street2;
  private String zipCode;
  private String city;

  // Instance Creation ******************************************************

  public Address() {
    this("", "", "", "", "", "");
  }

  public Address(String line1, String line2, String street1, String street2, String zipCode, String city) {
    this.street1 = street1;
    this.street2 = street2;
    this.line1 = line1;
    this.line2 = line2;
    this.zipCode = zipCode;
    this.city = city;
  }

  // Accessors **************************************************************

  public String getLine1() {
    return line1;
  }

  public void setLine1(String newValue) {
    String oldValue = getLine1();
    line1 = newValue;
    firePropertyChange(PROPERTY_LINE1, oldValue, newValue);
  }

  public String getLine2() {
    return line2;
  }

  public void setLine2(String newValue) {
    String oldValue = getLine2();
    line2 = newValue;
    firePropertyChange(PROPERTY_LINE2, oldValue, newValue);
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String newValue) {
    String oldValue = getStreet1();
    street1 = newValue;
    firePropertyChange(PROPERTY_STREET1, oldValue, newValue);
  }

  public String getStreet2() {
    return street2;
  }

  public void setStreet2(String newValue) {
    String oldValue = getStreet2();
    street2 = newValue;
    firePropertyChange(PROPERTY_STREET2, oldValue, newValue);
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String newValue) {
    String oldValue = getZipCode();
    zipCode = newValue;
    firePropertyChange(PROPERTY_ZIP_CODE, oldValue, newValue);
  }

  public String getCity() {
    return city;
  }

  public void setCity(String newValue) {
    String oldValue = getCity();
    city = newValue;
    firePropertyChange(PROPERTY_CITY, oldValue, newValue);
  }

}