package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name="DRIVER")
public class Driver {
	
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
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID")
	private Team team;
	
	@Column(name="ACTIVE")
	private boolean active = true;
	
	/*@OneToMany(mappedBy="driver",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultRace> resultRaces = new HashSet<>();
	
	@OneToMany(mappedBy="driver",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultQualifying> resultQualifying = new HashSet<>();
	
	@OneToMany(mappedBy="actualDriver1",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<User> actualDrivers1 = new HashSet<>();
	
	@OneToMany(mappedBy="actualDriver2",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<User> actualDrivers2 = new HashSet<>();
	
	@OneToMany(mappedBy="driver1",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> drivers1 = new HashSet<>();
	
	@OneToMany(mappedBy="driver2",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> drivers2 = new HashSet<>();*/
	

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

	/*public Set<ResultRace> getResultRaces() {
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
		return "Driver [id=" + id + ", name=" + name + ", price=" + price
				+ ", point=" + point + ", picture=" + picture + ", team="
				+ team + "]";
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
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		Driver other = (Driver) obj;
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
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
	

}
