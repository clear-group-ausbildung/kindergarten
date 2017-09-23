package de.clearit.kindergarten.desktop;

import java.util.List;

import javax.swing.Action;

import com.jgoodies.desktop.Appliance;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.quicksearch.ActionActivatable;
import com.jgoodies.quicksearch.Activatable;
import com.jgoodies.quicksearch.QuickSearchManager;
import com.jgoodies.quicksearch.QuickSearchProcessor;
import com.jgoodies.quicksearch.QuickSearchPublisher;
import com.jgoodies.quicksearch.QuickSearchState;
import com.jgoodies.uif2.util.UIFStringUtils;

/**
 * Looks up and returns activatable data for a given text content. These
 * information are requested by the QuickSearchManager. Checks all appliances,
 * whether they have are activatable, and if so, whether they have activatable
 * objects.
 * <p>
 *
 * The QuickSearchManager invokes a processor's {@code #search} method from a
 * background thread, and may indicate that the process has been canceled.
 *
 * @see QuickSearchManager
 * @see Activatable
 */
public final class DesktopQuickSearchProcessor implements QuickSearchProcessor {

  // QuickSearchProcessor Implementation ************************************

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean search(String content, QuickSearchPublisher publisher, QuickSearchState state) {
    if (UIFStringUtils.isBlank(content)) {
      return false;
    }
    for (Appliance app : DesktopManager.getAppliances()) {
      DefaultAppliance appliance = (DefaultAppliance) app;
      if (!appliance.matches(content)) {
        continue;
      }
      sleep(250);
      List<?> matchingObjects = appliance.computeMatchingObjects(content);
      if (matchingObjects.isEmpty()) {
        Action action = DesktopManager.createActivationAction(appliance);
        Activatable activatable = new ActionActivatable<>("Appliance", action, 10);
        publisher.publish(activatable);
      } else {
        String category = appliance.shortName();
        for (Object object : matchingObjects) {
          Action action = DesktopManager.createActivationAction(appliance, object);
          Activatable activatable = new ActionActivatable<>(category, action, 20);
          publisher.publish(activatable);
          sleep(25);
        }
      }
    }
    return true;
  }

  // Helper Code ************************************************************

  /**
   * Sleeps for the given <code>milliseconds</code> catching a potential
   * InterruptedException.
   *
   * @param milliseconds
   *          the time to sleep in milliseconds
   */
  private static void sleep(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      // Do nothing on interrupt.
    }
  }

}
