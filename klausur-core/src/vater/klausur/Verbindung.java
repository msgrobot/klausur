package vater.klausur;

import java.awt.Graphics;
import java.awt.Polygon;

import vater.klausur.NotenPunkt.Schueler;
import vater.klausur.NotenPunkt.SchuelerID;

public class Verbindung {

	public GUIKnoten Begin;
	public GUIKnoten Ende;
	public DatenSammlung ds;

	public Verbindung(GUIKnoten begin, GUIKnoten ende, DatenSammlung ds) {
		Begin = begin;
		Ende = ende;
		this.ds = ds;
	}

	// fall1 : gpz - aufgabe
	// fall2 : aufgabe - np
	// fall3 : np - note

	public void draw(Graphics g, int width) {

		if (Begin.equals(ds.SumAufgaben)) {
			Polygon p = new Polygon();

			double y1 = (Begin.miny + (Begin.hei * GUIKnoten.rundung)
					+ (Begin.sumBisherB == 0 ? 0
							:  ( Begin.hei -  Begin.hei * 2D * GUIKnoten.rundung)
									*  Begin.sumBisherB)
							/  Begin.Punktzahl);
			double f =  (Begin.sumBisherB + Ende.Punktzahl) /  Begin.Punktzahl;
			double y2 = Begin.miny + (Begin.hei * GUIKnoten.rundung)
					+  (Begin.hei - (Begin.hei * 2D * GUIKnoten.rundung)) * f;

			double xo =  ( width / 4D * 0.1D);
			double y3 =  (Ende.miny + Ende.hei * GUIKnoten.rundung);
			double y4 =  (Ende.maxy - Ende.hei * GUIKnoten.rundung);
			double h1 = y2 - y1;
			double h2 = y4 - y3;
			double sc = 1D / 25D;
			y1 += h1 * sc;
			y2 -= 2D * h1 * sc;

			y3 += h2 * sc;
			y4 -= 2D * h2 * sc;

			p.addPoint((int) (Begin.maxx + xo), (int) (y1));
			p.addPoint((int) (Ende.minx - xo), (int) y3);
			p.addPoint((int) (Ende.minx - xo), (int) y4);
			p.addPoint((int) (Begin.maxx + xo), (int) (y2));
			g.setColor(Ende.Farbe);
			g.fillPolygon(p);
			g.fillRect((int)Begin.maxx, (int) y1, (int) (xo+0.5), (int) (y2 - y1));
			g.fillRect((int) (Ende.minx - xo), (int) y3, (int) (xo+0.5), (int) (y4 - y3));
			Begin.sumBisherB += Ende.Punktzahl;
			Ende.sumBisherE += Ende.Punktzahl;
		} else {
			if (ds.AufgabenList.contains(Begin)) {
				NotenPunkt np = (NotenPunkt) Ende;
				int PunktZahlB = (int) (Begin.Punktzahl * SchuelerID.count);
				int PunktZahlE = 0;
				for (Schueler s : np.schueler) {
					for (Punkte p_ : s.SumPunkte) {
						if (p_.Aufgabe.equals(Begin.Aufschrift)) {
							PunktZahlE += p_.Punktzahl;
						}
					}
				}
				Polygon p = new Polygon();

				double y1 = (Begin.miny + (Begin.hei * GUIKnoten.rundung)
						+ (Begin.sumBisherB == 0 ? 0
								:  ( Begin.hei -  Begin.hei * 2D * GUIKnoten.rundung)
										*  Begin.sumBisherB)
								/  PunktZahlB);
				double f1 =  (Begin.sumBisherB + PunktZahlE) /  (PunktZahlB);
				double y2 = Begin.miny + (Begin.hei * GUIKnoten.rundung)
						+  (Begin.hei - (Begin.hei * 2D * GUIKnoten.rundung)) * f1;

				double xo = ( width / 4D * 0.1D);
				double y3 = (Ende.miny + (Ende.hei * GUIKnoten.rundung)
						+ (Ende.sumBisherE == 0 ? 0
								:  ( Ende.hei -  Ende.hei * 2D * GUIKnoten.rundung)
										*  Ende.sumBisherE)
								/  (ds.SumAufgaben.Punktzahl * np.schueler.size()));
				
				double f2 =  (Ende.sumBisherE + PunktZahlE) /  (ds.SumAufgaben.Punktzahl * np.schueler.size());
				double y4 = Ende.miny + (Ende.hei * GUIKnoten.rundung)
						+ (Ende.hei - (Ende.hei * 2D * GUIKnoten.rundung)) * f2;
				
				double h1 = y2 - y1;
				double h2 = y4 - y3;
				double sc = 1D / 25D;
				y1 += h1 * sc;
				y2 -= 2D * h1 * sc;

				y3 += h2 * sc;
				y4 -= 2D * h2 * sc;

				p.addPoint((int) (Begin.maxx + xo), (int) y1);
				p.addPoint((int) (Ende.minx - xo), (int) y3);
				p.addPoint((int) (Ende.minx - xo), (int) y4);
				p.addPoint((int) (Begin.maxx + xo), (int) y2);
				g.setColor(Begin.Farbe);
				g.fillPolygon(p);
				g.fillRect((int)(Begin.maxx), (int) y1, (int) (xo+0.5), (int) (y2 - y1));
				g.fillRect((int) (Ende.minx - xo), (int) y3, (int) (xo+0.5), (int) (y4 - y3));

				Begin.sumBisherB += PunktZahlE;
				Ende.sumBisherE += PunktZahlE;
			}else{
				NotenPunkt np = (NotenPunkt) Begin;
				double MPunktZahlB = ds.SumAufgaben.Punktzahl * np.schueler.size();;
				
				Polygon p = new Polygon();

				double y1 = (Begin.miny + (Begin.hei * GUIKnoten.rundung)
						+ (Begin.sumBisherB == 0 ? 0
								:  ( Begin.hei -  Begin.hei * 2D * GUIKnoten.rundung)
										*  Begin.sumBisherB)
								/  MPunktZahlB);
				double f1 =  (Begin.sumBisherB + Begin.Punktzahl) /  (MPunktZahlB);
				double y2 = Begin.miny + (Begin.hei * GUIKnoten.rundung)
						+  (Begin.hei - (Begin.hei * 2D * GUIKnoten.rundung)) * f1;

				Note n = (Note) Ende;
				double xo = ( width / 4D * 0.1D);
				double y3 = (Ende.miny + (Ende.hei * GUIKnoten.rundung)
						+ (Ende.sumBisherE == 0 ? 0
								:  ( Ende.hei -  Ende.hei * 2D * GUIKnoten.rundung)
										* Ende.sumBisherE)
								/  (n.anzSchueler()*ds.SumAufgaben.Punktzahl));
				double f2 =  (Ende.sumBisherE + Begin.Punktzahl) /  (ds.SumAufgaben.Punktzahl * n.anzSchueler());
				double y4 = Ende.miny + (Ende.hei * GUIKnoten.rundung)
						+ (Ende.hei - (Ende.hei * 2D * GUIKnoten.rundung)) * f2;
				
				
				double h1 = y2 - y1;
				double h2 = y4 - y3;
				double sc = 1D / 25D;
				y1 += h1 * sc;
				y2 -= 2D * h1 * sc;

				y3 += h2 * sc;
				y4 -= 2D * h2 * sc;

				p.addPoint((int) (Begin.maxx + xo), (int) y1);
				p.addPoint((int) (Ende.minx - xo), (int) y3);
				p.addPoint((int) (Ende.minx - xo), (int) y4);
				p.addPoint((int) (Begin.maxx + xo), (int) y2);
				g.setColor(Begin.Farbe);
				g.fillPolygon(p);
				g.fillRect((int)(Begin.maxx), (int) y1, (int) (xo+0.5), (int) (y2 - y1));
				g.fillRect((int) (Ende.minx - xo), (int) y3, (int) (xo+0.5), (int) (y4 - y3));

				Begin.sumBisherB += Begin.Punktzahl;
				Ende.sumBisherE += Begin.Punktzahl;
				
			}
		}
	}
}
