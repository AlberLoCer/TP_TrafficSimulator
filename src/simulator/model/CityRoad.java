package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	@Override
	void reduceTotalContamination() {
		totalCont = totalCont - weatherToCont();
		if(totalCont < 0) {
			totalCont = 0;
		}
	}

	@Override
	void updateSpeedLimit() {
		currSpeedLimit = maxSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return (int) Math.ceil(( (11.0-v.getContClass()) / 11.0) * currSpeedLimit);
	}

	private int weatherToCont() {
		switch(this.weather) {	
		case STORM :
			return 10;			
		case WINDY:
			return 10;			
		default:
			return 2;
		}
	}
}
