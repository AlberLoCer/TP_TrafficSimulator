package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private static final long serialVersionUID = 7536840294312563173L;
	private String[] colNames = { "Id", "Length", "Weather", "Max Speed", "Speed Limit",  "Total CO2", "CO2 Limit" };
	private List<Road> roads;
	
	public RoadsTableModel(Controller controller) {
		controller.addObserver(this);
	}

	
	public void setRoadList(List<Road> roads) {
		this.roads = roads;
		update();
	}


	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return roads == null ? 0 : roads.size();
	}
	
	public void update() {
		fireTableDataChanged();		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object s = null;
		switch (col) {
		case 0:
			s = roads.get(row).getId();
			break;
		case 1:
			s = roads.get(row).getLength();
			break;
		case 2:
			s = roads.get(row).getWeather().toString();
			break;
		case 3:
			s = roads.get(row).getMaxSpeed();
			break;
		case 4:
			s = roads.get(row).getCurrSpeedLimit();
			break;
		case 5:
			s = roads.get(row).getTotalCO2();
			break;
		case 6:
			s = roads.get(row).getCO2Limit();
			break;
		}
		return s;
	}
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setRoadList(map.getRoads());		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setRoadList(map.getRoads());		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setRoadList(map.getRoads());
	}

	@Override
	public void onError(String err) {
		
	}

}
