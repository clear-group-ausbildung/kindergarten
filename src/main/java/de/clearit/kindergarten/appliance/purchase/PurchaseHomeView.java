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
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

public class PurchaseHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeView.class);

  private static PurchaseHomeView instance;

  private final PurchaseHomeModel model;

  private UIFSearchField searchField;
  private JTable table;
  private PurchaseEditor editor;

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
    TableUtils.configureColumns(table,
        "[30dlu,60dlu], [50dlu,pref], [50dlu,pref], [50dlu,pref], [50dlu,pref], [50dlu,pref], [50dlu,pref]");

    Action editAction = model.getAction(PurchaseHomeModel.ACTION_EDIT_ITEM);
    ComponentUtils.registerDoubleClickAction(table, editAction);

    // editor = new PurchaseEditor(model.getSelectionInList().getSelectionHolder());
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("purchaseHome.mainInstruction"));
    builder.setSearchView(searchField);
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_EDIT_ITEM,
        AbstractHomeModel.ACTION_DELETE_ITEM, "---", AbstractHomeModel.ACTION_PRINT_ITEM);
    // builder.setPreview(preview.getPanel());

    return builder.getPanel();
  }

  // TableModel *************************************************************

  private static final class PurchaseTableModel extends AbstractTableAdapter<PurchaseBean> {

    private static final long serialVersionUID = 1L;

    PurchaseTableModel(ListModel<?> listModel) {
      super(listModel, getColumnNames());
    }

    private static String[] getColumnNames() {
      return new String[] { RESOURCES.getString("purchase.table.itemQuantity"), RESOURCES.getString(
          "purchase.table.itemNumber"), RESOURCES.getString("purchase.table.itemPrice"), RESOURCES.getString(
              "purchase.table.totalPrice"), RESOURCES.getString("purchase.table.profit"), RESOURCES.getString(
                  "purchase.table.payment"), RESOURCES.getString("purchase.table.vendor") };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      PurchaseBean purchase = getRow(rowIndex);
      switch (columnIndex) {
      case 0:
        return purchase.getItemQuantity();

      case 1:
        return purchase.getItemNumber();

      case 2:
        return purchase.getItemPrice();

      case 3:
        return purchase.getTotalPrice();

      case 4:
        return purchase.getProfit();

      case 5:
        return purchase.getPayment();

      case 6:
        String result = "";
        VendorBean vendor = VendorService.getInstance().getById(purchase.getVendorId());
        if (vendor != null) {
          result += vendor.getLastName();
          result += ", ";
          result += vendor.getFirstName();
        }
        return result;

      default:
        throw new IllegalStateException("Can't handle column index " + columnIndex);
      }
    }

  }

}
