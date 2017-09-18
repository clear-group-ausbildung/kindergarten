package de.clearit.kindergarten.appliance.purchase;

import java.util.List;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.application.KindergartenComponentFactory;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;

public class PurchaseAnalysis extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseAnalysis.class);
  private static final PurchaseService SERVICE = PurchaseService.getInstance();

  private final List<PurchaseBean> listPurchases;
  private final ValueModel itemCountModel = new ValueHolder();
  private final ValueModel itemSumModel = new ValueHolder();
  private final ValueModel kindergartenProfitModel = new ValueHolder();
  private final ValueModel vendorPayoutModel = new ValueHolder();

  private JComponent itemCountField;
  private JComponent itemSumField;
  private JComponent kindergartenProfitField;
  private JComponent vendorPayoutField;

  // Instance Creation ******************************************************

  public PurchaseAnalysis(final List<PurchaseBean> listPurchases) {
    super();
    this.listPurchases = listPurchases;
    itemCountModel.setValue(SERVICE.getItemCountByPurchases(this.listPurchases));
    itemSumModel.setValue(SERVICE.getItemSumByPurchases(this.listPurchases));
    kindergartenProfitModel.setValue(SERVICE.getKindergartenProfitByPurchases(this.listPurchases));
    vendorPayoutModel.setValue(SERVICE.getVendorPayoutByPurchases(this.listPurchases));
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormLayout layout = new FormLayout("3*(left:pref, $lcg, right:pref, $rgap), left:pref, $lcg, right:pref", "p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.addI15dROLabel("purchase.itemCount", CC.xy(1, 1));
    builder.add(itemCountField, CC.xy(3, 1));
    builder.addI15dROLabel("purchase.itemSum", CC.xy(5, 1));
    builder.add(itemSumField, CC.xy(7, 1));
    builder.addI15dROLabel("purchase.kindergartenProfit", CC.xy(11, 1));
    builder.add(kindergartenProfitField, CC.xy(13, 1));
    builder.addI15dROLabel("purchase.vendorPayout", CC.xy(15, 1));
    builder.add(vendorPayoutField, CC.xy(17, 1));

    return builder.getPanel();
  }

  private void initComponents() {
    itemCountField = KindergartenComponentFactory.createReadOnlyTextField(itemCountModel);
    itemSumField = KindergartenComponentFactory.createReadOnlyTextField(itemSumModel);
    kindergartenProfitField = KindergartenComponentFactory.createReadOnlyTextField(kindergartenProfitModel);
    vendorPayoutField = KindergartenComponentFactory.createReadOnlyTextField(vendorPayoutModel);
  }

}
