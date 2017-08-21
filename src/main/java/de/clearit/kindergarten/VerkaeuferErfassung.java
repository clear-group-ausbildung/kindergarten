package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class VerkaeuferErfassung extends JPanel{
  private JButton neu;
  private JButton config;
  private JButton delete;
  private TablePanel table;
  
    public VerkaeuferErfassung() {
      
      neu = new JButton("Neu");
      config = new JButton("Bearbeiten");
      delete = new JButton("Löschen");
      
      neu.setPreferredSize(new Dimension(100, 20));
      config.setPreferredSize(new Dimension(100, 20));
      delete.setPreferredSize(new Dimension(100, 20));
      
      table = new TablePanel();
      add(table, BorderLayout.CENTER);
      
      setLayout(new GridBagLayout());
      GridBagConstraints gc = new GridBagConstraints();
      
      /// grid für Buttons ///
      
      gc.weightx = 1;
      gc.weighty = 0;
      gc.anchor = GridBagConstraints.LAST_LINE_START;
      gc.insets = new Insets(0, 5, 0, 0);
      gc.gridx = 0;
      gc.gridy = 1;
      add(neu, gc);
      
      gc.weightx = 1;
      gc.weighty = 0;
      gc.anchor = GridBagConstraints.LAST_LINE_START;
      gc.insets = new Insets(0, 5, 0, 0);
      gc.gridx = 1;
      gc.gridy = 1;
      add(config, gc);
      
      gc.weightx = 1;
      gc.weighty = 0;
      gc.anchor = GridBagConstraints.LAST_LINE_START;
      gc.insets = new Insets(0, 5, 0, 0);
      gc.gridx = 2;
      gc.gridy = 1;
      add(delete, gc);
      
    }
}
