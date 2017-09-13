package de.clearit.kindergarten.appliance.purchase;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.component.text.UIFSearchField;
import com.jgoodies.uif2.component.text.UIFSearchField.SearchMode;
import com.jgoodies.uif2.util.ComponentUtils;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.appliance.HomeViewBuilder;
import de.clearit.kindergarten.appliance.vendor.VendorHomeModel;
import de.clearit.kindergarten.appliance.vendor.VendorPreview;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.VendorBean;

public class PurchaseHomeView extends AbstractView {
	
	private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeView.class);

	  private static PurchaseHomeView instance;
	  private final PurchaseHomeModel model;

	  private UIFSearchField searchField;
	  private JTable table;
	  private PurchasePreview preview;

	  // Instance Creation ******************************************************

	  private PurchaseHomeView(PurchaseHomeModel model) {
	    this.model = model;
	  }

	  static PurchaseHomeView getInstance() {
	    if (instance == null) {
	      instance = new PurchaseHomeView(PurchaseHomeModel.getInstance());
	    }
	    return instance;
	  }

	  // Building ***************************************************************

	  private void initComponents() {
	    searchField = new UIFSearchField(SearchMode.INSTANT);
	    searchField.setPrompt(RESOURCES.getString("purchaseHome.searchPrompt"));

	    table = new StripedTable(new PurchaseTableModel(model.getSelectionInList()));
	    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
	    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref]");

	    Action editAction = model.getAction(PurchaseHomeModel.ACTION_EDIT_ITEM);
	    ComponentUtils.registerDoubleClickAction(table, editAction);

	    preview = new PurchasePreview(model.getSelectionInList().getSelectionHolder());
	  }

	  @Override
	  protected JComponent buildPanel() {
	    initComponents();

	    HomeViewBuilder builder = new HomeViewBuilder();
	    builder.setTitle(RESOURCES.getString("purchaseHome.mainInstruction"));
	    builder.setSearchView(searchField);
	    builder.setListView(table);
	    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_EDIT_ITEM, AbstractHomeModel.ACTION_DELETE_ITEM);
	    builder.setPreview(preview.getPanel());

	    return builder.getPanel();
	  }

	  // TableModel *************************************************************

	  private static final class PurchaseTableModel extends AbstractTableAdapter<PurchaseBean> {

	    private static final long serialVersionUID = 1L;

	    PurchaseTableModel(ListModel<?> listModel) {
	      super(listModel, getColumnNames());
	    }

	    private static String[] getColumnNames() {
	      return new String[] { RESOURCES.getString("purchase.table.vendorId"), RESOURCES.getString("purchase.table.itemNumber"),
	          RESOURCES.getString("purchase.table.itemPrice") };
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	      PurchaseBean purchase = getRow(rowIndex);
	      switch (columnIndex) {
	      case 0:
	        return purchase.getVendorId();

	      case 1:
	        return purchase.getItemNumber();

	      case 2:
	        return purchase.getItemPrice();

	      default:
	        throw new IllegalStateException("Can't handle column index " + columnIndex);
	      }
	    }
	  }
}
