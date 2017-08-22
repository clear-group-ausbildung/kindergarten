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

import de.clearit.kindergarten.domain.Vendor;
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
    Format format = new VendorAppliance.AddressHTMLFormat();
    List<Vendor> vendors = new ArrayList<Vendor>(VendorBroker.INSTANCE.getList());

    // Check the name
    for (Iterator<Vendor> i = vendors.iterator(); i.hasNext();) {
      Vendor vendor = i.next();
      String name = vendor.getName();
      sleep(100);
      if (UIFStringUtils.startsWithIgnoreCase(name, trimmedContent)) {
        Completion completion = new DefaultCompletion(name, null, null, format.format(vendor), null, 0, name.equals(
            content), CaretPosition.END);
        publisher.publish(completion);
        i.remove();
      }
    }
    // Check the display string
    for (Iterator<Vendor> i = vendors.iterator(); i.hasNext();) {
      Vendor vendor = i.next();
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
    Vendor vendor = (Vendor) value;
    return vendor.getCode() + " - " + vendor.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object valueFor(Completion completion) {
    String displayString = completion.getDisplayString();
    int dashIndex = displayString.indexOf('-');
    if (dashIndex == -1) {
      return VendorBroker.INSTANCE.findByName(displayString);
    }
    String code = displayString.substring(0, dashIndex - 1);
    return VendorBroker.INSTANCE.findByCode(code);
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
