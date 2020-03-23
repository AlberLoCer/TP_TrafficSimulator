package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	//TODO: Check correctness of event JLable messages
	private JLabel timeLabel;
	private JLabel eventLabel;
    public StatusBar(Controller controller){
    	initGUI();
    }
    
    private void initGUI() {
    	this.setLayout(new FlowLayout(FlowLayout.LEFT));
    	timeLabel = new JLabel();
    	timeLabel.setText("Time: 0");
    	this.add(timeLabel);
    	
    	this.add(new JSeparator(SwingUtilities.VERTICAL));
    	
    	eventLabel = new JLabel();
    	eventLabel.setText("Event: -");
    	this.add(eventLabel);
    }

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("Event: " + "Simulation time advancing...");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("Event: " + "Simulation time advance stopped."); //Provisional
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("Event added"); //Provisional
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("Event: " + "Simulator state reseted"); //Provisional
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		//For event label??
	}

	@Override
	public void onError(String err) {
		eventLabel.setText(err); //Where is err initialized??
	}
}
