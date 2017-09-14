package de.clearit.kindergarten.appliance.purchase;

import java.util.EventObject;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.pane.form.FormPaneModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.uif2.application.UIFPresentationModel;
import com.jgoodies.uif2.util.TextComponentUtils;

import de.clearit.kindergarten.application.Dialogs;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

public class PurchaseEditorModel extends UIFPresentationModel<PurchaseBean> implements FormPaneModel {

  private static final long serialVersionUID = 1L;

  // Constants **************************************************************

  private static final long FIFTEEN_SECONDS = 15000;

  // Instance Fields ********************************************************

  private final SelectionInList<VendorBean> vendorList;
  private final CommitCallback<CommandValue> commitCallback;
  private final long creationTime;

  // Instance Creation ******************************************************

  PurchaseEditorModel(PurchaseBean purchase, CommitCallback<CommandValue> callback) {
    super(purchase);
    vendorList = new SelectionInList<>();
    vendorList.getList().addAll(VendorService.getInstance().getAll());
    this.commitCallback = callback;
    this.creationTime = System.currentTimeMillis();
  }

  public SelectionInList<VendorBean> getVendorList() {
    return vendorList;
  }

  // Actions ****************************************************************

  // FormPaneModel Implementation *******************************************

  @Override
  public void performAccept(EventObject e) {
    TextComponentUtils.commitImmediately();
    triggerCommit();
    commitCallback.committed(CommandValue.OK);
    JSDLUtils.closePaneFor(e);
  }

  @Override
  public String getAcceptText() {
    return CommandValue.OK.getMarkedText();
  }

  @Override
  public void performApply(EventObject e) {
    // Do nothing.
  }

  @Override
  public void performCancel(EventObject e) {
    paneClosing(e, CloseRequestHandler.NO_OPERATION);
  }

  @Override
  public void paneClosing(EventObject e, Runnable operation) {
    Runnable cancelOp = new WrappedOperation(commitCallback, CommandValue.CANCEL, operation);
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
    String objectName = getBean().getItemQuantity() + " x " + getBean().getItemNumber();
    Object commitValue = Dialogs.showUnsavedChangesDialog(e, objectName);
    if (commitValue == CommandValue.CANCEL) {
      return;
    }
    if (commitValue == CommandValue.DONT_SAVE) {
      cancelOp.run();
      return;
    }
    Runnable acceptOp = new WrappedOperation(commitCallback, CommandValue.OK, operation);
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

  // Helper Code ************************************************************

  /**
   * Checks and answers whether this model has been created recently. Useful to
   * reduce unnecessary Cancel confirmations, even if this model has been
   * modified.
   *
   * @return {@code true} if the creation time is less than 15 seconds from now
   */
  private boolean quiteYoung() {
    long now = System.currentTimeMillis();
    return now - creationTime < FIFTEEN_SECONDS;
  }

  private final class WrappedOperation implements Runnable {

    private final CommitCallback<CommandValue> commitCallback;
    private final CommandValue commitValue;
    private final Runnable postOperation;

    WrappedOperation(CommitCallback<CommandValue> commitCallback, CommandValue commitValue, Runnable postOperation) {
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
