package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.TrackDAO;
import hu.aronkatona.hibernateModel.Track;
import hu.aronkatona.service.interfaces.TrackService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TrackServiceImpl implements TrackService {

	@Autowired
	private TrackDAO trackDAO;

	@Override
	public void saveTrack(Track track) {
		trackDAO.saveTrack(track);
	}

	@Override
	public List<Track> getTracks() {
		return trackDAO.getTracks();
	}

	@Override
	public Track getTrackById(long id) {
		return trackDAO.getTrackById(id);
	}

	@Override
	public void deleteTrack(long id) {
		trackDAO.deleteTrack(id);
	}
	
	
}
