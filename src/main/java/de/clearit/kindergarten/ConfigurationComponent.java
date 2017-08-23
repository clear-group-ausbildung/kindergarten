package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ConfigurationComponent extends AbstractComponent {

  private JButton importButton;
  private JButton exportButton;

  // Instance Creation ******************************************************

  public ConfigurationComponent() {
    super();
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.gridx = 0;
    gc.gridy = 0;
    contentPanel.add(importButton, gc);

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.gridx = 0;
    gc.gridy = 1;
    contentPanel.add(exportButton, gc);

    return contentPanel;
  }

  // Initialization *********************************************************

  private void initComponents() {
    importButton = new JButton("Import");
    importButton.setPreferredSize(new Dimension(200, 35));

    exportButton = new JButton("Export");
    exportButton.setPreferredSize(new Dimension(200, 35));
  }

}
