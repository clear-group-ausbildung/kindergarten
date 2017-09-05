package de.clearit.kindergarten.appliance.vendor;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.spec.MenuSpec;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.jsdl.core.CommandValue;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The appliance for the vendor.
 */
public final class VendorAppliance extends DefaultAppliance {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorAppliance.class);

  private static VendorAppliance instance;

  // Instance Access ********************************************************

  private VendorAppliance() {
    super("vendor_maintenance", RESOURCES.getString("vendorAppliance.name"), RESOURCES.getString(
        "vendorAppliance.shortName"), RESOURCES.getString("vendorAppliance.quickSearchShortCut"));
  }

  public static VendorAppliance getInstance() {
    if (instance == null) {
      instance = new VendorAppliance();
    }
    return instance;
  }

  // Public API *************************************************************

  public void newVendor(String title, final CommitCallback<VendorBean> outerCallback) {
    final VendorBean newVendor = new VendorBean();
    final CommitCallback<CommandValue> callback = new CommitCallback<CommandValue>() {
      @Override
      public void committed(CommandValue result) {
        outerCallback.committed(result == CommandValue.OK ? newVendor : null);
      }
    };
    VendorEditorModel model = new VendorEditorModel(newVendor, callback);
    openVendorEditor(title, model, true);
  }

  public void editVendor(final String title, final VendorBean vendor, final CommitCallback<CommandValue> callback) {
    VendorEditorModel model = new VendorEditorModel(vendor, callback);
    openVendorEditor(title, model, false);
  }

  void openVendorEditor(String title, VendorEditorModel model, boolean newItem) {
    VendorEditorView view = new VendorEditorView(model);
    DesktopFrame frame = new DefaultDesktopFrame(DesktopManager.activeFrame(), title, true, VendorAppliance
        .getInstance(), null, null, null, view.getPanel(), null);
    frame.setVisible(true);
  }

  // Contributions for the Application New Menu *****************************

  public void addNewMenuItems(MenuSpec spec) {
    spec.add(VendorHomeModel.getInstance().getActionMap(), VendorHomeModel.ACTION_NEW_VENDOR);
  }

  // Implementing Abstract Behavior *****************************************

  /**
   * Returns the desktop frame that becomes visible on appliance activation. This
   * is an object based hub page.
   */
  @Override
  protected DesktopFrame createHomeFrame() {
    VendorHomeModel model = VendorHomeModel.getInstance();
    VendorHomeView view = VendorHomeView.getInstance();

    DesktopFrame parent = null;
    NavigationBarSpec navigationSpec = null;
    JComponent statusPane = null;

    return new DefaultDesktopFrame(parent, RESOURCES.getString("vendorHome.title"), false, this, null, model
        .contextSpec(), navigationSpec, view.getPanel(), statusPane);
  }

  // Conversion *************************************************************

  public static final class ExtrasHTMLFormat extends Format {

    private static final long serialVersionUID = 1L;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      if (obj == null) {
        return toAppendTo;
      }
      VendorBean vendor = (VendorBean) obj;
      toAppendTo.append("<html>");
      toAppendTo.append("Geliefert: " + (vendor.getDelivered() ? "Ja" : "Nein"));
      toAppendTo.append("<br>");
      toAppendTo.append("Dreckig: " + (vendor.getDirty() ? "Ja" : "Nein"));
      toAppendTo.append("<br>");
      toAppendTo.append("Abgeholt: " + (vendor.getFetched() ? "Ja" : "Nein"));
      toAppendTo.append("<br>");
      toAppendTo.append("Geld erhalten: " + (vendor.getReceivedMoney() ? "Ja" : "Nein"));
      toAppendTo.append("</html>");
      return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException("Can't parse.");
    }

  }

  public static final class ExtrasFormat extends Format {

    private static final long serialVersionUID = 1L;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      if (obj == null) {
        return toAppendTo;
      }
      VendorBean vendor = (VendorBean) obj;
      toAppendTo.append("Geliefert: " + (vendor.getDelivered() ? "Ja" : "Nein"));
      toAppendTo.append("\n");
      toAppendTo.append("Dreckig: " + (vendor.getDirty() ? "Ja" : "Nein"));
      toAppendTo.append("\n");
      toAppendTo.append("Abgeholt: " + (vendor.getFetched() ? "Ja" : "Nein"));
      toAppendTo.append("\n");
      toAppendTo.append("Geld erhalten: " + (vendor.getReceivedMoney() ? "Ja" : "Nein"));
      return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException("Can't parse.");
    }

  }

}