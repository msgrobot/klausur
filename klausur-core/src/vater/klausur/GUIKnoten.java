package vater.klausur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class GUIKnoten {

	public int Stelle;
	public double Punktzahl;
	public double AnzahlAnderer;
	public String Aufschrift;
	public Color Farbe;
	public static FancyWindow fw_;
	public abstract double SumVal();

	public double minx = -1;
	public double maxx = -1;
	public double miny = -1;
	public double maxy = -1;
	public double hei = -1;
	public double sumBisherB = 0;
	public double sumBisherE = 0;
	public boolean vis = false;
	
	public GUIKnoten(int stelle, double Punktzahl, String aufschrift, FancyWindow fw) {
		fw_ = fw;
		Stelle = stelle;
		this.Punktzahl = Punktzahl;
		Aufschrift = aufschrift;

		if (stelle %5 == 0) {
			Farbe = new Color(198, 127, 149, 150);
		}
		if (stelle %5 == 1) {
			Farbe = new Color(117, 160, 178, 150);
		}
		if (stelle %5 == 2) {
			Farbe = new Color(188, 148, 122, 150);
		}
		if (stelle %5 == 3) {
			Farbe = new Color(195, 194, 197, 150);
		}
		if (stelle %5 == 4) {
			Farbe = new Color(152, 151, 154, 150);
		}
		
	}

//	private Color cbs(String string) {
//		float r = (float) Integer.parseInt(string.substring(0, 2), 16) / 255.0f;
//		float g = (float) Integer.parseInt(string.substring(2, 4), 16) / 255.0f;
//		float b = (float) Integer.parseInt(string.substring(4, 6), 16) / 255.0f;
//		return new Color(r, g, b);
//	}

//	static int i=0;
//	static int cmax = 100;

	public static final double rundung = 1D/(2*Math.PI);
	public void draw(Graphics g, int ix, double y1, int m, int width) {
		double x, y, w;
		double dx = (int) (width / 4 * 0.2);

		x = width / 4 * ix + dx;
		y = y1 + 10 +100;
		w = width / 4 - 2 * dx;
		
		double h2 = (int) ((Math.sqrt(Punktzahl* (double)((ix >= 2)? ((double)FancyWindow.scale1/(double)NotenPunkt.alleVegebenenPunkte) : 1D)))*15D);

		g.setColor(Color.WHITE);
		double size = (int) h2;
		double offset = (int) size /2;
		g.setColor(Farbe);
		
		double minx_ = x + w / 2 - offset; minx = minx_;
		double miny_ = y - offset; miny = miny_;
		double maxx_ = minx + offset*2; maxx = maxx_;
		double maxy_ = miny + offset*2; maxy = maxy_;
		hei = offset * 2;
		double rundung_ = rundung*4.5D;
		g.fillRoundRect((int)minx, (int)miny, (int)(offset * 2),(int)( offset * 2), (int)(offset*rundung_), (int)(offset* rundung_));
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, (int) (offset/1.25)));
		double sh = g.getFontMetrics(g.getFont()).getHeight();
		g.drawString(Aufschrift, (int)(minx + offset - g.getFontMetrics(g.getFont()).stringWidth(Aufschrift) / 2D),(int)( miny + (maxy-miny)/2D - sh/2D + ((maxy-miny)/2D- sh/2D)/2));
		if((int)Punktzahl == Punktzahl){
			g.drawString((int)Punktzahl + "", (int)(minx + (maxx-minx)/2-g.getFontMetrics(g.getFont()).stringWidth((int)Punktzahl + "") / 2D),(int)( miny + (maxy-miny)/2D + sh/2D +((maxy-miny)/2D- sh/2D)/2));
		}else{
			g.drawString(Punktzahl + "",(int)(minx + (maxx-minx)/2 - g.getFontMetrics(g.getFont()).stringWidth(Punktzahl + "") / 2D),(int)( miny + (maxy-miny)/2D + sh/2D + ((maxy-miny)/2D- sh/2D)/2));
		}
	}
	public void resetsBB(){
		sumBisherB = 0;
	}
	public void resetsBE(){
		sumBisherE = 0;
	}


	public void reset() {
		resetsBB();
		resetsBE();
	}
}
