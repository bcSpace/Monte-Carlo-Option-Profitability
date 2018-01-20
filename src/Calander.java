
public class Calander {
	
	final int monday = 1; 
	final int tuesday = 2; 
	final int wednesday = 3; 
	final int thursday = 4; 
	final int friday = 5; 
	final int saturday = 6; 
	final int sunday = 7; 
	
	int currentDay = 1;
	
	void setDay(String day) {
		if(day.equals("monday")) currentDay = monday; 
		else if(day.equals("tuesday")) currentDay = tuesday; 
		else if(day.equals("wednesday")) currentDay = wednesday; 
		else if(day.equals("thursday")) currentDay = thursday; 
		else if(day.equals("friday")) currentDay = friday; 
		else if(day.equals("saturday")) currentDay = saturday; 
		else if(day.equals("sunday")) currentDay = sunday;
		System.exit(0);
	}
	
	void setDayC(int i) {
		currentDay = i;
	}
	
	void nextDay() {
		currentDay++;
		if(currentDay > sunday) currentDay = monday; 
	}
	
	boolean isTrading() {
		if(currentDay == sunday || currentDay == saturday) return false;
		return true; 
	}
	
	int currentDay() {return currentDay;}

}
