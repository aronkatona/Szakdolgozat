package hu.aronkatona.service.interfaces;

import java.util.List;

import hu.aronkatona.hibernateModel.Track;

public interface TrackService {
	
	public void saveTrack(Track track);
	public List<Track> getTracks();
	public Track getTrackById(long id);
	public void deleteTrack(long id);
	public boolean existingTrackByIdAndName(long id, String name);
}
