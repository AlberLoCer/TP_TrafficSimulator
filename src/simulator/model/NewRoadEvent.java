package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	String id;
	String srcJuncId;
	String destJuncId;
	int length;
	int contLimit;
	int maxSpeed;
	Weather weather;
	
	public NewRoadEvent(int time, String id, String srcJuncId, String destJuncId, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJuncId = srcJuncId;
		this.destJuncId = destJuncId;
		this.length = length;
		this.contLimit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(createRoad(map));
	}
	
	abstract Road createRoad(RoadMap roadMap) ;

}
