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
@Table(name="RESULT_QUALIFYING")
public class ResultQualifying {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="RESULT")
	private int result;
	
	@ManyToOne
	@JoinColumn(name="RACE_ID")
	private Race raceResultQualifyingId;
	
	@ManyToOne
	@JoinColumn(name="DRIVER_ID")
	private Driver driverResultQualifyingId;
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID")
	private Team teamResultQualifyingId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Race getRaceResultQualifyingId() {
		return raceResultQualifyingId;
	}

	public void setRaceResultQualifyingId(Race raceResultQualifyingId) {
		this.raceResultQualifyingId = raceResultQualifyingId;
	}

	public Driver getDriverResultQualifyingId() {
		return driverResultQualifyingId;
	}

	public void setDriverResultQualifyingId(Driver driverResultQualifyingId) {
		this.driverResultQualifyingId = driverResultQualifyingId;
	}

	public Team getTeamResultQualifyingId() {
		return teamResultQualifyingId;
	}

	public void setTeamResultQualifyingId(Team teamResultQualifyingId) {
		this.teamResultQualifyingId = teamResultQualifyingId;
	}
	
	
}
