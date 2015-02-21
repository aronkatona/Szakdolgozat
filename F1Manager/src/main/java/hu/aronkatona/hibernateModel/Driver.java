package hu.aronkatona.hibernateModel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="DRIVER")
public class Driver {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Size(min=3,max=100)
	@Column(name="NAME", nullable = false, unique = true)
	//TODO: regexp 
	private String name;
	
	@Column(name="PRICE")
	private long price;
	
	@Column(name="POINT")
	private int point;
	
	@Column(name="PICTURE", length = 2000)
	private String picture;
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID")
	private Team team;
	
	@OneToMany(mappedBy="driverResultRaceId",fetch = FetchType.EAGER)
	private Set<ResultRace> resultRaces = new HashSet<>();
	
	@OneToMany(mappedBy="driverResultQualifyingId",fetch = FetchType.EAGER)
	private Set<ResultQualifying> resultQualifying = new HashSet<>();
	
	@OneToMany(mappedBy="actualDriver1",fetch = FetchType.EAGER)
	private Set<User> actualDrivers1 = new HashSet<>();
	
	@OneToMany(mappedBy="actualDriver2",fetch = FetchType.EAGER)
	private Set<User> actualDrivers2 = new HashSet<>();
	
	@OneToMany(mappedBy="driver1",fetch = FetchType.EAGER)
	private Set<UserResultHistory> drivers1 = new HashSet<>();
	
	@OneToMany(mappedBy="driver2",fetch = FetchType.EAGER)
	private Set<UserResultHistory> drivers2 = new HashSet<>();
	

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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Set<ResultRace> getResultRaces() {
		return resultRaces;
	}

	public void setResultRaces(Set<ResultRace> resultRaces) {
		this.resultRaces = resultRaces;
	}

	public Set<ResultQualifying> getResultQualifying() {
		return resultQualifying;
	}

	public void setResultQualifying(Set<ResultQualifying> resultQualifying) {
		this.resultQualifying = resultQualifying;
	}

	public Set<User> getActualDrivers1() {
		return actualDrivers1;
	}

	public void setActualDrivers1(Set<User> actualDrivers1) {
		this.actualDrivers1 = actualDrivers1;
	}

	public Set<User> getActualDrivers2() {
		return actualDrivers2;
	}

	public void setActualDrivers2(Set<User> actualDrivers2) {
		this.actualDrivers2 = actualDrivers2;
	}

	public Set<UserResultHistory> getDrivers1() {
		return drivers1;
	}

	public void setDrivers1(Set<UserResultHistory> drivers1) {
		this.drivers1 = drivers1;
	}

	public Set<UserResultHistory> getDrivers2() {
		return drivers2;
	}

	public void setDrivers2(Set<UserResultHistory> drivers2) {
		this.drivers2 = drivers2;
	}

	
	
	

}