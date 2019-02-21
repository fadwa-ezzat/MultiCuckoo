
/*    */ import ec.util.MersenneTwisterFast;

/*    *//**
 * 
 * @author Fadwa Ezzat
 * This class uses the Mason library to simulate the Levy flight of cuckoo
 */
/*    */ public class Levy
/*    */ {
	/* 16 */ private static MersenneTwisterFast rng = new MersenneTwisterFast();

	/*    */
	/*    */ private static double bounded_uniform(double low, double high)
	/*    */ {
		/* 26 */ double x = rng.nextDouble(false, false);
		/*    */
		/* 28 */ double range = high - low;
		/* 29 */ x *= range;
		/* 30 */ x += low;
		/*    */
		/* 32 */ return x;
		/*    */ }

	/*    */
	/*    */ public static double sample(double mu)
	/*    */ {
		/* 50 */ double X = bounded_uniform(-1.570796326794897D, 1.570796326794897D);
		/*    */
		/* 53 */ double Y = -Math.log(rng.nextDouble(false, false));
		/* 54 */ double alpha = mu - 1.0D;
		/*    */
		/* 56 */ double Z = Math.sin(alpha * X) / Math.pow(Math.cos(X), 1.0D / alpha) *
		/* 57 */ Math.pow(Math.cos((1.0D - alpha) * X) / Y, (1.0D - alpha) / alpha);
		/* 58 */ return Z;
		/*    */ }

	/*    */
	/*    */ public static double sample_positive(double mu, double scale)
	/*    */ {
		/* 70 */ double l = sample(mu) * scale;
		/* 71 */ if (l < 0.0D) {
			/* 72 */ return -1.0D * l;
			/*    */ }
		/* 74 */ return l;
		/*    */ }

	/*    */
	/*    */ public static double sample_positive(double mu)
	/*    */ {
		/* 80 */ return sample_positive(mu, 1.0D);
		/*    */ }
	/*    */ }
