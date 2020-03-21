package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			this.time = time;
	}

	public int getTime() {
		return time;
	}

	@Override
	public int compareTo(Event o) {
		if (time < o.getTime()) {
			return -1;
		}
		else if (time > o.getTime()){
			return 1;
		}
		else {
			return 0;
		}
	}

	abstract void execute(RoadMap map);
	public abstract String toString();
}
