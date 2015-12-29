package vater.klausur;

public class Punkte {

	public String Schueler;
	public String Aufgabe;
	public double Punktzahl;
	
	public Punkte(String Schueler, String Aufgabe, double Punktzahl) {
		this.Schueler = Schueler;
		this.Aufgabe = Aufgabe;
		this.Punktzahl = Punktzahl;
	}
	
	public static Punkte bastleAusZeile(String zeile, String trennzeichen){
		
		String[] s = zeile.split(trennzeichen);
		
		Punkte ret = null;
		try {
			ret = new Punkte(s[0], s[1], Double.parseDouble(s[2]));
		} catch (Exception e) {	}
		
		return ret;
	}

}
