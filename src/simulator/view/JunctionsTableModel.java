package simulator.view;

import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private static final long serialVersionUID = 575580476704937756L;
	private List<Junction> junctions;
	private String[] colNames = { "Id", "Green", "Queues" };
	
	public JunctionsTableModel(Controller controller) {
		controller.addObserver(this);
	}
	
	public void setJunctionsList(List<Junction> junctions) {
		this.junctions = junctions;
		update();
	}

	
	public void update() {
		fireTableDataChanged();		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return junctions == null ? 0 : junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = junctions.get(rowIndex).getId();
			break;
		case 1:
			int index = junctions.get(rowIndex).getGreenLightIndex();
			
			if(index >= 0 && index < junctions.get(rowIndex).getQueueList().size()) {
				s = junctions.get(rowIndex).getInRoads().get(index).toString();
			}
			else {
				s =  new String("NONE");
			}
			break;
		case 2:
			Map<Road, List<Vehicle>> aux = junctions.get(rowIndex).getQueueMapList();
			s = aux.toString();
			if(s == null || aux.isEmpty()) {
				s =  new String(" ");
			}
			break;
		}
		return s;
	}
	
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
