package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import de.clearit.kindergarten.appliance.purchase.PurchaseEditorModel;

public class ButtonActionlistener implements ActionListener{

	JComponent textfeld;
	PurchaseEditorModel model;
	
	public ButtonActionlistener(JComponent textfeld, PurchaseEditorModel model) {
		this.textfeld = textfeld;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(((JTextField)textfeld).getText() != null) {
			model.addLineItem(null);
		}
		
	}

}
