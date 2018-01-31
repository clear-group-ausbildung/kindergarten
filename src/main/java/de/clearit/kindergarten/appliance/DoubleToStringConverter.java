package de.clearit.kindergarten.appliance;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;

public class DoubleToStringConverter extends AbstractConverter {

  private static final long serialVersionUID = 1L;  
  
  public DoubleToStringConverter(ValueModel doubleSubject) {
    super(doubleSubject);
  }

  @Override
  public void setValue(Object newValue) {
	  try {
	    String inputWithComma = (String)newValue;
	    String inputWithDot = inputWithComma.replace(',', '.');   
	    BigDecimal BigValue = new BigDecimal(inputWithDot);    
	    subject.setValue(BigValue);
	  }catch(NumberFormatException e) {
		  
	  }
  }
  
  @Override
  public Object convertFromSubject(Object subjectValue) {
    String result = "";
   
    if (subjectValue != null) {
      String outputWithDot = subjectValue.toString();
      
      NumberFormat nf = NumberFormat.getInstance();
      nf.setMaximumFractionDigits(2);
      nf.setMinimumFractionDigits(2);
      double value = Double.parseDouble(outputWithDot);
      result = nf.format(value);
      
      
//      Replace this with NumberFormat
//      
//      result = outputWithDot.replace('.', ',');
    }
    return result;
  }

}
