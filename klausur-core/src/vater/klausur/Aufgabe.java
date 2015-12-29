package vater.klausur;

public class Aufgabe extends GUIKnoten{

	public static double SumVal = 0;

	public Aufgabe(int stelle, double Punktzahl, String aufschrift, FancyWindow fw) {
		super(stelle, Punktzahl, aufschrift, fw);
		
		SumVal += Punktzahl;
	}

//	private Color cbs(String string) {
//		float r = (float) Integer.parseInt(string.substring(0, 2), 16) / 255.0f;
//		float g = (float) Integer.parseInt(string.substring(2, 4), 16) / 255.0f;
//		float b = (float) Integer.parseInt(string.substring(4, 6), 16) / 255.0f;
//		return new Color(r, g, b);
//	}

	static int i=0;
//	static int cmax = 100;

	public static Aufgabe bastleAusZeile(String zeile, String trennzeichen) {
		Aufgabe ret = null;
		String[] s = zeile.split(trennzeichen);

		if (trennzeichen.equals("#")) {
			ret = p(s[0]) != 0 ? new Aufgabe(i++, p(s[0]), s[1], fw_) : null;
		}
		return ret;
	}

	static double p(String s) {
		return Double.parseDouble(s);
	}

	@Override
	public double SumVal() {
		return SumVal;
	}
}
