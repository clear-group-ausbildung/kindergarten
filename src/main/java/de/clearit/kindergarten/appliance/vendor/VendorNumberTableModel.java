package de.clearit.kindergarten.appliance.vendor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;

/**
 * 
 * @author Kenan.Horoz
 *TableModle for the table in VendorEditorView 
 *containing VendorNumbers
 */
  public class VendorNumberTableModel extends AbstractTableAdapter<VendorNumberBean>{

  private static final long serialVersionUID = 1L;
  private ListModel<VendorNumberBean> listModel;
  private VendorEditorModel model;
  private List<VendorNumberBean> vendorNumberList = new ArrayList<VendorNumberBean>();

  public VendorNumberTableModel(ListModel<VendorNumberBean> listModel, VendorEditorModel model) {
    super(listModel, getColumnNames());
    this.model = model;
    this.listModel = listModel;
  }

  private static String[] getColumnNames() {
    return new String[] { "zugewiesene Verkäufernummern" };
  }
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
	 VendorNumberBean vbn = listModel.getElementAt(rowIndex);
	 
	 vendorNumberList.add(vbn);
	 
	 String vendorNumber = new Integer(vbn.getVendorNumber()).toString();
	 
	 VendorBean vendor = model.getVendor();
	 vbn.setVendorId(vendor.getId());
	 
	 vendor.setVendorNumbers(vendorNumberList);
  
   // String vendorNumber = getRow(rowIndex);
    switch (columnIndex) {
    case 0:
      return vendorNumber;
    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
  }
  
  

}