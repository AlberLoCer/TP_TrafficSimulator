package simulator.factories;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	private static final String type = "new_vehicle";

	private static final String timeKey = "time";
	private static final String idKey = "id";
	private static final String maxSpeedKey = "maxspeed";
	private static final String contClassKey = "class";
	private static final String itineraryKey = "itinerary";
	
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
