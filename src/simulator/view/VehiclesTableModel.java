package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	private String[] colNames = { "Id", "Location", "itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distance" };
	private List<Vehicle> vehicles;
	
	public VehiclesTableModel(Controller controller) {
		controller.addObserver(this);
	}
	
	public void update() {

		fireTableDataChanged();		
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return vehicles == null ? 0 : vehicles.size();
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
			s = vehicles.get(row).getId();
			break;
		case 1:
			s = vehicles.get(row).getLocation();
			break;
		case 2:
			s = vehicles.get(row).getItinerary();
			break;
		case 3:
			s = vehicles.get(row).getContClass();
			break;
		case 4:
			s = vehicles.get(row).getMaxSpeed();
			break;
		case 5:
			s = vehicles.get(row).getCurrSpeed();
			break;
		case 6:
			s = vehicles.get(row).getTotalContamination();
			break;
		case 7:
			s = vehicles.get(row).getTotalDistance();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
