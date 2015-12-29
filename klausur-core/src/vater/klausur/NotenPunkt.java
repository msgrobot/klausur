package vater.klausur;

import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import java.util.Locale;

//import javax.tools.JavaCompiler;
//import javax.tools.ToolProvider;

public class NotenPunkt extends GUIKnoten {

	static int sumPunkte;
	public static int alleVegebenenPunkte;

	List<Schueler> schueler;

	public NotenPunkt(int stelle, int Punkte, String aufschrift, FancyWindow fw, List<Schueler> schüler) {
		super(stelle, Punkte, aufschrift, fw);
		sumPunkte += Punkte;
		this.schueler = schüler;

		for (Schueler s : schüler) {
			Punktzahl += s.sumP();
		}
	}

	@Override
	public double SumVal() {
		return sumPunkte;
	}

	public static List<NotenPunkt> ausPunktenErstellen(List<Punkte> punkte, DatenSammlung ds, FancyWindow fw,
			String prozente) {
		fw_ = fw;
		List<List<Punkte>> punkteL = new ArrayList<List<Punkte>>();

		for (Punkte p : punkte) {
			SchuelerID sid = SchuelerID.getSchülerID(p.Schueler);
			alleVegebenenPunkte += p.Punktzahl;
			if (punkteL.size() <= sid.id) {
				punkteL.add(new ArrayList<Punkte>());
			}
			punkteL.get(sid.id).add(p);
		}

		List<Schueler> sumL = new ArrayList<Schueler>();
		for (List<Punkte> punkteEinesSchülers : punkteL) {
			sumL.add(new Schueler(punkteEinesSchülers, ds, prozente));
		}

		List<NotenPunkt> npl = new ArrayList<NotenPunkt>(15);

		int j = 0;
		for (int i = 15; i >= 0; i--) {
			List<Schueler> sl = new ArrayList<Schueler>();
			for (Schueler s : sumL) {
				if (s.NotenPunkt_ == i) {
					sl.add(s);
				}
			}
			npl.add(new NotenPunkt(j++, sl.size(), i + "", fw_, sl));
		}

		// TODO Notenpunkte ausrechnen , in liste packen

		return npl;
	}

	static List<NPDef> prozente_ = new ArrayList<NPDef>();

	public static int getNotenPunktVonProzent(double d, String prozente) {
		if (prozente_.size() == 0) {
			prozente = prozente.replace("/", "\\");
			File f = new File(prozente);
			if(!f.exists()){
				System.out.println("startCoppy");
				Utility.coppyCfg(prozente);
				System.out.println("finishCoppy");
			}
			System.out.println("cfg?: " + f.exists());
			Iterator<String> itr = Utility.readFile(f).iterator();
			while (itr.hasNext()) {
				String s = itr.next();
				s = s.replace("=>", "");
				s = s.replace("(", "");
				s = s.replace(")", "");
				s = s.replace("p", "");
				boolean größer = s.contains(">");
				boolean gleich = s.contains("=");
				boolean b2 = !(s.startsWith("#") || s.startsWith(" ") || s.startsWith("//") || s.startsWith("'"));
				boolean b = b2 && ((größer && gleich) || s.contains("<"));
				s = s.replace(" ", "");
				if (b) {
					String[] split;
					if (größer) {
						split = s.split(">");
					} else {
						split = s.split("<");
					}
					split[0] = split[0].replace(">", "").replace("<", "").replace("=", "");
					split[1] = split[1].replace(">", "").replace("<", "").replace("=", "");
					prozente_.add(new NPDef(Integer.parseInt(split[0]), Double.parseDouble(split[1]), größer, gleich));
				}
			}
		}
		for (NPDef t : prozente_) {
			if ((t.größer && d > t.e2) || ((!t.größer) && d < t.e2) || (t.gleich && d == t.e2)) {
				return t.e1;
			}
		}

		return -1;
	}

	public static class Schueler {
		public List<Punkte> SumPunkte;
		public String Schüler;

		public int NotenPunkt_;

		public Schueler(List<Punkte> sumPunkte, DatenSammlung ds, String prozente) {
			SumPunkte = sumPunkte;
			Schüler = sumPunkte.get(0).Schueler;
			NotenPunkt_ = NotenPunkt.getNotenPunktVonProzent(sumP() / ds.SumAufgaben.Punktzahl, prozente);
		}

		double sumP() {
			double r = 0;
			for (Punkte p : SumPunkte) {
				r += p.Punktzahl;
			}
			return r;
		}
	}

	static class SchuelerID {
		public static List<SchuelerID> schüler = new ArrayList<SchuelerID>();
		public String Schüler;
		public int id;
		public static int count = 0;

		public static SchuelerID getSchülerID(String schüler_) {
			for (SchuelerID s : schüler) {
				if (s.Schüler.equals(schüler_)) {
					return s;
				}
			}

			SchuelerID ret = new SchuelerID();
			ret.Schüler = schüler_;
			ret.id = count++;
			schüler.add(ret);
			return ret;
		}

		public static void reset() {
			count = 0;
		}
	}
}
