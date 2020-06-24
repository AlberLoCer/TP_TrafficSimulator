package simulator.model;

import java.util.List;

public class VipStrategy implements LightSwitchingStrategy {
	
	private int timeSlot;
	private String vipTag;
	
	public VipStrategy(int timeSlot, String vipTag) {
		this.timeSlot = timeSlot;
		this.vipTag = vipTag;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size() == 0) {
			return -1;
		}
		
		int searchStart = currGreen;
		if (currGreen == -1) {
			searchStart = 0;
		}
		
		int vipIdx = -1;
		boolean found = false;
		// Look from currGreen to end of list
		for (int i = searchStart; i < roads.size() && !found; i++) {
			List<Vehicle> currList = qs.get(i);
			
			for (int j = 0; j < currList.size() && !found; j++) {
				String id = currList.get(j).getId();
				if (id.endsWith(vipTag)) {
					found = true;
					vipIdx = i;
				}
			}
		}
		// Look from beginning of list to currGreen (if not found yet)
		for (int i = 0; i < searchStart && !found; i++) {
			List<Vehicle> currList = qs.get(i);
			
			for (int j = 0; j < currList.size() && !found; j++) {
				String id = currList.get(j).getId();
				if (id.endsWith(vipTag)) {
					found = true;
					vipIdx = i;
				}
			}
		}
		
		if (found) {
			return vipIdx;
		}			
		else if (currGreen == -1){
			return 0;
		}		
		else if(currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}			
		else {
			return (currGreen+1)%roads.size();
		}			
	}
}
