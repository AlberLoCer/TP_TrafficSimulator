package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeingStrategy> {
	
	private static final String type = "move_all_dqs";

	public MoveAllStrategyBuilder() {
		super(type);		
	}

	@Override
	protected DequeingStrategy createTheInstance(JSONObject data) {		
		return new MoveAllStrategy();
	}

}
