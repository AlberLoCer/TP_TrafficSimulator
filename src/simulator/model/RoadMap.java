package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> juncList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction> juncMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	private static final String junctionsKey = "junctions";
	private static final String roadKey = "roads";
	private static final String vehiclesKey = "vehicles";
	
	public RoadMap() {
		juncList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		juncMap = new HashMap<String, Junction>();
		roadMap = new HashMap<String, Road>();
		vehicleMap = new HashMap<String, Vehicle>();
	}

	void addJunction(Junction j) {
		if(juncMap.containsKey(j.getId())) {
			throw new UnsupportedOperationException("The junction map already contained the junction "+j.getId());
		}
		else {
			juncList.add(j);
			juncMap.put(j.getId(), j);
		}
	}
	
	void addRoad(Road r) {
		if(roadMap.containsKey(r.getId())) {
			throw new UnsupportedOperationException("The road map already contained the road "+r.getId());
		}
		
		else if (!juncMap.containsValue(r.getDestJunc()) || !juncMap.containsValue(r.getSrcJunc())) { //Ask if correct
			throw new UnsupportedOperationException("Could not find the junctions for the road "+r.getId());
		}
		
		else {
			roadList.add(r);
			roadMap.put(r.getId(), r);
		}
	}
	
	void addVehicle(Vehicle v) {
		boolean junctionsOK = true;
		for(Junction j : v.getItinerary()) {
			if (!juncMap.containsValue(j)) {
				junctionsOK = false;
			}
		}
		if(vehicleMap.containsKey(v.getId())) {
			throw new UnsupportedOperationException("The vehicle map already contained the vehicle "+v.getId());
		}	
		else if (!junctionsOK) {
			throw new UnsupportedOperationException("Some junctions in vehicle itinerary is not registered");
		}		
		else {			
			vehicleList.add(v);
			vehicleMap.put(v.getId(), v);
		}
	}
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		
		ArrayList<JSONObject> juncArray = new ArrayList<JSONObject>();
		for(Junction j : juncList) {
			juncArray.add(j.report());
		}
		jo.put(junctionsKey, juncArray);
		
		ArrayList<JSONObject> roadArray = new ArrayList<JSONObject>();
		for(Road r : roadList) {
			roadArray.add(r.report());
		}
		jo.put(roadKey, roadArray);
		
		ArrayList<JSONObject> vehicleArray = new ArrayList<JSONObject>();
		for(Vehicle v : vehicleList) {
			vehicleArray.add(v.report());
		}		
		jo.put(vehiclesKey, vehicleArray);
		
		return jo;
	}
	
	public void reset() {
		juncList.clear();
		roadList.clear();;
		vehicleList.clear();
		juncMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
	
	void advanceJunctions(int time) {
		for(Junction j : juncList) {
			j.advance(time);
		}
	}
	
	void advanceRoads(int time) {
		for(Road r : roadList) {
			r.advance(time);
		}
	}
	
	public Junction getJunction(String id) {	
		return juncMap.get(id);	
	}
	
	public Road getRoad(String id) {
		return roadMap.get(id);	
	}
	
	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(this.juncList);
	}

	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(this.vehicleList);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(this.roadList);
	}

}
