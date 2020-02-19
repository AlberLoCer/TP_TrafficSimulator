package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	String id;
	Junction srcJunc;
	Junction destJunc;
	int length;
	int contLimit;
	int maxSpeed;
	Weather weather;
	
	public NewRoadEvent(int time, String id, Junction srcJunc, Junction destJunc, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.contLimit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	void execute(RoadMap map, Road road) {
		map.addRoad(road);
	}

}
