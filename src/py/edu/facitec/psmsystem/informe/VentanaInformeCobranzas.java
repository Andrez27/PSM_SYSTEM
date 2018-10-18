package py.edu.facitec.psmsystem.informe;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRException;
import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.dao.CobranzaDao;
import py.edu.facitec.psmsystem.dao.EmpenoDao;
import py.edu.facitec.psmsystem.entidad.Cobranza;
import py.edu.facitec.psmsystem.tabla.TablaInformeCobranzas;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;
import javax.swing.JCheckBox;

public class VentanaInformeCobranzas extends JDialog {

	private List<Cobranza> lista;
	private TablaInformeCobranzas tablaInformeCobranzas;
	private CobranzaDao dao;
	private JTable table;
	private String filtro;
	private AbstractButton btnImprimir;
	private JLabel lblTotalRegistros;
	private JButton btnProcesar;
	private Map<String, Object> map;
	private JFormattedTextField tfDesdeFecha;
	private JFormattedTextField tfHastaFecha;
	private JButton btnCancelar;
	private JButton btnSalir;
	private JCheckBox chDetallado;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInformeCobranzas dialog = new VentanaInformeCobranzas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaInformeCobranzas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInformeCobranzas.class.getResource("/img/ventanas/icono.png")));
		setTitle("Informe de Cobranzas");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		tablaInformeCobranzas = new TablaInformeCobranzas();
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(180, 32, 64, 18);
		getContentPane().add(lblTotal);
		
		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(246, 32, 34, 18);
		getContentPane().add(lblTotalRegistros);
		
		tfDesdeFecha = new JFormattedTextField(FechaUtil.getMascara());
		tfDesdeFecha.setBounds(100, 9, 70, 20);
		getContentPane().add(tfDesdeFecha);
		tfDesdeFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfHastaFecha.requestFocus();
					tfHastaFecha.selectAll();
				}
			}
		});
		tfDesdeFecha.setColumns(10);
		
		tfHastaFecha = new JFormattedTextField(FechaUtil.getMascara());
		tfHastaFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					btnProcesar.requestFocus();
				}
			}
		});
		tfHastaFecha.setBounds(100, 32, 70, 20);
		getContentPane().add(tfHastaFecha);
		tfHastaFecha.setColumns(10);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesar();
				btnImprimir.requestFocus();
			}
		});
		btnProcesar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					procesar();
					btnImprimir.requestFocus();
				}
			}
		});
		btnProcesar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnProcesar.setBounds(303, 16, 122, 34);
		getContentPane().add(btnProcesar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 61, 663, 268);
		getContentPane().add(scrollPane);
		
		table = new JTable(tablaInformeCobranzas);
		scrollPane.setViewportView(table);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnImprimir.setBounds(10, 340, 122, 34);
		getContentPane().add(btnImprimir);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
				procesar();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBounds(420, 340, 122, 34);
		getContentPane().add(btnCancelar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalir.setBounds(552, 340, 122, 34);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnSalir);
		
		JLabel lblDesdeFecha = new JLabel("Desde Fecha: ");
		lblDesdeFecha.setHorizontalAlignment(SwingConstants.LEFT);
		lblDesdeFecha.setBounds(11, 10, 84, 19);
		getContentPane().add(lblDesdeFecha);
		
		JLabel lblHastaFecha = new JLabel("Hasta Fecha: ");
		lblHastaFecha.setHorizontalAlignment(SwingConstants.LEFT);
		lblHastaFecha.setBounds(11, 32, 84, 18);
		getContentPane().add(lblHastaFecha);
		
		chDetallado = new JCheckBox("Detallado");
		chDetallado.setBounds(138, 348, 97, 23);
		getContentPane().add(chDetallado);
	}

//-------------------------------------METODOS------------------------------------------------
	private void procesar() {
		dao = new CobranzaDao();
		
		Date fechaDesde = FechaUtil.convertirStringADateUtil(tfDesdeFecha.getText());
		Date fechaHasta = FechaUtil.convertirStringADateUtil(tfHastaFecha.getText());
		
		lista = dao.recuperarPorRangos(fechaDesde, fechaHasta);
		tablaInformeCobranzas.setLista(lista);
		tablaInformeCobranzas.fireTableDataChanged();
		table.setModel(tablaInformeCobranzas);
		TablaUtil.resizeTableColumnWidth(table);
		
		lblTotalRegistros.setText(lista.size()+"");
	}
	
	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
			return;
		}
		if (chDetallado.isSelected()) {
			String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
						   + "Total registros: "+lblTotalRegistros.getText()+"";
			Map<String, Object> map = new HashMap<>();
			map.put("filtros", filtros);
			ReportesUtil.GenerarInforme(lista, map, "InformeCobranzasDetallado");
		} else {
			String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
						   + "Total registros: "+lblTotalRegistros.getText()+"";
			Map<String, Object> map = new HashMap<>();
			map.put("filtros", filtros);
			ReportesUtil.GenerarInforme(lista, map, "InformeCobranzas");

		}

	}

	private void cancelar() {
		dao = new CobranzaDao();
		
		tfDesdeFecha.setText("");
		tfHastaFecha.setText("");
		
		tfDesdeFecha.requestFocus();
	}
}
