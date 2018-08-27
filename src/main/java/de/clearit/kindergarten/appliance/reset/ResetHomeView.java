package de.clearit.kindergarten.appliance.reset;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.uif2.AbstractView;

public class ResetHomeView extends AbstractView{
	private static final ResourceMap RESOURCES = Application.getResourceMap(ResetAppliance.class);

	  private static ResetHomeView instance;

	  private final ResetHomeModel model;
	  private JPanel administrationPanel;
	  private Container content;

	  // Instance Creation ******************************************************

	  private ResetHomeView(ResetHomeModel model) {
	    this.model = model;
	  }

	  public static ResetHomeView getInstance() {
	    if (instance == null) {
	      instance = new ResetHomeView(ResetHomeModel.getInstance());
	    }
	    return instance;
	  }

	  // Building ***************************************************************

	  private void initComponents() {
		  JLabel infoLabel = new JLabel();
		  JButton resetApp = new JButton("Zurücksetzen");
		  infoLabel.setText(RESOURCES.getString("reset.infoMessage"));
		
		  administrationPanel = new JPanel();
		  administrationPanel.setBackground(Color.WHITE);
		  resetApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResetHomeModel.getInstance().resetAll(arg0);				
			}  
		  });
		  
		  administrationPanel.setLayout(new GridBagLayout());
		  GridBagConstraints c = new GridBagConstraints();
		  c.gridx = 0;
		  c.gridy = 0;
		  administrationPanel.add(infoLabel, c);
		  
		  c.insets = new Insets(25, 0, 0, 0);
		  c.gridx = 0;
		  c.gridy = 1;
		  c.ipady = 25;
		  c.ipadx = 30;
		  administrationPanel.add(resetApp, c);
		  
	  }

	  @Override
	  protected JComponent buildPanel() {
	    initComponents();

	    return administrationPanel;
	  }

	}
