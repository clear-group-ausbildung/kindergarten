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
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;

/**
 * The home model for the purchase.
 */
public class PurchaseHomeModel extends AbstractHomeModel<PurchaseBean> {

  private static final long serialVersionUID = 1L;

  public static final String ACTION_NEW_PURCHASE = "newPurchase";
  public static final String ACTION_IMPORT_PURCHASES = "importPurchases";
  public static final String ACTION_EXPORT_PURCHASES = "exportPurchases";
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
  protected ListModel<PurchaseBean> getListModel() {
    return PurchaseService.getInstance().getListModel();
  }

  // Presentation Logic *****************************************************

  @Override
  protected void handleSelectionChange(boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
  }

  // Actions ****************************************************************

  @Override
  protected String[] contextActionNames() {
    return new String[] {};
  }

  @Action
  public void newItem(ActionEvent e) {
    String title = RESOURCES.getString("newPurchase.title");
    editItem(e, title, new PurchaseBean(), true);
  }

  @Action
  public void newPurchase(ActionEvent e) {
    newItem(e);
  }

  @Action(enabled = false)
  public void editItem(ActionEvent e) {
    String title = RESOURCES.getString("editPurchase.title");
    editItem(e, title, getSelection(), false);
  }

  private void editItem(EventObject e, String title, final PurchaseBean purchase, final boolean newItem) {
    PurchaseEditorModel model = new PurchaseEditorModel(purchase, new CommitCallback<CommandValue>() {
      @Override
      public void committed(CommandValue value) {
        if (newItem && (value == CommandValue.OK)) {
          PurchaseService.getInstance().create(purchase);
        }
      }
    });
    PurchaseAppliance.getInstance().openPurchaseEditor(title, model, newItem);
  }

  @Action(enabled = false)
  public void deleteItem(ActionEvent e) {
    PurchaseBean purchase = getSelection();
    String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", "Artikel-Nr: " + purchase
        .getItemNumber());
    TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    if (pane.getCommitValue() == CommandValue.YES) {
      PurchaseService.getInstance().delete(purchase);
    }
  }

  @Action
  public void importPurchases(ActionEvent e) {
    LOGGER.fine("Importing purchase\u2026");
  }

  @Action
  public void exportPurchases(ActionEvent e) {
    LOGGER.fine("Exporting purchase\u2026");
  }

}
