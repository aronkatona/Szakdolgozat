package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Driver;

import java.beans.PropertyEditorSupport;

public class DriverEditor extends PropertyEditorSupport{

	@Override
    public void setAsText(String text) throws IllegalArgumentException {
      setValue(text);
    }
    
    @Override
    public String getAsText() {
        Driver d = (Driver) this.getValue();
        return d.toString();
    }
}
