package de.clearit.kindergarten.appliance.purchase;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.List;
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
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorNumberService;

public class PurchaseEditorModel extends UIFPresentationModel<PurchaseBean> implements FormPaneModel {

  private static final String REGEX_NUMERIC = "^(?=\\d*[1-9])\\d+$";
  private static final long serialVersionUID = 1L;
  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorModel.class);
  private static final PurchaseService SERVICE = PurchaseService.getInstance();
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

    Pattern p = Pattern.compile("^[0-9,;]+$");

    String vendorNumber = PurchaseAppliance.getInstance().getView().getVendorNumber().getText();
    String itemNumber = PurchaseAppliance.getInstance().getView().getItemNumber().getText();
    String itemPrice = PurchaseAppliance.getInstance().getView().getItemPrice().getText();
    List<PurchaseBean> allPurchaseBeans = PurchaseService.getInstance().getAll();
    List<PurchaseBean> newCreatetBeans = getSelectionInList().getList();
    List<VendorNumberBean> allVendorNumberBeans = VendorNumberService.getInstance().getAll();
    Matcher match = p.matcher(itemPrice);
    if(vendorNumber.equals("") && itemNumber.equals("") && !match.matches()) {
    	//Close View by Pressing Enter in the Editor View
    	TextComponentUtils.commitImmediately();
        triggerCommit();
        getSelectionInList().getList().forEach(SERVICE::create);
        commitCallback.committed(CommandValue.OK);
    	final Runnable acceptOp = new WrappedOperation(commitCallback, CommandValue.OK, CloseRequestHandler.NO_OPERATION);
    	acceptOp.run();
    }else {
    	 if (vendorNumber.matches(REGEX_NUMERIC) && itemNumber.matches(REGEX_NUMERIC) && match.matches()) {
   	      state = false;
   	      PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
   	      for (int i = 0; i < allPurchaseBeans.size(); i++) {
   		    if(vendorNumber.equals(allPurchaseBeans.get(i).getVendorNumber().toString()) && itemNumber.equals(allPurchaseBeans.get(i).getItemNumber().toString())) {
   		     	JOptionPane.showMessageDialog(new JFrame(), "Dieser Artikel ist bereits vorhanden!\n" + "Verkäufernummer: " + allPurchaseBeans.get(i).getVendorNumber()
   		     			+ " Artikelnummer: " + allPurchaseBeans.get(i).getItemNumber());
   		     PurchaseAppliance.getInstance().getView().setVendorNumber(null);
	    	 PurchaseAppliance.getInstance().getView().setItemNumber(null);
	    	 PurchaseAppliance.getInstance().getView().setItemPrice(null);
		     PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();	
   		     return false;
   		    }
   	      }
   	      for(PurchaseBean purchaseBean : newCreatetBeans) {
   	    	  if(purchaseBean.getVendorNumber().toString().equals(vendorNumber) && purchaseBean.getItemNumber().toString().equals(itemNumber)) {
   	    		  JOptionPane.showMessageDialog(new JFrame(), "Dieser Artikel ist bereits vorhanden!\n" + "Verkäufernummer: "
   	    				  + vendorNumber + " Artikelnummer: " + itemNumber);
   	    		PurchaseAppliance.getInstance().getView().setVendorNumber(null);
   	    		PurchaseAppliance.getInstance().getView().setItemNumber(null);
   	    		PurchaseAppliance.getInstance().getView().setItemPrice(null);
   		        PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
   	    		return false;
   	    	  }
   	      }
   	      for(VendorNumberBean vendorNumberBean : allVendorNumberBeans) {
   	    	  if(vendorNumberBean.getVendorNumber() == Integer.parseInt(vendorNumber) ) {
   	    		  return true;
   	    	  }
   	      }if(!state) {
   	    	JOptionPane.showMessageDialog(new JFrame(), "Die Verkäufernummer (" + vendorNumber + ") auf die Sie diesen Artikel erfassen möchten ist nicht vorhanden!\n " + 
   	    			  "Bitte überprüfen Sie die Verkäufernummer!");
   	    	PurchaseAppliance.getInstance().getView().setVendorNumber(null);
	        PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
   	      }
	    } else {
	      JOptionPane.showMessageDialog(new JFrame(), "Falsche Eingabe. Bitte alle Felder richtig befüllen!");
	      if (!vendorNumber.matches(REGEX_NUMERIC)) {
	        PurchaseAppliance.getInstance().getView().setVendorNumber(null);
	        PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
	      }
	      if (!itemNumber.matches(REGEX_NUMERIC)) {
	        PurchaseAppliance.getInstance().getView().setItemNumber(null);
	        PurchaseAppliance.getInstance().getView().getItemNumber().requestFocusInWindow();
	        if (!vendorNumber.matches(REGEX_NUMERIC)) {
	          PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
	        }
	      }
	      if (!match.matches()) {
	        PurchaseAppliance.getInstance().getView().setItemPrice(null);
	        PurchaseAppliance.getInstance().getView().getItemPrice().requestFocusInWindow();
	        if (!itemNumber.matches(REGEX_NUMERIC)) {
	          PurchaseAppliance.getInstance().getView().getItemNumber().requestFocusInWindow();
	        }
	        if (!vendorNumber.matches(REGEX_NUMERIC)) {
	          PurchaseAppliance.getInstance().getView().getVendorNumber().requestFocusInWindow();
	        }
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
