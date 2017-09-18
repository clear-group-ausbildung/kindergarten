package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.appliance.DoubleToStringConverter;
import de.clearit.kindergarten.appliance.IntegerToStringConverter;
import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchaseEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorView.class);

  private final PurchaseEditorModel model;

  private JComponent itemNumberField;
  private JComponent itemPriceField;
  private JComponent vendorNumberField;

  // Instance Creation ******************************************************

  public PurchaseEditorView(PurchaseEditorModel model) {
    this.model = model;
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormPane pane = new FormPane(buildContent(), model);
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
  }

  private JComponent buildContent() {
    FormLayout layout = new FormLayout("2*(left:pref, $lcgap, 50dlu, $rgap), left:pref, $lcgap, 50dlu", "p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBackground(RESOURCES.getColor("content.background"));
    builder.addI15dLabel("purchase.itemNumber", CC.xy(1, 1));
    builder.add(itemNumberField, CC.xy(3, 1));
    builder.addI15dLabel("purchase.itemPrice", CC.xy(5, 1));
    builder.add(itemPriceField, CC.xy(7, 1));
    builder.addI15dLabel("purchase.vendor", CC.xy(9, 1));
    builder.add(vendorNumberField, CC.xy(11, 1));

    return builder.getPanel();
  }

}
