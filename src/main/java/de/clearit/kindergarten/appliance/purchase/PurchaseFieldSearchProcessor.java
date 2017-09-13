package de.clearit.kindergarten.appliance.purchase;

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

import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseBroker;

public class PurchaseFieldSearchProcessor extends AbstractFieldSearchProcessor{
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
    Format format;
    List<PurchaseBean> purchase = new ArrayList<PurchaseBean>(PurchaseBroker.INSTANCE.getList());

    // Check the name
    for (Iterator<PurchaseBean> i = purchase.iterator(); i.hasNext();) {
      PurchaseBean purchase1 = i.next();
      String vendorId = purchase1.getVendorId().toString();
      sleep(100);
      if (UIFStringUtils.startsWithIgnoreCase(vendorId, trimmedContent)) {
        Completion completion = new DefaultCompletion(vendorId, null, null, null, null, 0, vendorId.equals(
            content), CaretPosition.END);
        publisher.publish(completion);
        i.remove();
      }
    }
    // Check the display string
    for (Iterator<PurchaseBean> i = purchase.iterator(); i.hasNext();) {
    	PurchaseBean vendor = i.next();
      String display = getDisplayString(purchase);
      sleep(100);
      if (display.equals(trimmedContent)) {
        Completion completion = new DefaultCompletion(display, null, null, null, null, 0, true,
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
    PurchaseBean purchase = (PurchaseBean) value;
    return purchase.getVendorId().toString() + ", " + purchase.getItemNumber() + ", " + purchase.getItemPrice();
  }

  /**
   * {@inheritDoc}
   */

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

@Override
public Object valueFor(Completion arg0) {
	// TODO Auto-generated method stub
	return null;
}
}
