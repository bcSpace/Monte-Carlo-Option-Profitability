import java.util.Random;

class SimulateDay {

	private Random rng = new Random();
	
	double simulate(double priceB, double dailyVol) {
		return priceB + (dailyVol*priceB) * rng.nextGaussian();
	}
}
