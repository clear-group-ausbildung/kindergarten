package de.clearit.kindergarten;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;

public class TableComponent extends AbstractComponent {

  private PersonTableModel tableModel;
  private JTable table;

  // Instance Creation ******************************************************

  public TableComponent() {
    super();
  }

  // Initialization *********************************************************

  private void initComponents() {
    tableModel = new PersonTableModel();
    table = new JTable(tableModel);
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.add(table, BorderLayout.CENTER);
    return contentPanel;
  }
}
