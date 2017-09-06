package de.clearit.kindergarten.desktop;

import java.util.Collections;
import java.util.List;

import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.AbstractAppliance;

/**
 * The default appliance provides basic functionalities.
 */
public abstract class DefaultAppliance extends AbstractAppliance {

  private final String quickSearchName;

  // Instance Access ********************************************************

  protected DefaultAppliance(String id, String name, String shortName, String quickSearchName) {
    super(id, name, shortName);
    this.quickSearchName = quickSearchName;
  }

  // Quick Search ***********************************************************

  protected final String quickSearchName() {
    return quickSearchName;
  }

  /**
   * Checks and answers if this appliance matches the given content. Used by the
   * DesktopModel's CompletionProcessor to identify and return appliances that
   * match a given content.
   * 
   * @param content
   *          the content to match
   * @return true / false
   */
  public boolean matches(String content) {
    return startsWithIgnoreCase(quickSearchName(), content) || startsWithIgnoreCase(name(), content);

  }

  /**
   * Computes and returns a List of objects that match the given content. Invoked
   * by the DesktopModel's CompletionProcessor, if this appliances already matched
   * the given content.
   *
   * @param content
   *          the String used to look up the completions
   * @return a list of objects that match the given content
   */
  public List<?> computeMatchingObjects(String content) {
    return Collections.emptyList();
  }

  // Convenience Code *******************************************************

  public static String getTitle(ResourceMap resourceMap, boolean newItem, String objectName) {
    return newItem ? resourceMap.getString("newItem.title") : resourceMap.getString("editItem.title", objectName);
  }

  // Helper Code ************************************************************

  /**
   * Tests and answers if <code>str</code> starts with the given prefix ignoring
   * cases.
   *
   * <pre>
   * UIFStringUtils.startsWithIgnoreCase("a", null)  NullPointerException
   * UIFStringUtils.startsWithIgnoreCase(null, "a")  NullPointerException
   * UIFStringUtils.startsWithIgnoreCase("",  "")         == true
   * UIFStringUtils.startsWithIgnoreCase(" ", "")         == true
   * UIFStringUtils.startsWithIgnoreCase("John", "J")     == true
   * UIFStringUtils.startsWithIgnoreCase("John", "Jo")    == true
   * UIFStringUtils.startsWithIgnoreCase("John", "Joh")   == true
   * UIFStringUtils.startsWithIgnoreCase("John", "joh")   == true
   * UIFStringUtils.startsWithIgnoreCase("john", "Joh")   == true
   * UIFStringUtils.startsWithIgnoreCase("john", "joh")   == true
   * UIFStringUtils.startsWithIgnoreCase("John", "John")  == true
   * UIFStringUtils.startsWithIgnoreCase("John", "john")  == true
   * UIFStringUtils.startsWithIgnoreCase("John", "Jonny") == false
   * </pre>
   *
   * @param str
   *          the test string that may start with the prefix
   * @param prefix
   *          the prefix to test
   * @return true, if the string starts with the prefix, ignoring cases, false
   *         otherwise
   *
   * @see String#startsWith(java.lang.String)
   */
  private static boolean startsWithIgnoreCase(String str, String prefix) {
    return str.regionMatches(true, 0, prefix, 0, prefix.length());
  }

}