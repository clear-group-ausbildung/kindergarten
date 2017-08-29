package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
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

public class VerkaeuferErfassungsPanel extends JPanel implements ActionListener{
  private JButton neu;
  private JButton config;
  private JButton delete;
  private JButton save;
  private JPanel verkaeuferErfassungsPanel;
  
  private String textVorname;
  private String textNachname;
  private Integer nummer;
  private String textzusatz;
  
  private final JLabel vorname = new JLabel("Vorname:");
  private JTextField textFieldVorname = new JTextField(14);
  private final JLabel nachname = new JLabel("Familienname:");
  private JTextField textFieldNachname = new JTextField(14);
  private final JLabel telefon = new JLabel("Telefon:");
  private JTextField telefonField = new JTextField(14);
  private final JLabel zusatz = new JLabel("Zusatz:");
  private JTextField zusatzField = new JTextField(14);
  private Checkbox checkBox;
  
  private JTable tabelle;
  private DefaultTableModel dataTabelle;
  private JPanel tablePanel;
  private static final String [] spalten = {"Vorname", "Familienname", "Telefon", "Zusatz"};
  private Object[][] verkaeuferDaten;
  private int indexTabelle = 0;
  
  int clickCounter = 0;
  
    public VerkaeuferErfassungsPanel() {
      
      verkaeuferErfassungsPanel = new JPanel();
      //verkaeuferErfassungsPanel.setBackground(Color.LIGHT_GRAY);
      
      verkaeuferErfassungsPanel.setLayout(new GridBagLayout());
      GridBagConstraints gc = new GridBagConstraints();
      
      
      // Tabelle für die Anzeige der Erfassten Verkäufer
      
      tablePanel = new JPanel();
      
      dataTabelle = new DefaultTableModel(verkaeuferDaten, spalten);
      
      tabelle = new JTable(dataTabelle);
      tabelle.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      tabelle.setShowGrid(true);
      tabelle.setShowVerticalLines(true);
      
      JTableHeader header = tabelle.getTableHeader();
      tabelle.setBackground(new Color(252,252,252));
      tabelle.setEnabled(true);
      header.setFont(new Font("Dialog", Font.BOLD, 12));
      header.setBackground(new Color(212, 212, 212));
      header.setForeground(Color.BLACK);
      
      tabelle.setGridColor(Color.DARK_GRAY);
      tabelle.setPreferredSize(new Dimension(500,500)); //width, height
      tabelle.getAutoscrolls();
      tabelle.setPreferredScrollableViewportSize(tabelle.getPreferredSize());
      tabelle.setFillsViewportHeight(true);
      
      tablePanel.add(new JScrollPane(tabelle));
      
      // Buttons
      
      neu = new JButton("Neu");
      config = new JButton("Bearbeiten");
      delete = new JButton("Löschen");
      save = new JButton("Speichern");
      
      neu.addActionListener(this);
      config.addActionListener(this);
      delete.addActionListener(this);
      save.addActionListener(this);
      
      neu.setPreferredSize(new Dimension(100, 20));
      config.setPreferredSize(new Dimension(100, 20));
      delete.setPreferredSize(new Dimension(100, 20));
      save.setPreferredSize(new Dimension(100, 20));
      
      
      // Grid aufbau
      
      gc.weightx = 1;
      gc.weighty = 0;
      gc.fill = GridBagConstraints.HORIZONTAL;
      gc.anchor = GridBagConstraints.FIRST_LINE_START;
      gc.gridwidth = 4;
      gc.gridheight = 1;
      gc.insets = new Insets(0, 0, 0, 0);
      gc.gridx = 0;
      gc.gridy = 0;
      verkaeuferErfassungsPanel.add(tablePanel, gc);
      
      gc.weightx = 0;
      gc.weighty = 0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 4, 0, 0);
      gc.gridx = 0;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(neu, gc);
      
      gc.weightx = 0;
      gc.weighty = 0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 1;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(config, gc);
      
      gc.weightx = 0;
      gc.weighty = 0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 2;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(delete, gc);
      
      // Setze Save Button hinter delete!
      verkaeuferErfassungsPanel.add(save, gc);
      
      add(verkaeuferErfassungsPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton clicked = (JButton)e.getSource();
      JPanel erfassungsPanel = new JPanel();
      JPanel checkBoxPanel = new JPanel();
      String number;
      
      if(clicked == neu) {
        if(clickCounter != 1) {
          // Labels + Save Button
          erfassungsPanel.setBorder(BorderFactory.createEtchedBorder());
          
          erfassungsPanel.setLayout(new GridBagLayout());
          GridBagConstraints gc = new GridBagConstraints();
          
          // 4 Elemente in erster Reihe
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 2;
          erfassungsPanel.add(vorname, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 5);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 1;
          gc.gridy = 2;
          erfassungsPanel.add(textFieldVorname, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 5);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 2;
          gc.gridy = 2;
          erfassungsPanel.add(nachname, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 3;
          gc.gridy = 2;
          erfassungsPanel.add(textFieldNachname, gc);
          
          // 4 Elemente in zweiter Reihe
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 3;
          erfassungsPanel.add(telefon, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 5);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 1;
          gc.gridy = 3;
          erfassungsPanel.add(telefonField, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 5);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 2;
          gc.gridy = 3;
          erfassungsPanel.add(zusatz, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 3;
          gc.gridy = 3;
          erfassungsPanel.add(zusatzField, gc);
          
          // Angaben zur Ware - CheckBox
          
          checkBoxPanel.setLayout(new GridBagLayout());
          
          checkBox = new Checkbox();
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 0;
          checkBoxPanel.add(new Checkbox("gebracht"), gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 1;
          checkBoxPanel.add(new Checkbox("dreckig"), gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 2;
          checkBoxPanel.add(new Checkbox("abgeholt"), gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 3;
          checkBoxPanel.add(new Checkbox("Geld erh."), gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 4;
          erfassungsPanel.add(checkBoxPanel, gc);
          
          remove(save);
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridwidth = 1;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 5;
          erfassungsPanel.add(save, gc);
          
          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.anchor = GridBagConstraints.LINE_START;
          gc.insets = new Insets(5, 4, 0, 0);
          gc.gridwidth = 3;
          gc.gridheight = 1;
          gc.gridx = 0;
          gc.gridy = 2;
          verkaeuferErfassungsPanel.add(erfassungsPanel, gc);
          
          clickCounter = 1;
          
          revalidate();
          
          System.out.println("Neu anlegen wurde gestartet!");
          }
        }
        if(clicked == save) {
          
          /* TODO TODO TODO TODO
           * Überprüfung ob Felder Leer sind
           * Meldung noch einfügen - das Feld XX leer
           * 
           * mindestens 1 Feld Vorname oder Nachname muss 
           * beschrieben sein!
           */
          
          if(clickCounter == 1 ) {
            textVorname = textFieldVorname.getText();
            textNachname = textFieldNachname.getText();
            
            // This not work...
            if(telefonField.getText() == null) {
              nummer = 0;
            }else {
              nummer = Integer.parseInt(telefonField.getText());
            }
            textzusatz = zusatzField.getText();
            
            textFieldVorname.setText("");
            textFieldNachname.setText("");
            telefonField.setText("");
            zusatzField.setText("");
            
            // JTable
            dataTabelle.addRow(new Object[]{textVorname, textNachname, nummer, textzusatz});
            
            clickCounter = 1;
            /* Würde hier gerne ein neues Object (new Verkaeufer())
             * anlegen allerdings versteh ich nicht wie ich dann 
             * textVorname etc auslesen kann und in der JTable darstellen kann...
            */
            revalidate();
            System.out.println("Verkäufer wurde gespeichert!");
          }else {
            textVorname = textFieldVorname.getText();
            textNachname = textFieldNachname.getText();
            nummer = Integer.parseInt(telefonField.getText());
            textzusatz = zusatzField.getText();
            
            textFieldVorname.setText("");
            textFieldNachname.setText("");
            telefonField.setText("");
            zusatzField.setText("");
            
            // JTable Daten abändern
            dataTabelle.removeRow(tabelle.getSelectedRow());
            dataTabelle.insertRow(indexTabelle, new Object[]{textVorname, textNachname, nummer, textzusatz});
            clickCounter = 1;
          }
      }
        if(clicked == config) {
          /*
           * Bearbeiten von bereits angelegten Verkäufern
           */
          indexTabelle = tabelle.getSelectedRow();
          
          
          textFieldVorname.setText((String) tabelle.getValueAt(indexTabelle, 0));
          textFieldNachname.setText((String) tabelle.getValueAt(indexTabelle, 1));
          
          //number = (Integer) tabelle.getValueAt(indexTabelle, 2);
          //number = Integer.parseInt(telefonField.getText());
          //telefonField.setText((String) tabelle.getValueAt(indexTabelle, 2));
          
          zusatzField.setText((String) tabelle.getValueAt(indexTabelle, 3));
          clickCounter = 2;
        }
        if(clicked == delete) {
          /* Lösche Zeile X - Auswahl muss getroffen sein!
           */
          dataTabelle.removeRow(tabelle.getSelectedRow());
        }
    } 
}