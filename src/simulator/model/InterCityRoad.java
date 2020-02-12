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
		//Do cases
		int x = 0;
		if(w == Weather.SUNNY) {
			x = 2;
		}
		
		else if(w == Weather.CLOUDY) {
			x = 3;
		}
		
		else if(w == Weather.RAINY) {
			x = 10;
		}
		
		else if(w == Weather.WINDY) {
			x = 15;
		}
		
		else {
			x = 20;
		}
		
		return x;
	}


}
