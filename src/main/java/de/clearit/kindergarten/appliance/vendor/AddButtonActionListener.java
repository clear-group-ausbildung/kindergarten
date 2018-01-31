package de.clearit.kindergarten.appliance.vendor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jgoodies.binding.list.SelectionInList;
/**
 * 
 * @author Kenan.Horoz
 *Action Listener for the "Hinzufügen" button 
 *Adds the user input from Verkäufernummer to the table below
 */
public class AddButtonActionListener implements ActionListener {
	private JOptionPane validationPane;
    SelectionInList<String> selectionList;
    VendorEditorView view;
   
    public AddButtonActionListener(SelectionInList<String> selectionList, VendorEditorView view) {
        this.selectionList = selectionList;
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	validationPane = new JOptionPane();
    	
    	if ( Pattern.matches("^(?=\\d*[1-9])\\d+$", ((JTextField)view.getVendorNumberField()).getText()) )
    		
    	{
    		selectionList.getList().add(((JTextField)view.getVendorNumberField()).getText());
    	}

        else
        {
        	JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie eine gültige Verkäufernummer ein");
        }

        ((JTextField)view.getVendorNumberField()).setText("");
    }

}