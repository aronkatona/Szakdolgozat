package hu.aronkatona.formPropertyEditor;

import hu.aronkatona.hibernateModel.Track;

import java.beans.PropertyEditorSupport;

public class TrackEditor extends PropertyEditorSupport{

	 @Override
	    public void setAsText(String text) throws IllegalArgumentException {
	      setValue(text);
	    }
	    
	    @Override
	    public String getAsText() {
	        Track t = (Track) this.getValue();
	        return t.toString();
	    }
}
