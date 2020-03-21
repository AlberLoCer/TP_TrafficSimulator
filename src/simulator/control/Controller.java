package simulator.control;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private static final String key = "events";

	TrafficSimulator sim;
	Factory<Event> eventFact;
	
	public Controller(TrafficSimulator tS, Factory<Event> factory) {
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
		JSONArray ja = new JSONArray();
		for(int i = 0; i < n; i++) {
			sim.advance();
			ja.put(sim.report());
		}		
				
		JSONObject mainObj = new JSONObject();
		mainObj.put("states", ja);
		
		PrintStream p = new PrintStream(out);
		p.println(mainObj.toString(1));
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		sim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		sim.addEvent(e);
	}
}
