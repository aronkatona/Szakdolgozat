package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.TrackDAO;
import hu.aronkatona.hibernateModel.Track;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrackDAOImpl implements TrackDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveTrack(Track track) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(track);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Track> getTracks() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Track.class, "track").list();
	}

	@Override
	public Track getTrackById(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Track) session.get(Track.class, new Long(id));
	}

	@Override
	public void deleteTrack(long id) {
		Session session = sessionFactory.getCurrentSession();
		Track track =  (Track) session.get(Track.class, new Long(id));
		if(track != null){
			session.delete(track);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existingTrackByIdAndName(long id, String name) {
		Session session = sessionFactory.getCurrentSession();
		
		List<Track> tracks = session.createCriteria(Track.class).add(Restrictions.eq("name", name)).list();
		
		if(tracks.isEmpty()) return false;
		
		session.evict(tracks.get(0));
		
		if(id == 0 ) return true;
		else return tracks.get(0).getId() != id;		
	}
}
