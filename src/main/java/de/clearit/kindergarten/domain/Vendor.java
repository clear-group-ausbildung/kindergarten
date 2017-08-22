package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The model class for the Vendor entity.
 */
public final class Vendor extends Address {

  private static final long serialVersionUID = 1L;

  // Names of the Bound Bean Properties *************************************

  public static final String PROPERTY_CODE = "code";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_TAGS = "tags";
  public static final String PROPERTY_VAT_ID = "vatID";

  public static final String PROPERTY_COMMA_SEPARATED_TAGS = "commaSeparatedTags";

  // Fields *****************************************************************

  private String code;
  private String name;
  private List<String> tags = new ArrayList<String>();
  private String vatID;

  // Instance Creation ******************************************************

  public Vendor() {
    this("", "");
  }

  public Vendor(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public Vendor(String code, String name, String commaSeparatedTags, String vatID, String line1, String line2,
      String street1, String street2, String zip, String city) {
    super(line1, line2, street1, street2, zip, city);
    this.code = code;
    this.name = name;
    setCommaSeparatedTags(commaSeparatedTags);
    this.vatID = vatID;
  }

  // Accessors **************************************************************

  public String getCode() {
    return code;
  }

  public void setCode(String newValue) {
    String oldValue = getCode();
    code = newValue;
    firePropertyChange(PROPERTY_CODE, oldValue, newValue);
  }

  public String getName() {
    return name;
  }

  public void setName(String newValue) {
    String oldValue = getName();
    name = newValue;
    firePropertyChange(PROPERTY_NAME, oldValue, newValue);
  }

  public List<String> getTags() {
    return tags;
  }

  public String getCommaSeparatedTags() {
    StringBuilder builder = new StringBuilder();
    for (String tag : tags) {
      if (builder.length() > 0) {
        builder.append(", ");
      }
      builder.append(tag);
    }
    return builder.toString();
  }

  public void setCommaSeparatedTags(String newValue) {
    String oldValue = getCommaSeparatedTags();
    tags = Arrays.asList(newValue.split(", "));
    firePropertyChange(PROPERTY_COMMA_SEPARATED_TAGS, oldValue, newValue);
  }

  public String getVatID() {
    return vatID;
  }

  public void setVatID(String newValue) {
    String oldValue = getVatID();
    vatID = newValue;
    firePropertyChange(PROPERTY_VAT_ID, oldValue, newValue);
  }

}