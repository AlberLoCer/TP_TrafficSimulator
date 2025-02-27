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
	private static final long serialVersionUID = -7231663420645969226L;
	private JLabel timeLabel;
	private JLabel eventLabel;
	
    public StatusBar(Controller controller){
    	controller.addObserver(this);
    	initGUI();
    }
    
    private void initGUI() {
    	this.setLayout(new FlowLayout(FlowLayout.LEFT));
    	timeLabel = new JLabel();
    	timeLabel.setText("Time: 0");
    	this.add(timeLabel);
    	
    	this.add(new JSeparator(SwingUtilities.VERTICAL));
    	
    	eventLabel = new JLabel();
    	eventLabel.setText("Welcome!");
    	this.add(eventLabel);
    }

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("Event added( " + e + " )");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("Time: "+ time);
		eventLabel.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {
		eventLabel.setText(err); 
	}
}
