package de.clearit.kindergarten.appliance.purchase;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.VendorBean;

public class PurchaseEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorView.class);

  private final PurchaseEditorModel model;

  private JComponent itemQuantityField;
  private JComponent itemNumberField;
  private JComponent itemPriceField;
  private JComponent totalPriceField;
  private JComponent vendorBox;
  private JComponent paymentField;
  private JComponent profitField;

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
    itemQuantityField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_QUANTITY)));
    itemNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_NUMBER)));
    itemPriceField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_ITEM_PRICE)));
    totalPriceField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_TOTAL_PRICE)));
    vendorBox = BasicComponentFactory.createComboBox(model.getVendorList(), new VendorListCellRenderer());
    paymentField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_PAYMENT)));
    profitField = BasicComponentFactory.createTextField(new DoubleToStringConverter(model.getBufferedModel(
        PurchaseBean.PROPERTY_PROFIT)));
  }

  private JComponent buildContent() {
    FormLayout layout = new FormLayout("3*(left:pref, $lcgap, [50dlu,pref], $rgap), left:pref, $lcgap, [50dlu,pref]",
        "2*(p, $lg)");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBackground(RESOURCES.getColor("content.background"));
    builder.addI15dLabel("purchase.itemQuantity", CC.xy(1, 1));
    builder.add(itemQuantityField, CC.xy(3, 1));
    builder.addI15dLabel("purchase.itemNumber", CC.xy(5, 1));
    builder.add(itemNumberField, CC.xy(7, 1));
    builder.addI15dLabel("purchase.itemPrice", CC.xy(9, 1));
    builder.add(itemPriceField, CC.xy(11, 1));
    builder.addI15dLabel("purchase.totalPrice", CC.xy(13, 1));
    builder.add(totalPriceField, CC.xy(15, 1));
    builder.addI15dLabel("purchase.vendor", CC.xy(1, 3));
    builder.add(vendorBox, CC.xy(3, 3));
    builder.addI15dLabel("purchase.payment", CC.xy(5, 3));
    builder.add(paymentField, CC.xy(7, 3));
    builder.addI15dLabel("purchase.profit", CC.xy(9, 3));
    builder.add(profitField, CC.xy(11, 3));

    return builder.getPanel();
  }

  private static final class VendorListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
      Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      VendorBean vendor = (VendorBean) value;
      setText(vendor == null ? "" : (vendor.getLastName() + ", " + vendor.getFirstName()));
      return component;
    }

  }

  private static final class IntegerToStringConverter extends AbstractConverter {

    private static final long serialVersionUID = 1L;

    private IntegerToStringConverter(ValueModel integerSubject) {
      super(integerSubject);
    }

    @Override
    public void setValue(Object newValue) {
      Integer integerValue = Integer.valueOf((String) newValue);
      subject.setValue(integerValue);
    }

    @Override
    public Object convertFromSubject(Object subjectValue) {
      String result = "";
      if (subjectValue != null) {
        result = ((Integer) subjectValue).toString();
      }
      return result;
    }

  }

  private static final class DoubleToStringConverter extends AbstractConverter {

    private DoubleToStringConverter(ValueModel doubleSubject) {
      super(doubleSubject);
    }

    @Override
    public void setValue(Object newValue) {
      Double doubleValue = Double.valueOf((String) newValue);
      subject.setValue(doubleValue);
    }

    @Override
    public Object convertFromSubject(Object subjectValue) {
      String result = "";
      if (subjectValue != null) {
        result = ((Double) subjectValue).toString();
      }
      return result;
    }

  }

}
