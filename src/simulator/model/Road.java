package simulator.model;

import java.util.List;

import org.json.JSONObject;

public  abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	private int maxSpeed;
	private int currSpeedLimit;
	private int contLimit;
	private int totalCont;
	private int length;
	private Weather weather;
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
		//TODO
	}
	
	void exit(Vehicle v) {
		//TODO
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
