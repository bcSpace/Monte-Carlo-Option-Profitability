import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.text.DecimalFormat;

public class DataManagement {
	
	DecimalFormat df = new DecimalFormat("#.00"); 
	
	double underlyingPrice[] = new double[100];
	double putPrice[] = new double[100];
	double callPrice[] = new double[100];
	double pl[] = new double[100];
	
	int daysTillReachedHalf[] = new int[100000]; 
	int daysTillReachedLoss[] = new int[100000]; 
	int daysTillReachedMax[] = new int[100000]; 
	double plAtEnd[] = new double[100000]; 
	double plAtEndM[] = new double[100000];
	
	double finalUnderlyingPrice[] = new double[100000]; 
	
	int reachedHalf = 0; 
	int reachedMax = 0; 
	int reachedLoss = 0; 
	int profited = 0;
	
	double biggestLoss = 0; 
	double largestGain = 0; 
	double averageGain = 0; 
	
	void cleanData() {
		for(int i = 0; i < 100; i++) {
			underlyingPrice[i] = 0;
			putPrice[i] = 0;
			callPrice[i] = 0;
			pl[i] = 0;
			reachedHalf = 0;
			reachedMax = 0; 
			reachedLoss = 0;
			profited = 0; 
		}
		
		for(int i = 0; i < 100000; i++) {
			daysTillReachedHalf[i] = 100; 
			daysTillReachedMax[i] = 100; 
			daysTillReachedLoss[i] = 100; 
			finalUnderlyingPrice[i] = 100; 
		}
		
	}
	
	void storeData(int dataType, double value, int i) {
		if(dataType == 1) {
			underlyingPrice[i] = value; 
		} else if(dataType == 2) {
			putPrice[i] = value;
		} else if(dataType == 3) {
			callPrice[i] = value; 
		} else if(dataType == 4) {
			pl[i] = value; 
		} else if(dataType == 5) {
			daysTillReachedHalf[i] = (int)value; 
		} else if(dataType == 6){
			plAtEnd[i] = value; 
		} else if(dataType == 7){
			plAtEndM[i] = value; 
		} else if(dataType == 8){
			daysTillReachedLoss[i] = (int)value; 
 		} else {
 			finalUnderlyingPrice[i] = value; 
 		}
	}

	void printData(int amount, int dataType) {
		if(dataType == 1) {
			print("Underlying Price");
			for(int i = 0; i < amount; i++) {
				print(df.format(underlyingPrice[i])); 
			}
		} else if(dataType == 2) {
			print("Put Prices");
			for(int i = 0; i < amount; i++) {
				print(df.format(putPrice[i]));
			}
		} else if(dataType == 3) {
			print("Call Prices"); 
			for(int i = 0; i < amount; i++) {
				print(""+callPrice[i]);
			}
		} else if(dataType == 4){
			print("Profit loss");
			for(int i = 0; i < amount; i++) {
				print(df.format(pl[i])); 
			}
		} else if(dataType == 5){
			print("Days till reached half");
			for(int i = 0; i < amount; i++) {
				if(daysTillReachedHalf[i] != 100) print(""+daysTillReachedHalf[i]);
			}
		} else if(dataType == 6){
			print("Profits");
			for(int i = 0; i < amount; i++) {
				print("$"+df.format(plAtEnd[i])); 
			}
		} else if(dataType == 7){
			print("ProfitsM");
			for(int i = 0; i < amount; i++) {
				print("$"+df.format(plAtEndM[i])); 
			}
		} else if(dataType == 8){
			print("Days till reached max loss"); 
			for(int i = 0; i < amount; i++) {
				if(daysTillReachedLoss[i] != 100)print(""+daysTillReachedLoss[i]); 
			}
		} else {
			print("Final Underlying Prices");
			for(int i = 0; i < amount; i++) print("$"+df.format(finalUnderlyingPrice[i])); 
		}
		
		
	}
	
	double findChanceOfHalf(int units) {
		//print("Reached Half: " + reachedHalf);
		//print("Reached Max Loss: " + reachedLoss);
		//print("Reached Max Gain: " + reachedMax); 
		//print("Profited: " + profited); 
		//print("Amount of simulations: " + units);
		double chance = reachedHalf/units; 
		return chance; 
	}
	
	double findAverageDaysTill(int units, int type) {
		
		int ii = 0; 
		double j = 0; 
		int totalUnits = units; 
		if(type == 1) { //reached half
		for(int i = 0; i < units; i++) {
			if(daysTillReachedHalf[i] < 100) {
				ii += daysTillReachedHalf[i]; 
			} else {
				totalUnits--; 
			}
		}
		
		} else if(type == 2) { //reached max
			for(int i = 0; i < units; i++) {
				if(daysTillReachedMax[i] < 100) {
					ii += daysTillReachedMax[i]; 
				} else {
					totalUnits--; 
				}
			}
			
		} else if(type == 3) { //reached maxloss
			for(int i = 0; i < units; i++) {
				if(daysTillReachedLoss[i] < 100) {
					ii += daysTillReachedLoss[i]; 
				} else {
					totalUnits--; 
				}
			}
			
		}

		try {
			j = ii/totalUnits;
		}catch (java.lang.ArithmeticException divide_by_zero){
			System.out.println("Input error: Values must be greater than 0");
		}

		return j;
	}
	
	double findPLA(int units, int ii) {
		
		double total = 0; 
		if(ii == 1) {
		for(int i = 0; i < units; i++) {
			total+=plAtEnd[i];
		}
		} else {
			for(int i = 0; i < units; i++) {
				total+=plAtEndM[i];
			}
		}
		
		return total/units; 
	}
	
	void reportPL(double PL) {
		if(biggestLoss == 0) {
			biggestLoss = PL; 
			largestGain = PL;
		}
		if(PL < biggestLoss) {
			biggestLoss = PL; 
		}
		
		if(PL > largestGain) {
			largestGain = PL;
		}
	}
	
	void reachedHalf() {
		reachedHalf++;
	}
	
	void reachedLoss() {
		reachedLoss++;
	}
	
	void reachedMax() {
		reachedMax++;
	}
	
	void profited() {
		profited++;
	}
	
	
	
	void print(String s) {System.out.println(s);}
	
}
