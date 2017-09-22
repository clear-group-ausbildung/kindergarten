package de.clearit.kindergarten.appliance.purchase;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.codehaus.jackson.map.ObjectMapper;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.BlockingScope;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.application.Task;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;

/**
 * The home model for the purchase.
 */
public class PurchaseHomeModel extends AbstractHomeModel<PurchaseBean> {

  private static final long serialVersionUID = 1L;

  public static final String ACTION_NEW_PURCHASE = "newPurchase";
  public static final String ACTION_IMPORT_PURCHASES = "importPurchases";
  public static final String ACTION_EXPORT_PURCHASES = "exportPurchases";
  private static final Logger LOGGER = Logger.getLogger(PurchaseHomeModel.class.getName());
  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeModel.class);
  private static final PurchaseService SERVICE = PurchaseService.getInstance();
  private static PurchaseHomeModel instance;
  private final ValueModel itemCountModel = new ValueHolder(0);
  private final ValueModel itemSumModel = new ValueHolder(0.0);
  private final ValueModel kindergartenProfitModel = new ValueHolder(0.0);
  private final ValueModel vendorPayoutModel = new ValueHolder(0.0);

  // Instance Creation ******************************************************

  private PurchaseHomeModel() {
    super();
    refreshSummary();
  }

  static PurchaseHomeModel getInstance() {
    if (instance == null) {
      instance = new PurchaseHomeModel();
    }
    return instance;
  }

  public ValueModel getItemCountModel() {
    return itemCountModel;
  }

  public ValueModel getItemSumModel() {
    return itemSumModel;
  }

  public ValueModel getKindergartenProfitModel() {
    return kindergartenProfitModel;
  }

  public ValueModel getVendorPayoutModel() {
    return vendorPayoutModel;
  }

  // Initialization *********************************************************

  @Override
  protected ListModel<PurchaseBean> getListModel() {
    return SERVICE.getListModel();
  }

  // Presentation Logic *****************************************************

  @Override
  protected void handleSelectionChange(final boolean hasSelection) {
    handleSelectionChangeEditDelete(hasSelection);
  }

  // Actions ****************************************************************

  @Override
  protected String[] contextActionNames() {
    return new String[] {};
  }

  @Action
  public void newItem(final ActionEvent e) {
    final String title = RESOURCES.getString("newPurchase.title");
    editItem(e, title, new PurchaseBean(), true);
  }

  @Action
  public void newPurchase(final ActionEvent e) {
    newItem(e);
  }

  @Action(enabled = false)
  public void editItem(final ActionEvent e) {
    final String title = RESOURCES.getString("editPurchase.title");
    editItem(e, title, getSelection(), false);
  }

  private void editItem(final EventObject e, final String title, final PurchaseBean purchase, final boolean newItem) {
    final PurchaseEditorModel model = new PurchaseEditorModel(purchase, value -> {
      if (value == CommandValue.OK) {
        if (newItem) {
          SERVICE.create(purchase);
        } else {
          SERVICE.update(purchase);
        }
        refreshSummary();
      }
    });
    PurchaseAppliance.getInstance().openPurchaseEditor(title, model, newItem);
  }

  @Action(enabled = false)
  public void deleteItem(final ActionEvent e) {
    final PurchaseBean purchase = getSelection();
    final String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction", "Artikel-Nr: " + purchase
        .getItemNumber());
    final TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
    if (pane.getCommitValue() == CommandValue.YES) {
      SERVICE.delete(purchase);
      refreshSummary();
    }
  }

  @Action
  public Task<List<PurchaseBean>, Void> importPurchases(final ActionEvent e) {
    LOGGER.fine("Importing purchase\u2026");

    return new ImportPurchasesTask();

  }

  private final class ImportPurchasesTask extends Task<List<PurchaseBean>, Void> {

    private final TaskPane pane;
    private final File importFile;

    public ImportPurchasesTask() {
      super(BlockingScope.APPLICATION);
      pane = new TaskPane(MessageType.INFORMATION, "Importiere", CommandValue.OK);
      pane.setPreferredWidth(PreferredWidth.MEDIUM);
      pane.setProgressIndeterminate(true);
      pane.setProgressVisible(true);
      pane.setVisible(true);
      importFile = getImportPath();
    }

    @Override
    protected List<PurchaseBean> doInBackground() throws Exception {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(importFile, mapper.getTypeFactory().constructCollectionType(List.class,
          PurchaseBean.class));
    }

    @Override
    protected void succeeded(List<PurchaseBean> result) {
      super.succeeded(result);
      result.forEach(purchase -> {
        SERVICE.create(purchase);
      });
      pane.setVisible(false);
      String mainInstruction = RESOURCES.getString("importPurchases.message.text", result.size());
      TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
      pane.setPreferredWidth(PreferredWidth.MEDIUM);
      pane.showDialog(getEventObject(), RESOURCES.getString("importPurchases.message.title"));
    }

  }

  @Action
  public void exportPurchases(final ActionEvent e) {
    LOGGER.fine("Exporting purchase\u2026");

    List<PurchaseBean> purchaseList = SERVICE.getAll();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String exportPath = getExportPath() + ".json";
      mapper.writeValue(new File(exportPath), purchaseList);

      String mainInstruction = RESOURCES.getString("exportPurchases.message.text", purchaseList.size(), exportPath);
      TaskPane pane = new TaskPane(MessageType.INFORMATION, mainInstruction, CommandValue.OK);
      pane.setPreferredWidth(PreferredWidth.MEDIUM);
      pane.showDialog(e, RESOURCES.getString("exportPurchases.message.title"));
    } catch (final Exception e1) {
      LOGGER.severe("Fehler beim Exportieren der Verk\u00e4ufe!");
      e1.printStackTrace();
    }
  }

  private void refreshSummary() {
    itemCountModel.setValue(SERVICE.getItemCountByPurchases(getSelectionInList().getList()));
    itemSumModel.setValue(SERVICE.getItemSumByPurchases(getSelectionInList().getList()));
    kindergartenProfitModel.setValue(SERVICE.getKindergartenProfitByPurchases(getSelectionInList().getList()));
    vendorPayoutModel.setValue(SERVICE.getVendorPayoutByPurchases(getSelectionInList().getList()));
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

  private String getExportPath() {
    final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
    fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    fileChooser.setDialogTitle("Speichern unter...");
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON", "json"));
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setVisible(true);
    fileChooser.showSaveDialog(null);
    fileChooser.setVisible(false);

    System.out.println(fileChooser.getCurrentDirectory().toString());

    return fileChooser.getSelectedFile().toString();
  }

}
