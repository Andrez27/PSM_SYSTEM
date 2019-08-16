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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import py.edu.facitec.psmsystem.dao.CobranzaDao;
import py.edu.facitec.psmsystem.entidad.Cobranza;
import py.edu.facitec.psmsystem.tabla.TablaInformeCobranzas;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaInformeCobranzas extends JDialog {
	private static final long serialVersionUID = 1L;

	private JFormattedTextField tfDesdeFecha, tfHastaFecha;
	private JButton btnProcesar, btnCancelar, btnImprimir;
	private JLabel lblTotalRegistros;
	private TablaInformeCobranzas tablaInformeCobranzas;
	private List<Cobranza> lista;
	private CobranzaDao dao;
	private JTable table;
	private JComboBox<String> cbDetallado;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaInformeCobranzas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInformeCobranzas.class.getResource("/py/edu/facitec/psmsystem/img/icono.png")));
		setTitle("Informe de Cobranzas");
		setBounds(100, 100, 690, 415);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);

		tablaInformeCobranzas = new TablaInformeCobranzas();

		JLabel lblDesdeFecha = new JLabel("Desde Fecha: ");
		lblDesdeFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesdeFecha.setBounds(247, 11, 90, 19);
		getContentPane().add(lblDesdeFecha);

		tfDesdeFecha = new JFormattedTextField(FechaUtil.getMascara());
		tfDesdeFecha.setBounds(336, 10, 70, 20);
		getContentPane().add(tfDesdeFecha);
		tfDesdeFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfHastaFecha.requestFocus();
					tfHastaFecha.selectAll();
				}
			}
		});
		tfDesdeFecha.setColumns(10);

		JLabel lblHastaFecha = new JLabel("Hasta Fecha: ");
		lblHastaFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHastaFecha.setBounds(247, 33, 90, 18);
		getContentPane().add(lblHastaFecha);

		tfHastaFecha = new JFormattedTextField(FechaUtil.getMascara());
		tfHastaFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					btnProcesar.requestFocus();
				}
			}
		});
		tfHastaFecha.setBounds(336, 33, 70, 20);
		getContentPane().add(tfHastaFecha);
		tfHastaFecha.setColumns(10);

		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(414, 32, 64, 18);
		getContentPane().add(lblTotal);

		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setBounds(480, 32, 34, 18);
		getContentPane().add(lblTotalRegistros);

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
				if (c == KeyEvent.VK_ENTER) {
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

		table = new JTable(tablaInformeCobranzas);
		scrollPane.setViewportView(table);

		cbDetallado = new JComboBox();
		cbDetallado.setToolTipText("");
		cbDetallado.setModel(new DefaultComboBoxModel(new String[] {"Sintetico", "Detallado"}));
		cbDetallado.setSelectedIndex(0);
		cbDetallado.setBounds(313, 340, 97, 20);
		getContentPane().add(cbDetallado);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBounds(420, 340, 122, 34);
		getContentPane().add(btnCancelar);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnImprimir.setBounds(552, 340, 122, 34);
		getContentPane().add(btnImprimir);
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

	private void cancelar() {
		tfDesdeFecha.setValue(null);
		tfHastaFecha.setValue(null);

		lista.removeAll(lista);
		tablaInformeCobranzas.setLista(lista);
		tablaInformeCobranzas.fireTableDataChanged();
		tfDesdeFecha.requestFocus();
		lblTotalRegistros.setText(lista.size()+"");
	}

	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (cbDetallado.getSelectedIndex() == 0) {
			String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
					+ "Total registros: "+lblTotalRegistros.getText()+"";
			Map<String, Object> map = new HashMap<>();
			map.put("filtros", filtros);
			map.put("codigo", ""+((Math.random()*9999)+1000));
			ReportesUtil.GenerarInforme(lista, map, "InformeCobranzas");
		} else {
			String filtros = "Fecha: "+tfDesdeFecha.getText()+" "+"hasta"+" "+tfHastaFecha.getText()+" | "
					+ "Total registros: "+lblTotalRegistros.getText()+"";
			Map<String, Object> map = new HashMap<>();
			map.put("filtros", filtros);
			map.put("codigo", ""+((Math.random()*9999)+1000));
			ReportesUtil.GenerarInforme(lista, map, "InformeCobranzasDetallado");
		}
	}
}
