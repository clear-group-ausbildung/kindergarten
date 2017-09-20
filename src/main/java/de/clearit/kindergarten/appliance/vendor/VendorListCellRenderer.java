package de.clearit.kindergarten.appliance.vendor;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.clearit.kindergarten.domain.VendorBean;

public class VendorListCellRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
      boolean cellHasFocus) {
    Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    VendorBean vendor = (VendorBean) value;
    setText(vendor == null ? ""
        : (vendor.getVendorNumber() + ": " + vendor.getLastName() + ", " + vendor.getFirstName()));
    return component;
  }

}