package simulator.factories;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	public static final String type = "new_vehicle";

	public static final String timeKey = "time";
	public static final String idKey = "id";
	public static final String maxSpeedKey = "maxspeed";
	public static final String contClassKey = "class";
	public static final String itineraryKey = "itinerary";
	
	public NewVehicleEventBuilder() {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt(timeKey);
		String id = data.getString(idKey);
		int maxSpeed = data.getInt(maxSpeedKey);
		int contClass = data.getInt(contClassKey);
		
		JSONArray ja = data.getJSONArray(itineraryKey);
		ArrayList<String> itineraryId = new ArrayList<String>();
		for (int i = 0; i < ja.length(); i++) {
			itineraryId.add(ja.getString(i));
		}
		
		return new NewVehicleEvent(time, id, maxSpeed, contClass, itineraryId);
	}

}
