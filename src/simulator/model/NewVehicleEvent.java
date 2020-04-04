package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itineraryId;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itineraryId) {
		super(time); 
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itineraryId = itineraryId;
	}


	@Override
	void execute(RoadMap map) {
		// Convert list of strings in list of Junctions
		ArrayList<Junction> temp = new ArrayList<>();
		for (String juncId : itineraryId) {
			if(!map.getJunction(juncId).equals(null)) {
				temp.add(map.getJunction(juncId));
			}
		}
		
		Vehicle vehicle = new Vehicle(id, maxSpeed, contClass, temp);
		map.addVehicle(vehicle);
		vehicle.moveToNextRoad();

	}
	
	@Override
	public String toString() {
		return "New vehicle '" + id + "'";
	}

}
