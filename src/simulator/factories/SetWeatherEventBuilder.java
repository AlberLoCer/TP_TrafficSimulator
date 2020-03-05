package simulator.factories;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {
	
	public static final String type = "set_weather";
	public static final String timeKey = "time";
	public static final String infoKey = "info";
	
	public static final String roadSubKey = "road";
	public static final String weatherSubKey = "weather";
	
	public SetWeatherEventBuilder() {
		super(type);
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt(timeKey);
		
		JSONArray ja = data.getJSONArray(infoKey);
		ArrayList<Pair<String, Weather>> pairList = new ArrayList<>();
		ArrayList<JSONObject> jList = new ArrayList<>();

		for (int i = 0; i < ja.length(); i++) {
			jList.add(ja.getJSONObject(i));
		}
		
		for (JSONObject currJson : jList) {
			String roadId = currJson.getString(roadSubKey);
			Weather weather = Weather.valueOf(currJson.getString(weatherSubKey));
			Pair<String, Weather> currPair = new Pair<String, Weather>(roadId, weather);
			pairList.add(currPair);
		}
		
		return new SetWeatherEvent(time, pairList);
	}

}
