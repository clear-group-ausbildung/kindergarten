package de.clearit.kindergarten.application;

import java.awt.AWTKeyStroke;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jgoodies.application.Application;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.desktop.view.DesktopViews;
import com.jgoodies.jsdl.core.CloseHandler;
import com.jgoodies.jsdl.core.JSDLSetup;
import com.jgoodies.jsdl.core.pane.AbstractStyledPane;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.jgoodies.uif2.application.UIFApplication;
import com.jgoodies.uif2.splash.SplashWindow;

import de.clearit.kindergarten.appliance.documents.DocumentsAppliance;
import de.clearit.kindergarten.appliance.vendor.VendorAppliance;
import de.clearit.kindergarten.domain.SampleEntityFactory;

/**
 * The main application class.
 */
public final class KindergartenApplication extends UIFApplication {

  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    Application.launch(KindergartenApplication.class, args);
  }

  // Application API Extensions *********************************************

  public NavigationBarSpec applicationActions() {
    NavigationBarSpec spec = new NavigationBarSpec();
    // ActionGroup listGroup = new ActionGroup(
    // getResourceMap().getString("navigation.lists"),
    // new Action[][]{
    // { Desktop.getActivationAction(PendingInvoicesAppliance.getInstance()),
    // Desktop.getActivationAction(LastTwoDaysInvoicesAppliance.getInstance()) }
    // });

    Action action;
    spec.add(getResourceMap().getString("navigation.applications"));
    action = DesktopManager.createActivationAction(VendorAppliance.getInstance());
    action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl 1"));
    spec.add(action);

    // spec.addUnrelatedGap();
    // spec.add(getResourceMap().getString("navigation.maintenance"));
    // action =
    // DesktopManager.createActivationAction(ImporterAppliance.getInstance());
    // action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl
    // 2"));
    // spec.add(action);
    // action =
    // DesktopManager.createActivationAction(ExporterAppliance.getInstance());
    // action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl
    // 3"));
    // spec.add(action);

    spec.addUnrelatedGap();
    spec.add(getResourceMap().getString("navigation.help"));
    spec.add(MainModel.getInstance().getActionMap(), "openAboutDialog");

    return spec;
  }

  // Overriding/Extending Default Behavior **********************************

  @Override
  protected void configureUI() {
    Options.setPopupDropShadowEnabled(true);
    super.configureUI();
    try {
      // UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
      // UIManager.setLookAndFeel(new NimbusLookAndFeel());
      UIManager.setLookAndFeel(new WindowsLookAndFeel());
      if (LookUtils.IS_OS_MAC) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } else if ((LookUtils.IS_OS_WINDOWS_XP || LookUtils.IS_OS_WINDOWS_VISTA) && LookUtils.IS_LAF_WINDOWS_XP_ENABLED) {
        UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } else {
        UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // JSDLSetup.setEnhancedAccessibility(true);
    // UIFLayoutFocusTraversalPolicy.setAcceptNonEditableTextComponents(true);
    // UIFContainerOrderFocusTraversalPolicy.setAcceptNonEditableTextComponents(true);
  }

  @Override
  protected void configureHelp() {
    super.configureHelp();
    JSDLSetup.setDefaultHyperlinkHandler(MainModel.getInstance().createDefaultHyperlinkHandler());
  }

  /**
   * In the default startup sequence, this method is invoked before the GUI
   * creation.
   */
  @Override
  protected void preCreateAndShowGUI() {
    super.preCreateAndShowGUI();
    createSampleEntities();
    registerAppliances();
  }

  // Implementing Abstract Behavior *****************************************

  @Override
  protected void createAndShowGUI() {
    // Splash.setNote("Initializing Desktop");
    initializeDesktop();

    // Splash.setNote("Initializing Frame");
    JFrame frame = buildFrame();
    JOptionPane.setRootFrame(frame);

    VendorAppliance.getInstance().activate();

    // frame.pack();
    frame.setSize(950, 720);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    SplashWindow.disposeSplash();
  }

  // Helper Code ************************************************************

  private void createSampleEntities() {
    // Splash.setNote("Creating Sample Entities");
    SampleEntityFactory.INSTANCE.addSampleData();
  }

  private void registerAppliances() {
    // Splash.setNote("Registering Appliances");
    // Appliances
    DesktopManager.register(VendorAppliance.getInstance());

    // Maintenance
    // DesktopManager.register(ImporterAppliance.getInstance());
    // DesktopManager.register(ExporterAppliance.getInstance());

    // Documents
    DesktopManager.register(DocumentsAppliance.getInstance());
  }

  private void initializeDesktop() {
    DesktopManager.initialize("unused set by MainView#updateFrameTitleFormat", DesktopManager.createActivationAction(
        DocumentsAppliance.getInstance()));
    DesktopManager.INSTANCE.setOpenDocumentsViewLimit(5);
    addExitListener(MainModel.getInstance());
    JSDLSetup.setDefaultCloseHandler(new DesktopCloseHandler());
  }

  private JFrame buildFrame() {
    JFrame frame = DesktopViews.buildDefaultFrame();
    frame.add(new MainView(MainModel.getInstance()).getPanel());

    // KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(
    // "focusOwner", new PropertyChangeListener() {
    // public void propertyChange(PropertyChangeEvent evt) {
    // System.out.println("Focus owner changed. ");
    // System.out.println(" old=" + evt.getOldValue());
    // System.out.println(" new=" + evt.getNewValue());
    // }
    // });
    // frame.getRootPane().addPropertyChangeListener("defaultButton",
    // new PropertyChangeListener() {
    // public void propertyChange(PropertyChangeEvent evt) {
    // System.out.println("Default button changed.");
    // System.out.println("Old=" + evt.getOldValue());
    // System.out.println("New=" + evt.getNewValue());
    // }
    // });
    return frame;
  }

  private static final class DesktopCloseHandler implements CloseHandler {
    @Override
    public void closePane(AbstractStyledPane pane) {
      DesktopManager.closeActiveFrame();
    }
  }

}