package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	public static final String type = "most_crowded_lss";
	public static final String timeslotKey = "timeslot";

	public MostCrowdedStrategyBuilder() {
		super(type);		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1; // Default value
		if (data.has(timeslotKey)){
			timeSlot = data.getInt(timeslotKey);
		}
		return new MostCrowdedStrategy(timeSlot);
	}
}
