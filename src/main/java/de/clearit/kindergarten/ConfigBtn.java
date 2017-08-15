package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ConfigBtn extends JPanel {

  private JButton importBtn;
  private JButton exportBtn;

  public ConfigBtn() {
    importBtn = new JButton("Import");
    exportBtn = new JButton("Export");

    importBtn.setPreferredSize(new Dimension(200, 35));
    exportBtn.setPreferredSize(new Dimension(200, 35));

    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.gridx = 0;
    gc.gridy = 0;
    add(importBtn, gc);

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.gridx = 0;
    gc.gridy = 1;
    add(exportBtn, gc);
  }
  
}
