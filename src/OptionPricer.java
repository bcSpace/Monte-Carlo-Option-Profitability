
public class OptionPricer {
	
	final double e = 2.718281828459045; 
	
	double stockPrice;
	double strikePrice; 
	double riskFree; 
	double iv; 
	double time; 
	
	double d1;
	double d2; 
	
	double callPrice; 
	double putPrice; 
	
	void setVar(double strikePrice, double stockPrice, double riskFree, double iv, double time) {
		this.strikePrice = strikePrice; 
		this.stockPrice = stockPrice;
		this.riskFree = riskFree; 
		this.iv = iv; 
		this.time = time/365; 
		
		findD(); 
	}
	
	void setNewPrice(double stockPrice, double time) {
		this.stockPrice = stockPrice; 
		this.time = time/365; 
		findD(); 
	}
	
	void findD() {
		d1 = (Math.log(stockPrice/strikePrice) + (riskFree + ((iv*iv)/2)) * time) / (iv * Math.sqrt(time));  
		d2 = d1 - iv * Math.sqrt(time); 
	}
	
	double calculateCall() {
		callPrice = (stockPrice * N(d1)) - (strikePrice*Math.pow(e, -riskFree*time)*N(d2)); 
		if(callPrice < .01) callPrice = 0.00; 
		return callPrice; 
	}
	
	double calculatePut() {
		putPrice = strikePrice*Math.exp(-riskFree*time)*N(-d2)-stockPrice*N(-d1);
		if(putPrice <.01) putPrice = 0.00; 
		return putPrice; 
	}
	
	
	
	
	public double N(double X)
	{
	double L, K, w ;
	double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;

	L = Math.abs(X);
	K = 1.0 / (1.0 + 0.2316419 * L);
	w = 1.0 - 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L *L / 2) * (a1 * K + a2 * K *K + a3 
	* Math.pow(K,3) + a4 * Math.pow(K,4) + a5 * Math.pow(K,5));

	if (X < 0.0) 
	{
	w= 1.0 - w;
	}
	return w;
	}
}
