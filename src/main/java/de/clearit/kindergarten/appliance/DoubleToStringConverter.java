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
    Double doubleValue = Double.valueOf((String) newValue);
    subject.setValue(doubleValue);
  }

  @Override
  public Object convertFromSubject(Object subjectValue) {
    String result = "";
    if (subjectValue != null) {
      result = ((Double) subjectValue).toString();
    }
    return result;
  }

}
