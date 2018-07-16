
class Calender {
	
	private final int monday = 1;
	private final int tuesday = 2;
	private final int wednesday = 3;
	private final int thursday = 4;
	private final int friday = 5;
	private final int saturday = 6;
	private final int sunday = 7;
	
	int currentDay = 1;
	
	void setDay(String day) {
		switch (day) {
			case "monday":
				currentDay = monday;
				break;
			case "tuesday":
				currentDay = tuesday;
				break;
			case "wednesday":
				currentDay = wednesday;
				break;
			case "thursday":
				currentDay = thursday;
				break;
			case "friday":
				currentDay = friday;
				break;
			case "saturday":
				currentDay = saturday;
				break;
			case "sunday":
				currentDay = sunday;
				break;
		}
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
		return currentDay != sunday && currentDay != saturday;
	}
	
	int currentDay() {return currentDay;}

}
