import java.util.Random;

class SimulateDay {

	private final Random rng = new Random();
	
	double simulate(double priceB, double dailyVol) {
		return priceB + (dailyVol*priceB) * rng.nextGaussian();
	}
}
