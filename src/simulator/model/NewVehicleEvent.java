package simulator.model;

import java.util.List;

public class NewVehicleEvent extends Event {
	
	String id;
	int maxSpeed;
	int contClass;
	List<Junction> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(time); 
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}


	@Override
	void execute(RoadMap map) {
		Vehicle vehicle = new Vehicle(id, maxSpeed, contClass, itinerary);
		map.addVehicle(vehicle);
		vehicle.moveToNextRoad();
		// TODO: function call is done by local variable vehicle, but not for the copy in map

	}

}
