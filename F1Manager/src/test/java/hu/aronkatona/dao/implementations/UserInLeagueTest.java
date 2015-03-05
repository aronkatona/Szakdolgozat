package hu.aronkatona.dao.implementations;

import hu.aronkatona.hibernateModel.UserInLeague;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
import hu.aronkatona.service.interfaces.UserService;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class) 
@TransactionConfiguration(defaultRollback=false,transactionManager="transactionManager") 
@Transactional
public class UserInLeagueTest {

	@Autowired
	private UserInLeagueService userInLeagueService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void testJoin(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserInLeague.class,"userInLeague");
		criteria.setFetchMode("userInLeague.user", FetchMode.JOIN);
		criteria.createAlias("userInLeague.user", "user");
		ProjectionList columns = Projections.projectionList()
                .add(Projections.property("user.name"))
                .add(Projections.property("commentsRight"));
		criteria.setProjection(columns);
		 List<Object[]> list = criteria.list();
	        for(Object[] arr : list){
	            System.out.println(Arrays.toString(arr));
	        }

	}
}
