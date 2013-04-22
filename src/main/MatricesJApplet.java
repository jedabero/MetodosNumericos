/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import metodosnumericos.Matriz;
import resurces.RowNumberTable;

/**
 *
 * @author Jedabero
 */
public class MatricesJApplet extends JApplet implements ChangeListener, ItemListener{
    
    private Matriz matriz;
    private JScrollPane scrlpMatriz;
    private JTable tblMatriz;
    private JTable tblRowHeaders;
    private DefaultTableModel dtmMatriz;
    private int nRows;
    private int nCols;
    
    private SpinnerNumberModel spnmR;
    private JSpinner spnrRows;
    private SpinnerNumberModel spnmC;
    private JSpinner spnrCols;
    
    private String itemsElem[];
    private JComboBox<String> cbOpElem;
    private JTextField txtF1;
    private JTextField txtF2;
    private JTextField txtRes;
    
    private String itemsMatr[];
    private JComboBox<String> cbOpMatriz;
    private JTable tblRes;
    private JScrollPane scrlpRes;
    private DefaultTableModel dtmRes;
    
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        /* Create and display the applet */
        setLayout(new GridBagLayout());
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initComponents() {
        //SPINNERS
        JLabel lblR = new JLabel("Filas: ", JLabel.RIGHT);
        spnmR = new SpinnerNumberModel(3, 2, 25, 1);
        spnrRows = new JSpinner(spnmR);
        JLabel lblC = new JLabel("Columnas: ", JLabel.RIGHT);
        spnmC = new SpinnerNumberModel(3, 2, 25, 1);
        spnrCols = new JSpinner(spnmR);
        
        spnrRows.addChangeListener(this);
        
        componente(this, lblC, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, spnrCols, 1, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, lblR, 2, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, spnrRows, 3, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        
        //MATRIZ
        nCols = 3;
        nRows = 3;
        dtmMatriz = new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
            },
            new String [] {
                "1", "2", "3"
            });
        tblMatriz = new JTable(dtmMatriz);
        tblRowHeaders = new RowNumberTable(tblMatriz);
        scrlpMatriz = new JScrollPane();
        scrlpMatriz.setRowHeaderView(tblRowHeaders);
        scrlpMatriz.setViewportView(tblMatriz);
        
        componente(this, scrlpMatriz, 0, 1, 5, 2, 1.0, 1.0,
                GridBagConstraints.BOTH,"");
        
        creaMatriz();
        
        //LISTAS DE OPCIONES
        JLabel lblOpE = new JLabel("Calcular");
        itemsElem = new String[] {
            "Determinante", "Traza", "Cofactor en...", "Reducida en...",
            "Multiplicar por..."
        };
        cbOpElem = new JComboBox<String>(itemsElem);
        txtF1 = new JTextField();
        txtF2 = new JTextField();
        cbOpElem.addItemListener(this);
        componente(this, lblOpE, 0, 3, 1, 1, 1.0, 1.0,
                GridBagConstraints.NONE,"");
        componente(this, cbOpElem, 1, 3, 2, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, txtF1, 3, 3, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, txtF2, 4, 3, 1, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        
        txtRes = new JTextField();
        componente(this, txtRes, 0, 4, 5, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        
        JLabel lblOpM = new JLabel("Mostrar Matriz");
        itemsMatr = new String[] {
            "Valor Absoluto", "Adjunta", "Cofactor", "Diagonal", "Inversa",
            "Transpuesta", "Triangular Inferior", "Triangular Inferior Estricta",
            "Triangular Superior", "Triangular Superior Estricta"
        };
        cbOpMatriz = new JComboBox<String>(itemsMatr);
        cbOpMatriz.addItemListener(this);
        
        componente(this, lblOpM, 0, 5, 1, 1, 1.0, 1.0,
                GridBagConstraints.NONE,"");
        componente(this, cbOpMatriz, 1, 5, 4, 1, 1.0, 1.0,
                GridBagConstraints.HORIZONTAL,"");
        
        
        
    }
    
    private void creaMatriz(){
        BigDecimal[][] mt = new BigDecimal[nRows][nCols];
        for (int i = 0; i < mt.length; i++) {
            for (int j = 0; j < mt[0].length; j++) {
                String v = tblMatriz.getValueAt(i, j).toString();
                if(v.isEmpty()){
                    v = "1";
                }
                mt[i][j] = new BigDecimal(v);

            }
        }
        
        matriz = new Matriz(mt);
    }
    
    private static void componente(Container gbl, JComponent jc, int x, int y,
            int w, int h, double wx, double wy, int f, String toolTip){
        if (!toolTip.isEmpty()) {
            jc.setToolTipText(toolTip);
	}
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = x; gbc.gridy = y;
	gbc.gridwidth = w; gbc.gridheight = h;
	gbc.weightx = wx; gbc.weighty = wy;
	gbc.fill = f;
	gbl.add(jc, gbc);
	}
    
    // TODO overwrite start(), stop() and destroy() methods

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spnr = (JSpinner) e.getSource();
	int np = Integer.parseInt(spnr.getValue().toString());
        
        String h[] = new String[np];
        for (int i = 0; i < np; i++) {
            h[i] = ""+(i+1);
        }
        
        Object val[][] = new Object[np][np];
        int fin = 0;
        if(np>nRows){
            fin = nRows;
        }else if(np<nRows){
            fin = np;
        }
        for (int i = 0; i < fin; i++) {
            for (int j = 0; j < fin; j++) {
                val[i][j] = dtmMatriz.getValueAt(i, j);
            }
        }
        if(np>nRows){
            for (int i = 0; i < np; i++) {
                val[np-1][i] = "0";
            }
        
            for (int i = 0; i < np; i++) {
                val[i][np-1] = "0";
            }
        }
        
        nRows = np;
        nCols = np;
        
        dtmMatriz.setDataVector(val, h);
        tblMatriz.setModel(dtmMatriz);
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            try {
                if(e.getItem().toString().equals(cbOpElem.getItemAt(0))){
                    txtRes.setText(matriz.det().toString());
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(1))){
                    txtRes.setText(matriz.traza().toString());
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(2))){
                    JOptionPane.showMessageDialog(this, "Not Implemented yet");
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(3))){
                    JOptionPane.showMessageDialog(this, "Not Implemented yet");
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(4))){
                    JOptionPane.showMessageDialog(this, "Not Implemented yet");
                }
            } catch (Exception ex) {
                    Logger.getLogger(MatricesJApplet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getStateChange() == ItemEvent.DESELECTED){
            //DO NOTHING YET
        }
        
        
    }
}
