package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;


@Entity
@Table(name="TEAM")
public class Team {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotEmpty
	@Column(name="NAME", length = 100, nullable = false, unique = true)
	private String name;
	
	@Min(0)
	@Column(name="PRICE")
	private long price;
	
	@Column(name="POINT")
	private int point;
	
	@URL
	@Column(name="PICTURE", length = 2000)
	private String picture;
	
	@Column(name="ACTIVE")
	private boolean active = true;
	
	/*@OneToMany(mappedBy="team",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<Driver> drivers = new HashSet<>();
	
	@OneToMany(mappedBy="team",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultRace> resultRaces = new HashSet<>();
	
	@OneToMany(mappedBy="team",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultQualifying> resultQualifying = new HashSet<>();
	
	@OneToMany(mappedBy="actualTeam1",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<User> actualTeams1 = new HashSet<>();
	
	@OneToMany(mappedBy="actualTeam2",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<User> actualTeams2 = new HashSet<>();
	
	@OneToMany(mappedBy="actualTeam3",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<User> actualTeams3 = new HashSet<>();
	
	@OneToMany(mappedBy="team1",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> teams1 = new HashSet<>();
	
	@OneToMany(mappedBy="team2",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> teams2 = new HashSet<>();
	
	@OneToMany(mappedBy="team3",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> teams3 = new HashSet<>();*/

	
	public Team() {
	}
	
	public Team(String name, long price, int point, String picture,boolean active) {
		this.name = name;
		this.price = price;
		this.point = point;
		this.picture = picture;
		this.active = active;
	}
	
	

	public Team(long id, String name, long price, int point, String picture,boolean active) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.point = point;
		this.picture = picture;
		this.active = active;
	}

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

	/*public Set<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Set<Driver> drivers) {
		this.drivers = drivers;
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

	public Set<User> getActualTeams1() {
		return actualTeams1;
	}

	public void setActualTeams1(Set<User> actualTeams1) {
		this.actualTeams1 = actualTeams1;
	}

	public Set<User> getActualTeams2() {
		return actualTeams2;
	}

	public void setActualTeams2(Set<User> actualTeams2) {
		this.actualTeams2 = actualTeams2;
	}

	public Set<User> getActualTeams3() {
		return actualTeams3;
	}

	public void setActualTeams3(Set<User> actualTeams3) {
		this.actualTeams3 = actualTeams3;
	}

	public Set<UserResultHistory> getTeams1() {
		return teams1;
	}

	public void setTeams1(Set<UserResultHistory> teams1) {
		this.teams1 = teams1;
	}

	public Set<UserResultHistory> getTeams2() {
		return teams2;
	}

	public void setTeams2(Set<UserResultHistory> teams2) {
		this.teams2 = teams2;
	}

	public Set<UserResultHistory> getTeams3() {
		return teams3;
	}

	public void setTeams3(Set<UserResultHistory> teams3) {
		this.teams3 = teams3;
	}*/
	
	public void increasePrice(long price) {
		this.price += price;
	}
	
	public void increasePoint(int point){
		this.point += point;
	}
	
	


	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static boolean equals(Object o1 , Object o2){
		 return o1 == o2 || (o1 != null && o1.equals(o2));
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", price=" + price
				+ ", point=" + point + ", picture=" + picture + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result + point;
		result = prime * result + (int) (price ^ (price >>> 32));
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
		Team other = (Team) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (point != other.point)
			return false;
		if (price != other.price)
			return false;
		return true;
	}

	
	
	

}
