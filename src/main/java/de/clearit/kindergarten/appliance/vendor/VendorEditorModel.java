package de.clearit.kindergarten.appliance.vendor;

import java.util.EventObject;

import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.pane.form.FormPaneModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.uif2.application.UIFPresentationModel;
import com.jgoodies.uif2.util.TextComponentUtils;

import de.clearit.kindergarten.application.Dialogs;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The editor model for the vendor.
 */
public final class VendorEditorModel extends UIFPresentationModel<VendorBean> implements FormPaneModel {

  private static final long serialVersionUID = 1L;

  // Instance Fields ********************************************************

  private final CommitCallback<CommandValue> commitCallback;

  // Instance Creation ******************************************************

  public VendorEditorModel(VendorBean vendor, CommitCallback<CommandValue> callback) {
    super(vendor);
    this.commitCallback = callback;
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
    TextComponentUtils.commitImmediately();
    if (!isChanged() && !isBuffering()) { // Test for searching
      System.out.println("Nichts geaendert");
      cancelOp.run();
      return;
    }
    String objectName = getBean().getLastName() + ", " + getBean().getFirstName();
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