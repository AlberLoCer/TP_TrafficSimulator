package simulator.model;

import java.util.List;
import java.math.*;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private int maxSpeed;
	private int contClass;
	private List<Junction> itinerary;
	private int currSpeed;
	private Road road;
	private int totalContamination;
	private VehicleStatus status;
	private int location;
	private int totalDistance;
	


	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING) {
			//Update location
			this.setLocation(Math.min(this.getLocation()+this.getCurrSpeed(), this.getRoad().getLength()));
			//Update contamination
			int c = (this.getLocation()*this.getContClass())/10;
			this.setTotalContamination(this.getTotalContamination()+c);
			//Check end of road
			if(this.getLocation() == this.getRoad().getLength()) {
				this.setStatus(VehicleStatus.WAITING);
				//Method of queue in junction class
			}
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void setSpeed(int s) {
		int newSpeed;
		newSpeed = Math.min(s,getCurrSpeed());
		setCurrSpeed(newSpeed);
	}
	
	protected void moveToNextRoad() {
		
	}
	
	//GETTERS & SETTERS
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getContClass() {
		return contClass;
	}

	public void setContClass(int contClass) {
		if(contClass <= 10 && contClass >= 0) {
			this.contClass = contClass;
		}
		else {
			//Exception
		}
	}

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	public int getCurrSpeed() {
		return currSpeed;
	}

	public void setCurrSpeed(int currSpeed) {
		this.currSpeed = currSpeed;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public int getTotalContamination() {
		return totalContamination;
	}

	public void setTotalContamination(int totalContamination) {
		this.totalContamination = totalContamination;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

}
