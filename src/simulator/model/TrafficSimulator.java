package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	private List<TrafficSimObserver> observers;
	
	private RoadMap roadMap;
	private List<Event> eventList;
	private int simTime;
	
	public static final String timeKey = "time";
	public static final String stateKey = "state";
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		observers = new ArrayList<>();
		simTime = 0;
	}

	public void addEvent(Event e) {
		if(e.getTime() > simTime) {
			try {
				eventList.add(e);
				//Notify
				for (TrafficSimObserver obs : observers) {
					obs.onEventAdded(roadMap, eventList, e, simTime);
				} 
			} catch (Exception exc) {
				for (TrafficSimObserver obs : observers) {
					obs.onError(exc.getMessage());
				}
				throw exc;
			}
		}
	}
	
	void advanceJunctions(int time) {
		for(Junction j : roadMap.getJunctions()) {
			j.advance(time);
		}
	}
	
	void advanceRoads(int time) {
		for(Road r : roadMap.getRoads()) {
			r.advance(time);
		}
	}
	
	public void advance() {
		try {
			//Advance simulation time
			simTime++;
			//Notify
			for (TrafficSimObserver obs : observers) {
				obs.onAdvanceStart(roadMap, eventList, simTime);
			}
			//Execute and delete events if time 
			//Traverse the eventList being careful when deleting an element (because index shouldn't be incremented)
			while ((eventList.size()>0) && (eventList.get(0).getTime() == simTime)) {
				eventList.remove(0).execute(roadMap);
			}
			//Advance methods
			advanceJunctions(simTime);
			advanceRoads(simTime);
			//Notify
			for (TrafficSimObserver obs : observers) {
				obs.onAdvanceEnd(roadMap, eventList, simTime);
			} 
		} catch (Exception e) {
			for (TrafficSimObserver obs : observers) {
				obs.onError(e.getMessage());
			}
			throw e;
		}
	}
	
	public void reset() {
		try {
			roadMap.reset();
			eventList.clear();
			simTime = 0;
			//Notify
			for (TrafficSimObserver obs : observers) {
				obs.onReset(roadMap, eventList, simTime);
			} 
		} catch (Exception e) {
			for (TrafficSimObserver obs : observers) {
				obs.onError(e.getMessage());
			}
			throw e;
		}
	}
	
	public JSONObject report() {
		try {
			JSONObject jo = new JSONObject();
			jo.put(timeKey, simTime);
			jo.put(stateKey, roadMap.report());
			return jo;
		} catch (Exception e) {
			for (TrafficSimObserver obs : observers) {
				obs.onError(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public void addObserver(TrafficSimObserver o) {		
		try {
			if (!observers.contains(o)) {
				observers.add(o);
				o.onRegister(roadMap, eventList, simTime);
			}
		} catch (Exception e) {
			for (TrafficSimObserver obs : observers) {
				obs.onError(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		try {
			observers.remove(o);
		} catch (Exception e) {
			for (TrafficSimObserver obs : observers) {
				obs.onError(e.getMessage());
			}
			throw e;
		}		
	}
}
