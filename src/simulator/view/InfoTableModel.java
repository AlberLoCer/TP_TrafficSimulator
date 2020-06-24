package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class InfoTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	
	private List<List<Pair<Road, Integer>>> info;
	// The i-th position of the list contains the info about the tick i.
	// For each tick, it stores a list of pairs of road and their totalC02
	
	private String[] colNames = { "Tick", "Roads" };
	
	private int contLimit;
	

	public InfoTableModel(Controller controller) {
		controller.addObserver(this);
		contLimit = 1000;
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
	
	public void updateInfoList(RoadMap map, int simTime) {
		List<Road> roads = map.getRoads();
		List<Pair<Road, Integer>> list = new ArrayList<>();
		for (Road road : roads) {
			list.add(new Pair<>(road, road.getTotalCO2()));
		}
		
		this.info.add(simTime, list);
		
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
		return info == null ? 0 : info.size();
	}

	// returns the value of a particular cell (to display it visually)
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = rowIndex;
			break;
		case 1:
			List<Road> list = new ArrayList<>();
			for (Pair<Road, Integer> pair : info.get(rowIndex)) {
				if (pair.getSecond() > contLimit) {
					list.add(pair.getFirst());
				}
			}
			s = list.toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		updateInfoList(map, time);			
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		updateInfoList(map, time);			
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		updateInfoList(map, time);			
	}

	@Override
	public void onError(String err) {		
		
	}
}

