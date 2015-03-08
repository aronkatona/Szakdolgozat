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

@Entity
@Table(name="RESULT_QUALIFYING")
public class ResultQualifying {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Min(1)
	@Column(name="RESULT", nullable = false)
	private int result;
	
	@ManyToOne
	@JoinColumn(name="RACE_ID", nullable = false)
	private Race race;
	
	@ManyToOne
	@JoinColumn(name="DRIVER_ID", nullable = false)
	private Driver driver;
	
	@ManyToOne
	@JoinColumn(name="TEAM_ID", nullable = false)
	private Team team;

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

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	
	
}
