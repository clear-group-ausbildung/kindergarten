package de.clearit.kindergarten.appliance.vendor;

import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jgoodies.completion.text.Completion;
import com.jgoodies.completion.text.CompletionPublisher;
import com.jgoodies.completion.text.CompletionState;
import com.jgoodies.completion.text.DefaultCompletion;
import com.jgoodies.completion.text.DefaultCompletion.CaretPosition;
import com.jgoodies.uif2.search.AbstractFieldSearchProcessor;
import com.jgoodies.uif2.util.UIFStringUtils;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorBroker;

/**
 * Looks up Completions for Vendors, converts Vendor objects to Strings,
 * converts Vendor Completions to Vendors, and looks up a Vendor for a given
 * String.
 */
public final class VendorFieldSearchProcessor extends AbstractFieldSearchProcessor {

  // CompletionProcessor Implementation *************************************

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAutoActivatable(String content, int caretPosition) {
    return content.length() >= 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean search(String content, int caretPosition, // ignored
      CompletionPublisher publisher, CompletionState state) {
    String trimmedContent = content.trim();
    sleep(1000);
    Format format = new VendorAppliance.ExtrasHTMLFormat();
    List<VendorBean> vendors = new ArrayList<VendorBean>(VendorBroker.INSTANCE.getList());

    // Check the name
    for (Iterator<VendorBean> i = vendors.iterator(); i.hasNext();) {
      VendorBean vendor = i.next();
      String name = vendor.getFirstName();
      sleep(100);
      if (UIFStringUtils.startsWithIgnoreCase(name, trimmedContent)) {
        Completion completion = new DefaultCompletion(name, null, null, format.format(vendor), null, 0, name.equals(
            content), CaretPosition.END);
        publisher.publish(completion);
        i.remove();
      }
    }
    // Check the display string
    for (Iterator<VendorBean> i = vendors.iterator(); i.hasNext();) {
      VendorBean vendor = i.next();
      String display = getDisplayString(vendor);
      sleep(100);
      if (display.equals(trimmedContent)) {
        Completion completion = new DefaultCompletion(display, null, null, format.format(vendor), null, 0, true,
            CaretPosition.END);
        publisher.publish(completion);
        i.remove();
      }
    }

    return true;
  }

  // Finder Implementation *************************************************

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDisplayString(Object value) {
    if (value == null) {
      return "";
    }
    VendorBean vendor = (VendorBean) value;
    return vendor.getLastName() + ", " + vendor.getFirstName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object valueFor(Completion completion) {
    String displayString = completion.getDisplayString();
    int commaIndex = displayString.indexOf(',');
    String[] name = new String[] {};
    if (commaIndex == -1) {
      // Input was in format "Ritter Mark"
      name = displayString.split(" ");
      // e.g. name = { "Ritter", "Mark };
    }
    // Input was in format "Ritter, Mark"
    name = displayString.split(",");
    // e.g. name = { "Ritter", "Mark };
    // name[0] is the last name, name[1] is the first name
    return VendorBroker.INSTANCE.findByName(name[0], name[1]);
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
