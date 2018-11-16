package py.edu.facitec.psmsystem.informe;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.edu.facitec.psmsystem.dao.ClienteDao;
import py.edu.facitec.psmsystem.entidad.Cliente;
import py.edu.facitec.psmsystem.tabla.TablaListadoClientes;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaListadoClientes extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private List<Cliente> lista;
	private TablaListadoClientes tablaClientes;
	private ClienteDao dao;
	private JTable table;
	private JTextField tfDesdeNombre;
	private JTextField tfHastaNombre;
	@SuppressWarnings("rawtypes")
	private JComboBox cbOrden;
	private JLabel lblOrdenarPor;
	private AbstractButton btnImprimir;
	private JLabel lblTotalRegistros;
	private JButton btnProcesar;
	private JTextField tfHastaId;
	private JTextField tfDesdeId;
	private JButton btnCancelar;
	private JButton btnSalir;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaListadoClientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaListadoClientes.class.getResource("/img/ventanas/icono.png")));
		setTitle("Listado de Clientes");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		tablaClientes = new TablaListadoClientes();
		
		lblOrdenarPor = new JLabel("Ordenar por: ");
		lblOrdenarPor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrdenarPor.setBounds(372, 10, 77, 18);
		getContentPane().add(lblOrdenarPor);
		
		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(372, 35, 77, 18);
		getContentPane().add(lblTotal);
		
		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(451, 35, 34, 18);
		getContentPane().add(lblTotalRegistros);
		
		JLabel lblDesdeId = new JLabel("Desde Id: ");
		lblDesdeId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeId.setBounds(11, 10, 60, 19);
		getContentPane().add(lblDesdeId);
		
		JLabel lblHastaId = new JLabel("Hasta Id: ");
		lblHastaId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaId.setBounds(11, 34, 60, 18);
		getContentPane().add(lblHastaId);
		
		tfDesdeId = new JTextField();
		tfDesdeId.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfHastaId.requestFocus();
					tfHastaId.selectAll();
				}
			}
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c != e.VK_ENTER & c != e.VK_BACK_SPACE) {
					e.consume();
				}
				if (tfDesdeId.getText().length() == 5) {
					e.consume();
				}
			}
		});
			
		tfDesdeId.setColumns(10);
		tfDesdeId.setBounds(69, 10, 91, 20);
		getContentPane().add(tfDesdeId);
		
		tfHastaId = new JTextField();
		tfHastaId.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfDesdeNombre.requestFocus();
					tfDesdeNombre.selectAll();
				}
			}
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c != e.VK_ENTER & c != e.VK_BACK_SPACE) {
					e.consume();
				}
				if (tfDesdeId.getText().length() == 5) {
					e.consume();
				}
			}
		});
		tfHastaId.setColumns(10);
		tfHastaId.setBounds(69, 34, 91, 20);
		getContentPane().add(tfHastaId);
	
		
		JLabel lblDesdeNombre = new JLabel("Desde Nombre: ");
		lblDesdeNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeNombre.setBounds(163, 10, 98, 19);
		getContentPane().add(lblDesdeNombre);
		
		JLabel lblHastaNombre = new JLabel("Hasta Nombre: ");
		lblHastaNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaNombre.setBounds(163, 34, 98, 18);
		getContentPane().add(lblHastaNombre);
		
		tfDesdeNombre = new JTextField();
		tfDesdeNombre.setBounds(260, 10, 91, 20);
		getContentPane().add(tfDesdeNombre);
		tfDesdeNombre.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfHastaNombre.requestFocus();
					tfHastaNombre.selectAll();
				}
			}
		});
		tfDesdeNombre.setColumns(10);
		
		tfHastaNombre = new JTextField();
		tfHastaNombre.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					cbOrden.requestFocus();
				}
			}
		});
		tfHastaNombre.setBounds(260, 34, 91, 20);
		getContentPane().add(tfHastaNombre);
		tfHastaNombre.setColumns(10);
		
		cbOrden = new JComboBox();
		cbOrden.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					btnProcesar.requestFocus();
				}
			}
		});
		cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Id", "Nombre"}));
		cbOrden.setSelectedIndex(0);
		cbOrden.setBounds(451, 10, 91, 20);
		getContentPane().add(cbOrden);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesar();
				btnImprimir.requestFocus();
			}
		});
		btnProcesar.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
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
		
		table = new JTable(tablaClientes);
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
	
	}

//-------------------------------------METODOS------------------------------------------------
	private void procesar() {
		dao = new ClienteDao();
		int idDesde = 0;
		int idHasta = 9999999;
		try {
			idDesde = Integer.parseInt(tfDesdeId.getText());
		} catch (Exception e) {}
		try {
			idHasta = Integer.parseInt(tfHastaId.getText());
		} catch (Exception e) {}
		
		String nDesde = tfDesdeNombre.getText();
		String nHasta = tfHastaNombre.getText()+"zzzz";
		
		lista = dao.recuperarPorRangos(idDesde, idHasta, nDesde, nHasta, cbOrden.getSelectedIndex());
		tablaClientes.setLista(lista);
		tablaClientes.fireTableDataChanged();
		table.setModel(tablaClientes);
		TablaUtil.resizeTableColumnWidth(table);
		
		lblTotalRegistros.setText(lista.size()+"");
		
		btnImprimir.requestFocus();
	}
	
	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filtros = "Id: "+tfDesdeId.getText()+" "+"hasta"+" "+tfHastaId.getText()+" | "
					   + "Nombre: "+tfDesdeNombre.getText()+" "+"hasta"+" "+tfHastaNombre.getText()+" | "
					   + "Ordenado por: "+cbOrden.getSelectedItem().toString()+" | "
					   + "Total registros: "+lblTotalRegistros.getText()+"";
		Map<String, Object> map = new HashMap<>();
		map.put("filtros", filtros);
		map.put("codigo", ""+((Math.random()*9999)+1000));
		ReportesUtil.GenerarInforme(lista, map, "ListadoClientes");
	}

	private void cancelar() {
		tfDesdeId.setText("");
		tfDesdeNombre.setText("");
		tfHastaId.setText("");
		tfHastaNombre.setText("");
		
		lista.removeAll(lista);
		tablaClientes.setLista(lista);
		tablaClientes.fireTableDataChanged();
		
		lblTotalRegistros.setText(lista.size()+"");
		tfDesdeId.requestFocus();
	}
}
