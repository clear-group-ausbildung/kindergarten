package de.clearit.kindergarten.appliance.vendor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.binding.list.SelectionInList;

public class DeleteButtonActionListener implements ActionListener {
       SelectionInList<String> selectionList;
       VendorEditorView view;
    
    public DeleteButtonActionListener(SelectionInList<String> selectionList, VendorEditorView view) {
        this.selectionList = selectionList;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        selectionList.getList().remove(selectionList.getSelectionIndex());

    }

}