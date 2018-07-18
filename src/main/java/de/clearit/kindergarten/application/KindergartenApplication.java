package de.clearit.kindergarten.application;

import java.awt.AWTKeyStroke;

import javax.swing.Action;
import javax.swing.ImageIcon;
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
import com.jgoodies.looks.Options;
import com.jgoodies.uif2.application.UIFApplication;
import com.jgoodies.uif2.splash.SplashWindow;

import de.clearit.kindergarten.appliance.accounting.AccountAppliance;
import de.clearit.kindergarten.appliance.documents.DocumentsAppliance;
import de.clearit.kindergarten.appliance.purchase.PurchaseAppliance;
import de.clearit.kindergarten.appliance.vendor.VendorAppliance;

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

    Action action;
    spec.add(getResourceMap().getString("navigation.applications"));
    action = DesktopManager.createActivationAction(VendorAppliance.getInstance());
    action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl 1"));
    spec.add(action);

    action = DesktopManager.createActivationAction(PurchaseAppliance.getInstance());
    action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl 2"));
    spec.add(action);

    action = DesktopManager.createActivationAction(AccountAppliance.getInstance());
    action.putValue(Action.ACCELERATOR_KEY, AWTKeyStroke.getAWTKeyStroke("ctrl 3"));
    spec.add(action);
    
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
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
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
    registerAppliances();
  }

  // Implementing Abstract Behavior *****************************************

  @Override
  protected void createAndShowGUI() {
    initializeDesktop();

    JFrame frame = buildFrame();
    JOptionPane.setRootFrame(frame);

    VendorAppliance.getInstance().activate();

    frame.setSize(1200, 720);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    SplashWindow.disposeSplash();
  }

  // Helper Code ************************************************************

  private void registerAppliances() {
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
    ImageIcon icon = new ImageIcon(KindergartenApplication.class.getResource("resources/images/logo.gif"));
    frame.setIconImage(icon.getImage());
    frame.add(new MainView(MainModel.getInstance()).getPanel());
    return frame;
  }

  private static final class DesktopCloseHandler implements CloseHandler {
    @Override
    public void closePane(AbstractStyledPane pane) {
      DesktopManager.closeActiveFrame();
    }
  }

}