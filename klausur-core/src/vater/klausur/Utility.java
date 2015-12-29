package vater.klausur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Utility {
	
	public static File choseFile(String head, String option, String[] FileOptions) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
		fc.setDialogTitle(head);
		fc.setFileFilter(new FileNameExtensionFilter(FileOptions[0], FileOptions[1]));
		
		int returnVal = -1;
		if(option.equals("o")){
			returnVal = fc.showOpenDialog(null);
		}
		if(option.equals("s")){
			returnVal = fc.showSaveDialog(null);
		}
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return new File(fc.getSelectedFile().getAbsolutePath());
		}

		return null;
	}
	public static List<String> readFile(File file) {
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	public static List<String> readFile(InputStream is) {
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	public static void writeFile(File f, List<String> lines){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for(int i = 0; i < lines.size(); i++){
				bw.write(lines.get(i));
				if(i < lines.size() -1){
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static DatenSammlung erstelleNeueDatenSammlung(FancyWindow fw, String prozente){
		DatenSammlung ds = new DatenSammlung(fw);
		ds.ladenAufgaben();
		ds.ladenPunkte();
		ds.notenMachen(fw, prozente);
		System.out.println("done :D");
		return ds;
	}
	
	public static void coppyCfg(String prozente) {
		
		List<String> s1 = readFile(Utility.class.getClassLoader().getResourceAsStream("Res/Cfg.txt"));
		writeFile(new File(prozente), s1);
		
//		try {
//			
//			FileOutputStream fos = new FileOutputStream(prozente);
//			int i = 0;
//			while (i != -1) {
//				fos.write(is.read());
//				fo
//			}
//			fos.flush();
//			fos.close();
//			is.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
