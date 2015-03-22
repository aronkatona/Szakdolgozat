package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name="TRACK")
public class Track {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotEmpty
	@Column(name="NAME", length = 100, nullable = false, unique = true)
	private String name;
	
	@NotEmpty
	@Column(name="COUNTRY")
	private String country;
	
	@NotEmpty
	@Column(name="CITY")
	private String city;
	
	@URL
	@Column(name="PICTURE", length = 2000)
	private String picture;
	
	/*@OneToMany(mappedBy="track",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<Race> races = new HashSet<>();*/

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

	/*public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}*/

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		String newLine = System.lineSeparator();
		
		result.append("id: " + id + newLine);
		result.append("name: " + name + newLine);
		result.append("country: " + country + newLine);
		result.append("city: " + city + newLine);
		
		return result.toString();
	}
	
	
	
}
