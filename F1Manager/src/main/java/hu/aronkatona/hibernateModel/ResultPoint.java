package hu.aronkatona.hibernateModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RESULT_POINT")
public class ResultPoint {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="RESULT", unique = true)
	private int result;
	
	@Column(name="DRIVER_RACE_POINT")
	private int driverRacePoint;
	
	@Column(name="DRIVER_QUALIFICATION_POINT")
	private int driverQualificationPoint;
	
	@Column(name="TEAM_RACE_POINT")
	private int teamRacePoint;
	
	@Column(name="TEAM_QUALIFICATION_POINT")
	private int teamQualificationPoint;

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

	public int getDriverRacePoint() {
		return driverRacePoint;
	}

	public void setDriverRacePoint(int driverRacePoint) {
		this.driverRacePoint = driverRacePoint;
	}

	public int getDriverQualificationPoint() {
		return driverQualificationPoint;
	}

	public void setDriverQualificationPoint(int driverQualificationPoint) {
		this.driverQualificationPoint = driverQualificationPoint;
	}

	public int getTeamRacePoint() {
		return teamRacePoint;
	}

	public void setTeamRacePoint(int teamRacePoint) {
		this.teamRacePoint = teamRacePoint;
	}

	public int getTeamQualificationPoint() {
		return teamQualificationPoint;
	}

	public void setTeamQualificationPoint(int teamQualificationPoint) {
		this.teamQualificationPoint = teamQualificationPoint;
	}


	
	

}
