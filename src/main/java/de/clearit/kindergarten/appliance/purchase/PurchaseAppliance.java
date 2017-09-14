package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.spec.MenuSpec;
import com.jgoodies.desktop.spec.NavigationBarSpec;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;

/**
 * The appliance for the purchase.
 */
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

  // Contributions for the Application New Menu *****************************

  public void addNewMenuItems(MenuSpec spec) {
    spec.add(PurchaseHomeModel.getInstance().getActionMap(), PurchaseHomeModel.ACTION_NEW_PURCHASE);
  }

  // Implementing Abstract Behavior *****************************************

  /**
   * Returns the desktop frame that becomes visible on appliance activation. This
   * is an object based hub page.
   */
  @Override
  protected DesktopFrame createHomeFrame() {
    PurchaseHomeModel model = PurchaseHomeModel.getInstance();
    PurchaseHomeView view = PurchaseHomeView.getInstance();

    DesktopFrame parent = null;
    NavigationBarSpec navigationSpec = null;
    JComponent statusPane = null;

    return new DefaultDesktopFrame(parent, RESOURCES.getString("purchaseHome.title"), false, this, null, model
        .contextSpec(), navigationSpec, view.getPanel(), statusPane);
  }

}
