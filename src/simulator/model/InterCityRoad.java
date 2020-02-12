package simulator.model;

import java.util.List;

import javax.security.auth.x500.X500Principal;

public class InterCityRoad extends Road {

	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, 
			int length, Weather weather) { 
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather); 
	}

	@Override
	void reduceTotalContamination() {
		totalCont =(int)(((100.0-weatherToCont(this.weather))/100.0)*totalCont);
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
			currSpeedLimit = (int)(currSpeedLimit*0.8);
		}
		else {
			currSpeedLimit = maxSpeed;
		}
		return 0;
	}
	

	private int weatherToCont(Weather w) {
		int x;
		
		switch(weather) {
		
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case STORM:
			x = 20;
			break;
		case SUNNY:
			x = 2;
			break;
		case WINDY:
			x = 15;
			break;
		default:
			x = 20;
			break;
		}
		return x;
	}


}
