package de.clearit.kindergarten.appliance.vendor;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import de.clearit.kindergarten.domain.VendorBean;

/**
 * The views of the vendor appliance.
 */
public final class VendorViews {

  private VendorViews() {
    // Override default constructor; prevents instantiation.
  }

  // API ********************************************************************

  public static Format getCountryFormat() {
    return new VendorFormat();
  }

  // Conversion *************************************************************

  private static final class VendorFormat extends Format {

    private static final long serialVersionUID = 1L;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      VendorBean vendor = (VendorBean) obj;
      toAppendTo.append(vendor.getLastName() + ", " + vendor.getFirstName());
      return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException("Can't parse.");
    }

  }

}