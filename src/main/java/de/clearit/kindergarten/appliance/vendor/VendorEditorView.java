package de.clearit.kindergarten.appliance.vendor;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.pane.form.FormPane;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.builder.I15dPanelBuilder2;
import com.jgoodies.uif2.component.StripedTable;

import de.clearit.kindergarten.desktop.DesktopUtils;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * The editor view for the vendor.
 */
public final class VendorEditorView extends AbstractView  {
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorEditorView.class);
  private final VendorEditorModel model;
  private JComponent vendorNumberField;
  private JComponent firstNameField;
  private JComponent lastNameField;
  private JComponent phoneNumberField;
  private JScrollPane scrollPane; 
  private JTable vendorNumberTable;
  private JButton addBtn;
  private JButton removeBtn;
  SelectionInList<String> vendorSelectionList;
  
 // private final VendorNumberTableModel vendorModel;
  // Instance Creation ******************************************************
  public VendorEditorView(VendorEditorModel model) {
    this.model = model;
  }
  // Initialization *********************************************************
  private void initComponents() {
    vendorNumberField = new JTextField();
	//TODO an neue Datenstruktur anpassen done?
    firstNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_FIRST_NAME));
    lastNameField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_LAST_NAME));
    phoneNumberField = BasicComponentFactory.createTextField(model.getBufferedModel(VendorBean.PROPERTY_PHONE_NUMBER));
    vendorSelectionList = new SelectionInList<String>();
    vendorNumberTable = new StripedTable(new VendorNumberTableModel(vendorSelectionList));
    vendorNumberTable.setSelectionModel(new SingleListSelectionAdapter(vendorSelectionList.getSelectionIndexHolder()));
    scrollPane = new JScrollPane(vendorNumberTable);
    vendorNumberTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    addBtn = new JButton("Hinzuf\u00fcgen");
    addBtn.addActionListener(new AddButtonActionListener(vendorSelectionList, this));
    
    removeBtn = new JButton("Entfernen");
    removeBtn.addActionListener(new DeleteButtonActionListener(vendorSelectionList, this));
  }
  
@Override
  protected JComponent buildPanel() {
    initComponents();
    FormPane pane = new FormPane(buildContent(), model);
    pane.setBackground(RESOURCES.getColor("content.background"));
    return pane;
  }
  
  public JComponent buildContent() {                                             
    FormLayout layout = new FormLayout("150dlu:grow, 16dlu, 70dlu, pref:grow", "2*(p, p, $lg),p, p,30dlu, p , pref , pref , 30dlu ,pref, pref:grow, p");
    I15dPanelBuilder2 builder = new I15dPanelBuilder2(layout, RESOURCES);
    builder.setBorder(Borders.DLU14_BORDER);
    builder.addI15dLabel("vendor.firstName", CC.xy(1, 1));
    builder.add(firstNameField, CC.xy(1, 2));
    builder.addI15dLabel("vendor.lastName", CC.xy(1, 4));
    builder.add(lastNameField, CC.xy(1, 5));
    builder.addI15dLabel("vendor.phoneNumber", CC.xy(1, 7));
    builder.add(phoneNumberField, CC.xy(1, 8));
    builder.add(buildValidationFeedback(), CC.xywh(3, 1, 2, 8));
    builder.addI15dLabel("vendor.vendorNumber", CC.xy(1, 10));
    builder.add(vendorNumberField, CC.xy(1, 12));
    builder.add(addBtn, CC.xy(3, 12));
    builder.add(scrollPane, CC.xywh(1, 14, 1, 3));
    builder.add(removeBtn, CC.xy(3, 16));
    return builder.getPanel();
  }
  private JComponent buildValidationFeedback() {
    return DesktopUtils.buildValidationFeedbackPanel("Die Verk\u00e4ufernummer muss angegeben werden.",
        "Der Vorname ist optional.", "Der Nachname muss angegeben werden.", "Die Telefonnummer ist optional.");
  }
  
  public JComponent getVendorNumberField()
  {
		return vendorNumberField;
  }
  
  public void setVendorNumberField(JComponent vendorNumberField)
  {
		this.vendorNumberField = vendorNumberField;
  }
  
}