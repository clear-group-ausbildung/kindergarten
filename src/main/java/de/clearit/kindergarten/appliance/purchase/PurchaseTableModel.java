package de.clearit.kindergarten.appliance.purchase;

import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

public class PurchaseTableModel extends AbstractTableAdapter<PurchaseBean> {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseTableModel.class);

  public PurchaseTableModel(ListModel<?> listModel) {
    super(listModel, getColumnNames());
  }

  // Spaltenueberschrift
  private static String[] getColumnNames() {
    return new String[] { RESOURCES.getString("purchase.table.vendorNumber"), RESOURCES.getString(
        "purchase.table.vendorName"), RESOURCES.getString("purchase.table.itemNumber"), RESOURCES.getString(
            "purchase.table.itemPrice") };
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    PurchaseBean purchase = getRow(rowIndex);
    VendorBean vendorOfPurchase = VendorService.getInstance().findByVendorNumber(purchase.getVendorNumber());

    switch (columnIndex) {
    case 0:
      return purchase.getVendorNumber();
    case 1:
      return vendorOfPurchase.getVendorNameDisplayString();
    case 2:
      return purchase.getItemNumber();
    case 3:
      return purchase.getItemPrice();

    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }

}