package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{
	
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
			s = junctions.get(rowIndex).getQueueList().get(junctions.get(rowIndex).getGreenLightIndex()).toString();
			if(s == null) {
				s =  new String("NONE");
			}
			break;
		case 2:
			s = junctions.get(rowIndex).getQueueMapList().toString();
			if(s == null) {
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
