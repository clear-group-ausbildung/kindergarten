package de.clearit.kindergarten.domain.validation;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.validation.Validatable;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

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

    if (ValidationUtils.isBlank(vendor.getLastName())) {
      support.addError(VendorBean.PROPERTY_LAST_NAME, RESOURCES.getString("vendor.lastname.blank"));
    }
    if (vendor.getVendorNumbers() == null || vendor.getVendorNumbers().isEmpty()) {
      support.addError(VendorBean.PROPERTY_VENDOR_NUMBERS, RESOURCES.getString("vendor.vendornumbers.empty"));
    }

    return support.getResult();
  }

}
