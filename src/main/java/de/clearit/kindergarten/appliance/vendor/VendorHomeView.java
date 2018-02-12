package de.clearit.kindergarten.appliance.vendor;

import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListModel;

import org.sqlite.util.StringUtils;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.ComponentUtils;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.appliance.HomeViewBuilder;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The home view for the vendor.
 */
public final class VendorHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeView.class);

  private static VendorHomeView instance;

  private final VendorHomeModel model;

  private JTable table;
  private VendorPreview preview;

  // Instance Creation ******************************************************

  private VendorHomeView(VendorHomeModel model) {
    this.model = model;
  }

  public static VendorHomeView getInstance() {
    if (instance == null) {
      instance = new VendorHomeView(VendorHomeModel.getInstance());
    }
    return instance;
  }

  // Building ***************************************************************

  private void initComponents() {
    table = new StripedTable(new VendorTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [30dlu,60dlu], [50dlu,pref], [50dlu,pref]");

    Action editAction = model.getAction(VendorHomeModel.ACTION_EDIT_ITEM);
    ComponentUtils.registerDoubleClickAction(table, editAction);

    preview = new VendorPreview(model.getSelectionInList().getSelectionHolder());
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("vendorHome.mainInstruction"));
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_EDIT_ITEM,
        AbstractHomeModel.ACTION_DELETE_ITEM, "---", VendorHomeModel.ACTION_PRINT_RECEIPT,
        VendorHomeModel.ACTION_PRINT_ALL_RECEIPTS, VendorHomeModel.ACTION_PRINT_INTERNAL_RECEIPT);
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
      return new String[] { RESOURCES.getString("vendor.table.vendorNumber"), RESOURCES.getString(
          "vendor.table.firstName"), RESOURCES.getString("vendor.table.lastName"), RESOURCES.getString(
              "vendor.table.phoneNumber") };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      VendorBean vendor = getRow(rowIndex);
      switch (columnIndex) {
      case 0:
        if (!vendor.getVendorNumbers().isEmpty()) {
          return StringUtils.join(vendor.getVendorNumbers().stream().map(vendorNumber -> String.valueOf(vendorNumber
              .getVendorNumber())).collect(Collectors.toList()), ", ");
        } else {
          return "Keine Vorhanden";
        }
      case 1:
        return vendor.getFirstName();

      case 2:
        return vendor.getLastName();

      case 3:
        return vendor.getPhoneNumber();

      default:
        throw new IllegalStateException("Can't handle column index " + columnIndex);
      }
    }

  }

}
