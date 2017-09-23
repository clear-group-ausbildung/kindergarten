package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.list.SelectionInList;
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
import de.clearit.kindergarten.domain.ExportExcel;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

class VendorNumberChooserModel extends UIFPresentationModel<VendorBean> implements FormPaneModel {

  private static final long serialVersionUID = 1L;

  // Constants **************************************************************
  public static final String ACTION_ADD_VENDOR_NUMBER = "addVendorNumber";
  public static final String ACTION_REMOVE_VENDOR_NUMBER = "removeVendorNumber";

  private static final long FIFTEEN_SECONDS = 15000;
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorNumberChooserModel.class);
  private static final VendorService VENDOR_SERVICE = VendorService.getInstance();

  // Instance Fields ********************************************************

  private final SelectionInList<VendorBean> vendorList;
  private final SelectionInList<VendorBean> selectionInList;
  private final CommitCallback<CommandValue> commitCallback;
  private final long creationTime;

  // Instance Creation ******************************************************

  VendorNumberChooserModel(final VendorBean purchase, final CommitCallback<CommandValue> callback) {
    super(purchase);
    vendorList = new SelectionInList<>();
    vendorList.getList().addAll(VENDOR_SERVICE.getAll());
    selectionInList = new SelectionInList<>();
    this.commitCallback = callback;
    this.creationTime = System.currentTimeMillis();
    initModels();
    initPresentationLogic();
  }

  // Initialization *********************************************************

  private void initModels() {
    if (selectionInList.getList().size() > 0) {
      selectionInList.setSelectionIndex(0);
    }
    handleSelectionChange(selectionInList.hasSelection());
  }

  private void initPresentationLogic() {
    getSelectionInList().addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION, new SelectionChangeHandler(
        this));
  }

  // Models *****************************************************************

  public SelectionInList<VendorBean> getVendorList() {
    return vendorList;
  }

  public SelectionInList<VendorBean> getSelectionInList() {
    return selectionInList;
  }

  private VendorBean getSelection() {
    return getSelectionInList().getSelection();
  }

  // Event Handling *********************************************************

  private void handleSelectionChange(final boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
  }

  private void handleSelectionChangeEditDelete(final boolean hasSelection) {
    setActionEnabled(ACTION_REMOVE_VENDOR_NUMBER, hasSelection);
  }

  // Actions ****************************************************************

  @Action
  public void addVendorNumber(final ActionEvent e) {
    TextComponentUtils.commitImmediately();
    triggerCommit();
    getSelectionInList().getList().add(vendorList.getSelection());
    vendorList.clearSelection();
  }

  @Action(enabled = false)
  public void removeVendorNumber(final ActionEvent e) {
    final VendorBean vendor = getSelection();
    final String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", "Verk\u00e4ufernummer: " + vendor
        .getVendorNumber());
    final TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    if (pane.getCommitValue() == CommandValue.YES) {
      getSelectionInList().getList().remove(vendor);
    }
  }

  @Override
  public void paneClosing(EventObject e, Runnable operation) {
    final Runnable cancelOp = new WrappedOperation(commitCallback, CommandValue.CANCEL, operation);
    if (quiteYoung()) {
      System.out.println("Juenger als 15 Sekunden.");
      cancelOp.run();
      return;
    }
    TextComponentUtils.commitImmediately();
    if (!isChanged() && !isBuffering()) { // Test for searching
      System.out.println("Nichts geaendert");
      cancelOp.run();
      return;
    }
    final String objectName = "Verk\u00e4ufernummer: " + vendorList.getSelection().getVendorNumber();
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
  public String getAcceptText() {
    return RESOURCES.getString("editorModel.acceptText");
  }

  @Override
  public boolean isApplyEnabled() {
    return false;
  }

  @Override
  public boolean isApplyVisible() {
    return false;
  }

  @Override
  public void performAccept(EventObject e) {
    TextComponentUtils.commitImmediately();
    triggerCommit();
    TaskPane infoPane = new TaskPane(MessageType.INFORMATION, "Die ausgew\u00e4hlten Belege werden gedruckt.",
        CommandValue.OK);
    infoPane.setPreferredWidth(PreferredWidth.MEDIUM);
    infoPane.showDialog(e, "Belegedruck");

    List<VendorBean> vendorList = getSelectionInList().getList();
    StringBuilder vendorNumbers = new StringBuilder();
    Iterator<VendorBean> iter = vendorList.iterator();
    while (iter.hasNext()) {
      vendorNumbers.append(iter.next().getVendorNumber());
      if (iter.hasNext()) {
        vendorNumbers.append(" & ");
      }
    }
    ExportExcel.getInstance().createExcelForOneVendorWithMultipleVendorNumbers(vendorList);

    String mainInstruction = RESOURCES.getString("printReceipt.one.main", "Nr. " + vendorNumbers.toString() + " "
        + vendorList.get(0).getLastName() + ", " + vendorList.get(0).getFirstName());
    TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("printReceipt.one.title"));
    commitCallback.committed(CommandValue.OK);
    JSDLUtils.closePaneFor(e);
  }

  @Override
  public void performApply(EventObject e) {
    // Do nothing

  }

  @Override
  public void performCancel(EventObject e) {
    paneClosing(e, CloseRequestHandler.NO_OPERATION);
  }

  // Event Handlers *********************************************************

  private static final class SelectionChangeHandler implements PropertyChangeListener {

    private final VendorNumberChooserModel model;

    SelectionChangeHandler(final VendorNumberChooserModel model) {
      this.model = model;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
      model.handleSelectionChange(model.getSelectionInList().hasSelection());
    }
  }

  // Helper Code ************************************************************

  /**
   * Checks and answers whether this model has been created recently. Useful to
   * reduce unnecessary Cancel confirmations, even if this model has been
   * modified.
   *
   * @return {@code true} if the creation time is less than 15 seconds from now
   */
  private boolean quiteYoung() {
    final long now = System.currentTimeMillis();
    return now - creationTime < FIFTEEN_SECONDS;
  }

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
