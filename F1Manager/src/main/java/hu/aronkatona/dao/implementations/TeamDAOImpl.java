package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.TeamDAO;
import hu.aronkatona.hibernateModel.Team;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeamDAOImpl implements TeamDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public void saveTeam(Team team) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(team);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getTeams() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Team.class, "team").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getTeamsOrderByPrice() {
		return sessionFactory.getCurrentSession().createCriteria(Team.class,"team")
							  .addOrder(Order.desc("team.price")).list();
	}

	@Override
	public Team getTeamById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Team team = (Team) session.get(Team.class, new Long(id));
		//Hibernate.initialize(team.getDrivers());
		return team;
		//return (Team) session.get(Team.class, new Long(id));
	}

	@Override
	public void deleteTeam(long id) {
		Session session = sessionFactory.getCurrentSession();
		Team t = (Team) session.get(Team.class, new Long(id));
		if(t != null){
			session.delete(t);
		}
		
	}

	@Override
	public Team getTeamByIdExcel(long id) {
		Session session = sessionFactory.getCurrentSession();
		Team team = (Team) session.get(Team.class, new Long(id));
		if(team != null) session.evict(team);
		return team;
	}

	

}
