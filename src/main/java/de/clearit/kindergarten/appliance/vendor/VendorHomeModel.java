package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.logging.Logger;

import javax.swing.ListModel;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;
import de.clearit.kindergarten.domain.ExportExcel;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

/**
 * The home model for the vendor.
 */
public final class VendorHomeModel extends AbstractHomeModel<VendorBean> {

  private static final long serialVersionUID = 1L;

  public static final String ACTION_NEW_VENDOR = "newVendor";
  public static final String ACTION_PRINT_RECEIPT = "printReceipt";
  public static final String ACTION_PRINT_MULTIPLE_RECEIPTS = "printMultipleReceipts";
  public static final String ACTION_PRINT_ALL_RECEIPTS = "printAllReceipts";
  private static final Logger LOGGER = Logger.getLogger(VendorHomeModel.class.getName());
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeModel.class);
  private static VendorHomeModel instance;

  // Instance Creation ******************************************************

  private VendorHomeModel() {
    super();
  }

  static VendorHomeModel getInstance() {
    if (instance == null) {
      instance = new VendorHomeModel();
    }
    return instance;
  }

  // Initialization *********************************************************

  @Override
  protected ListModel<?> getListModel() {
    return VendorService.getInstance().getListModel();
  }

  // Presentation Logic *****************************************************

  @Override
  protected void handleSelectionChange(boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
    setActionEnabled(ACTION_PRINT_RECEIPT, hasSelection);
  }

  // Actions ****************************************************************

  @Override
  protected String[] contextActionNames() {
    return new String[] {};
  }

  @Action
  public void newItem(ActionEvent e) {
    String title = RESOURCES.getString("newVendor.title");
    editItem(e, title, new VendorBean(), true);
  }

  @Action
  public void newVendor(ActionEvent e) {
    newItem(e);
  }

  @Action(enabled = false)
  public void editItem(ActionEvent e) {
    String title = RESOURCES.getString("editVendor.title");
    editItem(e, title, getSelection(), false);
  }

  private void editItem(EventObject e, String title, final VendorBean vendor, final boolean newItem) {
    VendorEditorModel model = new VendorEditorModel(vendor, new CommitCallback<CommandValue>() {
      @Override
      public void committed(CommandValue value) {
        if (newItem && (value == CommandValue.OK)) {
          VendorService.getInstance().create(vendor);
        }
      }
    });
    VendorAppliance.getInstance().openVendorEditor(title, model, newItem);
  }

  @Action(enabled = false)
  public void deleteItem(ActionEvent e) {
    VendorBean vendor = getSelection();
    String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", vendor.getLastName() + ", " + vendor
        .getFirstName());
    TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    if (pane.getCommitValue() == CommandValue.YES) {
      VendorService.getInstance().delete(vendor);
    }
  }

  // TODO
  // Neue Action einbauen, f�r die wir das nutzen k�nnen:
  // ExportExcel.getInstance().createExcelForOneVendorWithMultipleVendorNumbers(...);

  @Action(enabled = false)
  public void printReceipt(ActionEvent e) {
    LOGGER.fine("Printing receipt\u2026");
    VendorBean vendor = getSelection();
    ExportExcel.getInstance().createExcelForOneVendor(vendor);
    LOGGER.fine("Receipt was printed successfully\\u2026");

    String mainInstruction = RESOURCES.getString("printReceipt.one.main", "Nr. " + vendor.getId() + " " + vendor
        .getLastName() + ", " + vendor.getFirstName());
    TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("printReceipt.one.title"));
  }

  @Action
  public void printMultipleReceipts(ActionEvent e) {
    LOGGER.fine("Printinging multiple receipt\u2026");

    VendorNumberChooserModel model = new VendorNumberChooserModel(getSelection(), new CommitCallback<CommandValue>() {
      @Override
      public void committed(CommandValue value) {
        if (value == CommandValue.OK) {

          // TODO: Marco
          // Hier deine Logik einbauen

        }
      }
    });

    VendorNumberChooserView view = new VendorNumberChooserView(model);
    DesktopFrame frame = new DefaultDesktopFrame(DesktopManager.activeFrame(), RESOURCES.getString(
        "numberChooser.title"), true, VendorAppliance.getInstance(), null, null, null, view.getPanel(), null);
    frame.setVisible(true);
  }

  @Action
  public void printAllReceipts(ActionEvent e) {
    LOGGER.fine("Printing all receipts\u2026");
    ExportExcel.getInstance().createExcelForAllVendors();
    LOGGER.fine("Receipts were printed successfully\\u2026");

    TaskPane pane = new TaskPane(MessageType.INFORMATION, RESOURCES.getString("printReceipt.all.main"),
        CommandValue.OK);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("printReceipt.all.title"));
  }

}
