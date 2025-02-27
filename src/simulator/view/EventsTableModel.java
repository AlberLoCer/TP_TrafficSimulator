package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	
	private List<Event> events;
	private String[] colNames = { "Time", "Desc." };
	

	public EventsTableModel(Controller controller) {
		controller.addObserver(this);
		// This method will trigger a call to onRegister(), 
		// in which we can get the initial list of events
	}

	public void update() {
		// This model holds the data.
		// The table holds the visual view.
		// So we need to notify the table that the data here has changed
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();		
	}
	
	public void setEventsList(List<Event> events) {
		this.events = events;
		update();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	// this is for the number of columns
	@Override
	public int getColumnCount() {
		return colNames.length;
	}
	
	// the number of row, like those in the events list
	@Override
	public int getRowCount() {
		return events == null ? 0 : events.size();
	}

	// returns the value of a particular cell (to display it visually)
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = events.get(rowIndex).getTime();
			break;
		case 1:
			s = events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setEventsList(events);			
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setEventsList(events);			
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setEventsList(events);	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setEventsList(events);		
	}

	@Override
	public void onError(String err) {		
		
	}
}

