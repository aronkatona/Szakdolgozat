package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Team;

import java.beans.PropertyEditorSupport;

public class TeamEditor extends PropertyEditorSupport{


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      setValue(text);
    }
    
    @Override
    public String getAsText() {
        Team team = (Team) this.getValue();
        return team.getName();
    }
}
