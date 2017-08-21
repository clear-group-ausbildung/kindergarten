package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationsBtn extends JPanel implements ActionListener {

  private JButton verkaufer;
  private JButton einkauf;
  private JButton abrechnung;
  private JButton drucken;
  private VerkaeuferErfassung vk;
  private int clicker;

  public NavigationsBtn() {
    verkaufer = new JButton("Verkäufer");
    einkauf = new JButton("Einkauf");
    abrechnung = new JButton("Abrechnung");
    drucken = new JButton("Drucken");

    verkaufer.setPreferredSize(new Dimension(200, 35));
    einkauf.setPreferredSize(new Dimension(200, 35));
    abrechnung.setPreferredSize(new Dimension(200, 35));
    drucken.setPreferredSize(new Dimension(200, 35));

    verkaufer.addActionListener(this);
    einkauf.addActionListener(this);
    abrechnung.addActionListener(this);
    drucken.addActionListener(this);
    
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
    clicker = 0;
  }
 
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    if(clicker != 1) {
      
      //remove(alles andere von anderen Buttons falls vorhanden);
      
      if(clicked == verkaufer) {
        vk = new VerkaeuferErfassung();
        clicker = 1;
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        /// Menüleiste soll sich nicht verändern ///
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
        
        /// Verkäufer Erfassungs Maske ///
        
        grid.weightx = 1;
        grid.weighty = 0;
        grid.gridx = 1;
        grid.gridy = 0;
        add(vk, grid);
        revalidate();
        
        System.out.println("Verkäufer übersicht!");
      }
    }
    if(clicker != 2) {
      if(clicked == einkauf) {
        
        System.out.println("Einkauf übersicht!");
      }
    }
    if(clicker != 3)
      if(clicked == abrechnung) {
        
        
        System.out.println("Abrechnung übersicht!");
      }
    if(clicker != 4) {
      if(clicked == drucken) {
        
        System.out.println("Drucken!");
      }
    }
  }
}
