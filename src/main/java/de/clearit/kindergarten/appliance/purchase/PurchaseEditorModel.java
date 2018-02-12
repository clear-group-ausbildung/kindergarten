package de.clearit.kindergarten.appliance.purchase;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.CollectionUtils;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;
import com.jgoodies.jsdl.core.pane.form.FormPaneModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.uif2.application.UIFPresentationModel;
import com.jgoodies.uif2.util.TextComponentUtils;

import de.clearit.kindergarten.application.Dialogs;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;

public class PurchaseEditorModel extends UIFPresentationModel<PurchaseBean> implements FormPaneModel {

  private static final String REGEX_NUMERIC = "^(?=\\d*[1-9])\\d+$";
  private static final long serialVersionUID = 1L;
  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorModel.class);
  private static final PurchaseService SERVICE = PurchaseService.getInstance();
  private static final int MODIFIER_FIRED_BY_MOUSE = 16;
  private static final int MODIFIER_FIRED_BY_ENTER = 0;

  // Constants **************************************************************

  public static final String ACTION_ADD_LINE_ITEM = "addLineItem";
  public static final String ACTION_REMOVE_LINE_ITEM = "removeLineItem";

  // Instance Fields ********************************************************

  private final transient CommitCallback<CommandValue> commitCallback;
  private transient SelectionInList<PurchaseBean> selectionInList;
  private final transient ValueModel itemCountModel = new ValueHolder(0);
  private final transient ValueModel itemSumModel = new ValueHolder(0.0);

  // Instance Creation ******************************************************

  public PurchaseEditorModel(final PurchaseBean purchase, final CommitCallback<CommandValue> callback) {
    super(purchase);
    this.commitCallback = callback;
    initModels();
    initPresentationLogic();
  }

  // Initialisation *********************************************************

  private void initModels() {
    selectionInList = new SelectionInList<>();
    if (CollectionUtils.isNotEmpty(selectionInList.getList())) {
      selectionInList.setSelectionIndex(0);
    }
    handleSelectionChange(selectionInList.hasSelection());
  }

  private void initPresentationLogic() {
    getSelectionInList().addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION, new SelectionChangeHandler(
        this));
  }

  // Models *****************************************************************

  public SelectionInList<PurchaseBean> getSelectionInList() {
    return selectionInList;
  }

  private PurchaseBean getSelection() {
    return getSelectionInList().getSelection();
  }

  public ValueModel getItemCountModel() {
    return itemCountModel;
  }

  public ValueModel getItemSumModel() {
    return itemSumModel;
  }

  // Event Handling *********************************************************

  private void handleSelectionChange(final boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
  }

  private void handleSelectionChangeEditDelete(final boolean hasSelection) {
    setActionEnabled(ACTION_REMOVE_LINE_ITEM, hasSelection);
  }

  // Actions ****************************************************************

  @Action
  public void addLineItem(final ActionEvent e) {

    TextComponentUtils.commitImmediately();
    triggerCommit();

    if (checkBeanContent()) {
      getSelectionInList().getList().add(getBean());
      refreshSummary();
      setBean(new PurchaseBean());
    }
  }

  // Check Bean Not Null ******************************************************
  private Boolean checkBeanContent() {
    boolean state = false;

    Pattern p = Pattern.compile("\\d+$");

    String vendorNumber = PurchaseAppliance.getInstance().getView().getVendorNumber().getText();
    String itemNumber = PurchaseAppliance.getInstance().getView().getItemNumber().getText();
    String itemPrice = PurchaseAppliance.getInstance().getView().getItemPrice().getText();

    Matcher match = p.matcher(itemPrice);

    if (vendorNumber.matches(REGEX_NUMERIC) && itemNumber.matches(REGEX_NUMERIC) && match.find()) {
      state = true;
      PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocus();
    } else {
      JOptionPane.showMessageDialog(new JFrame(), "Falsche Eingabe. Bitte alle Felder richtig befuellen!");
      if (!vendorNumber.matches(REGEX_NUMERIC)) {
        PurchaseAppliance.getInstance().getView().setVendorNumber(null);
      }
      if (!itemNumber.matches(REGEX_NUMERIC)) {
        PurchaseAppliance.getInstance().getView().setItemNumber(null);
        if (vendorNumber.matches(REGEX_NUMERIC)) {
          PurchaseAppliance.getInstance().getView().getItemNumber().requestFocus();
        }
      }
      if (!match.find()) {
        PurchaseAppliance.getInstance().getView().setItemPrice(null);
        if (itemNumber.matches(REGEX_NUMERIC) && vendorNumber.matches(REGEX_NUMERIC)) {
          PurchaseAppliance.getInstance().getView().getItemPrice().requestFocus();
        }
      }
    }
    return state;
  }

  @Action(enabled = false)
  public void removeLineItem(final ActionEvent e) {
    final PurchaseBean purchase = getSelection();
    final String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", "Artikel-Nr: " + purchase
        .getItemNumber() + ", Verk\u00e4fernummer: " + purchase.getVendorNumber());
    final TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    if (pane.getCommitValue() == CommandValue.YES) {
      getSelectionInList().getList().remove(purchase);
      refreshSummary();
    }
  }

  // FormPaneModel Implementation *******************************************

  @Override
  public void performAccept(final EventObject e) {

    if (e instanceof ActionEvent) {
      ActionEvent event = (ActionEvent) e;
      if (MouseEvent.BUTTON1_MASK == event.getModifiers()) {
        TextComponentUtils.commitImmediately();
        triggerCommit();
        getSelectionInList().getList().forEach(SERVICE::create);
        commitCallback.committed(CommandValue.OK);
        JSDLUtils.closePaneFor(e);
      } else if (MODIFIER_FIRED_BY_ENTER == event.getModifiers()) {
        addLineItem(event);
      }
    }
  }

  @Override
  public String getAcceptText() {
    return RESOURCES.getString("editorModel.acceptText");
  }

  @Override
  public void performApply(final EventObject e) {
    // Do nothing.
  }

  @Override
  public void performCancel(final EventObject e) {
    paneClosing(e, CloseRequestHandler.NO_OPERATION);
  }

  @Override
  public void paneClosing(final EventObject e, final Runnable operation) {
    final Runnable cancelOp = new WrappedOperation(commitCallback, CommandValue.CANCEL, operation);
    TextComponentUtils.commitImmediately();
    if (!isChanged() && !isBuffering()) { // Test for searching
      System.out.println("Nichts geaendert");
      cancelOp.run();
      return;
    }
    final String objectName = "Artikel-Nr: " + getBean().getItemNumber();
    final Object commitValue = Dialogs.showUnsavedChangesDialog(e, objectName);
    if (commitValue == CommandValue.CANCEL) {
      return;
    }
    if (commitValue == CommandValue.DONT_SAVE) {
      cancelOp.run();
      return;
    }
    final Runnable acceptOp = new WrappedOperation(commitCallback, CommandValue.OK, operation);
    acceptOp.run();
    // Eigentlich: executeOnSearchEnd(new ValidateAndSaveTask(acceptOp));
  }

  @Override
  public boolean isApplyVisible() {
    return false;
  }

  @Override
  public boolean isApplyEnabled() {
    return false;
  }

  private void refreshSummary() {
    itemCountModel.setValue(SERVICE.getItemCountByPurchases(selectionInList.getList()));
    itemSumModel.setValue(SERVICE.getItemSumByPurchases(selectionInList.getList()));
  }

  // Event Handlers *********************************************************

  private static final class SelectionChangeHandler implements PropertyChangeListener {

    private final PurchaseEditorModel model;

    SelectionChangeHandler(final PurchaseEditorModel model) {
      this.model = model;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
      model.handleSelectionChange(model.getSelectionInList().hasSelection());
    }
  }

  // Helper Code ************************************************************

  private final class WrappedOperation implements Runnable {

    private final CommitCallback<CommandValue> commitCallback;
    private final CommandValue commitValue;
    private final Runnable postOperation;

    WrappedOperation(final CommitCallback<CommandValue> commitCallback, final CommandValue commitValue,
        final Runnable postOperation) {
      this.commitCallback = commitCallback;
      this.commitValue = commitValue;
      this.postOperation = postOperation;
    }

    @Override
    public void run() {
      if (commitValue == CommandValue.OK) {
        triggerCommit();
      } else {
        triggerFlush();
      }
      commitCallback.committed(commitValue);
      DesktopManager.closeActiveFrame();
      postOperation.run();
    }
  }

}
