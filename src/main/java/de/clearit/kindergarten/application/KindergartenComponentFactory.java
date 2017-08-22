package de.clearit.kindergarten.application;

import java.text.Format;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.jsdl.core.util.JSDLUtils;

/**
 * Provides only static factory methods that vend components.
 *
 */
public final class KindergartenComponentFactory extends BasicComponentFactory {

  private KindergartenComponentFactory() {
    // Overrides default constructor; prevents instantiation.
  }

  // Factory Methods ********************************************************

  /**
   * Creates and returns a check box with the specified text label that is bound
   * to the given ValueModel. The check box is selected if and only if the model's
   * value equals {@code Boolean.TRUE}.
   * <p>
   *
   * The model is converted to the required ToggleButtonModel using a
   * ToggleButtonAdapter.
   *
   * @param valueModel
   *          the model that provides a Boolean value
   * @param markedText
   *          the check boxes' text label with optional mnemonic marker
   * @return a check box with the specified text bound to the given model,
   *         selected if the model's value equals Boolean.TRUE
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   */
  public static JCheckBox createCheckBox(ValueModel valueModel, String markedText) {
    JCheckBox box = BasicComponentFactory.createCheckBox(valueModel, markedText);
    box.setContentAreaFilled(false);
    JSDLUtils.configureMnemonic(box, markedText);
    return box;
  }

  /**
   * Creates and returns a radio button with the specified text label that is
   * bound to the given ValueModel. The radio button is selected if and only if
   * the model's value equals the specified choice.
   * <p>
   *
   * The model is converted to the required ToggleButton using a
   * RadioButtonAdapter.
   *
   * @param model
   *          the model that provides the current choice
   * @param choice
   *          this button's value
   * @param markedText
   *          the radio buttons' text label with optional mnemonic marker
   * @return a radio button with the specified text bound to the given model,
   *         selected if the model's value equals the specified choice
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   */
  public static JRadioButton createRadioButton(ValueModel model, Object choice, String markedText) {
    JRadioButton radio = BasicComponentFactory.createRadioButton(model, choice, markedText);
    radio.setContentAreaFilled(false);
    JSDLUtils.configureMnemonic(radio, markedText);
    return radio;
  }

  /**
   * Creates and returns a read-only formatted text field that binds its value to
   * the given model and converts Strings to values using the given Format.
   *
   * @param valueModel
   *          the model that provides the value
   * @param format
   *          the <code>Format</code> used to convert values into a text
   *          representation and vice versa via <code>#format</code> and
   *          <code>#parse</code>
   * @return a non-editable formatted text field without border that is bound to
   *         the given value model
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   */
  public static JFormattedTextField createReadOnlyFormattedTextField(ValueModel valueModel, Format format) {
    JFormattedTextField textField = createFormattedTextField(valueModel, format);
    textField.setEditable(false);
    textField.setBorder(null);
    JSDLUtils.configureTransparentBackground(textField);
    return textField;
  }

  /**
   * Creates and returns a read-only text area with the content bound to the given
   * ValueModel.
   *
   * @param valueModel
   *          the model that provides the value
   * @return a non-editable text area without border that is bound to the given
   *         value model
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   *
   * @see #createTextArea(ValueModel, boolean)
   */
  public static JTextArea createReadOnlyTextArea(ValueModel valueModel) {
    JTextArea textArea = createTextArea(valueModel, true);
    textArea.setEditable(false);
    textArea.setBorder(null);
    JSDLUtils.configureTransparentBackground(textArea);
    return textArea;
  }

  /**
   * Creates and returns a read-only text area with the content bound to the given
   * ValueModel.
   *
   * @param valueModel
   *          the model that provides the value
   * @param format
   *          the <code>Format</code> used to convert values into a text
   *          representation and vice versa via <code>#format</code> and
   *          <code>#parse</code>
   * @return a non-editable text area without border that is bound to the given
   *         value model
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   *
   * @see #createTextArea(ValueModel, boolean)
   */
  public static JTextArea createReadOnlyTextArea(ValueModel valueModel, Format format) {
    return createReadOnlyTextArea(ConverterFactory.createStringConverter(valueModel, format));
  }

  /**
   * Creates and returns a read-only text field with the given content.
   *
   * @param text
   *          the field's initial text content
   * @return a non-editable text field without border that is bound to the given
   *         value model
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   */
  public static JTextComponent createReadOnlyTextField(String text) {
    JTextComponent field = new JTextField(text);
    field.setEditable(false);
    field.setBorder(null);
    JSDLUtils.configureTransparentBackground(field);
    return field;
  }

  /**
   * Creates and returns a read-only text field with the content bound to the
   * given ValueModel.
   *
   * @param valueModel
   *          the model that provides the value
   * @return a non-editable text field without border that is bound to the given
   *         value model
   *
   * @throws NullPointerException
   *           if the valueModel is {@code null}
   */
  public static JTextComponent createReadOnlyTextField(ValueModel valueModel) {
    JTextComponent field = createTextField(valueModel);
    field.setEditable(false);
    field.setBorder(null);
    JSDLUtils.configureTransparentBackground(field);
    return field;
  }

}
