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
	
	@Column(name="RATE")
	private int rate;
	
	

	public ResultPoint() {
	}
	
	

	public ResultPoint(int result, int driverRacePoint,int driverQualificationPoint, int teamRacePoint,int teamQualificationPoint, int rate) {
		this.result = result;
		this.driverRacePoint = driverRacePoint;
		this.driverQualificationPoint = driverQualificationPoint;
		this.teamRacePoint = teamRacePoint;
		this.teamQualificationPoint = teamQualificationPoint;
		this.rate = rate;
	}



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

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "ResultPoint [id=" + id + ", result=" + result
				+ ", driverRacePoint=" + driverRacePoint
				+ ", driverQualificationPoint=" + driverQualificationPoint
				+ ", teamRacePoint=" + teamRacePoint
				+ ", teamQualificationPoint=" + teamQualificationPoint
				+ ", rate=" + rate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driverQualificationPoint;
		result = prime * result + driverRacePoint;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + rate;
		result = prime * result + this.result;
		result = prime * result + teamQualificationPoint;
		result = prime * result + teamRacePoint;
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
		ResultPoint other = (ResultPoint) obj;
		if (driverQualificationPoint != other.driverQualificationPoint)
			return false;
		if (driverRacePoint != other.driverRacePoint)
			return false;
		if (id != other.id)
			return false;
		if (rate != other.rate)
			return false;
		if (result != other.result)
			return false;
		if (teamQualificationPoint != other.teamQualificationPoint)
			return false;
		if (teamRacePoint != other.teamRacePoint)
			return false;
		return true;
	}

	
	

}
