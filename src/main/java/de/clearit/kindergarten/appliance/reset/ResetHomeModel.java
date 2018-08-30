package de.clearit.kindergarten.appliance.reset;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.application.Action;
import com.jgoodies.application.BlockingScope;
import com.jgoodies.application.Task;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.spec.ToolBarSpec;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorNumberService;
import de.clearit.kindergarten.domain.VendorService;

public class ResetHomeModel extends DefaultAppliance{

	  private static final Logger LOGGER = LoggerFactory.getLogger(ResetHomeModel.class);

	  private static ResetHomeModel instance;
	  private final VendorService VSERVICE = VendorService.getInstance();
	  private final PurchaseService PSERVICE = PurchaseService.getInstance();
	  private final VendorNumberService VNBSERVICE = VendorNumberService.getInstance();

	  // Instance Creation ******************************************************

	  private ResetHomeModel() {
	    super(null, null, null, null);
	  }

	  public static ResetHomeModel getInstance() {
	    if (instance == null) {
	      instance = new ResetHomeModel();
	    }
	    return instance;
	  }

	  // Actions ****************************************************************

	  public ToolBarSpec contextSpec() {
		    return contextActionNames().length == 0 ? null : new ToolBarSpec().add(getActionMap(), contextActionNames());
	  }
	  
	  private ActionMap getActionMap() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String[] contextActionNames() {
	    return new String[] {};
	  }

	  @Action
	  public Task<Void, Void> resetAll(ActionEvent e) {
	    LOGGER.debug("Remove all Beans");
	    return new resetApplication();
	  }

	  private final class resetApplication extends Task<Void, Void> {

	    resetApplication() {
	      super(BlockingScope.APPLICATION);
	      int reply = JOptionPane.showConfirmDialog(new JFrame(), "Sind Sie sicher das Sie alle Vorhandenen Daten unwiederruflich entfernen möchten?", "Titel", JOptionPane.YES_NO_OPTION);
	      //remove all existing Beans! 
	      if(reply == JOptionPane.YES_OPTION) {
			  ArrayList<VendorBean> allVendors = new ArrayList<VendorBean>(VSERVICE.getAll());
			  ArrayList<PurchaseBean> allPurchases = new ArrayList<PurchaseBean>(PSERVICE.getAll());
			  ArrayList<VendorNumberBean> allVendorNumbers = new ArrayList<VendorNumberBean>(VNBSERVICE.getAll());
		      for (VendorBean VendorBean : allVendors) {
		    	  VSERVICE.delete(VendorBean);
			  }
		      for(PurchaseBean PurchaseBean : allPurchases) {
		    	  PSERVICE.delete(PurchaseBean);
		      }
		      for(VendorNumberBean VendorNumberBean : allVendorNumbers) {
		    	  VNBSERVICE.delete(VendorNumberBean);
		      }
		      JOptionPane.showMessageDialog(new JFrame(), "Alle Verkäufer und Artikel wurden erfolgreich entfernt!", "Erfolgreicher Reset", JOptionPane.NO_OPTION);
	      }
	  }
	    @Override
	    protected Void doInBackground() {
	      return null;
	    }
	  }

	@Override
	protected DesktopFrame createHomeFrame() {
		// TODO Auto-generated method stub
		return null;
	}

}
