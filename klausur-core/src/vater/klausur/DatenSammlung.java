package vater.klausur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vater.klausur.FancyWindow.Mode;

public class DatenSammlung {

	List<Aufgabe> AufgabenList = new ArrayList<Aufgabe>();
	List<Punkte> PunkteList = new ArrayList<Punkte>();
	List<Note> aufgaben;

	List<Verbindung> Verbindungen = new ArrayList<Verbindung>();
	
	Aufgabe SumAufgaben;

	FancyWindow fw;

	public DatenSammlung(FancyWindow fw) {
		this.fw = fw;
	}

	public void notenMachen(FancyWindow fw, String prozente) {
		List<NotenPunkt> np = NotenPunkt.ausPunktenErstellen(PunkteList, this, fw, prozente);
		aufgaben = new ArrayList<Note>();
		aufgaben.add(new Note(1, np.get(0).Punktzahl + np.get(1).Punktzahl + np.get(2).Punktzahl, "1", fw,
				new NotenPunkt[] { np.get(0), np.get(1), np.get(2) }));

		aufgaben.add(new Note(2, np.get(3).Punktzahl + np.get(4).Punktzahl + np.get(5).Punktzahl, "2", fw,
				new NotenPunkt[] { np.get(3), np.get(4), np.get(5) }));

		aufgaben.add(new Note(3, np.get(6).Punktzahl + np.get(7).Punktzahl + np.get(8).Punktzahl, "3", fw,
				new NotenPunkt[] { np.get(6), np.get(7), np.get(8) }));

		aufgaben.add(new Note(4, np.get(9).Punktzahl + np.get(10).Punktzahl + np.get(11).Punktzahl, "4", fw,
				new NotenPunkt[] { np.get(9), np.get(10), np.get(11) }));

		aufgaben.add(new Note(5, np.get(12).Punktzahl + np.get(13).Punktzahl + np.get(14).Punktzahl, "5", fw,
				new NotenPunkt[] { np.get(12), np.get(13), np.get(14) }));

		aufgaben.add(new Note(6, np.get(15).Punktzahl, "6", fw, new NotenPunkt[] { np.get(15) }));
	}

	public void ladenAufgaben() {
		File datei = Utility.choseFile("Aufgaben laden", "o", new String[] { "Datein", "dat" });
		if (datei == null) {
			System.exit(0);
		}
		Aufgabe.SumVal = 0;
		Iterator<String> itr = Utility.readFile(datei).iterator();
		while (itr.hasNext()) {
			String s = itr.next();
			if (s.contains("#")) {
				AufgabenList.add(Aufgabe.bastleAusZeile(s, "#"));
			} else {
				SumAufgaben = new Aufgabe(0, Aufgabe.SumVal, s, fw);
			}

		}
		Aufgabe.SumVal /= 2;
		SumAufgaben.Punktzahl = Aufgabe.SumVal;
	}

	public void ladenPunkte() {
		File datei = Utility.choseFile("Punkte laden", "o", new String[] { "Datein", "dat" });
		if (datei == null) {
			System.exit(0);
		}
		Iterator<String> itr = Utility.readFile(datei).iterator();
		while (itr.hasNext()) {
			String s = itr.next();
			if (s.contains("#")) {
				PunkteList.add(Punkte.bastleAusZeile(s, "#"));
			}
		}
	}

	public void paint(Graphics g, int width, int height, Mode m) {
		Color c = g.getColor();
		g.clearRect(0, 0, fw.getWidth(), fw.getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, fw.getWidth(), fw.getHeight());
		Font f = g.getFont();
		String s = "� M V";
		width = fw.getWidth();
		height = fw.getHeight()-60;
		int h = height / 4;
		Font f_ = new java.awt.Font("Arial", 0, h);
		g.setFont(f_);
		g.setColor(new Color(0.3f, 0.4f, 0.7f, 0.1f));//075f
		
		for (int i = 0; i<3; i++){
			for(int j = 0; j<2; j++){
				g.drawString(s, (int) (width/2 - (h*2.5))*(6*i-j*2), (int) (height/2 - h/2 )*(j+1));
			}
		}

		g.setColor(c);
		g.setFont(f);
		if(m.equals(Mode.alles)){
			paintSumAufgaben(g, 0, width, height);
			paintAufgaben(g, 1, width, height);
			paintNotenPunkte(g, 2, width, height);
			paintNoten(g, 3, width, height);
			paintVerbindungen(g, width);
			
		}else if(m.equals(Mode.noten)){
			paintSumAufgaben(g,0,width*2, height);
			paintAufgaben(g, 1, width*2, height);
			paintVerbindungen(g, width);
		}
	}
	
	private void paintSumAufgaben(Graphics g, int i, int width, int height) {
		int m = 1;
		Aufgabe k = SumAufgaben;
		if (k != null) {
			k.draw(g, i, (height-30) / 3, m, width);
		}
	}

	private void paintAufgaben(Graphics g, int i, int width, int height) {
		int m = AufgabenList.size();
		Verbindungen.clear();
		for (int j = 0; j < m; j++) {
			GUIKnoten k = AufgabenList.get(j);
			if (k != null) {
				k.draw(g, i, (height-30) / (m) * j, (int) SumAufgaben.Punktzahl, width);
				Verbindungen.add(new Verbindung(SumAufgaben, k, this));
			}
		}
	}

	private void paintNotenPunkte(Graphics g, int i, int width, int height) {
		int m = 0;

		List<NotenPunkt> np = new ArrayList<NotenPunkt>();
		for (Note n : aufgaben) {
			for (NotenPunkt np_ : n.notenPunkte) {
				if (np_.Punktzahl != 0) {
					m++;
					np.add(np_);
				}
			}
		}

		for (int j = 0; j < m; j++) {
			GUIKnoten k = np.get(j);
			if (k != null) {
				k.draw(g, i, (height-30) / (m) * j, (int) NotenPunkt.alleVegebenenPunkte, width);
			}
			
			for (int l = 0; l < AufgabenList.size(); l++) {
				Verbindungen.add(new Verbindung(AufgabenList.get(l), k, this));
			}
			Verbindungen.add(new Verbindung(k, n(k), this));
		}
	}
	Note n(GUIKnoten k){
		NotenPunkt np = (NotenPunkt) k;
		for(Note n_ : aufgaben){
			for(NotenPunkt np_ : n_.notenPunkte){
				if(np_.equals(np)){
					return n_;
				}
			}
		}
		return null;
	}
	private void paintNoten(Graphics g, int i, int width, int height) {

		int m = 0;
		List<Note> n = new ArrayList<Note>();
		for (Note n_ : aufgaben) {
			if (n_.Punktzahl != 0) {
				m++;
				n.add(n_);
			}
		}

		for (int j = 0; j < m; j++) {
			GUIKnoten k = n.get(j);
			if (k != null) {
				k.draw(g, i, (height-30) / (m) * j, (int) NotenPunkt.alleVegebenenPunkte, width);
			}
		}
	}
	private void paintVerbindungen(Graphics g, int width) {
		
		for (int j = 0; j < Verbindungen.size(); j++) {
			Verbindung v = Verbindungen.get(j);
			if (v != null) {
				v.draw(g, width);
			}
		}
		SumAufgaben.reset();
		for(Aufgabe a : AufgabenList){
			a.reset();
		}
		for(Note n : aufgaben){
			n.reset();
			for(NotenPunkt np : n.notenPunkte){
				np.reset();
			}
		}
		
	}
}
