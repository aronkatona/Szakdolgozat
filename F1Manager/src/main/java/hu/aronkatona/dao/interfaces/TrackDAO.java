package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Track;

import java.util.List;

public interface TrackDAO {

	public void saveTrack(Track track);
	public List<Track> getTracks();
	public Track getTrackById(long id);
	public void deleteTrack(long id);
	public boolean existingTrackByIdAndName(long id, String name);

}
