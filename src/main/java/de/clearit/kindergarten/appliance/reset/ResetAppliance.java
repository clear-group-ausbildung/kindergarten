package de.clearit.kindergarten.appliance.reset;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopFrame;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;

public final class ResetAppliance extends DefaultAppliance{
	
	private static final ResourceMap RESOURCES = Application.getResourceMap(ResetAppliance.class);
	private static ResetAppliance instance;
	
	private ResetAppliance() {
	    super("reset_maintenance", RESOURCES.getString("resetAppliance.name"), RESOURCES.getString(
	        "resetAppliance.shortName"), RESOURCES.getString("resetAppliance.quickSearchShortCut"));
	  }
	
	private ResetAppliance(String id, String name, String shortName, String quickSearchName) {
		   super("reset_maintenance", RESOURCES.getString("resetAppliance.name"), RESOURCES.getString(
			        "resetAppliance.shortName"), RESOURCES.getString("resetAppliance.quickSearchShortCut"));
			  }

	public static ResetAppliance getInstance() {
	    if (instance == null) {
	      instance = new ResetAppliance();
	    }
	    return instance;
	  }
	
	 // Implementing Abstract Behavior *****************************************
	@Override
	protected DesktopFrame createHomeFrame() {
	    ResetHomeModel model = ResetHomeModel.getInstance();
	    ResetHomeView view = ResetHomeView.getInstance();

	    return new DefaultDesktopFrame(null, RESOURCES.getString("resetHome.title"), false, this, null, model
	        .contextSpec(), null, view.getPanel(), null);
	  }
	
}
