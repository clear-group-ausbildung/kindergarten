package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.component.factory.JSDLFactory;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.appliance.HomeViewBuilder;
import de.clearit.kindergarten.appliance.vendor.VendorListCellRenderer;

public class PurchaseHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeView.class);

  private static PurchaseHomeView instance;

  private final PurchaseHomeModel model;

  private JComponent vendorBox;
  private JTable table;
  private PurchaseHomeSummary summary;

  // Instance Creation ******************************************************

  private PurchaseHomeView(PurchaseHomeModel model) {
    this.model = model;
  }

  public static PurchaseHomeView getInstance() {
    if (instance == null) {
      instance = new PurchaseHomeView(PurchaseHomeModel.getInstance());
    }
    return instance;
  }

  // Building ***************************************************************

  private void initComponents() {
    vendorBox = BasicComponentFactory.createComboBox(model.getVendorList(), new VendorListCellRenderer());
    table = new StripedTable(new PurchaseTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref]");
    summary = new PurchaseHomeSummary(model);
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitleLabel(buildTitleView());
    builder.setSummary(summary.getPanel());
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_DELETE_ITEM,
        "---", PurchaseHomeModel.ACTION_IMPORT_PURCHASES, PurchaseHomeModel.ACTION_EXPORT_PURCHASES);

    return builder.getPanel();
  }

  private JComponent buildTitleView() {
    FormLayout layout = new FormLayout("left:pref, 25dlu, left:pref, $lcgap, 150dlu", "p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.add(JSDLFactory.createHeaderLabel(RESOURCES.getString("purchaseHome.mainInstruction")), CC.xy(1, 1));
    builder.addI15dLabel(RESOURCES.getString("filter.label"), CC.xy(3, 1));
    builder.add(vendorBox, CC.xy(5, 1));
    return builder.getPanel();
  }

}
