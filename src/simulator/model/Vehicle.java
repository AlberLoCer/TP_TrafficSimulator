package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private int maxSpeed;
	private int contClass;
	private List<Junction> itinerary;
	private int itineraryIdx;				// Index of the NEXT junction in the itinerary
	private int currSpeed;
	private Road road;
	private int totalContamination;
	private VehicleStatus status;
	private int location;
	private int totalDistance;	

	private static final String idKey = "id";
	private static final String speedKey = "speed";
	private static final String distanceKey = "distance";
	private static final String co2Key = "co2";
	private static final String classKey = "class";
	private static final String statusKey = "status";
	private static final String roadKey = "road";
	private static final String locationKey = "location";

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
			this.totalDistance += distTravelledOnCycle;
			
			//Update contamination
			int c = getContClass() * distTravelledOnCycle;
			setTotalContamination(getTotalContamination() + c);
			this.road.addContamination(c);			
			
			//Check end of road
			if(this.getLocation() == this.getRoad().getLength()) {
				this.setStatus(VehicleStatus.WAITING);
				this.setSpeed(0);
				// TODO: itineraryIdx or ItineraryIdx + 1 ??
				itinerary.get(itineraryIdx).enter(this);
			}
		}
	}
	
	void moveToNextRoad() {
		if (this.status == VehicleStatus.PENDING) {
			// Starts the trip -> no previous road
			this.road = itinerary.get(0).roadTo(itinerary.get(1));
			this.road.enter(this);
			itineraryIdx++;
			this.status = VehicleStatus.TRAVELING;
		}
		else if (this.status == VehicleStatus.WAITING){
			// Continues the trip
			this.road.exit(this);			
			if (itineraryIdx < itinerary.size() - 1) {
				this.road = itinerary.get(itineraryIdx).roadTo(itinerary.get(itineraryIdx + 1));
				this.location = 0;
				this.currSpeed = 0;
				this.status = VehicleStatus.TRAVELING;
				this.road.enter(this);
				itineraryIdx++;
			}
			else {
				// Has arrived to end of itinerary
				this.status = VehicleStatus.ARRIVED;
			}
		}
		else {
			// Status = ARRIVED -> End the trip -> no new road -> throw exception
			// Status = TRAVELLING -> Still moving on previous road -> throw exception			
			throw new UnsupportedOperationException();
		}
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
		if (status != VehicleStatus.ARRIVED) {
			jo.put(roadKey, road);
			jo.put(locationKey, location);
		}
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
	
	public int getLocation() {
		return location;
	}
	
	public int getCurrSpeed() {
		return currSpeed;
	}
	
	public int getContClass() {
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
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getTotalContamination() {
		return totalContamination;
	}
	
	public int getTotalDistance() {
		return totalDistance;
	}


	//PRIVATE GETTERS & SETTERS
	

	private  void setCurrSpeed(int currSpeed) {
		this.currSpeed = currSpeed;
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
}
