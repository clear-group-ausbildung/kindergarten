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

import de.clearit.kindergarten.desktop.DesktopUtils;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The editor view for the vendor.
 */
final class VendorEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorEditorView.class);

  private final VendorEditorModel model;

  private JComponent firstNameField;
  private JComponent lastNameField;
  private JComponent phoneNumberField;
  private JComponent deliveredBox;
  private JComponent dirtyBox;
  private JComponent fetchedBox;
  private JComponent receivedMoneyBox;

  // Instance Creation ******************************************************

  VendorEditorView(VendorEditorModel model) {
    this.model = model;
  }

  // Initialization *********************************************************

  private void initComponents() {
    firstNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_FIRST_NAME));
    lastNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_LAST_NAME));
    phoneNumberField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_PHONE_NUMBER));
    deliveredBox = BasicComponentFactory.createCheckBox(model.getBufferedModel(VendorBean.PROPERTY_DELIVERED), RESOURCES
        .getString("vendor.table.delivered"));
    dirtyBox = BasicComponentFactory.createCheckBox(model.getBufferedModel(VendorBean.PROPERTY_DIRTY), RESOURCES
        .getString("vendor.table.dirty"));
    fetchedBox = BasicComponentFactory.createCheckBox(model.getBufferedModel(VendorBean.PROPERTY_FETCHED), RESOURCES
        .getString("vendor.table.fetched"));
    receivedMoneyBox = BasicComponentFactory.createCheckBox(model.getBufferedModel(VendorBean.PROPERTY_RECEIVED_MONEY),
        RESOURCES.getString("vendor.table.receivedMoney"));

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
    builder.addI15dLabel("vendor.firstName", CC.xy(1, 1));
    builder.add(firstNameField, CC.xy(3, 1));
    builder.add(deliveredBox, CC.xy(5, 1));
    builder.addI15dLabel("vendor.lastName", CC.xy(1, 3));
    builder.add(lastNameField, CC.xy(3, 3));
    builder.add(dirtyBox, CC.xy(5, 3));
    builder.addI15dLabel("vendor.phoneNumber", CC.xy(1, 5));
    builder.add(phoneNumberField, CC.xy(3, 5));
    builder.add(fetchedBox, CC.xy(5, 5));
    builder.add(receivedMoneyBox, CC.xy(5, 7));
    builder.add(buildValidationFeedback(), CC.xyw(1, 9, 6));

    return builder.getPanel();
  }

  private JComponent buildValidationFeedback() {
    return DesktopUtils.buildValidationFeedbackPanel("Der Vorname muss angegeben werden.",
        "Der Nachname muss angegeben werden.", "Die Telefonnummer ist optional.", null);
  }

}