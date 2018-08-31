package de.clearit.kindergarten.appliance.accounting;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.ListModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.BlockingScope;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.application.Task;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;
import de.clearit.kindergarten.domain.export.ExportExcel;
import de.clearit.kindergarten.domain.print.PrintingService;

public class AccountHomeModel extends AbstractHomeModel<VendorBean>{
	 private static final long serialVersionUID = 1L;

	  public static final String ACTION_CREATE_RECEIPT = "createReceipt";
	  public static final String ACTION_PRINT_ALL_RECEIPTS = "printAllReceipts";
	  public static final String ACTION_CREATE_PDF_FILES = "createPDFFiles";

	  private static final Logger LOGGER = LoggerFactory.getLogger(AccountHomeModel.class);
	  private static final ResourceMap RESOURCES = Application.getResourceMap(AccountHomeModel.class);
	  private static final VendorService SERVICE = VendorService.getInstance();
	  private static AccountHomeModel instance;

	  // Instance Creation ******************************************************

	  private AccountHomeModel() {
	    super();
	  }

	  public static AccountHomeModel getInstance() {
	    if (instance == null) {
	      instance = new AccountHomeModel();
	    }
	    return instance;
	  }

	  // Initialisation *********************************************************

	  protected ListModel<?> getListModel() {
	    return SERVICE.getListModel();
	  }

	  // Presentation Logic *****************************************************
	  protected void handleSelectionChange(boolean hasSelection) {
		setActionEnabled(ACTION_CREATE_RECEIPT, hasSelection);
	  }

	  // Actions ****************************************************************

	  protected String[] contextActionNames() {
	    return new String[] {};
	  }
	
	  @Action(enabled = false)
	  public Task<Void, Void> createReceipt(ActionEvent e) {
	    LOGGER.debug("Printing receipt\u2026");
	    return new CreateSingleReceiptTask(getSelection());
	  }

	  @Action
	  public Task<Void, Void> printAllReceipts(ActionEvent e) {
	    LOGGER.debug("Printing all receipts\u2026");
	    return new PrintAllReceiptsTask();
	  }

	  @Action
	  public Task<Void, Void> createPDFFiles(ActionEvent e){
		  LOGGER.debug("Printing all receipts from one Page");
		  return new CreatePDFFiles();
	  }
	  
	  private final class CreateSingleReceiptTask extends Task<Void, Void> {

	    private final TaskPane progressPane;
	    private final VendorBean vendor;

	    CreateSingleReceiptTask(VendorBean vendor) {
	      super(BlockingScope.APPLICATION);
	      progressPane = new TaskPane(MessageType.INFORMATION, "Drucke Beleg", CommandValue.OK);
	      progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
	      progressPane.setProgressIndeterminate(true);
	      progressPane.setProgressVisible(true);
	      progressPane.setVisible(true);
	      this.vendor = vendor;
	      ExportExcel.getInstance().createExcelForOneVendor(this.vendor);
	    }

	    @Override
	    protected Void doInBackground() {
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
	      progressPane = new TaskPane(MessageType.INFORMATION, "Erstelle alle Belege.", CommandValue.OK);
	      progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
	      progressPane.setProgressIndeterminate(true);
	      progressPane.setProgressVisible(true);
	      progressPane.setVisible(true);
	      ExportExcel.getInstance().createExcelForAllVendors();
	      try {
			PrintingService.printAllExportedFiles();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    @Override
	    protected Void doInBackground() {
	      return null;
	    }
	  }

	  private final class PrintInternalReceiptTask extends Task<Void, Void> {

	    private final TaskPane progressPane;

	    PrintInternalReceiptTask() {
	      super(BlockingScope.APPLICATION);
	      progressPane = new TaskPane(MessageType.INFORMATION, "Drucke internen Beleg", CommandValue.OK);
	      progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
	      progressPane.setProgressIndeterminate(true);
	      progressPane.setProgressVisible(true);
	      progressPane.setVisible(true);
	      ExportExcel.getInstance().createExcelForInternalPayoff();
	    }

	    @Override
	    protected Void doInBackground() {
	      return null;
	    }
	  }
	  
	  private final class CreatePDFFiles extends Task<Void, Void>{

		private final TaskPane progressPane;
		  
		CreatePDFFiles() {
			super(BlockingScope.APPLICATION);
			progressPane = new TaskPane(MessageType.INFORMATION, "Erstelle alle Belege.", CommandValue.OK);
		    progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
		    progressPane.setProgressIndeterminate(true);
		    progressPane.setProgressVisible(true);
		    progressPane.setVisible(true);
		    ExportExcel.getInstance().createExcelForAllVendors();
		    new PrintInternalReceiptTask();
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
