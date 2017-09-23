package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.jsdl.core.CommandValue;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;
import de.clearit.kindergarten.domain.PurchaseBean;

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

  // Public API *************************************************************

  public void newPurchase(String title, final CommitCallback<PurchaseBean> outerCallback) {
    final PurchaseBean newPurchase = new PurchaseBean();
    final CommitCallback<CommandValue> callback = result -> outerCallback.committed(result == CommandValue.OK ? newPurchase : null);
    PurchaseEditorModel model = new PurchaseEditorModel(newPurchase, callback);
    openPurchaseEditor(title, model);
  }

  public void editPurchase(final String title, final PurchaseBean purchase, final CommitCallback<CommandValue> callback) {
    PurchaseEditorModel model = new PurchaseEditorModel(purchase, callback);
    openPurchaseEditor(title, model);
  }

  void openPurchaseEditor(String title, PurchaseEditorModel model) {
    PurchaseEditorView view = new PurchaseEditorView(model);
    DesktopFrame frame = new DefaultDesktopFrame(DesktopManager.activeFrame(), title, true, PurchaseAppliance
        .getInstance(), null, null, null, view.getPanel(), null);
    frame.setVisible(true);
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
