package de.clearit.kindergarten.appliance.vendor;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.application.KindergartenComponentFactory;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;

/**
 * The preview for a vendor.
 */
public final class VendorPreview extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorPreview.class);

  private final PresentationModel<VendorBean> model;

  private JComponent vendorNumberField;
  private JComponent firstNameField;
  private JComponent lastNameField;
  private JComponent phoneNumberField;

  // Instance Creation ******************************************************

  public VendorPreview(ValueModel vendorHolder) {
    model = new PresentationModel<>(vendorHolder);
  }

  // Building ***************************************************************

  private void initComponents() {
    List<VendorNumberBean> listVendorNumberBeans = model.getBean().getVendorNumbers();
    String vendorNumberDisplayString = listVendorNumberBeans.stream().map(vendor -> String.valueOf(vendor
        .getVendorNumber())).collect(Collectors.joining(", "));
    vendorNumberField = KindergartenComponentFactory.createReadOnlyTextField(vendorNumberDisplayString);
    firstNameField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        VendorBean.PROPERTY_FIRST_NAME));
    lastNameField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(VendorBean.PROPERTY_LAST_NAME));
    phoneNumberField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        VendorBean.PROPERTY_PHONE_NUMBER));

  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormLayout layout = new FormLayout("right:pref, $lcgap, pref", "4*(p)");
    layout.setRowGroups(new int[][] { { 1, 2, 3, 4 } });
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.addI15dROLabel("vendor.vendorNumber", CC.xy(1, 1));
    builder.add(vendorNumberField, CC.xy(3, 1));
    builder.addI15dROLabel("vendor.firstName", CC.xy(1, 2));
    builder.add(firstNameField, CC.xy(3, 2));
    builder.addI15dROLabel("vendor.lastName", CC.xy(1, 3));
    builder.add(lastNameField, CC.xy(3, 3));
    builder.addI15dROLabel("vendor.phoneNumber", CC.xy(1, 4));
    builder.add(phoneNumberField, CC.xy(3, 4));

    return builder.getPanel();
  }

}