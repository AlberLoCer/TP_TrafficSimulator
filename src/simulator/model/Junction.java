package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
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
		
	}
	
	Road roadTo(Junction j) {
		
		return null;	
	}
	@Override
	public JSONObject report() {
		
		return null;
	}

}
