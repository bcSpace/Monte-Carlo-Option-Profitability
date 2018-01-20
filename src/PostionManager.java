import java.text.DecimalFormat;

public class PostionManager {
	
	DecimalFormat df = new DecimalFormat("#.00"); 
	
	double callStrike; 
	double putStrike; 
	
	double currentOptionPrice; 
	
	double callPrice; 
	double putPrice; 
	
	int callSize;
	int putSize; 
	
	double currentPL;
	
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
