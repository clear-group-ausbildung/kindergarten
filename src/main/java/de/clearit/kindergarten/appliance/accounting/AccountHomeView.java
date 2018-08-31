package de.clearit.kindergarten.appliance.accounting;

import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.HomeViewBuilder;
import de.clearit.kindergarten.appliance.vendor.VendorHomeView;
import de.clearit.kindergarten.appliance.vendor.VendorPreview;
import de.clearit.kindergarten.domain.VendorBean;

public class AccountHomeView extends AbstractView{
	private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeView.class);

	  private static AccountHomeView instance;

	  private final AccountHomeModel model;

	  private JTable table;
	  private VendorPreview preview;

	  // Instance Creation ******************************************************

	  private AccountHomeView(AccountHomeModel model) {
	    this.model = model;
	  }

	  public static AccountHomeView getInstance() {
	    if (instance == null) {
	      instance = new AccountHomeView(AccountHomeModel.getInstance());
	    }
	    return instance;
	  }

	  // Building ***************************************************************

	  private void initComponents() {
	    table = new StripedTable(new VendorTableModel(model.getSelectionInList()));
	    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
	    TableUtils.configureColumns(table, "[30dlu,60dlu], [30dlu,60dlu], [50dlu,pref], [50dlu,pref]");

	    preview = new VendorPreview(model.getSelectionInList().getSelectionHolder());
	  }

	  @Override
	  protected JComponent buildPanel() {
	    initComponents();

	    HomeViewBuilder builder = new HomeViewBuilder();
	    builder.setTitle(RESOURCES.getString("vendorHome.mainInstruction"));
	    builder.setListView(table);
	    builder.setListBar(model.getActionMap(), "---", AccountHomeModel.ACTION_CREATE_RECEIPT, AccountHomeModel.ACTION_CREATE_PDF_FILES, AccountHomeModel.ACTION_PRINT_ALL_RECEIPTS);
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
	          return vendor.getVendorNumbers().stream().map(vendorNumber -> String.valueOf(vendorNumber.getVendorNumber()))
	              .collect(Collectors.joining(", "));
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
