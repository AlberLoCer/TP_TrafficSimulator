package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	List<Pair<String,Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws)  {
		super(time);
		if (ws != null) {
			this.ws = ws;
		}
		else {
			throw new NullPointerException();
		}
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String,Weather> w : ws) {
			Road road = map.getRoad(w.getFirst());
			if (road != null) {
				road.setWeather(w.getSecond());
			}
			else {
				throw new NullPointerException();
			}
		}

	}

}
