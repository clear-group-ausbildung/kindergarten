package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.CollectionUtils;

import com.jgoodies.application.Action;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.pane.form.FormPaneModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.uif2.application.UIFPresentationModel;
import com.jgoodies.uif2.util.TextComponentUtils;
import com.jgoodies.validation.ValidationResult;

import de.clearit.kindergarten.application.Dialogs;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorNumberService;
import de.clearit.kindergarten.domain.validation.VendorValidatable;
import de.clearit.kindergarten.utils.CollectorUtils;
import de.clearit.validation.view.ValidationSupport;

/**
 * The editor model for the vendor.
 */
public final class VendorEditorModel extends UIFPresentationModel<VendorBean> implements FormPaneModel {

  private static final long serialVersionUID = 1L;

  // Constants **************************************************************

  public static final String ACTION_ADD_VENDOR_NUMBER = "addVendorNumber";
  public static final String ACTION_REMOVE_VENDOR_NUMBER = "removeVendorNumber";

  // Instance Fields ********************************************************

  private final transient CommitCallback<CommandValue> commitCallback;
  private final transient ValidationSupport validationSupport;
  private transient SelectionInList<VendorNumberBean> selectionInList;
  private final transient ValueModel vendorNumberFieldModel = new ValueHolder();

  // Instance Creation ******************************************************

  public VendorEditorModel(VendorBean vendor, CommitCallback<CommandValue> callback) {
    super(vendor);
    this.commitCallback = callback;
    this.validationSupport = new ValidationSupport(new VendorValidatable(vendor));
    initModels();
    initPresentationLogic();
  }

  // Initialisation *********************************************************

  private void initModels() {
    selectionInList = new SelectionInList<>();
    selectionInList.getList().addAll(getBean().getVendorNumbers());
    if (CollectionUtils.isNotEmpty(selectionInList.getList())) {
      selectionInList.setSelectionIndex(0);
    }
    handleSelectionChange(selectionInList.hasSelection());
  }

  private void initPresentationLogic() {
    addBeanPropertyChangeListener(validationSupport.delayedValidationHandler());
    getSelectionInList().addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION, new SelectionChangeHandler(
        this));
  }

  // Models *****************************************************************

  public SelectionInList<VendorNumberBean> getSelectionInList() {
    return selectionInList;
  }

  private VendorNumberBean getSelection() {
    return getSelectionInList().getSelection();
  }

  public ValidationSupport getValidationSupport() {
    return validationSupport;
  }

  public ValueModel getVendorNumberFieldModel() {
    return vendorNumberFieldModel;
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
  public void addVendorNumber(ActionEvent e) {
    TextComponentUtils.commitImmediately();
    if (getVendorNumberFieldModel().getValue() != null) {
      // Check for vendor numbers in current model
      if (vendorNumberAlreadyExists(Integer.valueOf((String) getVendorNumberFieldModel().getValue()))) {
        JOptionPane.showMessageDialog(new JFrame(), "Verk\u00e4ufernummer bereits vorhanden!");
        // Reset input field
        getVendorNumberFieldModel().setValue(null);
        return;
      }

      if (!VendorNumberService.getInstance().isVendorNumberExisting(Integer.valueOf((String) getVendorNumberFieldModel()
          .getValue()))) {
        VendorNumberBean vendorNumberBean = new VendorNumberBean();
        // Read Vendor number from input field
        vendorNumberBean.setVendorNumber(Integer.valueOf((String) getVendorNumberFieldModel().getValue()));
        // Add to model list
        getSelectionInList().getList().add(vendorNumberBean);
      } else {
        JOptionPane.showMessageDialog(new JFrame(), "Verk\u00e4ufernummer bereits vorhanden!");
      }
      Collections.sort(getBean().getVendorNumbers(), Comparator.<VendorNumberBean>comparingInt(
          VendorNumberBean::getVendorNumber));
      Collections.sort(getSelectionInList().getList(), Comparator.<VendorNumberBean>comparingInt(
          VendorNumberBean::getVendorNumber));
      // Reset input field
      getVendorNumberFieldModel().setValue(null);
    }
  }

  @Action(enabled = false)
  public void removeVendorNumber(ActionEvent e) {
    VendorNumberBean vendorNumberBean = getSelection();
    // Remove from model list
    getSelectionInList().getList().remove(vendorNumberBean);
  }

  // FormPaneModel Implementation *******************************************

  @Override
  public void performAccept(EventObject e) {
    TextComponentUtils.commitImmediately();
    getBean().getVendorNumbers().clear();
    getBean().getVendorNumbers().addAll(selectionInList.getList());
    validationSupport.setValidatable(new VendorValidatable(fromCurrentValues()));
    ValidationResult result = validationSupport.getResult();
    if (!result.hasErrors()) {
      triggerCommit();
      commitCallback.committed(CommandValue.OK);
      JSDLUtils.closePaneFor(e);
      return;
    }
    Dialogs.vendorHasErrors(e, result);
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
    if (!isChanged() && !isBuffering()) { // Test for searching
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

  @Override
  public void release() {
    removeBeanPropertyChangeListener(validationSupport.delayedValidationHandler());
    setBean(null);
  }

  private VendorBean fromCurrentValues() {
    VendorBean bean = new VendorBean();
    bean.setFirstName((String) getBufferedValue(VendorBean.PROPERTY_FIRST_NAME));
    bean.setLastName((String) getBufferedValue(VendorBean.PROPERTY_LAST_NAME));
    bean.setPhoneNumber((String) getBufferedValue(VendorBean.PROPERTY_PHONE_NUMBER));
    bean.getVendorNumbers().addAll(selectionInList.getList());
    return bean;
  }

  private boolean vendorNumberAlreadyExists(Integer newVendorNumber) {
    try {
      selectionInList.getList().stream().filter(element -> element.getVendorNumber() == newVendorNumber).collect(
          CollectorUtils.singletonCollector());
    } catch (IllegalStateException e) {
      return false;
    }
    return true;
  }

  // Event Handlers *********************************************************

  private static final class SelectionChangeHandler implements PropertyChangeListener {

    private final VendorEditorModel model;

    SelectionChangeHandler(final VendorEditorModel model) {
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