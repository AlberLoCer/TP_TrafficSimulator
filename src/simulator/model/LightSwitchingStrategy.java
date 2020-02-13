package simulator.model;

import java.util.List;

public interface LightSwitchingStrategy {
	protected abstract int timeSlot;
	int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, 
			int lastSwitchingTime, int currTime);
}
