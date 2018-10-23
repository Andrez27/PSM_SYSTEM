package py.edu.facitec.psmsystem.informe;

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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import py.edu.facitec.psmsystem.dao.EmpenoDao;
import py.edu.facitec.psmsystem.entidad.Empeno;
import py.edu.facitec.psmsystem.tabla.TablaInformeEmpenos;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaInformeEmpenos extends JDialog {

	private List<Empeno> lista;
	private TablaInformeEmpenos tablaInformeEmpenos;
	private EmpenoDao dao;
	private JTable table;
	private AbstractButton btnImprimir;
	private JLabel lblTotalRegistros;
	private JButton btnProcesar;
	private JFormattedTextField tfDesdeFecha;
	private JFormattedTextField tfHastaFecha;
	private JButton btnCancelar;
	private JButton btnSalir;
	
	public VentanaInformeEmpenos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInformeEmpenos.class.getResource("/img/ventanas/icono.png")));
		setTitle("Informe de Empe\u00F1os");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		tablaInformeEmpenos = new TablaInformeEmpenos();
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(180, 34, 57, 18);
		getContentPane().add(lblTotal);
		
		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(238, 34, 34, 18);
		getContentPane().add(lblTotalRegistros);

		tfDesdeFecha = new JFormattedTextField(FechaUtil.getMascara());
		tfDesdeFecha.setBounds(103, 9, 70, 20);
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
		tfHastaFecha.setBounds(103, 32, 70, 20);
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
		btnProcesar.setBounds(276, 18, 122, 34);
		getContentPane().add(btnProcesar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 61, 663, 268);
		getContentPane().add(scrollPane);
		
		table = new JTable(tablaInformeEmpenos);
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
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
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
		
		JLabel lblHastaFecha = new JLabel("Hasta Fecha: ");
		lblHastaFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaFecha.setBounds(11, 34, 93, 18);
		getContentPane().add(lblHastaFecha);
		
		JLabel lblDesdeFecha = new JLabel("Desde Fecha: ");
		lblDesdeFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeFecha.setBounds(11, 11, 93, 19);
		getContentPane().add(lblDesdeFecha);
		
	}

//-------------------------------------METODOS------------------------------------------------
	private void procesar() {
		dao = new EmpenoDao();

		Date fechaDesde = FechaUtil.convertirStringADateUtil(tfDesdeFecha.getText());
		Date fechaHasta = FechaUtil.convertirStringADateUtil(tfHastaFecha.getText());
		
		lista = dao.recuperarPorRangos(fechaDesde, fechaHasta);
		tablaInformeEmpenos.setLista(lista);
		tablaInformeEmpenos.fireTableDataChanged();
		table.setModel(tablaInformeEmpenos);
		TablaUtil.resizeTableColumnWidth(table);
		
		lblTotalRegistros.setText(lista.size()+"");
	}
	
	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
			return;
		}
		String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
						+ "Total registros: "+lblTotalRegistros.getText()+"";
		Map<String, Object> map = new HashMap<>();
		map.put("filtros", filtros);
		map.put("codigo", ""+((Math.random()*9999)+1000));
		ReportesUtil.GenerarInforme(lista, map, "InformeEmpenos");
	}

	private void cancelar() {
		dao = new EmpenoDao();
		tfDesdeFecha.setText("");
		tfHastaFecha.setText("");
		
		tfDesdeFecha.requestFocus();
	}
}
