package ch.game;

public class Utils {

	public static int Clamp(int val, int min, int max) {
		if (val > max)
			return max;
		else if (val < min)
			return min;

		return val;
	}
	
}
