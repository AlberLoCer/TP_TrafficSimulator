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
	
	//Variables
	protected int time;
	protected String id;
	protected String src;
	protected String dest;
	protected int length;
	protected int co2;
	protected int maxSpeed;
	protected String weather;

	NewRoadEventBuilder(String type) {
		super(type);
	}
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		time = data.getInt(timeKey);
		id = data.getString(idKey);
		src = data.getString(srcKey);
		dest = data.getString(destKey);
		length = data.getInt(lengthKey);
		co2 = data.getInt(co2Key);
		maxSpeed = data.getInt(maxSpeedKey);
		weather = data.getString(weatherKey);
		
		return createEvent();
	}
	
	abstract NewRoadEvent createEvent();

}
