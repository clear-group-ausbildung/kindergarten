package de.clearit.kindergarten.appliance.accounting;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopFrame;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;

public final class AccountAppliance extends DefaultAppliance{
	
	private static final ResourceMap RESOURCES = Application.getResourceMap(AccountAppliance.class);
	private static AccountAppliance instance;
	
	private AccountAppliance() {
	    super("accounting_maintenance", RESOURCES.getString("accountingAppliance.name"), RESOURCES.getString(
	        "accountingAppliance.shortName"), RESOURCES.getString("accountingAppliance.quickSearchShortCut"));
	  }
	
	private AccountAppliance(String id, String name, String shortName, String quickSearchName) {
		   super("accounting_maintenance", RESOURCES.getString("accountingAppliance.name"), RESOURCES.getString(
			        "accountingAppliance.shortName"), RESOURCES.getString("accountingAppliance.quickSearchShortCut"));
			  }

	public static AccountAppliance getInstance() {
	    if (instance == null) {
	      instance = new AccountAppliance();
	    }
	    return instance;
	  }
	
	 // Implementing Abstract Behavior *****************************************
	@Override
	protected DesktopFrame createHomeFrame() {
	    AccountHomeModel model = AccountHomeModel.getInstance();
	    AccountHomeView view = AccountHomeView.getInstance();

	    return new DefaultDesktopFrame(null, RESOURCES.getString("accountingHome.title"), false, this, null, model
	        .contextSpec(), null, view.getPanel(), null);
	  }
	
}
