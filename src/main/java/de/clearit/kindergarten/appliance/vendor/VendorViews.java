package de.clearit.kindergarten.appliance.vendor;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import de.clearit.kindergarten.domain.Vendor;

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
      Vendor vendor = (Vendor) obj;
      toAppendTo.append(vendor.getName());
      return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException("Can't parse.");
    }

  }

}