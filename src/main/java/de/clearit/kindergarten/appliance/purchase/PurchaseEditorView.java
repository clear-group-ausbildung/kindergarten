package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.DoubleToStringConverter;
import de.clearit.kindergarten.appliance.IntegerToStringConverter;
import de.clearit.kindergarten.domain.PurchaseBean;

class PurchaseEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorView.class);

  private final PurchaseEditorModel model;

  private JComponent itemNumberField;
  private JComponent itemPriceField;
  private JComponent vendorNumberField;
  private JButton addButton;
  private JTable table;
  private PurchaseEditorSummary summary;

  // Instance Creation ******************************************************

  public PurchaseEditorView(final PurchaseEditorModel model) {
    this.model = model;
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    initComponents();

    final PurchaseEditorViewBuilder builder = new PurchaseEditorViewBuilder();
    builder.setSummary(summary.getPanel());
    builder.setEditor(buildEditor());
    builder.setEditorActions(buildActions());
    builder.setTableTitle(RESOURCES.getString("purchaseEditor.tableTitle"));
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), PurchaseEditorModel.ACTION_REMOVE_LINE_ITEM);

    final FormPane pane = new FormPane(builder.getPanel(), model);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }

  private void initComponents() {
    itemNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_NUMBER)));
    itemPriceField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_PRICE)));
    vendorNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_VENDOR_NUMBER)));
    addButton = new JButton(model.getAction(PurchaseEditorModel.ACTION_ADD_LINE_ITEM));
    addButton.addActionListener(e -> vendorNumberField.requestFocusInWindow());

    table = new StripedTable(new PurchaseTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref]");

    summary = new PurchaseEditorSummary(model);

  }

  private JComponent buildEditor() {
    final FormLayout layout = new FormLayout("2*(left:pref, $lcgap, 25dlu, $rgap), left:pref, $lcgap, 50dlu", "p");
    final I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBackground(RESOURCES.getColor("content.background"));
    builder.addI15dLabel("purchase.vendor", CC.xy(1, 1));
    builder.add(vendorNumberField, CC.xy(3, 1));
    builder.addI15dLabel("purchase.itemNumber", CC.xy(5, 1));
    builder.add(itemNumberField, CC.xy(7, 1));
    builder.addI15dLabel("purchase.itemPrice", CC.xy(9, 1));
    builder.add(itemPriceField, CC.xy(11, 1));

    return builder.getPanel();
  }

  private JComponent buildActions() {
    final ButtonBarBuilder2 builder = new ButtonBarBuilder2();
    builder.addButton(addButton);
    return builder.getPanel();
  }

}
