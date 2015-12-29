package vater.klausur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * @see http://stackoverflow.com/a/19472190/230513
 * @see http://stackoverflow.com/a/7519403/230513
 */
 public class PunkteEinstellen extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4746123320905415443L;
	/**
	 * 
	 */
    private static String[] header = {"Schueler", "Aufgabe", "Punkte"};
    private DefaultTableModel dtm = new DefaultTableModel(null, header) {

        /**
		 * 
		 */
		private static final long serialVersionUID = -3103303855289182860L;

		@Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }
    };
    JFrame frame;
    private JTable table = new JTable(dtm);
    private JScrollPane scrollPane = new JScrollPane(table);
    private JScrollBar vScroll = scrollPane.getVerticalScrollBar();
//    private int row;
    @SuppressWarnings("unused")
	private boolean isAutoScroll;
    private final JButton btnSpeichern = new JButton("Speichern");

    FancyWindow fw;
    private final JButton btnAufgabeLschen = new JButton("Zeile l\u00F6schen");
    public PunkteEinstellen(FancyWindow fw_, JFrame f, DatenSammlung ds_,String prozente_) {
    	frame = f;
    	fw = fw_;
    	final String prozente = prozente_; 
    	final DatenSammlung ds = ds_;
        this.setLayout(new BorderLayout());
        Dimension d = new Dimension(320, ds.PunkteList.size() * table.getRowHeight());
        table.setPreferredScrollableViewportSize(d);
        addRows(ds.PunkteList);
        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vScroll.addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                isAutoScroll = !e.getValueIsAdjusting();
            }
        });
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        JButton btnSchlerHinzufgen = new JButton(new AbstractAction("Add Row") {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });
        btnSchlerHinzufgen.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ds.PunkteList.add(new Punkte("Markus", "Aufgabe1", 10));
        	}
        });
        btnSchlerHinzufgen.setText("Sch\u00FCler hinzuf\u00FCgen");
        panel.add(btnSchlerHinzufgen);
        this.add(panel, BorderLayout.SOUTH);
        btnSpeichern.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		File f = Utility.choseFile("Punkte Speichern", "s", new String[]{"Datei","dat"});
        		List<String> lines = new ArrayList<String>();
//        		int s = 0;
        		for(int zeile = 0; zeile < dtm.getRowCount(); zeile++){
        			if(zeile >= ds.PunkteList.size()){ds.PunkteList.add(new Punkte("Markus", "Aufgabe 1", 10));}
        			ds.PunkteList.get(zeile).Schueler = (String) dtm.getValueAt(zeile, 0);
        			ds.PunkteList.get(zeile).Aufgabe = (String) dtm.getValueAt(zeile, 1);
        			Object po = dtm.getValueAt(zeile, 2);
        			double p = (Double) po;
        			ds.PunkteList.get(zeile).Punktzahl = p;
//        			s += fw.ds.Punkte.get(zeile).Punktzahl;
        			lines.add(ds.PunkteList.get(zeile).Schueler + "#" + ds.PunkteList.get(zeile).Aufgabe + "#" + ds.PunkteList.get(zeile).Punktzahl);
        		}
//        		Aufgabe.SumVal = s;
//        		fw.ds.SumAufgaben.Punktzahl = s;
        		fw.ds.notenMachen(fw, prozente);
        		fw.repaint();
        		if(f != null){
        			Utility.writeFile(f, lines);
        		}
        		exit();
        	}
        });
        btnAufgabeLschen.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int i = table.getSelectedRow();
        		if(i >= 0){
        			dtm.removeRow(i);
        			ds.PunkteList.remove(i);
        		}
        	}
        });
        
        panel.add(btnAufgabeLschen);
        
        panel.add(btnSpeichern);
    }

    protected void exit() {
    	frame.dispose();
    }

	private void addRows(List<Punkte> list) {
    	for(Punkte p : list){
    		dtm.addRow(new Object[]{p.Schueler, p.Aufgabe, p.Punktzahl});
    	}
	}

	private void addRow() {
        dtm.addRow(new Object[]{
                "Markus",
        		"Aufgabe 1",
                10.0
            });
    }
}
