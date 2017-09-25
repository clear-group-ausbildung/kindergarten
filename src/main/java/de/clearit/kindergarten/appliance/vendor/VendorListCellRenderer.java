package de.clearit.kindergarten.appliance.vendor;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.google.common.base.Strings;
import de.clearit.kindergarten.domain.VendorBean;

public class VendorListCellRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                boolean cellHasFocus) {
    Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    VendorBean vendor = (VendorBean) value;
    setText(formatVendor(vendor));
    return component;
  }

  private String formatVendor(VendorBean vendor) {
    StringBuilder builder = new StringBuilder();
    if (vendor != null) {
      if (vendor.getVendorNumber()!=null){
        builder.append(vendor.getVendorNumber());
        builder.append(": ");
      }
      builder.append(vendor.getLastName());
      if (!Strings.isNullOrEmpty(vendor.getFirstName())) {
        builder.append(", ");
        builder.append(vendor.getFirstName());
      }
    }
    return builder.toString();
  }

}