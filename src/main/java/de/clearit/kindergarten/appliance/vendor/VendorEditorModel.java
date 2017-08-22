package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import com.jgoodies.application.Action;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.pane.form.FormPaneModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.uif2.application.UIFPresentationModel;
import com.jgoodies.uif2.util.TextComponentUtils;

import de.clearit.kindergarten.application.Dialogs;
import de.clearit.kindergarten.domain.Vendor;

/**
 * The editor model for the vendor.
 */
public final class VendorEditorModel extends UIFPresentationModel<Vendor> implements FormPaneModel {

  private static final long serialVersionUID = 1L;

  // Action Method Names ****************************************************

  public static final String ACTION_NEW_COUNTRY = "newCountry";

  // Constants **************************************************************

  private static final long FIFTEEN_SECONDS = 15000;

  // Instance Fields ********************************************************

  private final CommitCallback<CommandValue> commitCallback;
  private final long creationTime;

  // Instance Creation ******************************************************

  VendorEditorModel(Vendor vendor, CommitCallback<CommandValue> callback) {
    super(vendor);
    this.commitCallback = callback;
    this.creationTime = System.currentTimeMillis();
  }

  // Actions ****************************************************************

  @Action
  public void newCountry(ActionEvent e) {
    // Country newCountry = ImporterAppliance.getInstance().createCountry(e);
    // if (newCountry != null) {
    // getBean().setCountry(newCountry);
    // }
  }

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
      System.out.println("J�nger als 15 Sekunden.");
      cancelOp.run();
      return;
    }
    TextComponentUtils.commitImmediately();
    if (!isChanged() && !isBuffering()) { // Test for searching
      System.out.println("Nichts ge�ndert");
      cancelOp.run();
      return;
    }
    String objectName = getBean().getName();
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