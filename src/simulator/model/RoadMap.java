package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	private  List<Junction> juncList;
	private  List<Road> roadList;
	private  List<Vehicle> vehicleList;
	private Map<String,Junction> juncMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
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
	if(vehicleMap.containsKey(v.getId())) {
		throw new UnsupportedOperationException("The vehicle map already contained the vehicle "+v.getId());
	}
	
	//TODO: Check itinerary. Not sure how to do it. Ask teacher.
	
	else {
		vehicleList.add(v);
		vehicleMap.put(v.getId(), v);
	}
}

}