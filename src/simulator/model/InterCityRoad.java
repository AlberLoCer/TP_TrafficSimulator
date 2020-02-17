package simulator.model;

public class InterCityRoad extends Road {

	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, 
			int length, Weather weather) { 
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather); 
	}

	@Override
	void reduceTotalContamination() {
		totalCont =(int)(( (100.0-weatherToCont()) / 100.0) * totalCont);
	}
	
	@Override
	void updateSpeedLimit() {
		if(totalCont > contLimit) {
			currSpeedLimit = (int) (maxSpeed*0.5);
		}
		else {
			currSpeedLimit = maxSpeed;
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(weather == Weather.STORM) {
			return (int)(currSpeedLimit*0.8);
		}
		else {
			return currSpeedLimit;
		}
	}
	

	private int weatherToCont() {		
		switch(this.weather) {
			case SUNNY:
				return 2;
			case CLOUDY:
				return 3;
			case RAINY:
				return 10;
			case WINDY:
				return 15;
			case STORM:
				return 20;		
			default:
				return 20;
		}
	}


}
