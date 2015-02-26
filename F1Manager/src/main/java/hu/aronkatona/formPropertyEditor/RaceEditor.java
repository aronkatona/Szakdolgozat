package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Race;

import java.beans.PropertyEditorSupport;

public class RaceEditor extends PropertyEditorSupport{

	@Override
    public void setAsText(String text) throws IllegalArgumentException {
      setValue(text);
    }
    
    @Override
    public String getAsText() {
        Race r = (Race) this.getValue();
        return r.toString();
    }
}
