package vater.klausur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * @see http://stackoverflow.com/a/19472190/230513
 * @see http://stackoverflow.com/a/7519403/230513
 */
 public class AufgabenEinstellen extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4746123320905415443L;
	/**
	 * 
	 */
    private static String[] header = {"Aufgabe", "Punkte"};
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
    private final JButton btnAufgabeLschen = new JButton("Aufgabe l\u00F6schen");
    public AufgabenEinstellen(FancyWindow fw_, JFrame f, final DatenSammlung ds, String prozente_) {
    	frame = f;
    	fw = fw_;
    	final String prozente = prozente_;
        this.setLayout(new BorderLayout());
        Dimension d = new Dimension(320, ds.AufgabenList.size() * table.getRowHeight());
        table.setPreferredScrollableViewportSize(d);
        addRows(ds.AufgabenList);
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
        JButton button = new JButton(new AbstractAction("Add Row") {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ds.AufgabenList.add(new Aufgabe(table.getRowCount(), 10, "Aufgabe", fw));
        	}
        });
        button.setText("Aufgabe hinzuf\u00FCgen");
        panel.add(button);
        this.add(panel, BorderLayout.SOUTH);
        btnSpeichern.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		File f = Utility.choseFile("Aufgaben speichern", "s", new String[]{"Datein", "dat"});
        		List<String> lines = new ArrayList<String>();
        		int s = 0;
        		for(int zeile = 0; zeile < dtm.getRowCount(); zeile++){
        			if(zeile >= ds.AufgabenList.size()){ds.AufgabenList.add(new Aufgabe(zeile, -1, "", fw));}
        			ds.AufgabenList.get(zeile).Aufschrift = (String) dtm.getValueAt(zeile, 0);
        			Object po = dtm.getValueAt(zeile, 1);
        			double p = (Double) po;
        			ds.AufgabenList.get(zeile).Punktzahl = p;
        			s += ds.AufgabenList.get(zeile).Punktzahl;
        			lines.add(ds.AufgabenList.get(zeile).Punktzahl + "#" + ds.AufgabenList.get(zeile).Aufschrift);
        		}
        		lines.add(ds.SumAufgaben.Aufschrift);
        		Aufgabe.SumVal = s;
        		ds.SumAufgaben.Punktzahl = s;
        		ds.notenMachen(fw, prozente);
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
        			ds.AufgabenList.remove(i);
        		}
        	}
        });
        
        panel.add(btnAufgabeLschen);
        
        panel.add(btnSpeichern);
    }

    protected void exit() {
    	frame.dispose();
    }

	private void addRows(List<Aufgabe> list) {
    	for(Aufgabe k : list){
    		dtm.addRow(new Object[]{k.Aufschrift,k.Punktzahl});
    	}
	}

	private void addRow() {
        dtm.addRow(new Object[]{
                "Aufgabe",
                10.0
            });
    }
}
