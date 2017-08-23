package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class VerkaeuferHomeComponent extends AbstractComponent {

  private JButton neuButton;
  private JButton configButton;
  private JButton deleteButton;
  private TableComponent tableComponent;

  // Instance Creation ******************************************************

  public VerkaeuferHomeComponent() {
    super();
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    JPanel contentPanel = new JPanel();

    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    /// grid fuer Buttons ///

    contentPanel.add(tableComponent.getPanel(), BorderLayout.CENTER);

    gc.weightx = 1;
    gc.weighty = 0;
    gc.anchor = GridBagConstraints.LAST_LINE_START;
    gc.insets = new Insets(0, 5, 0, 0);
    gc.gridx = 0;
    gc.gridy = 1;
    contentPanel.add(neuButton, gc);

    gc.weightx = 1;
    gc.weighty = 0;
    gc.anchor = GridBagConstraints.LAST_LINE_START;
    gc.insets = new Insets(0, 5, 0, 0);
    gc.gridx = 1;
    gc.gridy = 1;
    contentPanel.add(configButton, gc);

    gc.weightx = 1;
    gc.weighty = 0;
    gc.anchor = GridBagConstraints.LAST_LINE_START;
    gc.insets = new Insets(0, 5, 0, 0);
    gc.gridx = 2;
    gc.gridy = 1;
    contentPanel.add(deleteButton, gc);

    return contentPanel;
  }

  // Initialization *********************************************************

  private void initComponents() {
    neuButton = new JButton("Neu");
    neuButton.setPreferredSize(new Dimension(100, 20));

    configButton = new JButton("Bearbeiten");
    configButton.setPreferredSize(new Dimension(100, 20));

    deleteButton = new JButton("Loeschen");
    deleteButton.setPreferredSize(new Dimension(100, 20));

    tableComponent = new TableComponent();
  }
}
