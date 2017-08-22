package de.clearit.kindergarten.appliance.documents;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.spec.NavigationBarSpec;

import de.clearit.kindergarten.desktop.DefaultAppliance;
import de.clearit.kindergarten.desktop.DefaultDesktopFrame;

/**
 * The appliance for the documents.
 */
public final class DocumentsAppliance extends DefaultAppliance {

  private static final ResourceMap RESOURCES = Application.getResourceMap(DocumentsAppliance.class);

  private static DocumentsAppliance instance;

  // Instance Access ********************************************************

  private DocumentsAppliance() {
    super("documents", RESOURCES.getString("documentsAppliance.name"), RESOURCES.getString(
        "documentsAppliance.shortName"), RESOURCES.getString("documentsAppliance.quickSearchName"));
  }

  public static DocumentsAppliance getInstance() {
    if (instance == null) {
      instance = new DocumentsAppliance();
    }
    return instance;
  }

  // Overriding Default Behavior ********************************************

  /**
   * Checks and answers if this appliance matches the given content. Used by the
   * DesktopModel's CompletionProcessor to identify and return appliances that
   * match a given content.
   */
  @Override
  public boolean matches(String content) {
    return false;

  }

  // Implementing Abstract Behavior *****************************************

  /**
   * Returns the desktop frame that is visible on appliance activation. This is an
   * object based hub page.
   */
  @Override
  protected DesktopFrame createHomeFrame() {
    DocumentsHomeModel model = DocumentsHomeModel.getInstance();
    DocumentsHomeView view = DocumentsHomeView.getInstance();

    DesktopFrame parent = null; // MaintenanceHubAppliance.getInstance().homeContext();
    NavigationBarSpec navigationSpec = null;
    JComponent statusPane = null;

    return new DefaultDesktopFrame(parent, RESOURCES.getString("documentsHome.title"), false, this, null, model
        .contextSpec(), navigationSpec, view.getPanel(), statusPane);
  }

}