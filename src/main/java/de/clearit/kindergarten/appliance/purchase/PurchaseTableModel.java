package de.clearit.kindergarten.appliance.purchase;

import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchaseTableModel extends AbstractTableAdapter<PurchaseBean> {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseTableModel.class);

  public PurchaseTableModel(ListModel<?> listModel) {
    super(listModel, getColumnNames());
  }

  private static String[] getColumnNames() {
    return new String[] { RESOURCES.getString("purchase.table.itemNumber"), RESOURCES.getString(
        "purchase.table.itemPrice"), RESOURCES.getString("purchase.table.vendor") };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    PurchaseBean purchase = getRow(rowIndex);
    switch (columnIndex) {
    case 0:
      return purchase.getItemNumber();

    case 1:
      return purchase.getItemPrice();
    case 2:

    case 3:
      String result = "";
      // VendorBean vendor =
      // VendorService.getInstance().getById(purchase.getVendorId());
      // if (vendor != null) {
      // result += vendor.getLastName();
      // result += ", ";
      // result += vendor.getFirstName();
      // }
      return result;

    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }

}
