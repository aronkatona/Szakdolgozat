package hu.aronkatona.hibernateModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="LEAGUE")
public class League {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	@Column(name="NAME",length = 100, nullable = false, unique = true)
	private String name;
	
	@NotEmpty
	@Column(name="DESCRIPTION" ,length = 1000, nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_USER_ID",nullable = false)
	private User creator;
	
	@Column(name="NUMBER_OF_USERS")
	private short numberOfUsers;
	
	@Column(name="DATE" ,nullable = false)
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	
	@Column(name="AVG_POINTS")
	private short avgPoints;
	
	/*@OneToMany(mappedBy="league",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserInLeague> userInLeague = new HashSet<>();
	
	@OneToMany(mappedBy="league",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<LeagueComment> leagueComments = new HashSet<>();*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(short numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public short getAvgPoints() {
		return avgPoints;
	}

	public void setAvgPoints(short avgPoints) {
		this.avgPoints = avgPoints;
	}

	/*public Set<UserInLeague> getUserInLeague() {
		return userInLeague;
	}

	public void setUserInLeague(Set<UserInLeague> userInLeague) {
		this.userInLeague = userInLeague;
	}

	public Set<LeagueComment> getLeagueComments() {
		return leagueComments;
	}

	public void setLeagueComments(Set<LeagueComment> leagueComments) {
		this.leagueComments = leagueComments;
	}*/

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		String newLine = System.lineSeparator();
		
		result.append("id: " + id + newLine);
		result.append("name: " + name + newLine);
		result.append("description: " + description + newLine);
		result.append("creator" + creator.getName() + newLine);
		result.append("numberOfUsers: " + numberOfUsers + newLine);
		result.append("date: " + date + newLine);
		result.append("avgPoints: " + avgPoints + newLine);
		
		return result.toString();
	}

	
	
	
}
