package de.clearit.kindergarten.appliance.vendor;

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

/**
 * The preview for a vendor.
 */
public final class VendorPreview extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorPreview.class);

  private final PresentationModel<VendorBean> model;

  private JComponent extrasArea;

  private JComponent firstNameField;
  private JComponent lastNameField;
  private JComponent phoneNumberField;

  // Instance Creation ******************************************************

  public VendorPreview(ValueModel vendorHolder) {
    model = new PresentationModel<VendorBean>(vendorHolder);
  }

  // Building ***************************************************************

  private void initComponents() {
    extrasArea = KindergartenComponentFactory.createReadOnlyTextArea(model.getBeanChannel(),
        new VendorAppliance.ExtrasFormat());
    firstNameField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        VendorBean.PROPERTY_FIRST_NAME));
    lastNameField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(VendorBean.PROPERTY_LAST_NAME));
    phoneNumberField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        VendorBean.PROPERTY_PHONE_NUMBER));

  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormLayout layout = new FormLayout("[125dlu,pref], 21dlu, right:pref, $lcgap, pref", "4*(p)");
    layout.setRowGroups(new int[][] { { 1, 2, 3, 4 } });
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.add(extrasArea, CC.xywh(1, 1, 1, 4, "left, top"));

    builder.addI15dROLabel("vendor.firstName", CC.xy(3, 1));
    builder.add(firstNameField, CC.xy(5, 1));
    builder.addI15dROLabel("vendor.lastName", CC.xy(3, 2));
    builder.add(lastNameField, CC.xy(5, 2));
    builder.addI15dROLabel("vendor.phoneNumber", CC.xy(3, 3));
    builder.add(phoneNumberField, CC.xy(5, 3));

    return builder.getPanel();
  }

}