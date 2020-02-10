package simulator.model;

import java.util.List;

import javax.security.auth.x500.X500Principal;

public class InterCityRoad extends Road {

	private Junction srcJunc;
	private Junction destJunc;
	private int maxSpeed;
	private int currSpeedLimit;
	private int contLimit;
	private int totalCont;
	private int length;
	private Weather weather;
	private List<Vehicle> vehicles;
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, 
			int length, Weather weather) { 
		super(id, destJunc, destJunc, length, length, length, weather); 
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
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
		
		return 0;
	}
	

	private int weatherToCont(Weather w) {
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
