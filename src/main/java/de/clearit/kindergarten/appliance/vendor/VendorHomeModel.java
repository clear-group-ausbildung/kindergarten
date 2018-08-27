package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
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
import de.clearit.kindergarten.appliance.PostChangeHandler;
import de.clearit.kindergarten.appliance.purchase.PurchaseHomeModel;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorService;
import io.reactivex.Observable;

/**
 * The home model for the vendor.
 */
public final class VendorHomeModel extends AbstractHomeModel<VendorBean> {

  private static final long serialVersionUID = 1L;

  public static final String ACTION_IMPORT_VENDORS = "importVendors";
  public static final String ACTION_EXPORT_VENDORS = "exportVendors";

  private static final Logger LOGGER = LoggerFactory.getLogger(VendorHomeModel.class);
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeModel.class);
  private static final VendorService SERVICE = VendorService.getInstance();

  private static VendorHomeModel instance;
  private final PostChangeHandler postChangeHandler;

  // Instance Creation ******************************************************

  private VendorHomeModel() {
    super();
    postChangeHandler = PurchaseHomeModel.getInstance();
  }

  public static VendorHomeModel getInstance() {
    if (instance == null) {
      instance = new VendorHomeModel();
    }
    return instance;
  }

  // Initialisation *********************************************************

  @Override
  protected ListModel<?> getListModel() {
    return SERVICE.getListModel();
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
    String title = RESOURCES.getString("newVendor.title");
    editItem(title, new VendorBean(), true);
  }

  @Action(enabled = false)
  public void editItem(ActionEvent e) {
    String title = RESOURCES.getString("editVendor.title");
    editItem(title, getSelection(), false);
  }

  @Action
  public Task<List<VendorBean>, Void> importVendors(final ActionEvent e) {
    LOGGER.debug("Importing vendors\u2026");
    return new ImportVendorsTask();
  }

  @Action
  public Task<Void, Void> exportVendors(final ActionEvent e) {
    LOGGER.debug("Exporting vendors\u2026");
    return new ExportVendorsTask();
  }

  private void editItem(String title, final VendorBean vendor, final boolean newItem) {
    VendorEditorModel model = new VendorEditorModel(vendor, value -> {
      if (value == CommandValue.OK) {
        int indexOfNewOrUpdatedElement = 0;
        if (newItem) {
          SERVICE.create(vendor);
          postChangeHandler.onPostCreate();
          for (int i = 0; i < getSelectionInList().getList().size(); i++) {
            VendorBean element = getSelectionInList().getList().get(i);
            for (VendorNumberBean elementNumberBean : element.getVendorNumbers()) {
              for (VendorNumberBean callbackNumberBean : vendor.getVendorNumbers()) {
                if (callbackNumberBean.getVendorNumber() == elementNumberBean.getVendorNumber()) {
                  indexOfNewOrUpdatedElement = i;
                  break;
                }
              }
              break;
            }
          }
        } else {
          SERVICE.update(vendor);
          postChangeHandler.onPostUpdate();
          for (int i = 0; i < getSelectionInList().getList().size(); i++) {
            VendorBean element = getSelectionInList().getList().get(i);
            if (element.getId().equals(vendor.getId())) {
              indexOfNewOrUpdatedElement = i;
              break;
            }
          }
        }
        getSelectionInList().setSelectionIndex(indexOfNewOrUpdatedElement);
      }
    });
    VendorAppliance.getInstance().openVendorEditor(title, model);
  }

  @Action(enabled = false)
  public void deleteItem(ActionEvent e) {
    VendorBean vendor = getSelection();

    //If Abfrage wegen dem Komma
    if(vendor.getFirstName().isEmpty()){
    	 String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", vendor.getLastName());
    	 TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    	    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    	    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    	    if (pane.getCommitValue() == CommandValue.YES) {
    	        SERVICE.delete(vendor);
    	        postChangeHandler.onPostDelete();
    }
   }
    else{
    	 String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", vendor.getLastName()+", "+vendor.getFirstName());
    	 TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    	    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    	    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    	    if (pane.getCommitValue() == CommandValue.YES) {
    	        SERVICE.delete(vendor);
    	        postChangeHandler.onPostDelete();
      }

    }
  }

  private final class ImportVendorsTask extends Task<List<VendorBean>, Void> {

	    private final TaskPane progressPane;
	    private final File importFile;

	    ImportVendorsTask() {
	      super(BlockingScope.APPLICATION);
	      importFile = getImportPath();
		  progressPane = new TaskPane(MessageType.INFORMATION, "Importiere", CommandValue.OK);
		  if(importFile != null) {
			  progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
			  progressPane.setProgressIndeterminate(true);
			  progressPane.setProgressVisible(true);
			  progressPane.setVisible(true);
		  }
	    }

	    @Override
	    protected List<VendorBean> doInBackground() throws FileNotFoundException{  
	    	if(importFile != null) {
		    	return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(new FileReader(importFile),
		          new TypeToken<List<VendorBean>>() {
		          }.getType());
	    	}
	    	return null;
	    }

	    @Override
	    protected void succeeded(List<VendorBean> result) {
	      super.succeeded(result);
	      if(result != null) {
		      LOGGER.debug("# Vendor elements to create: {}", result.size());
		      Observable<VendorBean> observableVendors = Observable.fromIterable(result);
	
		      System.out.println("RESULT = " + result);
	
		      long beginNanos = System.nanoTime();
		      observableVendors.subscribe(vendorBean -> SERVICE.importVendors(vendorBean), Observable::error, () -> {
		        SERVICE.flush();
		        LOGGER.debug("Finished Import after {} ms", (System.nanoTime() - beginNanos) / 1_000_000);
		        progressPane.setVisible(false);
	//	        refreshSummary();
		        String mainInstruction = RESOURCES.getString("importVendors.message.text", result.size());
		        TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
		        pane.setPreferredWidth(PreferredWidth.MEDIUM);
		        pane.showDialog(getEventObject(), RESOURCES.getString("importVendors.message.title"));
		      });
	      }
	    }

	    private File getImportPath() {
	      final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
	      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON", "json"));
	      fileChooser.setAcceptAllFileFilterUsed(false);
	      fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
	      fileChooser.setDialogTitle("Datei \u00f6ffnen...");
	      fileChooser.setVisible(true);
	      fileChooser.showOpenDialog(null);
	      fileChooser.setVisible(false);

	      return fileChooser.getSelectedFile();
	    }

	  }

	  private final class ExportVendorsTask extends Task<Void, Void> {

	    private final TaskPane progressPane;
	    private final List<VendorBean> vendorList;
	    private final String exportPath;

	    ExportVendorsTask() {
	      super(BlockingScope.APPLICATION);
	      exportPath = getExportPath() + ".json";
	      progressPane = new TaskPane(MessageType.INFORMATION, "Exportiere", CommandValue.OK);
		  progressPane.setPreferredWidth(PreferredWidth.MEDIUM);
		  progressPane.setProgressIndeterminate(true);
		  progressPane.setProgressVisible(true);
		  progressPane.setVisible(true);
	      vendorList = SERVICE.getAll();
	    }

	    @Override
	    protected Void doInBackground() throws IOException {
	      long beginNanos = System.nanoTime();
	      String listVendorsAsJSONString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(
	          vendorList, new TypeToken<List<VendorBean>>() {
	          }.getType());
	      try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(new File(exportPath)))) {
	        jsonWriter.jsonValue(listVendorsAsJSONString);
	        jsonWriter.flush();
	      }
	      LOGGER.debug("Finished Export after {} ms", (System.nanoTime() - beginNanos) / 1_000_000);
	      return null;
	    }

	    @Override
	    protected void succeeded(Void result) {
	      super.succeeded(result);
	      if(result != null) {
		      progressPane.setVisible(false);
		      String mainInstruction = RESOURCES.getString("exportVendors.message.text", vendorList.size(), exportPath);
		      TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
		      pane.setPreferredWidth(PreferredWidth.MEDIUM);
		      pane.showDialog(getEventObject(), RESOURCES.getString("exportVendors.message.title"));
	      }
	    }

	    private String getExportPath() {
	      final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
	      fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
	      fileChooser.setDialogTitle("Speichern unter...");
	      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON", "json"));
	      fileChooser.setAcceptAllFileFilterUsed(false);
	      fileChooser.setVisible(true);
	      fileChooser.showSaveDialog(null);
	      fileChooser.setVisible(false);
	      
	      File selectedFile = fileChooser.getSelectedFile();
	      if(selectedFile != null) {
	    	  return selectedFile.toString();
	      }
	      return null;
	    }

	  }

}
