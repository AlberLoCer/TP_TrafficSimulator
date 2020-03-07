package simulator.model;

import java.util.List;

import javax.xml.transform.ErrorListener;

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
		//Traverse the eventList being careful when deleting an element (because index shouldn't be incremented)
		int i = 0;
		while (i < eventList.size()) {
			Event e = eventList.get(i);
			if(e.getTime() == simTime) {
				e.execute(roadMap);
				eventList.remove(e); 
			}
			else {
				i++;
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
