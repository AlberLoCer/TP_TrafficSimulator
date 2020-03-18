package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {
	
	public NewCityRoadEvent(int time, String id, String srcJuncId, String destJuncId, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJuncId, destJuncId, length, co2Limit, maxSpeed, weather);
	}
	
	@Override
	Road createRoad (RoadMap map) {
		return new CityRoad(id, map.getJunction(srcJuncId), 
				map.getJunction(destJuncId), maxSpeed, contLimit, length, weather);
	}

	@Override
	public String toString() {
		return "New Road '" + id + "'";
	}
}
