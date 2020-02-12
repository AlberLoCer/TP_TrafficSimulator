package simulator.model;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, 
			int length, Weather weather) { 
		super(id); 
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
	}

	@Override
	public void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		//TODO: Sort vehicles by location
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	void addContamination(int c) {
		if(c >= 0) {
			this.totalCont += c;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
	
	void enter(Vehicle v) {
		vehicles.add(v);
		if(v.getCurrSpeed() > 0 && v.getLocation() != 0) {
			throw new UnknownError("The vehicle is either moving or not in the starting point of the road");
		}
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	public int getLength() {
		return length;
	}

	public Junction getSrcJunc() {
		return srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	
}
