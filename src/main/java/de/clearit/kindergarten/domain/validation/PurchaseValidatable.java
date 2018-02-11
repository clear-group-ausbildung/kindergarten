package de.clearit.kindergarten.domain.validation;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.validation.Validatable;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchaseValidatable implements Validatable {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseValidatable.class);

  private final PurchaseBean purchase;

  // Instance Creation ******************************************************

  public PurchaseValidatable(PurchaseBean purchase) {
    this.purchase = purchase;
  }

  // Validatable Implementation *********************************************

  public ValidationResult validate() {
    PropertyValidationSupport support = new PropertyValidationSupport(purchase, "");

    if (purchase.getItemNumber() == null || ValidationUtils.isBlank(purchase.getItemNumber().toString())
        || !ValidationUtils.isAlpha(purchase.getItemNumber().toString())) {
      support.addError(PurchaseBean.PROPERTY_ITEM_NUMBER, RESOURCES.getString("purchase.itemnumber.nonnumeric"));
    }

    if (purchase.getItemPrice() == null || ValidationUtils.isBlank(purchase.getItemPrice().toString())) {
      support.addError(PurchaseBean.PROPERTY_ITEM_PRICE, RESOURCES.getString("purchase.itemprice.nonnumeric"));
    }

    if (purchase.getVendorNumber() == null || ValidationUtils.isBlank(purchase.getVendorNumber().toString())
        || !ValidationUtils.isAlpha(purchase.getVendorNumber().toString())) {
      support.addError(PurchaseBean.PROPERTY_VENDOR_NUMBER, RESOURCES.getString("purchase.vendornumber.nonnumeric"));
    }

    return support.getResult();
  }
}
