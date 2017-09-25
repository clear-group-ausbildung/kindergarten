package de.clearit.kindergarten.appliance.vendor;

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

import de.clearit.kindergarten.appliance.IntegerToStringConverter;
import de.clearit.kindergarten.desktop.DesktopUtils;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The editor view for the vendor.
 */
public final class VendorEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorEditorView.class);

  private final VendorEditorModel model;

  private JComponent vendorNumberField;
  private JComponent firstNameField;
  private JComponent lastNameField;
  private JComponent phoneNumberField;

  // Instance Creation ******************************************************

  public VendorEditorView(VendorEditorModel model) {
    this.model = model;
  }

  // Initialization *********************************************************

  private void initComponents() {
    vendorNumberField = BasicComponentFactory.createTextField(new IntegerToStringConverter(model.getBufferedModel(
        VendorBean.PROPERTY_VENDOR_NUMBER)));
    firstNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_FIRST_NAME));
    lastNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_LAST_NAME));
    phoneNumberField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_PHONE_NUMBER));
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    FormPane pane = new FormPane(buildContent(), model);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }

  private JComponent buildContent() {
    FormLayout layout = new FormLayout("150dlu:grow, 16dlu, pref:grow", "3*(p, p, $lg), p, p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBorder(Borders.DLU14_BORDER);
    builder.addI15dLabel("vendor.vendorNumber", CC.xy(1, 1));
    builder.add(vendorNumberField, CC.xy(1, 2));
    builder.addI15dLabel("vendor.firstName", CC.xy(1, 4));
    builder.add(firstNameField, CC.xy(1, 5));
    builder.addI15dLabel("vendor.lastName", CC.xy(1, 7));
    builder.add(lastNameField, CC.xy(1, 8));
    builder.addI15dLabel("vendor.phoneNumber", CC.xy(1, 10));
    builder.add(phoneNumberField, CC.xy(1, 11));
    builder.add(buildValidationFeedback(), CC.xywh(3, 1, 1, 11));

    return builder.getPanel();
  }

  private JComponent buildValidationFeedback() {
    return DesktopUtils.buildValidationFeedbackPanel("Die Verk\u00e4ufernummer muss angegeben werden.",
        "Der Vorname ist optional.", "Der Nachname muss angegeben werden.", "Die Telefonnummer ist optional.");
  }

}