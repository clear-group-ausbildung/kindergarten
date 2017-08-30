package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EinkaufsErfassungsPanel extends JPanel{
  private JLabel ueberschrift;
  private JLabel summe;
  private JLabel summenBildung;
  
  private JPanel uebersichtsPanel;
  private JLabel anzahlVkLabel;
  private JLabel anzahlVk;
  private JLabel anzahlWareLabel;
  private JLabel anzahlWare;
  private JLabel umsatzLabel;
  private JLabel umsatz;
  private JLabel gewinnLabel;
  private JLabel gewinn;
  private JLabel auszahlungLabel;
  private JLabel auszahlung;
  
  private JPanel mainPanel;
  private JPanel erfassungsPanel;
  private JPanel topPanel;
  
  public EinkaufsErfassungsPanel() {
    
    // Panels
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(500, 560));
    
    topPanel = new JPanel();
    erfassungsPanel = new JPanel();
    
    // Setze Grid auf Main Panel
    topPanel.setLayout(new GridBagLayout());
    erfassungsPanel.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    
    // Initialisiere
    ueberschrift = new JLabel("Einkäufe");
    summe = new JLabel("Summe: ");
    summenBildung = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    // Initialisierung für die Übersicht AutoSumme und co soll hier dargestellt werden
    
    uebersichtsPanel = new JPanel();
    uebersichtsPanel.setPreferredSize(new Dimension(200, 100));
    anzahlVkLabel = new JLabel("Anzahl Verkäufer:");
    anzahlVk = new JLabel(Integer.toString(new Integer(0)));
    
    anzahlWareLabel = new JLabel("Anzahl Ware:");
    anzahlWare = new JLabel(Integer.toString(new Integer(0)));
    
    umsatzLabel = new JLabel("Umsatz:");
    umsatz = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    gewinnLabel = new JLabel("Gewinn:");
    gewinn = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    auszahlungLabel = new JLabel("Auszahlung:");
    auszahlung = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    uebersichtsPanel.setLayout(new GridBagLayout());
    uebersichtsPanel.setBorder(BorderFactory.createEtchedBorder());
    
    //Grid für die Übersicht
    
    // First Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 0;
    grid.gridy = 0;
    uebersichtsPanel.add(anzahlVkLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 1;
    grid.gridy = 0;
    uebersichtsPanel.add(anzahlVk, grid);
    
    //Second Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); 
    grid.gridwidth = 1;
    grid.gridx = 0;
    grid.gridy = 1;
    uebersichtsPanel.add(anzahlWareLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 1;
    grid.gridy = 1;
    uebersichtsPanel.add(anzahlWare, grid);
    
    //Third Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); 
    grid.gridwidth = 1;
    grid.gridx = 0;
    grid.gridy = 2;
    uebersichtsPanel.add(umsatzLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 1;
    grid.gridy = 2;
    uebersichtsPanel.add(umsatz, grid);
    
    //Fourth Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); 
    grid.gridwidth = 1;
    grid.gridx = 0;
    grid.gridy = 3;
    uebersichtsPanel.add(gewinnLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 1;
    grid.gridy = 3;
    uebersichtsPanel.add(gewinn, grid);
    
    //Fifth Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); 
    grid.gridwidth = 1;
    grid.gridx = 0;
    grid.gridy = 4;
    uebersichtsPanel.add(auszahlungLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.insets = new Insets(0, 5, 5, 5); //top left bottom right
    grid.gridwidth = 1;
    grid.gridx = 1;
    grid.gridy = 4;
    uebersichtsPanel.add(auszahlung, grid);
    
    // Setze mit Grid alles auf das mainPanel
    
    // First Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.gridwidth = 4; /// TODO
    grid.gridx = 0;
    grid.gridy = 0;
    topPanel.add(ueberschrift, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_END;
    grid.gridwidth = 1;
    grid.gridheight = 4;
    grid.gridx = 5; /// TODO
    grid.gridy = 0;
    topPanel.add(uebersichtsPanel, grid);
    
    // Second Row
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_END;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 4;
    topPanel.add(summe, grid);
    
    summe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_END;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 1;
    grid.gridy = 4;
    topPanel.add(summenBildung, grid);
    
    
    // Erfassung TopPanel TODO
    
    topPanel.setPreferredSize(new Dimension(495, 150));

    mainPanel.add(topPanel, BorderLayout.NORTH);
    
    // Erfassung BottomPanel TODO
    
    erfassungsPanel.setPreferredSize(new Dimension(495, 400));
    erfassungsPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

    mainPanel.add(erfassungsPanel, BorderLayout.SOUTH);
    
    // Adde das mainPanel
    add(mainPanel);
  }
}
