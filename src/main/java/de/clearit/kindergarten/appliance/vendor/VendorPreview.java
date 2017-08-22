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
import de.clearit.kindergarten.domain.Vendor;

/**
 * The preview for a vendor.
 */
public final class VendorPreview extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorPreview.class);

  private final PresentationModel<Vendor> model;

  private JComponent addressArea;

  private JComponent codeField;
  private JComponent nameField;
  private JComponent tagsField;
  private JComponent vatIDField;

  // Instance Creation ******************************************************

  public VendorPreview(ValueModel vendorHolder) {
    model = new PresentationModel<Vendor>(vendorHolder);
  }

  // Building ***************************************************************

  private void initComponents() {
    addressArea = KindergartenComponentFactory.createReadOnlyTextArea(model.getBeanChannel(),
        new VendorAppliance.AddressFormat());
    codeField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(Vendor.PROPERTY_CODE));
    nameField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(Vendor.PROPERTY_NAME));
    tagsField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(
        Vendor.PROPERTY_COMMA_SEPARATED_TAGS));
    vatIDField = KindergartenComponentFactory.createReadOnlyTextField(model.getModel(Vendor.PROPERTY_VAT_ID));

  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormLayout layout = new FormLayout("[125dlu,pref], 21dlu, right:pref, $lcgap, pref", "5*(p)");
    layout.setRowGroups(new int[][] { { 1, 2, 3, 4, 5 } });
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.add(addressArea, CC.xywh(1, 1, 1, 5, "left, top"));

    builder.addROLabel("Name:", CC.xy(3, 1));
    builder.add(nameField, CC.xy(5, 1));
    builder.addROLabel("Code:", CC.xy(3, 2));
    builder.add(codeField, CC.xy(5, 2));
    builder.addROLabel("Tags:", CC.xy(3, 3));
    builder.add(tagsField, CC.xy(5, 3));
    builder.addROLabel("VAT ID:", CC.xy(3, 4));
    builder.add(vatIDField, CC.xy(5, 4));

    return builder.getPanel();
  }

}