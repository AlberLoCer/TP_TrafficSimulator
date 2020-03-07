package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	public static final String idKey = "id";
	public static final String greenKey = "green";
	public static final String queueKey = "queues";
	
	List<Road> incomingRoads;
	Map<Junction,Road> outgoingRoads;
	List<List<Vehicle>> queueList;
	
	// Used when a car enters the junction (to get the list<vehicles> from its road object
	Map<Road,List<Vehicle>> queueMapList;		
	
	int greenLightIdx;
	int lastSwitchTime;
	LightSwitchingStrategy lsStrategy;
	DequeingStrategy dqStrategy;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeingStrategy dqStrategy, 
			int xCoor, int yCoor) { 
		
		super(id); 
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		
		greenLightIdx = -1;
		
		incomingRoads = new ArrayList<>();
		outgoingRoads = new HashMap<>();
		queueList = new ArrayList<>();
		queueMapList = new HashMap<>();
	}

	@Override
	void advance(int time) {
		

	}

	void addIncommingRoad(Road r) {
		if (r.getDestJunc().equals(this)) {
			incomingRoads.add(r);
			LinkedList<Vehicle> aux = new LinkedList<>();
			queueList.add(aux);
			queueMapList.put(r, aux);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	void addOutGotingRoad(Road r){
		Junction destJunc = r.getDestJunc();
		if (r.getSrcJunc().equals(this)) {
			if (outgoingRoads.containsKey(destJunc)) {
				throw new IllegalArgumentException();
			}
			else {
				outgoingRoads.put(destJunc, r);
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	void enter(Road r, Vehicle v) {
		//TODO: implement method
	}
	
	Road roadTo(Junction j) {
		//TODO: implement method
		return null;	
	}
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put(idKey, _id);
		if (greenLightIdx > -1 && greenLightIdx <= incomingRoads.size()) {
			jo.put(greenKey, incomingRoads.get(greenLightIdx).getId());
		}
		else {
			jo.put(greenKey, "none");
		}
		jo.put(queueKey, queueList);
		return jo;
	}

}
