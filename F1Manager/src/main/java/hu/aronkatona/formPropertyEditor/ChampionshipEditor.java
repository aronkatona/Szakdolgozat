package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Championship;

import java.beans.PropertyEditorSupport;

public class ChampionshipEditor extends PropertyEditorSupport{


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      setValue(text);
    }
    
    @Override
    public String getAsText() {
        Championship c = (Championship) this.getValue();
        return c.toString();
    }
}
