package simulator.factories;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
public class SetContClassEventBuilder extends Builder<Event> {

	private static final String type = "set_cont_class";
	private static final String timeKey = "time";
	private static final String infoKey = "info";
	
	private static final String vehicleSubKey = "vehicle";
	private static final String contClassSubKey = "class";
	
	public SetContClassEventBuilder() {
		super(type);		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt(timeKey);
		
		JSONArray ja = data.getJSONArray(infoKey);
		ArrayList<Pair<String, Integer>> pairList = new ArrayList<>();
		ArrayList<JSONObject> jList = new ArrayList<>();

		for (int i = 0; i < ja.length(); i++) {
			jList.add(ja.getJSONObject(i));
		}
		
		for (JSONObject currJson : jList) {
			String vehicleId = currJson.getString(vehicleSubKey);
			//Integer contClass = Integer.parseInt(currJson.getString(contClassSubKey));
			int contClass = currJson.getInt(contClassSubKey);
			Pair<String, Integer> currPair = new Pair<String, Integer>(vehicleId, contClass);
			pairList.add(currPair);
		}
		
		return new NewSetContClassEvent(time, pairList);
	}
}
