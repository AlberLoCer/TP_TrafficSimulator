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
}
