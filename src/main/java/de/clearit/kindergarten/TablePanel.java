package de.clearit.kindergarten;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

public class TablePanel extends JPanel {
  
  private PersonTableModel tableModel;
  private JTable table;
  
  public TablePanel() {
    
    tableModel = new PersonTableModel();
    table = new JTable(tableModel);
    
    setLayout(new BorderLayout());
    
    add(table, BorderLayout.CENTER);
    
  }
}
