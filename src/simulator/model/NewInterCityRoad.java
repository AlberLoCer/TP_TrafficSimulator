package simulator.model;

public class NewInterCityRoad extends NewRoadEvent {

	public NewInterCityRoad(int time, String id, String srcJuncId, String destJuncId, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJuncId, destJuncId, length, co2Limit, maxSpeed, weather);
	}

	@Override
	Road createRoad (RoadMap map) {
		return new InterCityRoad(id, map.getJunction(srcJuncId), 
				map.getJunction(destJuncId), maxSpeed, contLimit, length, weather);
	}

}
