package de.clearit.kindergarten.appliance.purchase;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopFrame;

import de.clearit.kindergarten.desktop.DefaultAppliance;

public class PurchaseAppliance extends DefaultAppliance {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseAppliance.class);

  private static PurchaseAppliance instance;

  // Instance Access ********************************************************

  private PurchaseAppliance() {
    super("purchase_maintenance", RESOURCES.getString("purchaseAppliance.name"), RESOURCES.getString(
        "purchaseAppliance.shortName"), RESOURCES.getString("purchaseAppliance.quickSearchShortCut"));
  }

  public static PurchaseAppliance getInstance() {
    if (instance == null) {
      instance = new PurchaseAppliance();
    }
    return instance;
  }

  @Override
  protected DesktopFrame createHomeFrame() {
    // TODO Auto-generated method stub
    return null;
  }

}
