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
	public String toString(){
		StringBuilder result = new StringBuilder();
		String newLine = System.lineSeparator();
		
		result.append("id: " + id + newLine);
		result.append("year: " + year + newLine);
		
		return result.toString();
	}

	
	
	
}
