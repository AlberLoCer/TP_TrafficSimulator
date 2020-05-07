package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public  abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	protected int maxSpeed;
	protected int currSpeedLimit;
	protected int contLimit;
	protected int totalCont;
	private int length;
	protected Weather weather;
	private List<Vehicle> vehicles;			// Keep always sorted
	private Comparator<Vehicle> vComp;
	
	private static final String idKey = "id";
	private static final String speedLimitKey = "speedlimit";
	private static final String weatherKey = "weather";
	private static final String co2Key = "co2";
	private static final String vehiclesKey = "vehicles";
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, 
			int length, Weather weather) { 		
		super(id); 
		
		if (maxSpeed > 0 && contLimit >= 0 && length > 0 
				&& srcJunc != null && destJunc != null && weather != null) {
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
			this.currSpeedLimit = maxSpeed;
			this.totalCont = 0;
			this.vehicles = new ArrayList<>();
			this.vComp = new Comparator<Vehicle>() {

				@Override
				public int compare(Vehicle v1, Vehicle v2) {
						int v1Loc = v1.getLocation();
						int v2Loc = v2.getLocation();
						if (v1Loc == v2Loc) {
							return 0;
						}
						else if (v1Loc > v2Loc) {
							// v1 should be placed before v2 in list -> tell sort that it's smaller
							// because the Collections.sort sorts in ascending order (smallers first)
							return -1;
						}
						else {
							return 1;
						}
					}
			};
			
			destJunc.addIncommingRoad(this);
			srcJunc.addOutGotingRoad(this);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	private void sortVehicleList () {
		Collections.sort(vehicles, vComp);
	}

	@Override
	public void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v : vehicles) {
			if (v.getStatus() == VehicleStatus.TRAVELING) {
				v.setSpeed(calculateVehicleSpeed(v));
				v.advance(time);
			}			
		}		
		sortVehicleList();
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put(idKey, _id);
		jo.put(speedLimitKey, currSpeedLimit);
		jo.put(weatherKey, weather);
		jo.put(co2Key, totalCont);
		
		JSONArray ja = new JSONArray();
		for (Vehicle v : vehicles) {
			ja.put(v.getId());
		}
		
		jo.put(vehiclesKey, ja);
		
		return jo;
	}
	
	void addContamination(int c) {
		if(c >= 0) {
			this.totalCont += c;
		}
		else {
			throw new IllegalArgumentException("Contamination cannot be negative!");
		}
	}	
	
	void enter(Vehicle v) {
		if(v.getCurrSpeed() == 0 && v.getLocation() == 0) {
			vehicles.add(v);
		}
		else {
			throw new IllegalArgumentException("The vehicle is either moving or not in the starting point of the road");
		}
	}
	
	void setWeather(Weather weather) {
		if(weather != null) {
			this.weather = weather;
		}
		else {
			throw new NullPointerException("Weather was passed as null");
		}
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	public int getLength() {
		return length;
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}

	public Junction getSrcJunc() {
		return srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}
	
	public int getTotalCO2() {
		return totalCont;
	}
	
	public int getCO2Limit() {
		return contLimit;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getCurrSpeedLimit() {
		return currSpeedLimit;
	}
	
	

	
	
	
}
