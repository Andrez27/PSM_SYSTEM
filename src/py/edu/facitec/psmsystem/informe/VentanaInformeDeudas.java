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

import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.tabla.TablaDeudaCliente;
import py.edu.facitec.psmsystem.util.ReportesUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaInformeDeudas extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField tfNombre;
	private JLabel lblNombre, lblTotalRegistros;
	private List<DeudaCliente> lista;
	private TablaDeudaCliente tablaInformeDeudas;
	private DeudaClienteDao dao;
	private JTable table;
	private JButton btnProcesar, btnCancelar, btnImprimir;
	private JComboBox<String> cbEstado;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaInformeDeudas() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaInformeDeudas.class.getResource("/py/edu/facitec/psmsystem/img/icono.png")));
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

		lblNombre = new JLabel("Nombre: ");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(11, 10, 70, 19);
		getContentPane().add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setBounds(80, 10, 304, 19);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);

		JLabel lblEstado = new JLabel("Estado: ");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setBounds(366, 10, 70, 14);
		getContentPane().add(lblEstado);

		cbEstado = new JComboBox();
		cbEstado.setToolTipText("");
		cbEstado.setModel(
				new DefaultComboBoxModel(new String[] { "Activo", "Vencido", "Cobrado", "Anulado", "Todos" }));
		cbEstado.setSelectedIndex(0);
		cbEstado.setBounds(439, 10, 92, 19);
		getContentPane().add(cbEstado);

		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(359, 32, 77, 18);
		getContentPane().add(lblTotal);

		lblTotalRegistros = new JLabel("");
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setBounds(439, 33, 34, 18);
		getContentPane().add(lblTotalRegistros);
		btnProcesar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnProcesar.setBounds(552, 10, 122, 34);
		getContentPane().add(btnProcesar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 61, 663, 268);
		getContentPane().add(scrollPane);

		table = new JTable(tablaInformeDeudas);
		scrollPane.setViewportView(table);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.setBounds(420, 341, 122, 34);
		getContentPane().add(btnCancelar);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnImprimir.setBounds(552, 341, 122, 34);
		getContentPane().add(btnImprimir);

	}

	// -------------------------------------METODOS------------------------------------------------
	private void procesar() {
		dao = new DeudaClienteDao();
		if (cbEstado.getSelectedIndex() == 4) {
			lista = dao.recuperarPorNombre(tfNombre.getText());
		} else {
			lista = dao.filtroInforme(tfNombre.getText(), cbEstado.getSelectedIndex());
		}
		tablaInformeDeudas.setLista(lista);
		tablaInformeDeudas.fireTableDataChanged();
		table.setModel(tablaInformeDeudas);
		TablaUtil.resizeTableColumnWidth(table);
		lblTotalRegistros.setText(lista.size() + "");
	}

	private void cancelar() {
		tfNombre.setText("");
		cbEstado.setSelectedIndex(0);

		lista.removeAll(lista);
		tablaInformeDeudas.setLista(lista);
		tablaInformeDeudas.fireTableDataChanged();

		tfNombre.requestFocus();
		lblTotalRegistros.setText(lista.size() + "");
	}

	private void imprimir() {
		if (lista == null) {
			JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "Atención!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filtros;
		if (tfNombre.getText().isEmpty()) {
			filtros = "Deudas de: |" + cbEstado.getSelectedItem().toString() + "| hasta la fecha";
		} else {
			filtros = "Deudas de: |" + tfNombre.getText() + "| con estado |" + cbEstado.getSelectedItem().toString()
					+ "| hasta la fecha";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("filtros", filtros);
		map.put("codigo", "" + ((Math.random() * 9999) + 1000));
		ReportesUtil.GenerarInforme(lista, map, "InformeDeudas");
	}
}
