package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.desktop.DesktopUtils;
import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchaseEditorView extends AbstractView {
 private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorView.class);

  private final PurchaseEditorModel model;

  private JComponent vendorIdField;
  private JComponent itemNumberField;
  private JComponent itemPriceField;

  // Instance Creation ******************************************************

  PurchaseEditorView(PurchaseEditorModel model) {
    this.model = model;
  }

  // Initialization *********************************************************

  private void initComponents() {
    vendorIdField = BasicComponentFactory.createTextField(model.getBufferedModel(PurchaseBean.PROPERTY_VENDOR_ID));
    itemNumberField = BasicComponentFactory.createTextField(model.getBufferedModel(PurchaseBean.PROPERTY_ITEM_NUMBER));
    itemPriceField = BasicComponentFactory.createTextField(model.getBufferedModel(PurchaseBean.PROPERTY_ITEM_PRICE));

  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormPane pane = new FormPane(buildContent(), model);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }

  private JComponent buildContent() {
    FormLayout layout = new FormLayout("left:pref, $lcgap, 120dlu, 6dlu, 120dlu, 0:grow",
        "3*(p, $lg), p, 21dlu, 14dlu:grow");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBorder(Borders.DLU14_BORDER);
    builder.addI15dLabel("purchase.vendorId", CC.xy(1, 1));
    builder.add(vendorIdField, CC.xy(3, 1));
    builder.add(itemNumberField, CC.xy(5, 1));
    builder.addI15dLabel("purchase.itemNumber", CC.xy(1, 3));
    builder.add(itemNumberField, CC.xy(3, 3));
    builder.addI15dLabel("purchase.itemPrice", CC.xy(1, 5));
    builder.add(itemPriceField, CC.xy(3, 5));
    builder.add(buildValidationFeedback(), CC.xyw(1, 9, 6));

    return builder.getPanel();
  }

  private JComponent buildValidationFeedback() {
    return DesktopUtils.buildValidationFeedbackPanel("Die Verkäufer ID muss angegeben werden.",
    "Die Artikelnummer muss angegeben werden.", "Der Artikel Preis muss angegeben werden.", null);
    }
}
