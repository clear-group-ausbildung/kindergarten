package de.clearit.kindergarten.appliance.purchase;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.appliance.vendor.VendorAppliance;
import de.clearit.kindergarten.application.KindergartenComponentFactory;
import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchasePreview extends AbstractView {
	
  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchasePreview.class);

  private final PresentationModel<PurchaseBean> model;

  private JComponent extrasArea;

  private JComponent vendorIdField;
  private JComponent itemNumberField;
  private JComponent itemPriceField;

  // Instance Creation ******************************************************

  public PurchasePreview(ValueModel purchaseHolder) {
    model = new PresentationModel<PurchaseBean>(purchaseHolder);
  }

  // Building ***************************************************************

  private void initComponents() {
    vendorIdField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        PurchaseBean.PROPERTY_VENDOR_ID));
    itemNumberField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(PurchaseBean.PROPERTY_ITEM_NUMBER));
    itemPriceField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(PurchaseBean.PROPERTY_ITEM_PRICE));
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormLayout layout = new FormLayout("[125dlu,pref], 21dlu, right:pref, $lcgap, pref", "3*(p)");
    layout.setRowGroups(new int[][] { { 1, 2, 3 } });
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.add(extrasArea, CC.xywh(1, 1, 1, 4, "left, top"));

    builder.addI15dROLabel("purchase.vendorId", CC.xy(3, 1));
    builder.add(vendorIdField, CC.xy(5, 1));
    builder.addI15dROLabel("purchase.itemNumber", CC.xy(3, 2));
    builder.add(itemNumberField, CC.xy(5, 2));
    builder.addI15dROLabel("purchase.itemPrice", CC.xy(3, 3));
    builder.add(itemPriceField, CC.xy(5, 3));
    return builder.getPanel();
  }
}
