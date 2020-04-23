package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	List<Pair<String,Weather>> ws;
	
	private String str;

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
	
	@Override
	public String toString() {
		str = "Change Weather: [";
		for (Pair<String,Weather> w : ws) {		
			str += " (" + w.getFirst() + "," + w.getSecond() + ") ";			
		}
		str += "]";
		return str;
	}

}
