package hu.aronkatona.service.interfaces;

import hu.aronkatona.utils.RaceResultFormModel;

import java.util.List;

public interface RaceResultService {

	public void saveRaceResult(RaceResultFormModel raceResultFormModel);
	public List<String[]> getRaceResultByRaceId(long raceId);
	public boolean checkSameDriver(RaceResultFormModel raceResultFormModel);
}
