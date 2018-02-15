package de.clearit.kindergarten.domain.validation;

import org.apache.commons.collections4.CollectionUtils;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.validation.Validatable;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;

import de.clearit.kindergarten.domain.VendorBean;

public class VendorValidatable implements Validatable {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorValidatable.class);

  private final VendorBean vendor;

  // Instance Creation ******************************************************

  public VendorValidatable(VendorBean vendor) {
    this.vendor = vendor;
  }

  // Validatable Implementation *********************************************

  public ValidationResult validate() {
    PropertyValidationSupport support = new PropertyValidationSupport(vendor, "");

    if (CollectionUtils.isEmpty(vendor.getVendorNumbers())) {
      support.addError(VendorBean.PROPERTY_VENDOR_NUMBERS, RESOURCES.getString("vendor.vendornumbers.empty"));
    }

    return support.getResult();
  }

}
