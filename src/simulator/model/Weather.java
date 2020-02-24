package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	
	// ValueOf equivalent. Reinventing libraries.
//	public static Weather parseStr(String str) {
//		switch (str.toUpperCase()) {
//		case "SUNNY":
//			return Weather.SUNNY;
//		case "CLOUDY":			
//			return Weather.CLOUDY;			
//		case "RAINY":			
//			return Weather.RAINY;
//		case "WINDY":			
//			return Weather.WINDY;		
//		case "STORM":			
//			return Weather.STORM;
//		default:
//			return null;
//		}
//	}
}
