package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class EinkaufsErfassungsPanel extends JPanel implements ActionListener{
  private JLabel ueberschrift;
  private JLabel summe;
  private JLabel summenBildung;
  private Double summeEinkauf = 0.00;
  
  private JPanel uebersichtsPanel;
  private JLabel anzahlVkLabel;
  private Integer verkaeufe = 0;
  private JLabel anzahlVk;
  private JLabel anzahlArtikelLabel;
  private Integer anzahlArtikel = 0;
  private JLabel anzahlWare;
  private JLabel umsatzLabel;
  private Double countSumme = 0.00;
  private JLabel umsatz;
  private JLabel gewinnLabel;
  private Double prozentualerWert = 0.00;
  private JLabel gewinn;
  private JLabel auszahlungLabel;
  private JLabel auszahlung;
  
  private JPanel mainPanel;
  private JPanel erfassungsPanel;
  private JPanel topPanel;
  
  private JLabel verkaeuferIdLabel;
  private JTextField verkaeuferIdField;
  private JLabel artikelNummerLabel;
  private JTextField artikelNummerField;
  private JLabel preisLabel;
  private JTextField preisField;
  private JButton add;
  
  private JTable tabelle;
  private DefaultTableModel uebersichtsTabelle;
  private static final String [] spalten = {"Verkäufer-ID", "Artikelnummer", "Preis €"};
  private Object[][] einkaufsDaten;
  private JScrollPane scrollPane;
  private JButton finish;
  
  public EinkaufsErfassungsPanel() {
    
    // Panels
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(500, 560));
    
    topPanel = new JPanel();
    erfassungsPanel = new JPanel();
    
    // Setze Grid auf Panel
    topPanel.setLayout(new GridBagLayout());
    erfassungsPanel.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    
    // Erfassung TopPanel
    
    topPanel.setPreferredSize(new Dimension(495, 150));
    topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
    
    // Initialisiere
    ueberschrift = new JLabel("Einkäufe");
    summe = new JLabel("Summe: ");
    summenBildung = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    // Initialisierung für die Übersicht AutoSumme und co soll hier dargestellt werden
    
    uebersichtsPanel = new JPanel();
    uebersichtsPanel.setPreferredSize(new Dimension(200, 100));
    anzahlVkLabel = new JLabel("Anzahl Verkäufe:");
    anzahlVk = new JLabel(Integer.toString(new Integer(0)));
    
    anzahlArtikelLabel = new JLabel("Anzahl Artikel:");
    anzahlWare = new JLabel(Integer.toString(new Integer(0)));
    
    umsatzLabel = new JLabel("Umsatz:");
    umsatz = new JLabel(Double.toString(new Double(0.00)) + " €");
    
    gewinnLabel = new JLabel("Gewinn:");
    gewinnLabel.setForeground(Color.RED);
    gewinnLabel.setFont((new Font("Dialog", Font.BOLD, 12)));
    gewinn = new JLabel(Double.toString(new Double(0.00)) + " €"); // -20% an den Kindergarten
    gewinn.setForeground(Color.RED);
    gewinn.setFont((new Font("Dialog", Font.BOLD, 12)));
    
    auszahlungLabel = new JLabel("Auszahlung:");
    auszahlung = new JLabel(Double.toString(new Double(0.00)) + " €");
    auszahlung.setFont((new Font("Dialog", Font.BOLD, 12)));
    
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
    uebersichtsPanel.add(anzahlArtikelLabel, grid);
    
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
    
    // Setze mit Grid alles auf das Panel
    
    // First Row
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.gridwidth = 4;
    grid.gridx = 0;
    grid.gridy = 0;
    topPanel.add(ueberschrift, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_END;
    grid.gridwidth = 1;
    grid.gridheight = 4;
    grid.gridx = 5;
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
    
    mainPanel.add(topPanel, BorderLayout.NORTH);
    
    // Erfassung BottomPanel
    
    finish = new JButton("Fertig");
    finish.addActionListener(this);
    erfassungsPanel.setPreferredSize(new Dimension(495, 400));
    
    verkaeuferIdLabel = new JLabel("Verkäufer ID");
    verkaeuferIdField = new JTextField(10);
    
    artikelNummerLabel = new JLabel("Artikelnummer");
    artikelNummerField = new JTextField(10);
    
    preisLabel = new JLabel("Preis:");
    preisField = new JTextField(10);
    
    uebersichtsPanel = new JPanel();
    uebersichtsPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
    
    // JTable für die Übersicht der pro Einkauf erfassten Artikel
    
    uebersichtsTabelle = new DefaultTableModel(einkaufsDaten, spalten);
    
    tabelle = new JTable(uebersichtsTabelle) {
      public boolean isCellEditable(int x, int y) {
        return false;
      }
    };
    
    tabelle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tabelle.getColumnModel().getColumn(0).setPreferredWidth(100);
    tabelle.getColumnModel().getColumn(1).setPreferredWidth(117);
    tabelle.setShowGrid(true);
    tabelle.setShowVerticalLines(true);
    
    JTableHeader header = tabelle.getTableHeader();
    tabelle.setBackground(new Color(252,252,252));
    tabelle.setEnabled(true);
    header.setFont(new Font("Dialog", Font.BOLD, 12));
    header.setBackground(new Color(253, 249, 253));
    header.setForeground(Color.BLACK);
    
    tabelle.setGridColor(Color.DARK_GRAY);
    tabelle.setPreferredSize(new Dimension(230, 100)); //width, height
    tabelle.getAutoscrolls();
    tabelle.setPreferredScrollableViewportSize(tabelle.getPreferredSize());
    tabelle.setFillsViewportHeight(true);
    
    uebersichtsPanel.add(new JScrollPane(tabelle));
    
    // First Row
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 0;
    erfassungsPanel.add(verkaeuferIdLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 2;
    grid.gridy = 0;
    erfassungsPanel.add(verkaeuferIdField, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.HORIZONTAL;
    grid.anchor = GridBagConstraints.LINE_END;
    grid.gridwidth = 4;
    grid.gridheight = 4;
    grid.gridx = 3;
    grid.gridy = 0;
    erfassungsPanel.add(uebersichtsPanel, grid);
    
    // Second Row
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 1;
    erfassungsPanel.add(artikelNummerLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 2;
    grid.gridy = 1;
    erfassungsPanel.add(artikelNummerField, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    // Third Row
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 2;
    erfassungsPanel.add(preisLabel, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 2;
    grid.gridy = 2;
    erfassungsPanel.add(preisField, grid);
    
    // Fourth Row
    
    add = new JButton("Hinzufügen");
    add.addActionListener(this);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.fill = GridBagConstraints.NONE;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 3;
    erfassungsPanel.add(add, grid);
    
    grid.anchor = GridBagConstraints.LINE_END;
    grid.fill = GridBagConstraints.NONE;
    grid.insets = new Insets(0, 0, 0, 12);
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 3;
    grid.gridy = 4;
    erfassungsPanel.add(finish, grid);
    
    mainPanel.add(erfassungsPanel, BorderLayout.CENTER);

    // Adde das mainPanel
    add(mainPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    if(clicked == add) {
      Double summe = 0.00;
      
      String verkaeufer = verkaeuferIdField.getText();
      Integer artikelnr = Integer.parseInt(artikelNummerField.getText());
      Double preis = Double.parseDouble(preisField.getText());
      
      uebersichtsTabelle.addRow(new Object[]{verkaeufer, artikelnr, preis});
      
      verkaeuferIdField.setText("");
      artikelNummerField.setText("");
      preisField.setText("");
      
      for(int i = 0; i < tabelle.getRowCount(); i++) {
        summe += (Double) tabelle.getValueAt(i, 2);
      }
      setSummeEinkauf(summe);
      summenBildung.setText(summe.toString() + " €");
      
      revalidate();
    }
    if(clicked == finish) {
      countSumme += getSummeEinkauf();
      
      // Anzeige für die Anzahl der bereits verkauften Artikel (GESAMMT)
      for(int i = 0; i < tabelle.getRowCount(); i++) {
        anzahlArtikel++;
      }
      anzahlArtikel.toString();
      anzahlWare.setText(anzahlArtikel.toString());
      
      // Anzeige für wieviele die Anzahl der Verkäufe
      verkaeufe++;
      anzahlVk.setText(verkaeufe.toString());
      umsatz.setText(countSumme.toString() + " €");
      
      // Abzug von 20% an den Kindergarten
      prozentualerWert = (countSumme*20/100);
      gewinn.setText(prozentualerWert.toString() + " €");
      
      // Anzeige Auszahlung an die Verkäufer
      prozentualerWert = (countSumme-(countSumme*20/100));
      auszahlung.setText(prozentualerWert.toString() + " €");
      
      // Leere Tabelle
      uebersichtsTabelle.setRowCount(0);
      revalidate();
    }
  }
  
  public Double getSummeEinkauf() {
    return summeEinkauf;
  }

  public void setSummeEinkauf(Double summeEinkauf) {
    this.summeEinkauf = summeEinkauf;
  }
}
