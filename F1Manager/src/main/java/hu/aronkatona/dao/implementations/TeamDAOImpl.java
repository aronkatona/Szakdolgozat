package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.TeamDAO;
import hu.aronkatona.hibernateModel.Team;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamDAOImpl implements TeamDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public void saveTeam(Team t) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(t);	
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Team> getTeams() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Team.class, "team");
		return criteria.list();

	}

	@Transactional
	@Override
	public Team getTeamById(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Team) session.get(Team.class, new Long(id));
	}

	@Transactional
	@Override
	public void deleteTeam(long id) {
		Session session = sessionFactory.getCurrentSession();
		Team t = (Team) session.get(Team.class, new Long(id));
		if(t != null){
			session.delete(t);
		}
		
	}

}
