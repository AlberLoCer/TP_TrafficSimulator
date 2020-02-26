package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {
	
	public static final String type = "new_inter_city_road";
	public static final String timeKey = "time";
	public static final String idKey = "id";
	public static final String srcKey = "src";
	public static final String destKey = "dest";
	public static final String lengthKey = "length";
	public static final String co2Key = "co2limit";
	public static final String maxSpeedKey = "maxSpeed";
	public static final String weatherKey = "weather";

	NewInterCityRoadEventBuilder() {
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
		
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2, maxSpeed, Weather.valueOf(weather));
	}

}
