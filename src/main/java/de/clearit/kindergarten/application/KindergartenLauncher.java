package de.clearit.kindergarten.application;

import com.jgoodies.uif2.osx.OSXApplicationMenu;
import com.jgoodies.uif2.splash.SplashWindow;

/**
 * This class has been designed to show a splash quickly. It aims to load as few
 * framework classes as possible. Therefore it has been separated from the
 * Application class (reduces the number of imports). And it doesn't use the
 * Application framework resource support, just to avoid the initialization of
 * the full ResourceMap hierarchy which may be packed with quite a lot of keys
 * that we don't need at this time.
 *
 */
public final class KindergartenLauncher {

  public static void main(String[] args) {
    OSXApplicationMenu.setAboutName("Kassenverwaltung");
    SplashWindow.splash(KindergartenLauncher.class.getResource("resources/images/logo.gif"));
    SplashWindow.invokeMain("de.clearit.kindergarten.application.KindergartenApplication", args);
  }

}