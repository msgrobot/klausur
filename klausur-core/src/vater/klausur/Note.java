package vater.klausur;

public class Note extends GUIKnoten{

	NotenPunkt[] notenPunkte;
	public Note(int stelle, double Punktzahl, String aufschrift, FancyWindow fw, NotenPunkt[] notenpunkte) {
		super(stelle, Punktzahl, aufschrift, fw);
		this.notenPunkte = notenpunkte;
		
		for(NotenPunkt p : notenpunkte){
			Punktzahl += p.Punktzahl;
		}
	}

	@Override
	public double SumVal() {
		return 0;
	}

	public double anzSchueler() {
		int s = 0;
		for(NotenPunkt p : notenPunkte){
			s += p.schueler.size();
		}
		return s;
	}

}
