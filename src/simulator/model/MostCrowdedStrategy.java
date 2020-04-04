package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int timeSlot;
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size() == 0) {
			return -1;
		}	
		else if(currGreen == -1) {
			return giveGreen(qs, 0);
		}
		else if(currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		else {
			return giveGreen(qs, currGreen + 1);
		}		
	}
	
	private int giveGreen(List<List<Vehicle>> qs, int firstIdx) {
		int idx = 0;
		int i = 0;
		int aux = -1;
		List<Vehicle> inside;
		
		while(i != firstIdx) {
			inside = qs.get(i);
			if (inside.size() > aux) {
				aux = inside.size();
				idx = i;
			}
			i = (i+1)%qs.size();
		}
		
		return idx;
	}
	

}
