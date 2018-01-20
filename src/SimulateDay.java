import java.util.Random;

public class SimulateDay {

	Random rng = new Random();
	
	double simulate(double priceB, double dailyVol) {
		return priceB + (dailyVol*priceB) * rng.nextGaussian();
	}
}
