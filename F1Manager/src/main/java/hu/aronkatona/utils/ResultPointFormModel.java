package hu.aronkatona.utils;

public class ResultPointFormModel {
	
	private long[] ids;

	private int[] driverRacePoints;
	
	private int[] driverQualificationPoints;
	
	private int[] teamRacePoints;
	
	private int[] teamQualificationPoints;

	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	public int[] getDriverRacePoints() {
		return driverRacePoints;
	}

	public void setDriverRacePoints(int[] driverRacePoints) {
		this.driverRacePoints = driverRacePoints;
	}

	public int[] getDriverQualificationPoints() {
		return driverQualificationPoints;
	}

	public void setDriverQualificationPoints(int[] driverQualificationPoints) {
		this.driverQualificationPoints = driverQualificationPoints;
	}

	public int[] getTeamRacePoints() {
		return teamRacePoints;
	}

	public void setTeamRacePoints(int[] teamRacePoints) {
		this.teamRacePoints = teamRacePoints;
	}

	public int[] getTeamQualificationPoints() {
		return teamQualificationPoints;
	}

	public void setTeamQualificationPoints(int[] teamQualificationPoints) {
		this.teamQualificationPoints = teamQualificationPoints;
	}



	
	
}
