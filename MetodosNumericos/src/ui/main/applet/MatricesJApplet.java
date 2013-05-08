
package ui.main.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
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

import resources.RowNumberTable;
import vectores.Matriz;

/**
 *
 * @author Jedabero
 */
public class MatricesJApplet extends Applet
        implements ChangeListener, ItemListener, ActionListener{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5286129162295380504L;
	private Matriz matriz;
    private JScrollPane scrlpMatriz;
    private JTable tblMatriz;
    private JTable tblRowHeaders;
    private DefaultTableModel dtmMatriz;
    private int nRows;
    private int nCols;
    
    private JButton btnUpdateMatriz;
    
    private SpinnerNumberModel spnmR;
    private JSpinner spnrRows;
    @SuppressWarnings("unused")
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
    private JTable tblResRowHeaders;
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
        this.setBackground(new Color(238, 238, 238));
        //SPINNERS
        JLabel lblR = new JLabel("Filas: ", JLabel.RIGHT);
        spnmR = new SpinnerNumberModel(3, 2, 25, 1);
        spnrRows = new JSpinner(spnmR);
        JLabel lblC = new JLabel("Columnas: ", JLabel.RIGHT);
        spnmC = new SpinnerNumberModel(3, 2, 25, 1);
        spnrCols = new JSpinner(spnmR);
        
        spnrRows.addChangeListener(this);
        
        componente(this, lblC, 0, 0, 1, 1, 1, 0.1,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, spnrCols, 1, 0, 1, 1, 1, 0.1,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, lblR, 2, 0, 1, 1, 1, 0.1,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, spnrRows, 3, 0, 1, 1, 1, 0.1,
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
        
        componente(this, scrlpMatriz, 0, 1, 4, 4, 1.0, 1.0,
                GridBagConstraints.BOTH,"");
        
        btnUpdateMatriz = new JButton("Actualiza Matriz");
        btnUpdateMatriz.addActionListener(this);
        componente(this, btnUpdateMatriz, 0, 5, 4, 1, 1.0, 0.5,
                GridBagConstraints.BOTH,"");
        
        creaMatriz();
        
        //LISTAS DE OPCIONES
        JLabel lblOpE = new JLabel("Calcular");
        itemsElem = new String[] {
            "Determinante", "Traza", "Cofactor en...", "Reducida en...",
            "Multiplicar por..."
        };
        cbOpElem = new JComboBox<String>(itemsElem);
        cbOpElem.addItemListener(this);
        txtF1 = new JTextField("0");
        txtF2 = new JTextField("0");
        componente(this, lblOpE, 0, 6, 1, 1, 1.0, 0.5,
                GridBagConstraints.NONE,"");
        componente(this, cbOpElem, 1, 6, 3, 1, 1.0, 0.5,
                GridBagConstraints.HORIZONTAL,"");
        componente(this, txtF1, 0, 7, 2, 1, 1.0, 0.1,
                GridBagConstraints.HORIZONTAL,"i");
        componente(this, txtF2, 2, 7, 2, 1, 1.0, 0.1,
                GridBagConstraints.HORIZONTAL,"j");
        
        txtRes = new JTextField();
        try {
            txtRes.setText(""+matriz.det());
        } catch (Exception ex) {
            Logger.getLogger(MatricesJApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
        componente(this, txtRes, 0, 8, 4, 1, 1.0, 0.1,
                GridBagConstraints.HORIZONTAL,"Resultado");
        
        JLabel lblOpM = new JLabel("Mostrar Matriz");
        itemsMatr = new String[] {
            "Valor Absoluto", "Adjunta", "Cofactor", "Diagonal", "Inversa",
            "Transpuesta", "Triangular Inferior", "Triangular Inferior Estricta",
            "Triangular Superior", "Triangular Superior Estricta"
        };
        cbOpMatriz = new JComboBox<String>(itemsMatr);
        cbOpMatriz.addItemListener(this);
        
        componente(this, lblOpM, 0, 9, 1, 1, 1.0, 0.5,
                GridBagConstraints.NONE,"");
        componente(this, cbOpMatriz, 1, 9, 3, 1, 1.0, 0.5,
                GridBagConstraints.HORIZONTAL,"");
        
        //Matriz resultado
        dtmRes = new javax.swing.table.DefaultTableModel(
            matriz.abs().getMatriz(),
            new String [] {
                "1", "2", "3"
            });
        tblRes = new JTable(dtmRes);
        tblResRowHeaders = new RowNumberTable(tblRes);
        scrlpRes = new JScrollPane();
        scrlpRes.setRowHeaderView(tblResRowHeaders);
        scrlpRes.setViewportView(tblRes);
        
        componente(this, scrlpRes, 0, 10, 4, 4, 1.0, 1.0,
                GridBagConstraints.BOTH,"");
        
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
    
    private void setResultTable(Matriz m){
        String h[] = new String[m.getN()];
        for (int i = 0; i < h.length; i++) {
            h[i] = ""+(i+1);
        }
        dtmRes.setDataVector(m.getMatriz(), h);
        tblRes.setModel(dtmRes);
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
                    txtRes.setText(matriz.det()+"");
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(1))){
                    txtRes.setText(matriz.traza()+"");
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(2))){
                    int I = Integer.parseInt(txtF1.getText());
                    int J = Integer.parseInt(txtF2.getText());
                    txtRes.setText(matriz.cofactor(I, J)+"");
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(3))){
                    int I = Integer.parseInt(txtF1.getText());
                    int J = Integer.parseInt(txtF2.getText());
                    setResultTable(matriz.reducida(I, J));
                }else if(e.getItem().toString().equals(cbOpElem.getItemAt(4))){
                    JOptionPane.showMessageDialog(this, "Not Implemented yet");
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(0))){
                    setResultTable(matriz.abs());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(1))){
                    setResultTable(matriz.adjunta());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(2))){
                    setResultTable(matriz.cofactor());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(3))){
                    setResultTable(matriz.diagonal());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(4))){
                    System.out.println(matriz.detEquals0());
                    setResultTable(matriz.inversa());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(5))){
                    setResultTable(matriz.transpuesta());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(6))){
                    setResultTable(matriz.trianguloInferior());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(7))){
                    setResultTable(matriz.trianguloInferiorEstricto());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(8))){
                    setResultTable(matriz.trianguloSuperior());
                }else if(e.getItem().toString().equals(cbOpMatriz.getItemAt(9))){
                    setResultTable(matriz.trianguloSuperiorEstricto());
                }
            } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Problema", JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getStateChange() == ItemEvent.DESELECTED){
            //DO NOTHING YET
        }
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        creaMatriz();
    }
}
