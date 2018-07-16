import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.text.DecimalFormat;

class DataManagement {
	
	private final DecimalFormat df = new DecimalFormat("#.00");
	
	private final double[] underlyingPrice = new double[100];
	private final double[] putPrice = new double[100];
	private final double[] callPrice = new double[100];
	private final double[] pl = new double[100];
	
	private final int[] daysTillReachedHalf = new int[100000];
	private final int[] daysTillReachedLoss = new int[100000];
	private final int[] daysTillReachedMax = new int[100000];
	private final double[] plAtEnd = new double[100000];
	private final double[] plAtEndM = new double[100000];
	
	private final double[] finalUnderlyingPrice = new double[100000];
	
	int reachedHalf = 0; 
	private int reachedMax = 0;
	int reachedLoss = 0; 
	int profited = 0;
	
	private double biggestLoss = 0;
	private double largestGain = 0;
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
        switch (dataType) {
            case 1:
                underlyingPrice[i] = value;
                break;
            case 2:
                putPrice[i] = value;
                break;
            case 3:
                callPrice[i] = value;
                break;
            case 4:
                pl[i] = value;
                break;
            case 5:
                daysTillReachedHalf[i] = (int) value;
                break;
            case 6:
                plAtEnd[i] = value;
                break;
            case 7:
                plAtEndM[i] = value;
                break;
            case 8:
                daysTillReachedLoss[i] = (int) value;
                break;
            default:
                finalUnderlyingPrice[i] = value;
                break;
        }
	}

	void printData(int amount, int dataType) {
        switch (dataType) {
            case 1:
                print("Underlying Price");
                for (int i = 0; i < amount; i++) {
                    print(df.format(underlyingPrice[i]));
                }
                break;
            case 2:
                print("Put Prices");
                for (int i = 0; i < amount; i++) {
                    print(df.format(putPrice[i]));
                }
                break;
            case 3:
                print("Call Prices");
                for (int i = 0; i < amount; i++) {
                    print("" + callPrice[i]);
                }
                break;
            case 4:
                print("Profit loss");
                for (int i = 0; i < amount; i++) {
                    print(df.format(pl[i]));
                }
                break;
            case 5:
                print("Days till reached half");
                for (int i = 0; i < amount; i++) {
                    if (daysTillReachedHalf[i] != 100) print("" + daysTillReachedHalf[i]);
                }
                break;
            case 6:
                print("Profits");
                for (int i = 0; i < amount; i++) {
                    print("$" + df.format(plAtEnd[i]));
                }
                break;
            case 7:
                print("ProfitsM");
                for (int i = 0; i < amount; i++) {
                    print("$" + df.format(plAtEndM[i]));
                }
                break;
            case 8:
                print("Days till reached max loss");
                for (int i = 0; i < amount; i++) {
                    if (daysTillReachedLoss[i] != 100) print("" + daysTillReachedLoss[i]);
                }
                break;
            default:
                print("Final Underlying Prices");
                for (int i = 0; i < amount; i++) print("$" + df.format(finalUnderlyingPrice[i]));
                break;
        }
		
		
	}
	
	double findChanceOfHalf(int units) {
		//print("Reached Half: " + reachedHalf);
		//print("Reached Max Loss: " + reachedLoss);
		//print("Reached Max Gain: " + reachedMax); 
		//print("Profited: " + profited); 
		//print("Amount of simulations: " + units);
        return (double) (reachedHalf/units);
	}
	
	double findAverageDaysTill(int units, int type) {
		
		int ii = 0; 
		double j = 0; 
		int totalUnits = units;
        switch (type) {
            case 1:  //reached half
                for (int i = 0; i < units; i++) {
                    if (daysTillReachedHalf[i] < 100) {
                        ii += daysTillReachedHalf[i];
                    } else {
                        totalUnits--;
                    }
                }

                break;
            case 2:  //reached max
                for (int i = 0; i < units; i++) {
                    if (daysTillReachedMax[i] < 100) {
                        ii += daysTillReachedMax[i];
                    } else {
                        totalUnits--;
                    }
                }

                break;
            case 3:  //reached maxloss
                for (int i = 0; i < units; i++) {
                    if (daysTillReachedLoss[i] < 100) {
                        ii += daysTillReachedLoss[i];
                    } else {
                        totalUnits--;
                    }
                }

                break;
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
	
	
	
	private void print(String s) {System.out.println(s);}
	
}
