package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	@Override
	void reduceTotalContamination() {
		if(totalCont-weatherToCont(weather) >= 0) {
			totalCont = totalCont-weatherToCont(weather);
		}
		
		else {
			throw new UnsupportedOperationException("Contamination cannot be negative!");
		}
	}

	@Override
	void updateSpeedLimit() {
		currSpeedLimit = maxSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		v.setSpeed((int)(((11.0-v.getContClass())/11.0)*currSpeedLimit));
		return 0;
	}

	private int weatherToCont(Weather w) {
		int x;
		switch(weather) {
	
		case STORM:
			x = 10;
			break;
			
		case WINDY:
			x = 10;
			break;
			
		default:
			x = 2;
			break;
		}
		return x;
	}
}
