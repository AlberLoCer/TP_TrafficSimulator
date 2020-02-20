package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeingStrategy> {
	
	private static final String type = "move_first_dqs";

	public MoveFirstStrategyBuilder() {
		super(type);		
	}

	@Override
	protected DequeingStrategy createTheInstance(JSONObject data) {		
		return new MoveFirstStrategy();
	}

}
