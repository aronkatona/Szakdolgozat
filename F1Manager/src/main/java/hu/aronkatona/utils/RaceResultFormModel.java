package hu.aronkatona.utils;

import hu.aronkatona.hibernateModel.Race;

public class RaceResultFormModel {
	
	private Race race;
	
	private int[] qualifyingDrivers;
	
	private int[] raceDrivers;

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public int[] getQualifyingDrivers() {
		return qualifyingDrivers;
	}

	public void setQualifyingDrivers(int[] qualifyingDrivers) {
		this.qualifyingDrivers = qualifyingDrivers;
	}

	public int[] getRaceDrivers() {
		return raceDrivers;
	}

	public void setRaceDrivers(int[] raceDrivers) {
		this.raceDrivers = raceDrivers;
	}

	

	

}
