package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;

import java.beans.PropertyEditorSupport;

public class TeamEditor extends PropertyEditorSupport{

	private final TeamService teamService;

    public TeamEditor(TeamService teamService){
        this.teamService= teamService;
    }

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
