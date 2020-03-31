launcher/Main.java:

 - should close the input and output files after running the simulator


control/Controller.java: 


 - it might be better to print directly to 'p' instead of creating a json

   object for the whole result, just in case the output is too large


factories/NewJunctionEventBuilder.java:


 - the constants should be private


factories/NewRoadEventBuilder.java:


 - the constants should be private


 - better to have the local variables as protected fields, and createEvent 

   without arguments


factories/NewCityRoadEventBuilder.java:


 - the constants should be private


factories/NewInterCityRoadEventBuilder.java:


 - the constants should be private


factories/NewVehicleEventBuilder.java:


 - the constants should be private


factories/SetWeatherEventBuilder.java:


 - the constants should be private


factories/SetContClassEventBuilder.java:


 - the constants should be private


factories/RoundRobinStrategyBuilder.java:


 - the constants should be private


factories/MostCrowdedStrategyBuilder.java:


 - the constants should be private


factories/MoveFirstStrategyBuilder.java:


 - the constants should be private


factories/MoveAllStrategyBuilder.java:


 - the constants should be private


model/Event.java: OK


model/NewJunctionEvent.java:


 - fields can be protected or private, you have them package protected


model/NewVehicleEvent.java:


 - fields can be protected or private, you have them package protected


 - better check that map.getJunction(juncId) is not null


model/NewRoadEvent.java:


 - fields can be protected or private, you have them package protected


model/NewCityRoadEvent.java: OK


model/NewInterCityRoadEvent.java: OK


model/SetWeatherEvent.java: OK


model/SetContClassEvent.java: OK


model/LightSwitchingStrategy.java: OK


model/RoundRobinStrategy.java: OK


model/MostCrowdedStrategy.java:


 - in giveGreen the two loop can be merged to one while loop, by incrementing

   i as i=(i+1)%qs.size and exiting the loop when i=firstIdx


model/DequeuingStrategy.java: OK


model/MoveAllStrategy.java: OK


model/MoveFirstStrategy.java:


 - should check that q is not empty it si not enough to do it in junction


model/Road.java:


 - the constants should be private

 

 - better create the comparator (of sortVehicleList) only once in the constructor


model/InterCityRoad.java: OK


model/CityRoad.java:


 - remove Math.ceil (and download examples again).


model/Junction.java:


 - the constants should be private


 - fields can be protected or private, you have them package protected


 - the queues should be printed in order as they appear in incommingRoad, this

   does not traverse them in order


	for (Road road : queueMapList.keySet()) {

          ..

        }


    replace queueMapList.keySet() by incoming roads, otherwise test 1 doesn't pass.


model/Vehicle.java:


 - the constants should be private


model/RoadMap.java:


 - the constants should be private


 - you do not check that the itinerary is OK, you just check that junctions exist. 

   You should check that there is a road between consecutive junctions.


 - remove advanceRoad and advanceJunction and do it directly in TrafficSimulator. The

   purpose of RoadMap is just to store the objects, the logic is in TrafficSimulator


model/TrafficSimulator.java:


 - in addEvent you should check that the event's time is at least like the current time


 - there is no need to traverse the whole list, you should exit once e.getTime() is

   different from simTime.


	while (eventList.size()>0 && eventList.get(0).getTime() == simTime) {

		eventList.remove(0).execute(roadMap);

	}