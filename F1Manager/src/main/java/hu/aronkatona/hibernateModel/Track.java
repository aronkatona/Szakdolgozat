package hu.aronkatona.hibernateModel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="TRACK")
public class Track {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Size(min=3,max=100)
	@Column(name="NAME", nullable = false, unique = true)
	//TODO: regexp 
	private String name;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="PICTURE", length = 2000)
	private String picture;
	
	@OneToMany(mappedBy="track",fetch = FetchType.EAGER)
	private Set<Race> races = new HashSet<>();

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}

	
	
	
	
}
