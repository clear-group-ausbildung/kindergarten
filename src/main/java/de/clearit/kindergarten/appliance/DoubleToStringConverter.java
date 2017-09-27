package de.clearit.kindergarten.appliance;

import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;

public class DoubleToStringConverter extends AbstractConverter {

  private static final long serialVersionUID = 1L;

  public DoubleToStringConverter(ValueModel doubleSubject) {
    super(doubleSubject);
  }

  @Override
  public void setValue(Object newValue) {
    String inputWithComma = (String)newValue;
    String inputWithDot = inputWithComma.replace(',', '.');
    Double doubleValue = Double.valueOf(inputWithDot);
    subject.setValue(doubleValue);
  }

  @Override
  public Object convertFromSubject(Object subjectValue) {
    String result = "";
    if (subjectValue != null) {
      String outputWithDot = subjectValue.toString();
      result = outputWithDot.replace('.', ',');
    }
    return result;
  }

}
