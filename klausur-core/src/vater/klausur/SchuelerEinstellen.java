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
 public class SchuelerEinstellen extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4746123320905415443L;
	/**
	 * 
	 */
    private static String[] header = {"Schueler"};
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
    private final JButton btnSchuelerLschen = new JButton("Schueler l�schen");
    GUIKnoten k;
    public SchuelerEinstellen(FancyWindow fw_, JFrame f, DatenSammlung ds_,String prozente_, GUIKnoten k_) {
    	frame = f;
    	fw = fw_;
    	this.k = k_;
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
        		for(Aufgabe a : ds.AufgabenList){
        			ds.PunkteList.add(new Punkte("Markus", a.Aufschrift, a.Punktzahl));
        		}
        	}
        });
        btnSchlerHinzufgen.setText("Schueler hinzuf�gen");
        panel.add(btnSchlerHinzufgen);
        this.add(panel, BorderLayout.SOUTH);
        btnSpeichern.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		File f = Utility.choseFile("Punkte Speichern", "s", new String[]{"Datei","dat"});
        		List<String> lines = new ArrayList<String>();
//        		int s = 0;
        		for(int zeile = 0; zeile < dtm.getRowCount(); zeile++){
        			boolean schuelerVorhanden = false;
        			for(int i = ds.PunkteList.size()-1; i>= 0; i--){
        				if(ds.PunkteList.get(i).Schueler.equals(dtm.getValueAt(zeile, 0))){
        					schuelerVorhanden = true;
        					break;
        				}
        			}
        			if(!schuelerVorhanden){
        				
        			}
        			if(zeile >= ds.PunkteList.size()){ds.PunkteList.add(new Punkte("Markus", ds.AufgabenList.get(0).Aufschrift, ds.AufgabenList.get(0).Punktzahl));}
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
        btnSchuelerLschen.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int i = table.getSelectedRow();
        		if(i >= 0){
        			for(int j = ds.PunkteList.size(); j>=0; j--){
        				if(ds.PunkteList.get(j).Schueler.equals(dtm.getValueAt(i, 0))){
        					ds.PunkteList.remove(j);
        				}
        			}
        			dtm.removeRow(i);
        		}
        	}
        });
        
        panel.add(btnSchuelerLschen);
        
        panel.add(btnSpeichern);
    }

    protected void exit() {
    	frame.dispose();
    }

	private void addRows(List<Punkte> list) {
    	for(Punkte p : list){
    		dtm.addRow(new Object[]{p.Schueler});
    	}
	}

	private void addRow() {
        dtm.addRow(new Object[]{
                "Markus"
            });
    }
}
