package de.clearit.kindergarten;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class FewPanel extends JPanel {

  private Toolbar toolbar;

  public FewPanel() {
    toolbar = new Toolbar();

    setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    /// Positionierung der Toolbar///

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.anchor = GridBagConstraints.FIRST_LINE_START;
    gc.insets = new Insets(5, 5, 5, 5);
    gc.gridx = 0;
    gc.gridy = 0;
    add(toolbar, gc);
  }
  
}
