package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class RondaboutJunction extends Junction {

	private static final String idKey = "id";
	private static final String greenKey = "green";
	private static final String queueKey = "queues";
	private static final String roadSubKey = "road";
	private static final String vehiclesSubKey = "vehicles";
	private static final String typeKey = "type";
	
	RondaboutJunction(String id, LightSwitchingStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id, lsStrategy, dqStrategy, xCoor, yCoor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void advance(int time) {
		if (greenLightIdx != -1 && greenLightIdx < incomingRoads.size()) {
			List<Vehicle> currQueue = queueMapList.get(incomingRoads.get(greenLightIdx));
			if (!currQueue.isEmpty()) {
				List<Vehicle> toDequeue = dqStrategy.dequeue(currQueue);
					Vehicle v = toDequeue.get(toDequeue.size()-1);
					v.moveToNextRoad();
					currQueue.remove(v);
			}
			if(currQueue.isEmpty()) {
				int newGreen = lsStrategy.chooseNextGreen(incomingRoads, queueList, greenLightIdx, lastSwitchTime, time);
				if (newGreen != greenLightIdx) {
					greenLightIdx = newGreen;
					lastSwitchTime = time;
				}
			}
		}
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
		jo.put(typeKey, "ra");
		
		return jo;
	}
	

	
	}


