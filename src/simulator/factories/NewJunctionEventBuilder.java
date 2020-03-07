package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	public static final String type = "new_junction";
	public static final String timeKey = "time";
	public static final String idKey = "id";
	public static final String coordinateKey = "coor";
	public static final String ls_strategy_key = "ls_strategy";
	public static final String dq_strategy_key = "dq_strategy";
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, 
			Factory<DequeingStrategy> dqsFactory) {
		super(type);
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}
	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt(timeKey);
		String id = data.getString(idKey);
		
		
		JSONArray coorJArray = data.getJSONArray(coordinateKey);
		int coorX = coorJArray.getInt(0);
		int coorY = coorJArray.getInt(1);
//		String coorString = data.getString(coordinateKey);		
//		int coorX = Integer.parseInt(coorString.substring(2, 4));
//		int coorY = Integer.parseInt(coorString.substring(6, 8)); //TODO: Check if it is right
		
		
		
		LightSwitchingStrategy ls = lssFactory.createInstance(data.getJSONObject(ls_strategy_key));
		DequeingStrategy dq = dqsFactory.createInstance(data.getJSONObject(dq_strategy_key));

		return new NewJunctionEvent(time, id, ls, dq, coorX, coorY);
	}

}
