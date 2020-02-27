package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap roadMap;
	private List<Event> eventList;
	private int simTime;
	
	public static final String timeKey = "time";
	public static final String stateKey = "state";
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		simTime = 0;
	}

	public void addEvent(Event e) {
		eventList.add(e);
	}
	
	public void advance() {
		//Advance simulation time
		simTime++;
		//Execute and delete events if time 
		for(Event e : eventList) {
			if(e.getTime() == simTime) {
				e.execute(roadMap);
				eventList.remove(e); //TODO: Check whether deletion while iterating may cause errors
			}
		}
		//Advance methods
		roadMap.advanceJunctions(simTime);
		roadMap.advanceRoads(simTime);
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		simTime = 0;
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put(timeKey, simTime);
		jo.put(stateKey, roadMap.report());
		return jo;
	}
}
