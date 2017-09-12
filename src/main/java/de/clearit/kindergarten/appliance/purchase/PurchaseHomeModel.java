package de.clearit.kindergarten.appliance.purchase;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.logging.Logger;
import javax.swing.ListModel;
import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;
import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorBroker;

/**
 * The home model for the vendor.
 */
public final class PurchaseHomeModel extends AbstractHomeModel<PurchaseBean> {

  private static final long serialVersionUID = 1L;

  static final String ACTION_NEW_VENDOR = "newVendor";

  private static final Logger LOGGER = Logger.getLogger(PurchaseHomeModel.class.getName());

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeModel.class);

  private static PurchaseHomeModel instance;

  // Instance Creation ******************************************************

  private PurchaseHomeModel() {
    super();
  }

  static PurchaseHomeModel getInstance() {
    if (instance == null) {
      instance = new PurchaseHomeModel();
    }
    return instance;
  }

  // Initialization *********************************************************

  @Override
  protected ListModel<?> getListModel() {
    return VendorBroker.INSTANCE.getListModel();
  }

  // Presentation Logic *****************************************************

  @Override
  protected void handleSelectionChange(boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
    setActionEnabled(ACTION_PRINT_ITEM, hasSelection);
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
          VendorBroker.INSTANCE.add(vendor);
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
      VendorBroker.INSTANCE.remove(vendor);
    }
  }

  @Action(enabled = false)
  public void printItem(ActionEvent e) {
    LOGGER.fine("Printing vendor\u2026");
  }

}
