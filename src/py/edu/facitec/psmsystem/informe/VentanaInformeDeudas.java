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

import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.tabla.TablaDeudaCliente;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaInformeDeudas extends JDialog {

	private List<DeudaCliente> lista;
	private TablaDeudaCliente tablaInformeDeudas;
	private DeudaClienteDao dao;
	private JTable table;
	private AbstractButton btnImprimir;
	private JButton btnProcesar;
	private JButton btnCancelar;
	private JButton btnSalir;
	
	public VentanaInformeDeudas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInformeDeudas.class.getResource("/img/ventanas/icono.png")));
		setTitle("Informe de Deudas");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		tablaInformeDeudas = new TablaDeudaCliente();
		
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
		btnProcesar.setBounds(552, 10, 122, 34);
		getContentPane().add(btnProcesar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 61, 663, 268);
		getContentPane().add(scrollPane);
		
		table = new JTable(tablaInformeDeudas);
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
		
		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(394, 33, 77, 18);
		getContentPane().add(lblTotal);
		
		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(475, 33, 34, 18);
		getContentPane().add(label_1);
		
	}

//-------------------------------------METODOS------------------------------------------------
	private void procesar() {
//		dao = new DeudaClienteDao();
//
//		Date fechaDesde = FechaUtil.convertirStringADateUtil(tfDesdeFecha.getText());
//		Date fechaHasta = FechaUtil.convertirStringADateUtil(tfHastaFecha.getText());
//		
//		lista = dao.recuperarPorRangos(fechaDesde, fechaHasta);
//		tablaInformeDeudas.setLista(lista);
//		tablaInformeDeudas.fireTableDataChanged();
//		table.setModel(tablaInformeDeudas);
//		TablaUtil.resizeTableColumnWidth(table);
//		
//		lblTotalRegistros.setText(lista.size()+"");
	}
	
	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
//						+ "Total registros: "+lblTotalRegistros.getText()+"";
//		Map<String, Object> map = new HashMap<>();
//		map.put("filtros", filtros);
//		map.put("codigo", ""+((Math.random()*9999)+1000));
//		ReportesUtil.GenerarInforme(lista, map, "InformeDeudas");
	}

	private void cancelar() {
		dao = new DeudaClienteDao();
//		tfDesdeFecha.setText("");
//		tfHastaFecha.setText("");
//		
//		tfDesdeFecha.requestFocus();
	}
}
