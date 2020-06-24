package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;
import simulator.model.VipStrategy;

public class VipStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	private static final String type = "vip_lss";
	private static final String vipTagKey = "viptag";
	private static final String timeslotKey = "timeslot";

	public VipStrategyBuilder() {
		super(type);		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1; // Default value
		String tag = "vip";
		if (data.has(timeslotKey)){
			timeSlot = data.getInt(timeslotKey);
		}
		if (data.has(vipTagKey)){
			tag = data.getString(vipTagKey);
		}
		return new VipStrategy(timeSlot, tag);
	}

}
