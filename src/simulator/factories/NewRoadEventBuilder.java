package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	
	public static final String timeKey = "time";
	public static final String idKey = "id";
	public static final String srcKey = "src";
	public static final String destKey = "dest";
	public static final String lengthKey = "length";
	public static final String co2Key = "co2limit";
	public static final String maxSpeedKey = "maxspeed";
	public static final String weatherKey = "weather";

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
