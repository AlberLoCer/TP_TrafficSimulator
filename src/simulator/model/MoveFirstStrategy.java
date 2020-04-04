package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		ArrayList<Vehicle> aux = new ArrayList<Vehicle>();
		if(!q.isEmpty()) {
			aux.add(q.get(0));
		}
		return aux;
	}

}
