package de.clearit.kindergarten.appliance.vendor;

import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.clearit.kindergarten.domain.VendorNumberBean;

/**
 * TableModle for the table in VendorEditorView containing VendorNumbers.
 */
public class VendorNumberTableModel extends AbstractTableAdapter<VendorNumberBean> {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorNumberTableModel.class);

  public VendorNumberTableModel(ListModel<?> listModel) {
    super(listModel, getColumnNames());
  }

  private static String[] getColumnNames() {
    return new String[] { RESOURCES.getString("vendor.assignedVendorNumbers") };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    VendorNumberBean vendorNumberBean = getRow(rowIndex);
    if (columnIndex == 0) {
      return vendorNumberBean.getVendorNumber();
    } else {
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }

}