package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_RESULT_HISTORY")
public class UserResultHistory {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="USER_ID" ,nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="RACE_ID",nullable = false)
	private Race race;
	
	@ManyToOne
	@JoinColumn(name="DRIVER1_ID",nullable = false)
	private Driver driver1;
	
	@ManyToOne
	@JoinColumn(name="DRIVER2_ID",nullable = false)
	private Driver driver2;
	
	@ManyToOne
	@JoinColumn(name="TEAM1_ID",nullable = false)
	private Team team1;
	
	@ManyToOne
	@JoinColumn(name="TEAM2_ID",nullable = false)
	private Team team2;
	
	@ManyToOne
	@JoinColumn(name="TEAM3_ID",nullable = false)
	private Team team3;
	
	@Column(name="POINT",nullable = false)
	private long point;
	
	@Column(name="MONEY",nullable = false)
	private long money;
	
	@Column(name="POSITION",nullable = false)
	private long position;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Driver getDriver1() {
		return driver1;
	}

	public void setDriver1(Driver driver1) {
		this.driver1 = driver1;
	}

	public Driver getDriver2() {
		return driver2;
	}

	public void setDriver2(Driver driver2) {
		this.driver2 = driver2;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Team getTeam3() {
		return team3;
	}

	public void setTeam3(Team team3) {
		this.team3 = team3;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
	
	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "UserResultHistory [id=" + id + ", user=" + user + ", race="
				+ race + ", driver1=" + driver1 + ", driver2=" + driver2
				+ ", team1=" + team1 + ", team2=" + team2 + ", team3=" + team3
				+ ", point=" + point + ", money=" + money + ", position="
				+ position + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver1 == null) ? 0 : driver1.hashCode());
		result = prime * result + ((driver2 == null) ? 0 : driver2.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (money ^ (money >>> 32));
		result = prime * result + (int) (point ^ (point >>> 32));
		result = prime * result + (int) (position ^ (position >>> 32));
		result = prime * result + ((race == null) ? 0 : race.hashCode());
		result = prime * result + ((team1 == null) ? 0 : team1.hashCode());
		result = prime * result + ((team2 == null) ? 0 : team2.hashCode());
		result = prime * result + ((team3 == null) ? 0 : team3.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResultHistory other = (UserResultHistory) obj;
		if (driver1 == null) {
			if (other.driver1 != null)
				return false;
		} else if (!driver1.equals(other.driver1))
			return false;
		if (driver2 == null) {
			if (other.driver2 != null)
				return false;
		} else if (!driver2.equals(other.driver2))
			return false;
		if (id != other.id)
			return false;
		if (money != other.money)
			return false;
		if (point != other.point)
			return false;
		if (position != other.position)
			return false;
		if (race == null) {
			if (other.race != null)
				return false;
		} else if (!race.equals(other.race))
			return false;
		if (team1 == null) {
			if (other.team1 != null)
				return false;
		} else if (!team1.equals(other.team1))
			return false;
		if (team2 == null) {
			if (other.team2 != null)
				return false;
		} else if (!team2.equals(other.team2))
			return false;
		if (team3 == null) {
			if (other.team3 != null)
				return false;
		} else if (!team3.equals(other.team3))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
	
	
	
}
