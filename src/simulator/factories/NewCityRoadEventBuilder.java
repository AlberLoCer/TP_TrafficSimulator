package simulator.factories;

import simulator.model.NewCityRoadEvent;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {
	
	private static final String type = "new_city_road";
	
	public NewCityRoadEventBuilder() {
		super(type);		
	}
	
	@Override
	NewRoadEvent createEvent() {
		return new NewCityRoadEvent(time, id, src, dest, length, co2, maxSpeed, Weather.valueOf(weather));
	}

}
