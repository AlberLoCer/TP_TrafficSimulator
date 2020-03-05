package simulator.control;


import java.awt.List;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.BuilderBasedFactory;
import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.TrafficSimulator;

public class Controller {
	private static final String key = "events";

	TrafficSimulator sim;
	BuilderBasedFactory<Event> eventFact;
	
	public Controller(TrafficSimulator tS, BuilderBasedFactory<Event> factory) {
		if(tS == null || factory == null) {
			throw new NullPointerException();
		
		}
		else {
			this.sim = tS;
			this.eventFact = factory;
		}
		
	}

	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		if(jo.has(key)) {
			JSONArray jArray = jo.getJSONArray(key);
			Event eAux;
			for(int i = 0; i < jArray.length(); i++) {
				eAux = eventFact.createInstance(jArray.getJSONObject(i));
				sim.addEvent(eAux);
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public void run(int n, OutputStream out) {
		for(int i = 0; i < n; i++) {
			sim.advance();
		}
		PrintStream p = new PrintStream(out);
		p.println(sim.report());
	}
	
	public void reset() {
		sim.reset();
	}
}
