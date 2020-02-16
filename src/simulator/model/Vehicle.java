package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
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
		
		if (maxSpeed >= 0 && contClass >= 0 && contClass <= 10 && itinerary.size()> 1) {
		
			// "Do not store list itinerary as it is received by the construction, but rather copy
			// it into a new read-only list (to avoid modifying it from outside)"
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.currSpeed = 0;						// TODO: Or -1 ?
			this.totalContamination = 0;
			this.status = VehicleStatus.PENDING;
			this.location = 0;						// TODO: Or -1 ?
			this.totalDistance = 0;
		}
		else{
			throw new IllegalArgumentException();
		}
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
				//TODO: Method of queue in junction class
			}
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	void setSpeed(int s) {
		if (s >= 0) {
			setCurrSpeed(Math.min(s, getMaxSpeed()));
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	void moveToNextRoad() {
		
	}
	
	void setContClass(int contClass) {
		if(contClass <= 10 && contClass >= 0) {
			this.contClass = contClass;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}
	
	int getLocation() {
		return location;
	}
	
	int getCurrSpeed() {
		return currSpeed;
	}
	
	int getContClass() {
		return contClass;
	}
	
	public VehicleStatus getStatus() {
		return status;
	}
	
	public List<Junction> getItinerary() {
		return itinerary;
	}
	
	public Road getRoad() {
		return road;
	}
	
	//PRIVATE GETTERS & SETTERS
	
	private int getMaxSpeed() {
		return maxSpeed;
	}

	private void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	private void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	private void setCurrSpeed(int currSpeed) {
		this.currSpeed = currSpeed;
	}

	private void setRoad(Road road) {
		this.road = road;
	}

	private int getTotalContamination() {
		return totalContamination;
	}

	private void setTotalContamination(int totalContamination) {
		this.totalContamination = totalContamination;
	}

	private void setStatus(VehicleStatus status) {
		this.status = status;
	}

	private void setLocation(int location) {
		this.location = location;
	}

	private int getTotalDistance() {
		return totalDistance;
	}

	private void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

}
