package vater.klausur;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FancyWindow extends JFrame{

	public static double scale1 = 100.0;
	/**
	 * 
	 */
	DatenSammlung ds;
	private static final long serialVersionUID = -2472590293721915955L;
	//BufferStrategy bufferStrategy-;
	FancyWindow fc;JMenuBar menuBar;
private JButton btnAlsPngExportieren;
private JButton button;
private JButton button_1;
	public FancyWindow(int w, int h,String prozente_){
		setTitle("Klausurauswertung - � Markus Vater, Abi 2016");
		fc = this;
		final String prozente = prozente_;
		this.ds = Utility.erstelleNeueDatenSammlung(this, prozente);
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDaten = new JMenu("Daten");
		menuBar.add(mnDaten);
		
		JMenuItem mntmAufgaben = new JMenuItem("Aufgaben");
		final FancyWindow me = this;
		mntmAufgaben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new JFrame();
				f.setTitle("Aufgaben �ndern");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                AufgabenEinstellen nlt = new AufgabenEinstellen(me, f, ds, prozente);
                f.getContentPane().add(nlt);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
			}
		});
		mnDaten.add(mntmAufgaben);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Punkte");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				f.setTitle("Vergebene Punkte �ndern");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                PunkteEinstellen nlt = new PunkteEinstellen(me, f, ds, prozente);
                f.getContentPane().add(nlt);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
			}
		});
		mnDaten.add(mntmNewMenuItem);
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnDaten.add(mntmBeenden);
		
		Panel panel = new Panel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		button = new JButton("-");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scale1 -= scale1 * 0.10D;
				repaint();
			}
		});
		panel.add(button);
		
		btnAlsPngExportieren = new JButton("als PNG Exportieren");
		panel.add(btnAlsPngExportieren);
		
		button_1 = new JButton("+");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scale1 += scale1 * 0.10D;
				repaint();
			}
		});
		panel.add(button_1);
		btnAlsPngExportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D ig2 = bi.createGraphics();
				ds.paint(ig2, getWidth(), getHeight(), Mode.alles);
				paint(ig2, Utility.erstelleNeueDatenSammlung(fc, prozente));
				try {
					File file = Utility.choseFile("Bild Speichern", "s", new String[]{"Bild", "PNG"});
					String path = file.getAbsolutePath();
					String s = path.substring(path.length()-4, path.length());
					if(!s.equalsIgnoreCase(".png")){
						path += ".png";
					}
					file = new File(path);
					
					ImageIO.write(bi, "PNG", file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    		
			}
		});
		
		//createBufferStrategy(2);
		//bufferStrategy = getBufferStrategy();
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {	}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Note k : ds.aufgaben){
					for(NotenPunkt np : k.notenPunkte){
						if(check(np,e.getX(), e.getY(), me)){
							setmode(Mode.schueler);
							return;
						}
					}
					if(check(k, getX(), getY(), me)){
						return;
					}
				}
				for(Aufgabe k : ds.AufgabenList){
					if(check(k, e.getX(), e.getY(), me)){
						setmode(Mode.noten);
						return;
					}
				}
				if(check(ds.SumAufgaben, e.getX(), e.getY(), me)){
					setmode(Mode.noten);
					return;
				}
				
				if (!m.equals(Mode.alles)){
					setmode(Mode.alles);
					return;
				}
			}

			private boolean check(GUIKnoten k, int x, int y, FancyWindow fw) {
				
				boolean b = k.minx <= x && k.maxx >= x && k.miny <= y && k.maxy >= y;
				return b;		
			}
		});
	}
	
	BufferStrategy bs = null;
	@Override
	public void repaint() {
		if(bs == null){
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}

		if(bs != null){
			Graphics g = bs.getDrawGraphics();
			paint(g, ds);
			bs.show();
		}else{
			repaint(100);
		}
		
		
	};
	void setmode(Mode m){
		
		if(m.equals(Mode.alles)){
			ds.SumAufgaben.vis = true;
			for(Aufgabe a : ds.AufgabenList){
				a.vis = true;
			}
			for(Note n : ds.aufgaben){
				n.vis = true;
				for(NotenPunkt np : n.notenPunkte){
					np.vis = true;
				}
			}
		}else if(m.equals(Mode.noten)){
			ds.SumAufgaben.vis = true;
			for(Aufgabe a : ds.AufgabenList){
				a.vis = true;
			}
			for(Note n : ds.aufgaben){
				n.vis = false;
				for(NotenPunkt np : n.notenPunkte){
					np.vis = false;
				}
			}
		}else if(m.equals(Mode.schueler)){
			ds.SumAufgaben.vis = false;
			for(Aufgabe a : ds.AufgabenList){
				a.vis = false;
			}
			for(Note n : ds.aufgaben){
				n.vis = true;
				for(NotenPunkt np : n.notenPunkte){
					np.vis = true;
				}
			}
		}
		
		FancyWindow.m = m;
		repaint();
	}
	@Override
	public void paint(Graphics g){
		paint(g, ds);
	}
	public static Mode m = Mode.alles;
	public void paint(Graphics g, DatenSammlung ds){
		
//		Graphics g = bufferStrategy.getDrawGraphics();
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
          RenderingHints.VALUE_RENDER_QUALITY);
        
        
        
        ds.paint(g2, getWidth(), getHeight()-33-40, m);
        
        
        menuBar.paint(g);
        btnAlsPngExportieren.paint(g);
        button.paint(g);
        button_1.paint(g);
              //		bufferStrategy.show();
//		paintComponents(g);
	}
//	
//	private static void addPopup(Component component, final JPopupMenu popup) {
//		component.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			public void mouseReleased(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			private void showMenu(MouseEvent e) {
//				popup.show(e.getComponent(), e.getX(), e.getY());
//			}
//		});
//	}
	enum Mode{
		alles,
		noten,
		schueler
	}
}
