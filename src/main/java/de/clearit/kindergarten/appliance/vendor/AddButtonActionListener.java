package de.clearit.kindergarten.appliance.vendor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jgoodies.binding.list.SelectionInList;

import de.clearit.kindergarten.domain.VendorNumberBean;
/**
 * 
 * @author Kenan.Horoz
 *Action Listener for the "Hinzufügen" button 
 *Adds the user input from Verkäufernummer to the table below
 */
public class AddButtonActionListener implements ActionListener {
    private SelectionInList<VendorNumberBean> selectionList;
 	private VendorEditorView view;
   
    public AddButtonActionListener(SelectionInList<VendorNumberBean> selectionList, VendorEditorView view) {
        this.selectionList = selectionList;
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if ( Pattern.matches("^(?=\\d*[1-9])\\d+$", ((JTextField)view.getVendorNumberField()).getText()) ){
    		VendorNumberBean vnb = new VendorNumberBean();
    		vnb.setVendorNumber(Integer.parseInt(((JTextField) view.getVendorNumberField()).getText().toString()));
    		addToList(vnb);	
    	}else{
        	JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie eine gültige Verkäufernummer ein");
        }

        ((JTextField)view.getVendorNumberField()).setText("");
    }

    private void addToList(VendorNumberBean vendorNumberBean) {
    	selectionList.getList().add(vendorNumberBean);
    }
    
    public SelectionInList<VendorNumberBean> getSelectionList() {
    	return selectionList;
    }
    
}
