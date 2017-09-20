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
    return new String[] { RESOURCES.getString("purchase.table.vendorNumber"), RESOURCES.getString(
        "purchase.table.itemNumber"), RESOURCES.getString("purchase.table.itemPrice") };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    PurchaseBean purchase = getRow(rowIndex);
    switch (columnIndex) {
    case 0:
      return purchase.getVendorNumber();

    case 1:
      return purchase.getItemNumber();
    case 2:

    case 3:
      return purchase.getItemPrice();

    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }

}
