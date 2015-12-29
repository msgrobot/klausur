package vater.klausur;


public class Main {
	
	public static void main(String[] args) {
		int w = 800;
		int h = 600;
		String s= Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		s = s.replace("\\", "/");
		if (s.endsWith("/")){
			s = s.substring(0,s.length() - 1);
		}
		String s1[] = s.split("/");
		if(s1.length >= 2){
			String name = s1[s1.length-1];
			s = s.replace(name, "");
		}
		s=s.substring(1, s.length());
		s+="Cfg.txt";
		FancyWindow fw = new FancyWindow(w, h, s);

		try {
			fw.repaint();
			Thread.sleep(100);
			fw.repaint();
			Thread.sleep(100);
			fw.repaint();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
