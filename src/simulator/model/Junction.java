package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	private static final String idKey = "id";
	private static final String greenKey = "green";
	private static final String queueKey = "queues";
	private static final String roadSubKey = "road";
	private static final String vehiclesSubKey = "vehicles";
	
	protected List<Road> incomingRoads;
	protected Map<Junction,Road> outgoingRoads;
	protected List<List<Vehicle>> queueList;
	
	// Used when a car enters the junction (to get the list<vehicles> from its road object)
	protected Map<Road,List<Vehicle>> queueMapList;		
	
	protected int greenLightIdx;
	protected int lastSwitchTime;
	protected LightSwitchingStrategy lsStrategy;
	protected DequeingStrategy dqStrategy;
	protected int xCoor, yCoor;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeingStrategy dqStrategy, 
			int xCoor, int yCoor) { 
		
		super(id); 
		
		if (lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0) {			
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			
			greenLightIdx = -1;
			
			incomingRoads = new ArrayList<>();
			outgoingRoads = new HashMap<>();
			queueList = new ArrayList<>();
			queueMapList = new HashMap<>();
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	void advance(int time) {
		if (greenLightIdx != -1 && greenLightIdx < incomingRoads.size()) {
			List<Vehicle> currQueue = queueMapList.get(incomingRoads.get(greenLightIdx));
			if (!currQueue.isEmpty()) {
				List<Vehicle> toDequeue = dqStrategy.dequeue(currQueue);
				for (Vehicle v : toDequeue) {
					v.moveToNextRoad();
					currQueue.remove(v);
				}
			}
		}
		
		int newGreen = lsStrategy.chooseNextGreen(incomingRoads, queueList, greenLightIdx, lastSwitchTime, time);
		if (newGreen != greenLightIdx) {
			greenLightIdx = newGreen;
			lastSwitchTime = time;
		}
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
		if (r.getSrcJunc().equals(this)) {
			Junction destJunc = r.getDestJunc();
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
	
	void enter( Vehicle v) {		
		queueMapList.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction j) {
		return outgoingRoads.get(j);
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
		
		JSONArray jArray = new JSONArray();
		for (Road road : incomingRoads) {
			JSONObject jObject = new JSONObject();
			jObject.put(roadSubKey, road.getId());
		
			JSONArray vehiclesJArray = new JSONArray();
			for (Vehicle v : queueMapList.get(road)) {
				vehiclesJArray.put(v.getId());
			}
			jObject.put(vehiclesSubKey, vehiclesJArray);			
			
			jArray.put(jObject);
		}
		jo.put(queueKey, jArray);		
		
		return jo;
	}
	
	public int getX() {
		return xCoor;
	}
	
	public int getY() {
		return yCoor;
	}
	
	public int getGreenLightIndex() {
		return greenLightIdx;
	}
	
	public List<Road> getInRoads(){
		return Collections.unmodifiableList(incomingRoads);
	}
	
	public Map<Junction, Road> getOutRoads(){
		return  Collections.unmodifiableMap(outgoingRoads);
	}

	public List<List<Vehicle>> getQueueList() {
		return queueList;
	}

	public Map<Road, List<Vehicle>> getQueueMapList() {
		return queueMapList;
	}
	
	
	
	

}
