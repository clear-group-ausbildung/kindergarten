package de.clearit.kindergarten.appliance.vendor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

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
	 VendorNumberBean vnb = listModel.getElementAt(rowIndex); 
	 vendorNumberList = getVendorNumberList(); 
	 vendorNumberList.add(vnb);
	 String vendorNumber = new Integer(vnb.getVendorNumber()).toString();

    switch (columnIndex) {
    case 0:
      return vendorNumber;
    default:
      throw new IllegalStateException("Can't handle column index " + columnIndex);
    }
        
  }
  
  public List<VendorNumberBean> getVendorNumberList(){
  	return vendorNumberList;
  }
  

}