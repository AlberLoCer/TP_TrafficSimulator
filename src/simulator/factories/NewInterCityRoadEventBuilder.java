package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {
	
	public static final String type = "new_inter_city_road";

	public NewInterCityRoadEventBuilder() {
		super(type);
	}

	@Override
	NewRoadEvent createEvent(int time, String id, String src, String dest, int length, int co2, int maxSpeed,
			String weather) {
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2, maxSpeed, Weather.valueOf(weather));
	}

}
