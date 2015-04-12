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

	@Override
	public String toString() {
		return "ResultQualifying [id=" + id + ", result=" + result + ", race="
				+ race + ", driver=" + driver + ", team=" + team + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((race == null) ? 0 : race.hashCode());
		result = prime * result + this.result;
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
		ResultQualifying other = (ResultQualifying) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (id != other.id)
			return false;
		if (race == null) {
			if (other.race != null)
				return false;
		} else if (!race.equals(other.race))
			return false;
		if (result != other.result)
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	
	
	
}
