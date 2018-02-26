package de.clearit.kindergarten.appliance;

import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;

public class IntegerToStringConverter extends AbstractConverter {

  private static final long serialVersionUID = 1L;

  public IntegerToStringConverter(ValueModel integerSubject) {
    super(integerSubject);
  }

  @Override
  public void setValue(Object newValue) {
    try {
      Integer integerValue = Integer.valueOf((String) newValue);
      subject.setValue(integerValue);
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public Object convertFromSubject(Object subjectValue) {
    String result = "";
    if (subjectValue != null) {
      result = subjectValue.toString();
    }
    return result;
  }

}