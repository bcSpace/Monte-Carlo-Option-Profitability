import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {
	
	JFrame frame = new JFrame(); 
	JLabel[] label = new JLabel[20]; 
	JTextField[] field = new JTextField[20]; 
	JCheckBox box = new JCheckBox(); 
	JTextArea box2 = new JTextArea(20, 25); 
	JButton enter = new JButton("Enter");
	
	
	DecimalFormat df = new DecimalFormat("#.00"); 
	
	Calander c = new Calander(); 
	OptionPricer op = new OptionPricer(); 
	SimulateDay sd = new SimulateDay(); 
	PostionManager pm = new PostionManager(); 
	DataManagement dm = new DataManagement(); 
	
	double startingPrice = 183.03;
	double currentPrice = startingPrice; 
	double vol = .5; 
	double dailyVol = vol/Math.sqrt(252); 
	double optionVol = 0; 
	
	double riskFreeRate;
	int daysToExpire; 
	double strikePrice; 
	double halfMax; 
	double maxLoss; 
	
	int daysLeft;
	int daysPassed = 0; 
	int startingDays; 
	
	double currentOptionPrice = 0; 
	double currentPL = 0;
	
	int amountOfSimulations = 100000; 
	int currentSimulation = 0;
	
	boolean reachedHalf = false; 
	boolean reachedMax = false; 
	boolean reachedMaxLoss = false; 
	
	double percentToManageAt = .35; 
	double percentToCutAt = 1.5; 
	
	boolean testingCall; 
	
	String outputData = ""; 
	
	public Main() {
		setUpGUI(); 
		
		
		//print("Starting Call Price: $" + pm.callPrice); 
		//print("Maxium Loss if M: $" + maxLoss);
		//print("Maxium Profit if M: $" + halfMax);
		
		//print("Days till half average: " + dm.findAverageDaysTill(amountOfSimulations, 1));
		//print("Chance of reaching half of max profit: " + dm.findChanceOfHalf(amountOfSimulations)); 
		
		//print("Average P&L: $"+df.format(dm.findPLA(amountOfSimulations,1)));
		//print("Average P&L if Managenaged: $" + df.format(dm.findPLA(amountOfSimulations,2)));
		//print("Biggest Loss: $" + df.format(dm.biggestLoss));
		//print("Largest Gain: $" + df.format(dm.largestGain));
		
		//dm.printData(amountOfSimulations, 5);
		
	}
	
	void runProgram() {
		
		
		double i = Double.parseDouble(field[0].getText());
		double ii = Double.parseDouble(field[1].getText()); 
		double iii = Double.parseDouble(field[2].getText()); 
		double j = Double.parseDouble(field[3].getText()); 
		int jj = Integer.parseInt(field[4].getText()); 
		int jjj = Integer.parseInt(field[5].getText()); 
		boolean call = box.isSelected(); 
		
		optionVol = Double.parseDouble(field[9].getText()); 
		
		percentToCutAt = Double.parseDouble(field[7].getText());
		percentToManageAt = Double.parseDouble(field[8].getText()); 
		
		amountOfSimulations = Integer.parseInt(field[6].getText()); 
		currentSimulation = 0; 
		
		c.currentDay = jjj; 
		dm.cleanData();
		setUp(i, ii, iii, j, jj, call); 
		
		
		for(int p = 0; p < amountOfSimulations; p++) {
			runSimulation(); 
			dm.storeData(6, currentPL, currentSimulation);
			dm.storeData(7, currentPL, currentSimulation);
			if(reachedHalf) {
				dm.storeData(7, pm.calculatePL(halfMax), currentSimulation);
			} else if(reachedMaxLoss){
				dm.storeData(7, pm.calculatePL(maxLoss), currentSimulation);
			} else {
				dm.storeData(7, currentPL, currentSimulation);
			}

			//if(!reachedMaxLoss) dm.reportPL(pm.calculatePL(currentOptionPrice));
			if(currentPL > 0) dm.profited();
			currentSimulation++;
			}
		
		double a,b,c; 
		
		
		outputData = "Starting Call Price: $" + pm.currentOptionPrice + "\n";
		outputData += "Price to manage at: $" + halfMax + "\n"; 
		outputData += "Price to cut losses at: $" + maxLoss + "\n"; 
		outputData += "Days till half average: " + dm.findAverageDaysTill(amountOfSimulations, 1) + "\n";
		outputData += "Days till max loss: " + dm.findAverageDaysTill(amountOfSimulations, 3) + "\n";
		outputData += "Chance of reaching half of max profit: " + dm.findChanceOfHalf(amountOfSimulations) + "\n";
		outputData += "Average P&L if Managenaged: $" + df.format(dm.findPLA(amountOfSimulations,2)) + "\n";
		b = dm.profited;
		c = amountOfSimulations; 
		a = (b/c)*100; 
		outputData += "Chance of profit: " + a + "\n"; 
		b = dm.reachedLoss;
		a = (b/c)*100; 
		outputData += "Chance of Max Loss: " + a + "\n"; 
		b = dm.reachedHalf; 
		a = (b/c)*100; 
		outputData += "Chance of Manage: " + a + "\n"; 
		
		
		
		box2.setText(outputData);
		
		//dm.printData(amountOfSimulations, 9);
		
				//print("Starting Call Price: $" + pm.currentOptionPrice); 
				//print("Maxium Loss if M: $" + maxLoss);
				//print("Maxium Profit if M: $" + halfMax);
				
				//print("Days till half average: " + dm.findAverageDaysTill(amountOfSimulations, 1));
				//print("Chance of reaching half of max profit: " + dm.findChanceOfHalf(amountOfSimulations)); 
				
				//print("Average P&L: $"+df.format(dm.findPLA(amountOfSimulations,1)));
				//print("Average P&L if Managenaged: $" + df.format(dm.findPLA(amountOfSimulations,2)));
				//print("Biggest Loss: $" + df.format(dm.biggestLoss));
			//print("Largest Gain: $" + df.format(dm.largestGain));
		
	}
	
	void setUp(double strikePrice, double currentPrice, double riskFreeRate, double IV, int daysToExpire, boolean call) {
		op.setVar(strikePrice, currentPrice, riskFreeRate, optionVol, daysToExpire);
		this.strikePrice = strikePrice; 
		this.riskFreeRate = riskFreeRate; 
		this.currentPrice = currentPrice; 
		this.startingPrice = currentPrice; 
		this.daysToExpire = daysToExpire; 
		this.daysLeft = daysToExpire; 
		this.startingDays = daysLeft; 
		this.vol = IV; 
		this.dailyVol = vol/Math.sqrt(252); 
		
		testingCall = call; 
		
		currentOptionPrice = priceOption(call, currentPrice, daysToExpire); 
		//print("$$" + currentOptionPrice); 
		
		halfMax = currentOptionPrice*percentToManageAt;
		maxLoss = currentOptionPrice*percentToCutAt; 
		
		
		pm.setOptionPrice(currentOptionPrice);
		
		//print input data
		//print("Strike Price: " + strikePrice); 
		//print("Risk Free Rate: " + riskFreeRate);
		//print("Underlying Price: " + currentPrice);
		//print("Days to Expire: " + daysToExpire); 
		//print("Current IV: " + vol);
		//print("Current Daily IV: " + dailyVol);
	}
	
	void runSimulation() {
		
		daysPassed = 0; 
		currentPrice = startingPrice; 
		daysLeft = startingDays; 

		reachedHalf = false; 
		reachedMax = false; 
		reachedMaxLoss = false; 
		
		//print(df.format(currentPrice)); 
		
		dm.storeData(1, currentPrice, 0);
		dm.storeData(2, priceOption(testingCall, currentPrice, daysLeft), 0);
		
		for(int i = 0; i < startingDays; i++) {
			daysPassed++; 
			if(c.isTrading()) {currentPrice = sd.simulate(currentPrice, dailyVol);
			
			} else {
				
			}
			c.nextDay();
			daysLeft--; 
			
			currentOptionPrice = priceOption(testingCall, currentPrice, daysLeft); 
			
			
			if(currentOptionPrice < halfMax && !reachedHalf && !reachedMaxLoss) {
				dm.storeData(5, daysPassed, currentSimulation);
				dm.reachedHalf(); 
				reachedHalf = true; 
				i = startingDays+1; 
			}
			
			if(currentOptionPrice > maxLoss && !reachedHalf && !reachedMaxLoss) {
				dm.storeData(8, daysPassed, currentSimulation);
				dm.reachedLoss(); 
				reachedMaxLoss = true; 
				i = startingDays+1; 
			}
			
			//if(currentOptionPrice < .01 && !reachedMax && !reachedMaxLoss) {
				//dm.reachedMax();
				//if(!reachedHalf) dm.reachedHalf(); 
				//reachedMax = true; 
				//i = startingDays+1; 
			//}
			currentPL = pm.calculatePL(currentOptionPrice); 
			dm.storeData(1, currentPrice, i+1);
			
			
			dm.storeData(2, currentOptionPrice, i+1);
			dm.storeData(4, currentPL, i+1);
		}
		
		//dm.storeData(9, currentPrice, currentSimulation);
		
		//dm.printData(startingDays, 1);
		//dm.printData(startingDays, 2);
		//dm.printData(startingDays, 4);
		
	}
	
	double priceOption(boolean call, double currentPrice, int daysLeft) {
		op.setNewPrice(currentPrice, daysLeft);
		if(call) {
			return op.calculateCall();
		} else {
			return op.calculatePut();
		}
	}
	
	int numberOfWeekdays(int startingDay, int daysToExpire) {
		int ii = 0; 
		c.setDayC(startingDay);
		for(int i = 0; i < daysToExpire; i++) {
			if(c.isTrading()) ii++; 
			c.nextDay();
			
		}
		return ii; 
	}
	
	//double strikePrice, double currentPrice, double riskFreeRate, double IV, int daysToExpire, boolean call
	
	void setUpGUI() {
		
		frame.setSize(500, 500);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		label[0] = new JLabel("Strike Price: "); 
		label[1] = new JLabel("Underlying Price: "); 
		label[2] = new JLabel("Risk Free Rate: ");
		label[3] = new JLabel("Impiled Vol: "); 
		label[4] = new JLabel("Days To Expire: ");
		label[5] = new JLabel("Current Day of week: "); 
		label[6] = new JLabel("Call: "); 
		label[7] = new JLabel("Amount Of Simulations: "); 
		label[8] = new JLabel("Percent To Cut Losses At: ");
		label[9] = new JLabel("Percent To Manage At: ");
		label[10] = new JLabel("IV of option: "); 
		
		field[0] = new JTextField(5); //strike price
		field[1] = new JTextField(5); //current price
		field[2] = new JTextField(5); //risk free rate
		field[3] = new JTextField(5); //IV
		field[4] = new JTextField(5); //days to expire
		field[5] = new JTextField(5); //current day
		field[6] = new JTextField(5); //amount of simulations
		field[7] = new JTextField(5); //percent to cut losses at
		field[8] = new JTextField(5); //percent to manage at
		field[9] = new JTextField(5); //iv of the option
		
		
		frame.add(label[0]); 
		frame.add(field[0]);
		
		frame.add(label[1]); 
		frame.add(field[1]);
		
		frame.add(label[2]); 
		frame.add(field[2]);
		
		frame.add(label[3]); 
		frame.add(field[3]);
		
		frame.add(label[10]);
		frame.add(field[9]); 
		
		frame.add(label[4]); 
		frame.add(field[4]);
		
		frame.add(label[5]); 
		frame.add(field[5]);
		
		frame.add(label[8]);
		frame.add(field[7]);
		
		frame.add(label[9]);
		frame.add(field[8]);
		
		frame.add(label[7]);
		frame.add(field[6]); 
		
		frame.add(label[6]); 
		frame.add(box);
		
		frame.add(enter); 
		
		frame.add(box2);
		

        enter.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	runProgram(); 
            }
        });  
        
	}
	
	void print(String s) {
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
