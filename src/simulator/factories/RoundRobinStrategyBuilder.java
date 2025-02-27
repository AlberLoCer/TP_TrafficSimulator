package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	private static final String type = "round_robin_lss";
	private static final String timeslotKey = "timeslot";

	public RoundRobinStrategyBuilder() {
		super(type);		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1; // Default value
		if (data.has(timeslotKey)){
			timeSlot = data.getInt(timeslotKey);
		}
		return new RoundRobinStrategy(timeSlot);
	}

}
