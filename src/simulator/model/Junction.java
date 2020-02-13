package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	List<Road> incomingRoads;
	Map<Junction,Road> outgoingRoads;
	List<List<Vehicle>> queueList;
	//Map<Road,List<Vehicle>> queueMapList;
	int greenLightIdx;
	int lastSwitchTime;
	LightSwitchingStrategy lsStrategy;
	DequeingStrategy dqStrategy;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeingStrategy dqStrategy, 
			int xCoor, int yCoor) { super(id); 
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
	}

	@Override
	void advance(int time) {
		

	}

	void addIncommingRoad(Road r) {
		incomingRoads.add(r);
		//new LinkedList<Road>().add(r);
	}
	
	void addOutGotingRoad(Road r){
		outgoingRoads.put(r.getDestJunc(), r);
		
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
