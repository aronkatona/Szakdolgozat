package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CHAMPIONSHIP")
public class Championship {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	

	@Column(name="YEAR",nullable = false)
	private int year;

	
	/*@OneToMany(mappedBy="championship",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<Race> races = new HashSet<>();
	
	@OneToMany(mappedBy="championship",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<ChampionshipResult> championshipResults = new HashSet<>();*/
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	/*public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}

	public Set<ChampionshipResult> getChampionshipResults() {
		return championshipResults;
	}

	public void setChampionshipResults(Set<ChampionshipResult> championshipResults) {
		this.championshipResults = championshipResults;
	}*/
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Championship [id=" + id + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + year;
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
		Championship other = (Championship) obj;
		if (id != other.id)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	
	
	
}
