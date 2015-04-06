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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="RACE")
public class Race {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	
	@NotNull
	@Column(name="DATE",nullable = false, unique = true)
	@Type(type="date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="TRACK_ID",nullable = false)
	private Track track;
	
	@ManyToOne
	@JoinColumn(name="CHAMPIONSHIP_ID",nullable = false)
	private Championship championship;
	
	@Column(name="IS_RESULT_SET")
	private boolean resultSet;
	
	/*@OneToMany(mappedBy="race",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultRace> resultRaces = new HashSet<>();
	
	@OneToMany(mappedBy="race",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<ResultQualifying> resultQualifying = new HashSet<>();
	
	@OneToMany(mappedBy="race",fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT) 
	private Set<UserResultHistory> races = new HashSet<>();*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
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

	public Set<UserResultHistory> getRaces() {
		return races;
	}

	public void setRaces(Set<UserResultHistory> races) {
		this.races = races;
	}*/

	public boolean isResultSet() {
		return resultSet;
	}

	public void setResultSet(boolean resultSet) {
		this.resultSet = resultSet;
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		String newLine = System.lineSeparator();
		
		result.append("id: " + id + newLine);
		result.append("date: " + date + newLine);
		result.append("track: " + track.getName() + newLine);
		result.append("championship: " + championship.getYear() +  newLine);
		result.append("resultSet: " + resultSet + newLine);
		
		return result.toString();
	}

	
	
	
}
