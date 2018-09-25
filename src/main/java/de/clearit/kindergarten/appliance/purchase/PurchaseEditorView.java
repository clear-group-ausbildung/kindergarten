package de.clearit.kindergarten.appliance.purchase;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

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
public class PurchaseEditorView extends AbstractView {
	
  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorView.class);
  private final PurchaseEditorModel model;
  private JComponent itemNumberLabel;
  private JComponent itemNumberField;
  private JComponent itemPriceLabel;
  private JComponent itemPriceField;
  private JComponent vendorNumberLabel;
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
    initFonts();
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
    itemNumberLabel = new JLabel(RESOURCES.getString("purchase.itemNumber"));
    itemNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_NUMBER)));
    itemPriceLabel = new JLabel(RESOURCES.getString("purchase.itemPrice"));
    itemPriceField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_PRICE)));
    vendorNumberLabel = new JLabel(RESOURCES.getString("purchase.vendor"));
    vendorNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_VENDOR_NUMBER)));
    addButton = new JButton(model.getAction(PurchaseEditorModel.ACTION_ADD_LINE_ITEM));
    addButton.addActionListener(e -> vendorNumberField.requestFocusInWindow());
    table = new StripedTable(new PurchaseTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "[30dlu,60dlu], [50dlu,pref], [50dlu,pref], [50dlu,pref]");
    summary = new PurchaseEditorSummary(model);
  }
  
  private void initFonts() {
    final Font regurlar18PtFieldFont = itemNumberLabel.getFont().deriveFont(18.0f);
    itemNumberLabel.setFont(regurlar18PtFieldFont);
    itemNumberField.setFont(regurlar18PtFieldFont);
    itemPriceLabel.setFont(regurlar18PtFieldFont);
    itemPriceField.setFont(regurlar18PtFieldFont);
    vendorNumberLabel.setFont(regurlar18PtFieldFont);
    vendorNumberField.setFont(regurlar18PtFieldFont);
    addButton.setFont(regurlar18PtFieldFont);
  }
  
  private JComponent buildEditor() {
    final FormLayout layout = new FormLayout("2*(left:pref, $lcgap, 25dlu, $rgap), left:pref, $lcgap, 50dlu", "p");
    final I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBackground(RESOURCES.getColor("content.background"));
    builder.add(vendorNumberLabel, CC.xy(1, 1));
    builder.add(vendorNumberField, CC.xy(3, 1));
    builder.add(itemNumberLabel, CC.xy(5, 1));
    builder.add(itemNumberField, CC.xy(7, 1));
    builder.add(itemPriceLabel, CC.xy(9, 1));
    builder.add(itemPriceField, CC.xy(11, 1));
    return builder.getPanel();
  }
  
  private JComponent buildActions() {
    final ButtonBarBuilder2 builder = new ButtonBarBuilder2();
    builder.addButton(addButton);
    return builder.getPanel();
  }
  
  public JTextField getVendorNumber() {
    return (JTextField) vendorNumberField;
  }
  
  public void setVendorNumber(String vendorNumber) {
    ((JTextField) vendorNumberField).setText(vendorNumber);
  }
  
  public JTextField getItemNumber() {
    return (JTextField) itemNumberField;
  }
  
  public void setItemNumber(String itemNumber) {
    ((JTextField) itemNumberField).setText(itemNumber);
  }
  
  public JTextField getItemPrice() {
    return (JTextField) itemPriceField;
  }
  public void setItemPrice(String itemPrice) {
    ((JTextField) itemPriceField).setText(itemPrice);
  }
  
}