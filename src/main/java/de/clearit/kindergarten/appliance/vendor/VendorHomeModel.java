package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ListModel;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.BlockingScope;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.application.Task;
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
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorNumberService;
import de.clearit.kindergarten.domain.VendorService;

/**
 * The home model for the vendor.
 */
public final class VendorHomeModel extends AbstractHomeModel<VendorBean> {

  private static final long serialVersionUID = 1L;

  public static final String ACTION_PRINT_RECEIPT = "printReceipt";
  public static final String ACTION_PRINT_MULTIPLE_RECEIPTS = "printMultipleReceipts";
  public static final String ACTION_PRINT_ALL_RECEIPTS = "printAllReceipts";

  private static final Logger LOGGER = Logger.getLogger(VendorHomeModel.class.getName());
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeModel.class);
  private static final VendorService SERVICE = VendorService.getInstance();
  private static final VendorNumberService NUMBERSERVICE = VendorNumberService.getInstance();
  private static VendorHomeModel instance;
  
  private List<VendorNumberBean> vendorNumberBeansList;  

  // Instance Creation ******************************************************

  private VendorHomeModel() {
    super();
  }

  public static VendorHomeModel getInstance() {
    if (instance == null) {
      instance = new VendorHomeModel();
    }
    return instance;
  }

  // Initialization *********************************************************

  @Override
  protected ListModel<?> getListModel() {
    return SERVICE.getListModel();
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
    vendorNumberBeansList = new ArrayList<VendorNumberBean>();
    editItem(title, new VendorBean(), true);
  }

  @Action(enabled = false)
  public void editItem(ActionEvent e) {
    String title = RESOURCES.getString("editVendor.title");
    editItem(title, getSelection(), false);
  }
  
  public void setVendorNumberBeansList(List<VendorNumberBean> vendorNumberBeansList) {
	  this.vendorNumberBeansList = vendorNumberBeansList;
  }
  
  
//TODO
	private void editItem(String title, final VendorBean vendor, final boolean newItem) {
    VendorEditorModel model = new VendorEditorModel(vendor, value -> {
      if (value == CommandValue.OK) {
        if (newItem) {
          SERVICE.create(vendor);
          VendorBean bean = SERVICE.findByName(vendor.getFirstName(), vendor.getLastName());
          for(int i = 0; i < vendorNumberBeansList.size(); i++) {
        	  VendorNumberBean vnb = vendorNumberBeansList.get(i);
        	  vnb.setVendorId(bean.getId());
        	  NUMBERSERVICE.create(vnb);
        	  System.out.println(vnb.getVendorId());
          }
          bean.setVendorNumbers(vendorNumberBeansList);
        } else {
          SERVICE.update(vendor);
        }
      }
    });
    VendorAppliance.getInstance().openVendorEditor(title, model);
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
      SERVICE.delete(vendor);
      //TODO and delete vendor numbers
    }
  }

  @Action(enabled = false)
  public Task<Void, Void> printReceipt(ActionEvent e) {
    LOGGER.fine("Printing receipt\u2026");
    return new PrintSingleReceiptTask(getSelection());
  }

  @Action
  public void printMultipleReceipts(ActionEvent e) {
    LOGGER.fine("Printing multiple receipt\u2026");

    VendorNumberChooserModel model = new VendorNumberChooserModel(getSelection(), value -> {
      if (value == CommandValue.OK) {
        // Do nothing specific
        LOGGER.fine("Successfully printed receipts.");
      }
    });

    VendorNumberChooserView view = new VendorNumberChooserView(model);
    DesktopFrame frame = new DefaultDesktopFrame(DesktopManager.activeFrame(), RESOURCES.getString(
        "numberChooser.title"), true, VendorAppliance.getInstance(), null, null, null, view.getPanel(), null);
    frame.setVisible(true);
  }

  @Action
  public Task<Void, Void> printAllReceipts(ActionEvent e) {
    LOGGER.fine("Printing all receipts\u2026");
    return new PrintAllReceiptsTask();
  }

  private final class PrintSingleReceiptTask extends Task<Void, Void> {

    private final TaskPane progressPane;
    private final VendorBean vendor;

    PrintSingleReceiptTask(VendorBean vendor) {
      super(BlockingScope.APPLICATION);
      TaskPane infoPane = new TaskPane(MessageType.INFORMATION, "Der Beleg wird gedruckt.", CommandValue.OK);
      infoPane.setPreferredWidth(PreferredWidth.MEDIUM);
      infoPane.showDialog(getEventObject(), "Belegdruck");
      progressPane = new TaskPane(MessageType.INFORMATION, "Drucke Beleg", CommandValue.OK);
      progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
      progressPane.setProgressIndeterminate(true);
      progressPane.setProgressVisible(true);
      progressPane.setVisible(true);
      this.vendor = vendor;
      ExportExcel.getInstance().createExcelForOneVendor(this.vendor);
    }

    @Override
    protected Void doInBackground() throws Exception {
      return null;
    }

    @Override
    protected void succeeded(Void result) {
      super.succeeded(result);
      progressPane.setVisible(false);
      
      String path = System.getProperty("user.home") + "/Desktop/Basar Abrechnungen";      
      String mainInstruction = RESOURCES.getString("printReceipt.one.main", "Nr. " + vendor.getId() + " " + vendor
          .getLastName() + ", " + vendor.getFirstName(), path);
      TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
      pane.setPreferredWidth(PreferredWidth.MEDIUM);
      pane.showDialog(getEventObject(), RESOURCES.getString("printReceipt.one.title"));
    }

  }

  private final class PrintAllReceiptsTask extends Task<Void, Void> {

    private final TaskPane progressPane;

    PrintAllReceiptsTask() {
      super(BlockingScope.APPLICATION);
      TaskPane infoPane = new TaskPane(MessageType.INFORMATION, "Alle Belege werden gedruckt.", CommandValue.OK);
      infoPane.setPreferredWidth(PreferredWidth.MEDIUM);
      infoPane.showDialog(getEventObject(), "Belegedruck");
      progressPane = new TaskPane(MessageType.INFORMATION, "Drucke alle Belege", CommandValue.OK);
      progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
      progressPane.setProgressIndeterminate(true);
      progressPane.setProgressVisible(true);
      progressPane.setVisible(true);
      ExportExcel.getInstance().createExcelForAllVendors();
    }

    @Override
    protected Void doInBackground() throws Exception {
      return null;
    }

    @Override
    protected void succeeded(Void result) {
      super.succeeded(result);
      progressPane.setVisible(false);
      String path = System.getProperty("user.home") + "/Desktop/Basar Abrechnungen";  
      TaskPane pane = new TaskPane(MessageType.INFORMATION, RESOURCES.getString("printReceipt.all.main", path),
          CommandValue.OK);
      pane.setPreferredWidth(PreferredWidth.MEDIUM);
      pane.showDialog(getEventObject(), RESOURCES.getString("printReceipt.all.title"));
    }

  }

}
