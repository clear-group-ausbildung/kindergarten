package de.clearit.kindergarten.appliance.purchase;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.codehaus.jackson.map.ObjectMapper;

import com.jgoodies.application.Action;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.CommitCallback;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;
import de.clearit.kindergarten.domain.entity.Purchase;

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

	// Instance Creation ******************************************************

	private PurchaseHomeModel() {
		super();
	}

	static PurchaseHomeModel getInstance() {
		if (instance == null) {
			instance = new PurchaseHomeModel();
		}
		return instance;
	}

	// Initialization *********************************************************

	@Override
	protected ListModel<PurchaseBean> getListModel() {
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
		String title = RESOURCES.getString("newPurchase.title");
		editItem(e, title, new PurchaseBean(), true);
	}

	@Action
	public void newPurchase(ActionEvent e) {
		newItem(e);
	}

	@Action(enabled = false)
	public void editItem(ActionEvent e) {
		String title = RESOURCES.getString("editPurchase.title");
		editItem(e, title, getSelection(), false);
	}

	private void editItem(EventObject e, String title, final PurchaseBean purchase, final boolean newItem) {
		PurchaseEditorModel model = new PurchaseEditorModel(purchase, new CommitCallback<CommandValue>() {
			@Override
			public void committed(CommandValue value) {
				if (newItem && (value == CommandValue.OK)) {
					SERVICE.create(purchase);
				}
			}
		});
		PurchaseAppliance.getInstance().openPurchaseEditor(title, model, newItem);
	}

	@Action(enabled = false)
	public void deleteItem(ActionEvent e) {
		PurchaseBean purchase = getSelection();
		String mainInstruction = RESOURCES.getString("deleteItem.mainInstruction",
				"Artikel-Nr: " + purchase.getItemNumber());
		TaskPane pane = new TaskPane(MessageType.QUESTION, mainInstruction, CommandValue.YES, CommandValue.NO);
		pane.setPreferredWidth(PreferredWidth.MEDIUM);
		pane.showDialog(e, RESOURCES.getString("deleteItem.title"));
		if (pane.getCommitValue() == CommandValue.YES) {
			SERVICE.delete(purchase);
		}
	}

	@Action
	public void importPurchases(ActionEvent e) {
		LOGGER.fine("Importing purchase\u2026");

		ObjectMapper mapper = new ObjectMapper();
		File importFile = getImportPath();
		try {
			List<PurchaseBean> purchaseList = mapper.readValue(importFile,
					mapper.getTypeFactory().constructCollectionType(List.class, PurchaseBean.class));
			purchaseList.forEach(purchase -> {
				Purchase.createIt("item_number", purchase.getItemNumber(), "item_price", purchase.getItemPrice(),
						"vendor_number", purchase.getVendorNumber());
			});
		} catch (IOException e1) {
			LOGGER.severe("Fehler beim Importieren der Verku+00e4ufe!");
			e1.printStackTrace();
		}
	}

	@Action
	public void exportPurchases(ActionEvent e) {
		LOGGER.fine("Exporting purchase\u2026");

		List<PurchaseBean> purchaseList = SERVICE.getAll();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(getExportPath()), purchaseList);
		} catch (IOException e1) {
			LOGGER.severe("Fehler beim Exportieren der Verk�ufe!");
			e1.printStackTrace();
		}
	}

	private File getImportPath() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON", "json"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("Datei �ffnen...");
		fileChooser.setVisible(true);
		fileChooser.showOpenDialog(null);
		fileChooser.setVisible(false);

		return fileChooser.getSelectedFile();
	}

	private String getExportPath() {
		File exportFile = new File("kindergarten_verkaeufe_export.json");
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setDialogTitle("Speichern unter...");
		fileChooser.setSelectedFile(exportFile);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setVisible(true);
		fileChooser.showSaveDialog(null);
		fileChooser.setVisible(false);

		return fileChooser.getCurrentDirectory().toString();
	}

}
