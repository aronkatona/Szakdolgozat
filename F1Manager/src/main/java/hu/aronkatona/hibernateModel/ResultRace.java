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
@Table(name="RESULT_RACE")
public class ResultRace {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="RESULT" ,nullable = false)
	private int result;
	
	@ManyToOne
	@JoinColumn(name="RACE_ID" ,nullable = false)
	private Race raceResultRaceId;
	
	@ManyToOne
	@JoinColumn(name="DRIVER_ID" ,nullable = false)
	private Driver driverResultRaceId;
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID" ,nullable = false)
	private Team teamResultRaceId;

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

	public Race getRaceResultRaceId() {
		return raceResultRaceId;
	}

	public void setRaceResultRaceId(Race raceResultRaceId) {
		this.raceResultRaceId = raceResultRaceId;
	}

	public Driver getDriverResultRaceId() {
		return driverResultRaceId;
	}

	public void setDriverResultRaceId(Driver driverResultRaceId) {
		this.driverResultRaceId = driverResultRaceId;
	}

	public Team getTeamResultRaceId() {
		return teamResultRaceId;
	}

	public void setTeamResultRaceId(Team teamResultRaceId) {
		this.teamResultRaceId = teamResultRaceId;
	}

	

	
	
	
	

}
