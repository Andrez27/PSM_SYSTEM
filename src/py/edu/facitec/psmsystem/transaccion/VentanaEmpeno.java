package py.edu.facitec.psmsystem.transaccion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.componente.VentanaGenerica;
import py.edu.facitec.psmsystem.controlador.VentanaEmpenoControlador;
import py.edu.facitec.psmsystem.dao.ConfiguracionDao;
import py.edu.facitec.psmsystem.entidad.Configuracion;
import py.edu.facitec.psmsystem.util.FechaUtil;

public class VentanaEmpeno extends VentanaGenerica{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private JComboBox cbEstado;
	private JFormattedTextField tfFechaRegistro;
	private JFormattedTextField tfFechaVencimiento;
	private JTextField tfCliente;
	private JButton btnBuscarCliente;
	private NumberTextField tfValorEmpeno;
	private JTextPane tfObs;
	private JTextField tfDescripcion;
	private JTextPane tfDetalle;
	private NumberTextField tfValorTotal;
	private JTextField tfId;
	private NumberTextField tfCuota;
	public JLabel lblValidarCuota;

	public void setUpControlador() {
		new VentanaEmpenoControlador(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaEmpeno() {
		gettBuscador().setLocation(420, 55);
		getMiToolBar().setBounds(10, 11, 400, 64);

		getMiToolBar().btncnEliminar.setText("Anular");
		getMiToolBar().btncnEliminar.setActionCommand("Eliminar");
		getMiToolBar().btncnModificar.setVisible(false);
		setResizable(false);

		getPanelFormulario().setBounds(10, 81, 400, 369);
		gettBuscador().setToolTipText("Buscar por n\u00FAmero o cliente");
		setTitle("Formulario de Empe\u00F1o");
		setBounds(100, 100, 890, 664);

		setLocationRelativeTo(this);
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 461, 851, 14);
		getContentPane().add(separator);

		JLabel lblId = new JLabel("N\u00FAmero: ");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblId.setBounds(0, 34, 115, 20);
		getPanelFormulario().add(lblId);

		JLabel lblEstado = new JLabel("Estado: ");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEstado.setBounds(226, 36, 89, 20);
		getPanelFormulario().add(lblEstado);

		JLabel lblFechaDia = new JLabel("Fecha dia: ");
		lblFechaDia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaDia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFechaDia.setBounds(0, 86, 115, 20);
		getPanelFormulario().add(lblFechaDia);

		JLabel lblVencimiento = new JLabel("Vencimiento: ");
		lblVencimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVencimiento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVencimiento.setBounds(203, 86, 112, 20);
		getPanelFormulario().add(lblVencimiento);

		JLabel lblCliente = new JLabel("Cliente: ");
		lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCliente.setBounds(0, 139, 115, 20);
		getPanelFormulario().add(lblCliente);

		JLabel lblValorEmpeno = new JLabel("Vlr. empe\u00F1o: ");
		lblValorEmpeno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorEmpeno.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblValorEmpeno.setBounds(0, 192, 115, 20);
		getPanelFormulario().add(lblValorEmpeno);

		JLabel lblCuotas = new JLabel("Cuotas: ");
		lblCuotas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuotas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCuotas.setBounds(245, 192, 70, 20);
		getPanelFormulario().add(lblCuotas);

		JLabel lblGs = new JLabel("Gs.");
		lblGs.setBounds(227, 200, 25, 14);
		getPanelFormulario().add(lblGs);

		JLabel lblObservacion = new JLabel("Observaci\u00F3n: ");
		lblObservacion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblObservacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblObservacion.setBounds(0, 233, 115, 20);
		getPanelFormulario().add(lblObservacion);

		tfId = new JTextField();
		tfId.setHorizontalAlignment(SwingConstants.CENTER);
		tfId.setEditable(false);
		tfId.setBounds(115, 35, 73, 20);
		getPanelFormulario().add(tfId);
		tfId.setColumns(10);

		cbEstado = new JComboBox();
		cbEstado.setEnabled(false);
		cbEstado.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Vencido", "Cobrado", "Anulado"}));
		cbEstado.setToolTipText("");
		cbEstado.setBounds(316, 36, 74, 19);
		getPanelFormulario().add(cbEstado);

		tfFechaRegistro = new JFormattedTextField(FechaUtil.getMascara());
		tfFechaRegistro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					btnBuscarCliente.requestFocus();
				}
			}
		});
		tfFechaRegistro.setEditable(false);
		tfFechaRegistro.setEnabled(false);
		tfFechaRegistro.setBounds(115, 86, 73, 20);
		getPanelFormulario().add(tfFechaRegistro);

		tfFechaVencimiento = new JFormattedTextField(FechaUtil.getMascara());
		tfFechaVencimiento.setEditable(false);
		tfFechaVencimiento.setBounds(319, 88, 71, 20);
		getPanelFormulario().add(tfFechaVencimiento);

		tfCliente = new JTextField();
		tfCliente.setEditable(false);
		tfCliente.setEnabled(false);
		tfCliente.setBounds(115, 141, 237, 20);
		getPanelFormulario().add(tfCliente);
		tfCliente.setColumns(10);

		btnBuscarCliente = new JButton(". . .");
		btnBuscarCliente.setEnabled(false);
		btnBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfValorEmpeno.requestFocus();
					tfValorEmpeno.selectAll();
				}
			}
		});
		btnBuscarCliente.setToolTipText("Buscar cliente");
		btnBuscarCliente.setActionCommand("BuscarCliente");
		btnBuscarCliente.setBounds(352, 141, 38, 21);
		getPanelFormulario().add(btnBuscarCliente);

		tfValorEmpeno = new NumberTextField();
		tfValorEmpeno.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				calculoTotal();
			}
		});
		tfValorEmpeno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfCuota.requestFocus();
					tfCuota.selectAll();
				}
			}
		});
		tfValorEmpeno.setEditable(false);
		tfValorEmpeno.setEnabled(false);
		tfValorEmpeno.setHorizontalAlignment(SwingConstants.RIGHT);
		tfValorEmpeno.setBounds(115, 194, 109, 20);
		getPanelFormulario().add(tfValorEmpeno);

		tfCuota = new NumberTextField();
		tfCuota.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarCuota.setVisible(false);
				calculoTotal();
			}
		});
		tfCuota.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) & c!= KeyEvent.VK_ENTER & c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
					lblValidarCuota.setVisible(true);
				}else{
					lblValidarCuota.setVisible(false);
				}
				if (tfCuota.getText().length() == 20) {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfObs.requestFocus();
					tfObs.selectAll();
				}
			}
		});
		tfCuota.setEditable(false);
		tfCuota.setEnabled(false);
		tfCuota.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCuota.setBounds(316, 194, 74, 20);
		getPanelFormulario().add(tfCuota);
		tfCuota.setColumns(10);

		tfObs = new JTextPane();
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_TAB) {
					tfDescripcion.requestFocus();
					tfDescripcion.selectAll();
				}
			}
		});

		lblValidarCuota = new JLabel("*Solo n\u00FAmeros");
		lblValidarCuota.setVisible(false);
		lblValidarCuota.setForeground(Color.RED);
		lblValidarCuota.setBounds(316, 214, 84, 14);
		getPanelFormulario().add(lblValidarCuota);

		tfObs.setEditable(false);
		tfObs.setEnabled(false);
		tfObs.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tfObs.setBounds(118, 233, 272, 100);
		getPanelFormulario().add(tfObs);

		JLabel lblDatosDelProducto = new JLabel("Datos del producto");
		lblDatosDelProducto.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblDatosDelProducto.setBounds(10, 472, 259, 29);
		getContentPane().add(lblDatosDelProducto);

		JLabel lblDescripcion = new JLabel("Descripci\u00F3n: ");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcion.setBounds(0, 512, 125, 20);
		getContentPane().add(lblDescripcion);

		JLabel lblDetalle = new JLabel("Detalle: ");
		lblDetalle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDetalle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDetalle.setBounds(23, 554, 102, 20);
		getContentPane().add(lblDetalle);

		JLabel lblValorTotal = new JLabel("Valor total: ");
		lblValorTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblValorTotal.setBounds(583, 512, 102, 20);
		getContentPane().add(lblValorTotal);

		tfDescripcion = new JTextField();
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_ENTER) {
					tfDetalle.requestFocus();
					tfDetalle.selectAll();
				}
			}
		});

		JLabel label = new JLabel("Gs.");
		label.setBounds(818, 518, 25, 14);
		getContentPane().add(label);
		tfDescripcion.setEditable(false);
		tfDescripcion.setEnabled(false);
		tfDescripcion.setBounds(125, 512, 447, 20);
		getContentPane().add(tfDescripcion);
		tfDescripcion.setColumns(10);

		tfValorTotal = new NumberTextField();
		tfValorTotal.setEditable(false);
		tfValorTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfValorTotal.setBounds(686, 512, 129, 20);
		getContentPane().add(tfValorTotal);

		tfDetalle = new JTextPane();
		tfDetalle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tfDetalle.setEnabled(false);
		tfDetalle.setEditable(false);
		tfDetalle.setBounds(125, 554, 734, 59);
		tfObs.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(tfDetalle);

	}

	//-------------------------------------------METODO PARA CALCULAR VALOR TOTAL-------------------------------------------
	private void calculoTotal() {
		tfFechaVencimiento.setValue(FechaUtil.convertirDateUtilAString(FechaUtil.sumarMes(FechaUtil.convertirStringADateUtil(tfFechaRegistro.getText()), Integer.parseInt(tfCuota.getText()) )));
		ConfiguracionDao dao = new ConfiguracionDao();
		Configuracion configuracion = dao.recuperarPorId(1);
		Double interes = ((Double.parseDouble(tfValorEmpeno.getText())*configuracion.getInteres()) / 100) * tfCuota.getValue();
		tfValorTotal.setText((interes + Double.parseDouble(tfValorEmpeno.getText()))+"");
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getCbEstado() {
		return cbEstado;
	}
	@SuppressWarnings("rawtypes")
	public void setCbEstado(JComboBox cbEstado) {
		this.cbEstado = cbEstado;
	}
	public JFormattedTextField getTfFechaRegistro() {
		return tfFechaRegistro;
	}
	public void setTfFechaRegistro(JFormattedTextField tfFechaRegistro) {
		this.tfFechaRegistro = tfFechaRegistro;
	}
	public JFormattedTextField getTfFechaVencimiento() {
		return tfFechaVencimiento;
	}
	public void setTfFechaVencimiento(JFormattedTextField tfFechaVencimiento) {
		this.tfFechaVencimiento = tfFechaVencimiento;
	}
	public JTextField getTfCliente() {
		return tfCliente;
	}
	public void setTfCliente(JTextField tfCliente) {
		this.tfCliente = tfCliente;
	}
	public JButton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}
	public void setBtnBuscarCliente(JButton btnBuscarCliente) {
		this.btnBuscarCliente = btnBuscarCliente;
	}
	public NumberTextField getTfValorEmpeno() {
		return tfValorEmpeno;
	}
	public void setTfValorEmpeno(NumberTextField tfValorEmpeno) {
		this.tfValorEmpeno = tfValorEmpeno;
	}
	public JTextPane getTfObs() {
		return tfObs;
	}
	public void setTfObservacion(JTextPane tfObservacion) {
		this.tfObs = tfObservacion;
	}
	public JTextField getTfDescripcion() {
		return tfDescripcion;
	}
	public void setTfDescripcion(JTextField tfDescripcion) {
		this.tfDescripcion = tfDescripcion;
	}
	public JTextPane getTfDetalle() {
		return tfDetalle;
	}
	public void setTfDetalle(JTextPane tfDetalle) {
		this.tfDetalle = tfDetalle;
	}
	public NumberTextField getTfValorTotal() {
		return tfValorTotal;
	}
	public void setTfValorTotal(NumberTextField tfValorTotal) {
		this.tfValorTotal = tfValorTotal;
	}
	public JTextField getTfId() {
		return tfId;
	}
	public void setTfId(JTextField tfId) {
		this.tfId = tfId;
	}
	public NumberTextField getTfCuota() {
		return tfCuota;
	}
	public void setTfCuota(NumberTextField tfCuota) {
		this.tfCuota = tfCuota;
	}
	public JLabel getLblValidarCuota() {
		return lblValidarCuota;
	}
	public void setLblValidarCuota(JLabel lblValidarCuota) {
		this.lblValidarCuota = lblValidarCuota;
	}
}
