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

import py.edu.facitec.psmsystem.dao.ProductoDao;
import py.edu.facitec.psmsystem.entidad.Producto;
import py.edu.facitec.psmsystem.tabla.TablaListadoProductos;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaListadoProductos extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField tfDesdeId, tfHastaId, tfDesdeDescri, tfHastaDescri;
	private JButton btnProcesar, btnCancelar, btnImprimir;
	private JLabel lblTotalRegistros;
	private TablaListadoProductos tablaProductos;
	private List<Producto> lista;
	private ProductoDao dao;
	private JTable table;
	private JComboBox<String> cbOrden;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaListadoProductos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaListadoProductos.class.getResource("/img/icono.png")));
		setTitle("Listado de Productos");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);

		tablaProductos = new TablaListadoProductos();

		JLabel lblDesdeId = new JLabel("Desde Id: ");
		lblDesdeId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeId.setBounds(8, 10, 60, 19);
		getContentPane().add(lblDesdeId);

		JLabel lblHastaId = new JLabel("Hasta Id: ");
		lblHastaId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaId.setBounds(8, 35, 60, 18);
		getContentPane().add(lblHastaId);

		JLabel lblOrdenarPor = new JLabel("Ordenar por: ");
		lblOrdenarPor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrdenarPor.setBounds(382, 10, 77, 18);
		getContentPane().add(lblOrdenarPor);

		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(382, 34, 77, 18);
		getContentPane().add(lblTotal);

		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(461, 35, 34, 18);
		getContentPane().add(lblTotalRegistros);

		tfDesdeId = new JTextField();
		tfDesdeId.setBounds(68, 10, 91, 20);
		getContentPane().add(tfDesdeId);
		tfDesdeId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfHastaId.requestFocus();
					tfHastaId.selectAll();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c != KeyEvent.VK_ENTER & c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
				if (tfDesdeId.getText().length() == 5) {
					e.consume();
				}
			}
		});
		tfDesdeId.setColumns(10);

		tfHastaId = new JTextField();
		tfHastaId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfDesdeDescri.requestFocus();
					tfDesdeDescri.selectAll();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c != KeyEvent.VK_ENTER & c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
				if (tfDesdeId.getText().length() == 5) {
					e.consume();
				}
			}
		});
		tfHastaId.setBounds(68, 35, 91, 20);
		getContentPane().add(tfHastaId);
		tfHastaId.setColumns(10);

		tfDesdeDescri = new JTextField();
		tfDesdeDescri.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfHastaDescri.requestFocus();
					tfHastaDescri.selectAll();
				}
			}
		});
		tfDesdeDescri.setColumns(10);
		tfDesdeDescri.setBounds(285, 10, 91, 20);
		getContentPane().add(tfDesdeDescri);

		tfHastaDescri = new JTextField();
		tfHastaDescri.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					cbOrden.requestFocus();
				}
			}
		});
		tfHastaDescri.setColumns(10);
		tfHastaDescri.setBounds(285, 35, 91, 20);
		getContentPane().add(tfHastaDescri);

		cbOrden = new JComboBox();
		cbOrden.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					btnProcesar.requestFocus();
				}
			}
		});
		cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Id", "Descripci\u00F3n"}));
		cbOrden.setSelectedIndex(0);
		cbOrden.setBounds(461, 10, 84, 20);
		getContentPane().add(cbOrden);

		btnProcesar = new JButton("Procesar");
		btnProcesar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					procesar();
					btnImprimir.requestFocus();
				}
			}
		});
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesar();
				btnImprimir.requestFocus();
			}
		});
		btnProcesar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnProcesar.setBounds(552, 10, 122, 34);
		getContentPane().add(btnProcesar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 61, 663, 268);
		getContentPane().add(scrollPane);

		table = new JTable(tablaProductos);
		scrollPane.setViewportView(table);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprimir.setBounds(423, 340, 122, 34);
		getContentPane().add(btnImprimir);

		//		JButton btnSalir = new JButton("Salir");
		//		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		//		btnSalir.setBounds(552, 340, 122, 34);
		//		btnSalir.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				dispose();
		//			}
		//		});
		//		getContentPane().add(btnSalir);

		JLabel lblDesdeDescri = new JLabel("Desde Descripci\u00F3n: ");
		lblDesdeDescri.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeDescri.setBounds(162, 10, 124, 19);
		getContentPane().add(lblDesdeDescri);

		JLabel lblHastaDescri = new JLabel("Hasta Descripci\u00F3n: ");
		lblHastaDescri.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaDescri.setBounds(162, 35, 124, 18);
		getContentPane().add(lblHastaDescri);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBounds(552, 340, 122, 34);
		getContentPane().add(btnCancelar);
	}

	//-------------------------------------METODOS------------------------------------------------
	private void procesar() {
		dao = new ProductoDao();
		int idDesde = 0;
		int idHasta = 9999999;
		try {
			idDesde = Integer.parseInt(tfDesdeId.getText());
		} catch (Exception e) {}
		try {
			idHasta = Integer.parseInt(tfHastaId.getText());
		} catch (Exception e) {}

		String descriDesde = tfDesdeDescri.getText();
		String descriHasta = tfHastaDescri.getText()+"zzzz";

		lista = dao.recuperarPorRangos(idDesde, idHasta, descriDesde, descriHasta, cbOrden.getSelectedIndex());
		tablaProductos.setLista(lista);
		tablaProductos.fireTableDataChanged();
		table.setModel(tablaProductos);
		TablaUtil.resizeTableColumnWidth(table);

		lblTotalRegistros.setText(lista.size()+"");
	}

	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filtros = "Id: "+tfDesdeId.getText()+" "+"hasta"+" "+tfHastaId.getText()+" | "
				+ "Nombre: "+tfDesdeDescri.getText()+" "+"hasta"+" "+tfHastaDescri.getText()+" | "
				+ "Ordenado por: "+cbOrden.getSelectedItem().toString()+" | "
				+ "Total registros: "+lblTotalRegistros.getText()+"";
		Map<String, Object> map = new HashMap<>();
		map.put("filtros", filtros);
		map.put("codigo", ""+((Math.random()*9999)+1000));
		ReportesUtil.GenerarInforme(lista, map, "ListadoProductos");
	}

	private void cancelar() {
		tfDesdeId.setText("");
		tfDesdeDescri.setText("");
		tfHastaId.setText("");
		tfDesdeDescri.setText("");
		tfDesdeId.requestFocus();

		lista.removeAll(lista);
		tablaProductos.setLista(lista);
		tablaProductos.fireTableDataChanged();

		lblTotalRegistros.setText(lista.size()+"");
		tfDesdeId.requestFocus();
	}
}
