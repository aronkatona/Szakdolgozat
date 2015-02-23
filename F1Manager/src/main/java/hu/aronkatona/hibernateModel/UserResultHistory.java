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
	
	
	
	
}
