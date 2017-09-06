package de.clearit.kindergarten.appliance.vendor;

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
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The home view for the vendor.
 */
final class VendorHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeView.class);

  private static VendorHomeView instance;

  private final VendorHomeModel model;

  private UIFSearchField searchField;
  private JTable table;
  private VendorPreview preview;

  // Instance Creation ******************************************************

  private VendorHomeView(VendorHomeModel model) {
    this.model = model;
  }

  static VendorHomeView getInstance() {
    if (instance == null) {
      instance = new VendorHomeView(VendorHomeModel.getInstance());
    }
    return instance;
  }

  // Building ***************************************************************

  private void initComponents() {
    searchField = new UIFSearchField(SearchMode.INSTANT);
    searchField.setPrompt(RESOURCES.getString("vendorHome.searchPrompt"));

    table = new StripedTable(new VendorTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref]");

    Action editAction = model.getAction(VendorHomeModel.ACTION_EDIT_ITEM);
    ComponentUtils.registerDoubleClickAction(table, editAction);

    preview = new VendorPreview(model.getSelectionInList().getSelectionHolder());
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("vendorHome.mainInstruction"));
    builder.setSearchView(searchField);
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_EDIT_ITEM,
        AbstractHomeModel.ACTION_DELETE_ITEM, "---", AbstractHomeModel.ACTION_PRINT_ITEM);
    builder.setPreview(preview.getPanel());

    return builder.getPanel();
  }

  // TableModel *************************************************************

  private static final class VendorTableModel extends AbstractTableAdapter<VendorBean> {

    private static final long serialVersionUID = 1L;

    VendorTableModel(ListModel<?> listModel) {
      super(listModel, getColumnNames());
    }

    private static String[] getColumnNames() {
      return new String[] { RESOURCES.getString("vendor.table.firstName"), RESOURCES.getString("vendor.table.lastName"),
          RESOURCES.getString("vendor.table.phoneNumber") };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      VendorBean vendor = getRow(rowIndex);
      switch (columnIndex) {
      case 0:
        return vendor.getFirstName();

      case 1:
        return vendor.getLastName();

      case 2:
        return vendor.getPhoneNumber();

      default:
        throw new IllegalStateException("Can't handle column index " + columnIndex);
      }
    }

  }

}
