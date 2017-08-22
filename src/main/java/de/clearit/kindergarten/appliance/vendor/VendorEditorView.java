package de.clearit.kindergarten.appliance.vendor;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.component.HelpLink;
import com.jgoodies.jsdl.core.pane.form.TabbedFormPane;
import com.jgoodies.looks.Options;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.desktop.DesktopUtils;
import de.clearit.kindergarten.domain.Vendor;

/**
 * The editor view for the vendor.
 */
final class VendorEditorView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorEditorView.class);

  private final VendorEditorModel model;

  private JComponent codeField;
  private JComponent nameField;
  private JTextField tagsField;
  private JComponent vatIDField;

  private JComponent line1Field;
  private JComponent line2Field;
  private JComponent street1Field;
  private JComponent street2Field;
  private JComponent zipCodeField;
  private JComponent cityField;

  private JComponent help;

  // Instance Creation ******************************************************

  VendorEditorView(VendorEditorModel model) {
    this.model = model;
  }

  // Initialization *********************************************************

  private void initComponents() {
    codeField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_CODE));
    nameField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_NAME));
    tagsField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_COMMA_SEPARATED_TAGS));
    Options.setSelectOnFocusGainEnabled(tagsField, Boolean.FALSE);
    vatIDField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_VAT_ID));

    line1Field = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_LINE1));
    line2Field = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_LINE2));
    line2Field.getAccessibleContext().setAccessibleName(RESOURCES.getString("address.name2"));
    street1Field = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_STREET1));
    street2Field = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_STREET2));
    street2Field.getAccessibleContext().setAccessibleName(RESOURCES.getString("address.street2"));
    zipCodeField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_ZIP_CODE));
    zipCodeField.getAccessibleContext().setAccessibleName(RESOURCES.getString("address.zip"));
    cityField = BasicComponentFactory.createTextField(model.getBufferedModel(Vendor.PROPERTY_CITY));
    cityField.getAccessibleContext().setAccessibleName(RESOURCES.getString("address.city"));
    help = new HelpLink(RESOURCES.getString("address.help.text"), RESOURCES.getString("address.help.url"));
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    TabbedFormPane pane = new TabbedFormPane(model);
    pane.addTab("General", buildGeneralTab());
    pane.addTab("Communication", buildCommunicationTab());
    // pane.setInitialFocusOwner(codeField);
    // pane.setInitialFocusRequested(false);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }

  private JComponent buildGeneralTab() {
    FormLayout layout = new FormLayout("left:pref, $lcgap, 40dlu, 2dlu, 80dlu, 6dlu, 116dlu, 0:grow",
        "p, $lg, p, $lg, p, $lg, p, 21dlu, " + "p, $lg, p, $lg, p, $lg, p, $lg, p, $lg, p, 9dlu," + "p, 14dlu:grow, "
            + "p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBorder(Borders.DLU14_BORDER);
    builder.addI15dLabel("vendor.code", CC.xy(1, 1));
    builder.add(codeField, CC.xyw(3, 1, 3));
    builder.addI15dLabel("vendor.name", CC.xy(1, 3));
    builder.add(nameField, CC.xyw(3, 3, 5));
    builder.addI15dLabel("vendor.tags", CC.xy(1, 5));
    builder.add(tagsField, CC.xyw(3, 5, 5));
    builder.addI15dLabel("vendor.vatID", CC.xy(1, 7));
    builder.add(vatIDField, CC.xyw(3, 7, 3));

    builder.addI15dLabel("address.name", CC.xy(1, 11));
    builder.add(line1Field, CC.xyw(3, 11, 5));
    builder.add(line2Field, CC.xyw(3, 13, 5));
    builder.addI15dLabel("address.street", CC.xy(1, 15));
    builder.add(street1Field, CC.xyw(3, 15, 5));
    builder.add(street2Field, CC.xyw(3, 17, 5));
    builder.addI15dLabel("address.zipCity", CC.xy(1, 19));
    builder.add(zipCodeField, CC.xy(3, 19));
    builder.add(cityField, CC.xy(5, 19));

    builder.add(help, CC.xyw(1, 21, 8, "left, center"));

    builder.add(buildValidationFeedback(), CC.xyw(1, 23, 8));

    return builder.getPanel();
  }

  private JComponent buildValidationFeedback() {
    return DesktopUtils.buildValidationFeedbackPanel("Der Code muss angegeben werden.",
        "Der Name wird bereits verwendet.", "Die USt.-ID soll mehr als 12 Zeichen haben.", null);
  }

  private JComponent buildCommunicationTab() {
    JPanel panel = new JPanel(null);
    panel.setOpaque(false);
    return panel;
  }

}