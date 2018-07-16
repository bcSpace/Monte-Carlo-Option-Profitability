import java.text.DecimalFormat;

class PositionManager {
	
	DecimalFormat df = new DecimalFormat("#.00"); 
	
	double callStrike; 
	double putStrike; 
	
	double currentOptionPrice; 
	
	private double callPrice;
	private double putPrice;
	
	int callSize;
	int putSize; 
	
	private double currentPL;
	
	void setOptionPrice(double premiumPaid) {
		currentOptionPrice = premiumPaid; 
	}
	
	void setPutVars(double premiumPaid) {
		putPrice = premiumPaid; 
	}
	
	void setCallVars(double premiumPaid) {
		callPrice = premiumPaid; 
	}
	
	double calculatePL(double currentPremium) {
		currentPL = currentOptionPrice - currentPremium; 
		return currentPL; 
	}

}
