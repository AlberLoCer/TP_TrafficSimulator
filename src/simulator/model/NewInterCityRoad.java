package simulator.model;

public class NewInterCityRoad extends NewRoadEvent {

	public NewInterCityRoad(int time, String id, Junction srcJunc, Junction destJunc, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		super.execute(map, new InterCityRoad(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather));
	}

}
