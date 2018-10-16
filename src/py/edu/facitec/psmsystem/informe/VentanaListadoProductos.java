package py.edu.facitec.psmsystem.informe;

import java.awt.EventQueue;
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

import org.hibernate.Session;
import org.hibernate.query.Query;

import net.sf.jasperreports.engine.JRException;
import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.dao.ClienteDao;
import py.edu.facitec.psmsystem.dao.ProductoDao;
import py.edu.facitec.psmsystem.entidad.Producto;
import py.edu.facitec.psmsystem.tabla.TablaListadoProductos;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.Factory;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaListadoProductos extends JDialog {

	private List<Producto> lista;
	private TablaListadoProductos tablaProductos;
	private ProductoDao dao;
	private JTable table;
	private String filtro;
	private NumberTextField tfDesdeId;
	private NumberTextField tfHastaId;
	private JComboBox cbOrden;
	private AbstractButton btnImprimir;
	private JLabel lblTotalRegistros;
	private JButton btnProcesar;
	private JTextField tfDesdeDescri;
	private JTextField tfHastaDescri;
	private JButton btnCancelar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListadoProductos dialog = new VentanaListadoProductos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaListadoProductos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaListadoProductos.class.getResource("/img/ventanas/icono.png")));
		setTitle("Listado de Productos");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		tablaProductos = new TablaListadoProductos();
		
		JLabel lblDesdeId = new JLabel("Desde Id: ");
		lblDesdeId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeId.setBounds(11, 10, 60, 19);
		getContentPane().add(lblDesdeId);
		
		JLabel lblHastaId = new JLabel("Hasta Id: ");
		lblHastaId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaId.setBounds(11, 34, 60, 18);
		getContentPane().add(lblHastaId);
		
		JLabel lblOrdenarPor = new JLabel("Ordenar por: ");
		lblOrdenarPor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrdenarPor.setBounds(360, 10, 77, 18);
		getContentPane().add(lblOrdenarPor);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(360, 34, 77, 18);
		getContentPane().add(lblTotal);
		
		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(439, 34, 34, 18);
		getContentPane().add(lblTotalRegistros);
		
		tfDesdeId = new NumberTextField();
		tfDesdeId.setBounds(68, 10, 91, 20);
		getContentPane().add(tfDesdeId);
		tfDesdeId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfHastaId.requestFocus();
					tfHastaId.selectAll();
				}
			}
		});
		tfDesdeId.setColumns(10);
		
		tfHastaId = new NumberTextField();
		tfHastaId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfDesdeDescri.requestFocus();
					tfDesdeDescri.selectAll();
				}
			}
		});
		tfHastaId.setBounds(68, 34, 91, 20);
		getContentPane().add(tfHastaId);
		tfHastaId.setColumns(10);

		tfDesdeDescri = new JTextField();
		tfDesdeDescri.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfHastaDescri.requestFocus();
					tfHastaDescri.selectAll();
				}
			}
		});
		tfDesdeDescri.setColumns(10);
		tfDesdeDescri.setBounds(259, 10, 91, 20);
		getContentPane().add(tfDesdeDescri);
		
		tfHastaDescri = new JTextField();
		tfHastaDescri.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					cbOrden.requestFocus();
				}
			}
		});
		tfHastaDescri.setColumns(10);
		tfHastaDescri.setBounds(259, 34, 91, 20);
		getContentPane().add(tfHastaDescri);
		
		cbOrden = new JComboBox();
		cbOrden.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					btnProcesar.requestFocus();
				}
			}
		});
		cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Id", "Descripci\u00F3n"}));
		cbOrden.setSelectedIndex(0);
		cbOrden.setBounds(439, 10, 91, 20);
		getContentPane().add(cbOrden);
		
		btnProcesar = new JButton("Procesar");
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
		btnImprimir.setBounds(10, 340, 122, 34);
		getContentPane().add(btnImprimir);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalir.setBounds(552, 340, 122, 34);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnSalir);
		
		JLabel lblDesdeDescri = new JLabel("Desde Descri: ");
		lblDesdeDescri.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeDescri.setBounds(168, 10, 91, 19);
		getContentPane().add(lblDesdeDescri);
		
		JLabel lblHastaDescri = new JLabel("Hasta Descri: ");
		lblHastaDescri.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaDescri.setBounds(168, 34, 91, 18);
		getContentPane().add(lblHastaDescri);
		
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
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
			return;
		}
		String filtros = "Id: "+tfDesdeId.getText()+" "+"hasta"+" "+tfHastaId.getText()+" | "
					   + "Nombre: "+tfDesdeDescri.getText()+" "+"hasta"+" "+tfHastaDescri.getText()+" | "
					   + "Ordenado por: "+cbOrden.getSelectedItem().toString()+" | "
					   + "Total registros: "+lblTotalRegistros.getText()+"";
		Map<String, Object> map = new HashMap<>();
		map.put("filtros", filtros);
		ReportesUtil.GenerarInforme(lista, map, "ListadoProductos");

	}
	
	private void cancelar() {
		dao = new ProductoDao();
		
		tfDesdeId.setText("");
		tfHastaId.setText("");
		tfDesdeDescri.setText("");
		tfHastaDescri.setText("");
		
		tfDesdeId.requestFocus();
	}
	
}