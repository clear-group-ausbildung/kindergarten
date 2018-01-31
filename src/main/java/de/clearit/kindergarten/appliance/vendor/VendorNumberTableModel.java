package de.clearit.kindergarten.appliance.vendor;
import javax.swing.ListModel;
import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import de.clearit.kindergarten.domain.VendorBean;

/**
 * 
 * @author Kenan.Horoz
 *TableModle for the table in VendorEditorView 
 *containing VendorNumbers
 */
  public class VendorNumberTableModel extends AbstractTableAdapter<String> {

  private static final long serialVersionUID = 1L;
  private static final ResourceMap RESOURCES = Application.getResourceMap(VendorHomeView.class);

  public VendorNumberTableModel(ListModel<?> listModel) {
    super(listModel, getColumnNames());
  }

  private static String[] getColumnNames() {
    return new String[] { RESOURCES.getString("vendor.table.vendorNumber") };
  }
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    String vendorNumber = getRow(rowIndex);
    switch (columnIndex) {
    case 0:
      return vendorNumber;
    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }
  
  

}