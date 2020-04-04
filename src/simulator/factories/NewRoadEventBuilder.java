package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRoadEvent;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	
	private static final String timeKey = "time";
	private static final String idKey = "id";
	private static final String srcKey = "src";
	private static final String destKey = "dest";
	private static final String lengthKey = "length";
	private static final String co2Key = "co2limit";
	private static final String maxSpeedKey = "maxspeed";
	private static final String weatherKey = "weather";

	NewRoadEventBuilder(String type) {
		super(type);
	}
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt(timeKey);
		String id = data.getString(idKey);
		String src = data.getString(srcKey);
		String dest = data.getString(destKey);
		int length = data.getInt(lengthKey);
		int co2 = data.getInt(co2Key);
		int maxSpeed = data.getInt(maxSpeedKey);
		String weather = data.getString(weatherKey);
		
		return createEvent(time, id, src, dest, length, co2, maxSpeed, weather);
	}
	
	abstract NewRoadEvent createEvent(int time, String id, String src, String dest, int length,
			int co2, int maxSpeed, String weather);

}
