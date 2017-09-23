package de.clearit.kindergarten.appliance.vendor;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;

import de.clearit.kindergarten.application.KindergartenComponentFactory;

class VendorNumberChooserView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorNumberChooserView.class);

  private final VendorNumberChooserModel model;

  private JComponent vendorBox;
  private JButton addButton;
  private JList<Integer> vendorNumberList;
  private JButton removeButton;

  public VendorNumberChooserView(VendorNumberChooserModel model) {
    super();
    this.model = model;
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();
    FormPane pane = new FormPane(buildContent(), model);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }

  @SuppressWarnings("unchecked")
  private void initComponents() {
    addButton = new JButton(model.getAction(VendorNumberChooserModel.ACTION_ADD_VENDOR_NUMBER));
    addButton.addActionListener(e -> vendorBox.requestFocusInWindow());
    vendorBox = BasicComponentFactory.createComboBox(model.getVendorList(), new VendorListCellRenderer());
    vendorNumberList = KindergartenComponentFactory.createList(model.getSelectionInList(),
        new VendorListCellRenderer());
    removeButton = new JButton(model.getAction(VendorNumberChooserModel.ACTION_REMOVE_VENDOR_NUMBER));
  }

  private JComponent buildContent() {
    FormLayout layout = new FormLayout("150dlu, 9dlu, p", "2*([14dlu,p]), $lg, fill:100dlu:grow, p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);

    builder.setBackground(Color.WHITE);
    builder.addI15dLabel("vendor.vendorNumber", CC.xy(1, 1));
    builder.add(vendorBox, CC.xy(1, 2));
    builder.add(buildAction(addButton), CC.xy(3, 2));
    builder.add(new JScrollPane(vendorNumberList), CC.xyw(1, 4, 3));
    builder.add(buildAction(removeButton), CC.xy(1, 5));

    return builder.getPanel();
  }

  private JComponent buildAction(JButton button) {
    final ButtonBarBuilder2 builder = new ButtonBarBuilder2();
    builder.addButton(button);
    return builder.getPanel();
  }

}
