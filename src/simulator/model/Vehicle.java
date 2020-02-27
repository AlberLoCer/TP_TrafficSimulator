package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.*;

import org.json.JSONObject;

import simulator.factories.NewVehicleEventBuilder;

public class Vehicle extends SimulatedObject {

	private int maxSpeed;
	private int contClass;
	private List<Junction> itinerary;
	private int itineraryIdx;
	private int currSpeed;
	private Road road;
	private int totalContamination;
	private VehicleStatus status;
	private int location;
	private int totalDistance;	

	public static final String idKey = "id";
	public static final String speedKey = "speed";
	public static final String distanceKey = "distance";
	public static final String co2Key = "co2";
	public static final String classKey = "class";
	public static final String statusKey = "status";
	public static final String roadKey = "road";
	public static final String locationKey = "location";

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
			this.itineraryIdx = 0;					// TODO: Or -1 ?
		}
		else{
			throw new IllegalArgumentException();
		}
	}

	@Override
	void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING) {
			//Update location
			int newLocation = Math.min(this.getLocation()+this.getCurrSpeed(), this.getRoad().getLength());
			int distTravelledOnCycle = newLocation - getLocation();		// For contamination calculus
			this.setLocation(newLocation);
			
			//Update contamination
			int c = getContClass() * distTravelledOnCycle;
			setTotalContamination(getTotalContamination() + c);
			this.road.addContamination(c);			
			
			//Check end of road
			if(this.getLocation() == this.getRoad().getLength()) {
				this.setStatus(VehicleStatus.WAITING);
				this.setSpeed(0);
				//TODO: Check this: Method of queue in junction class
				itinerary.get(itineraryIdx).enter(road, this);
			}
		}
	}
	
	void moveToNextRoad() {
		//TODO: implement
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put(idKey, _id);
		jo.put(speedKey, currSpeed);
		jo.put(distanceKey, totalDistance);
		jo.put(co2Key, totalContamination);
		jo.put(classKey, contClass);
		jo.put(statusKey, status);
		jo.put(roadKey, road);
		jo.put(locationKey, location);
		
		return jo;
	}
	
	void setSpeed(int s) {
		if (s >= 0) {
			setCurrSpeed(Math.min(s, getMaxSpeed()));
		}
		else {
			throw new IllegalArgumentException("Speed must be positive");
		}
	}
	

	
	void setContClass(int contClass) {
		if(contClass <= 10 && contClass >= 0) {
			this.contClass = contClass;
		}
		else {
			throw new IllegalArgumentException("Contamination must be between [0-10]");
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
		// Itinerary is an unmodifiable list
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

//	private void setItinerary(List<Junction> itinerary) {		// Has to be done with Collections.inmutableList
//		this.itinerary = itinerary;
//	}

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
