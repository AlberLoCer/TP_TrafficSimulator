package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	String id;
	int maxSpeed;
	int contClass;
	List<String> itineraryId;
	
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
			temp.add(map.getJunction(juncId));
		}
		
		Vehicle vehicle = new Vehicle(id, maxSpeed, contClass, temp);
		map.addVehicle(vehicle);
		vehicle.moveToNextRoad();
	}

}
