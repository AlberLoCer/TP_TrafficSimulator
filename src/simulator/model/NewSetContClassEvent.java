package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	
	private List<Pair<String, Integer>> cs;

	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if (cs != null) {
			this.cs = cs;
		}
		else {
			throw new NullPointerException();
		}	
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String,Integer> c : cs) {
			Vehicle vehicle = map.getVehicle(c.getFirst());
			if (vehicle != null) {
				vehicle.setContClass(c.getSecond());
			}
			else {
				throw new NullPointerException();
			}
		}
	}
	
	@Override
	public String toString() {
		String event = "Event: Change CO2 class: [";
		for(Pair<String,Integer> c : cs) {
			event+="("+c.getFirst()+"," + c.getSecond() + ")";
		}
		event+="]";
		return event;
	}

}
