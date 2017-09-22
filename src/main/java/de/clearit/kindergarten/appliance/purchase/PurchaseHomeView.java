package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.appliance.HomeViewBuilder;

public class PurchaseHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeView.class);

  private static PurchaseHomeView instance;

  private final PurchaseHomeModel model;

  private JTable table;
  private PurchaseHomeSummary summary;

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
    table = new StripedTable(new PurchaseTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref]");
    summary = new PurchaseHomeSummary(model);
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("purchaseHome.mainInstruction"));
    builder.setSummary(summary.getPanel());
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_DELETE_ITEM,
        "---", PurchaseHomeModel.ACTION_IMPORT_PURCHASES, PurchaseHomeModel.ACTION_EXPORT_PURCHASES);

    return builder.getPanel();
  }

}
