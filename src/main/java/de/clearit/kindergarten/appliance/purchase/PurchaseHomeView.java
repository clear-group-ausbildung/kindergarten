package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.component.text.UIFSearchField;
import com.jgoodies.uif2.component.text.UIFSearchField.SearchMode;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.AbstractHomeModel;
import de.clearit.kindergarten.appliance.HomeViewBuilder;

public class PurchaseHomeView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseHomeView.class);

  private static PurchaseHomeView instance;

  private final PurchaseHomeModel model;

  private UIFSearchField searchField;
  private JTable table;
  private PurchaseAnalysis analysis;

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

    analysis = new PurchaseAnalysis(model.getSelectionInList().getList());

  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("purchaseHome.mainInstruction"));
    builder.setSearchView(searchField);
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), AbstractHomeModel.ACTION_NEW_ITEM, AbstractHomeModel.ACTION_DELETE_ITEM,
        "---", PurchaseHomeModel.ACTION_IMPORT_PURCHASES, PurchaseHomeModel.ACTION_EXPORT_PURCHASES);
    builder.setPreview(analysis.getPanel());

    return builder.getPanel();
  }

}
