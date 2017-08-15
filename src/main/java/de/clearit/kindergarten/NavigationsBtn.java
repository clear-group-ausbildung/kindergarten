package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationsBtn extends JPanel {

  private JButton verkaufer;
  private JButton einkauf;
  private JButton abrechnung;
  private JButton drucken;

  public NavigationsBtn() {
    verkaufer = new JButton("Verkäufer");
    einkauf = new JButton("Einkauf");
    abrechnung = new JButton("Abrechnung");
    drucken = new JButton("Drucken");

    verkaufer.setPreferredSize(new Dimension(200, 35));
    einkauf.setPreferredSize(new Dimension(200, 35));
    abrechnung.setPreferredSize(new Dimension(200, 35));
    drucken.setPreferredSize(new Dimension(200, 35));


    setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 0;
    add(verkaufer, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 1;
    add(einkauf, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 2;
    add(abrechnung, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 3;
    add(drucken, grid);
  }
  
}
