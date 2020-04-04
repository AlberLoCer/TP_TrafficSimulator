package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	private static final String type = "new_junction";
	private static final String timeKey = "time";
	private static final String idKey = "id";
	private static final String coordinateKey = "coor";
	private static final String ls_strategy_key = "ls_strategy";
	private static final String dq_strategy_key = "dq_strategy";
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
		LightSwitchingStrategy ls = lssFactory.createInstance(data.getJSONObject(ls_strategy_key));
		DequeingStrategy dq = dqsFactory.createInstance(data.getJSONObject(dq_strategy_key));

		return new NewJunctionEvent(time, id, ls, dq, coorX, coorY);
	}

}
