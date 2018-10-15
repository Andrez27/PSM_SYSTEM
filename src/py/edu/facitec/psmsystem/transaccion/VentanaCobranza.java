package py.edu.facitec.psmsystem.transaccion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.componente.VentanaGenerica;
import py.edu.facitec.psmsystem.controlador.VentanaCobranzaControlador;
import py.edu.facitec.psmsystem.util.FechaUtil;

public class VentanaCobranza extends VentanaGenerica{
	private JLabel lblFechaCobro;
	private JFormattedTextField tfFechaCobro;
	private JLabel lblMontoACobrar;
	private JLabel lblMontoAbonado;
	private JLabel lblVuelto;
	private NumberTextField tfAbonado;
	private NumberTextField tfMontoTotal;
	private NumberTextField tfVuelto;
	private JTextField tfBuscarDeuda;
	private JTable tablaDeuda;
	private JButton btnBuscarDeuda;
	private JButton btnRemover;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCobranza dialog = new VentanaCobranza();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setUpControlador() {
		new VentanaCobranzaControlador(this);
	}

	public VentanaCobranza() {
		gettBuscador().setLocation(420, 55);
		getMiToolBar().setBounds(10, 11, 400, 64);

		getMiToolBar().btncnGuardar.setText("Cobrar");
		getMiToolBar().btncnGuardar.setActionCommand("Guardar");
		getMiToolBar().btncnEliminar.setText("Anular");
		getMiToolBar().btncnEliminar.setActionCommand("Eliminar");
		getMiToolBar().btncnModificar.setVisible(false);
		
		gettBuscador().setToolTipText("Buscar por ID o cliente");
		setTitle("Formulario de Cobranza");
		setBounds(100, 100, 890, 500);

		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);
		
		JLabel lblGs = new JLabel("Gs.");
		lblGs.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGs.setVerticalAlignment(SwingConstants.BOTTOM);
		lblGs.setHorizontalAlignment(SwingConstants.LEFT);
		lblGs.setBounds(371, 268, 25, 25);
		getPanelFormulario().add(lblGs);

		JLabel label = new JLabel("Gs.");
		label.setBounds(177, 317, 25, 14);
		getPanelFormulario().add(label);

		JLabel label_1 = new JLabel("Gs.");
		label_1.setBounds(345, 319, 25, 14);
		getPanelFormulario().add(label_1);

		JLabel lblFechaCobro = new JLabel("Fecha Cobro:");
		lblFechaCobro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaCobro.setBounds(2, 268, 82, 14);
		getPanelFormulario().add(lblFechaCobro);

		JLabel lblMontoACobrar = new JLabel("Monto a Cobrar:");
		lblMontoACobrar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoACobrar.setBounds(158, 268, 94, 14);
		getPanelFormulario().add(lblMontoACobrar);

		JLabel lblMontoAbonado = new JLabel("Abonado:");
		lblMontoAbonado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoAbonado.setBounds(20, 314, 64, 14);
		getPanelFormulario().add(lblMontoAbonado);

		JLabel lblVuelto = new JLabel("Vuelto:");
		lblVuelto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVuelto.setBounds(201, 314, 52, 14);
		getPanelFormulario().add(lblVuelto);

		tfBuscarDeuda = new JTextField();
		tfBuscarDeuda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfBuscarDeuda.requestFocus();
					tfBuscarDeuda.selectAll();
				}
			}
		});
		tfBuscarDeuda.setToolTipText("Ingrese n\u00FAmero de deuda");
		tfBuscarDeuda.setEditable(false);
		tfBuscarDeuda.setEnabled(false);
		tfBuscarDeuda.setBounds(87, 10, 44, 20);
		getPanelFormulario().add(tfBuscarDeuda);
		tfBuscarDeuda.setColumns(10);

		tfFechaCobro = new JFormattedTextField(FechaUtil.getMascara());
		tfFechaCobro.setEditable(false);
		tfFechaCobro.setBounds(89, 265, 68, 20);
		getPanelFormulario().add(tfFechaCobro);
		tfFechaCobro.setColumns(10);

		tfMontoTotal = new NumberTextField();
		tfMontoTotal.setEditable(false);
		tfMontoTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMontoTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		tfMontoTotal.setBounds(253, 265, 116, 28);
		getPanelFormulario().add(tfMontoTotal);

		tfAbonado = new NumberTextField();
		tfAbonado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					if (tfAbonado.getValue() >= tfMontoTotal.getValue()) {
						calcularVuelto();
					} else {
						JOptionPane.showMessageDialog(null, "Valor abonado es menor que el monto de la deuda");
					}
				}
			}
		});
		tfAbonado.setEditable(false);
		tfAbonado.setEnabled(false);
		tfAbonado.setHorizontalAlignment(SwingConstants.RIGHT);
		tfAbonado.setBounds(89, 311, 87, 20);
		getPanelFormulario().add(tfAbonado);

		tfVuelto = new NumberTextField();
		tfVuelto.setEditable(false);
		tfVuelto.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVuelto.setBounds(256, 312, 87, 20);
		getPanelFormulario().add(tfVuelto);
		
		JScrollPane scrollPane = new JScrollPane();
		tablaDeuda = new JTable();
		scrollPane.setViewportView(tablaDeuda);
		scrollPane.setBounds(10, 36, 381, 222);
		getPanelFormulario().add(scrollPane);
		
		JLabel lblNroDeuda = new JLabel("Nro. Deuda:");
		lblNroDeuda.setBounds(14, 14, 70, 14);
		getPanelFormulario().add(lblNroDeuda);
		
		btnBuscarDeuda = new JButton("Agregar");
		btnBuscarDeuda.setEnabled(false);
		btnBuscarDeuda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfAbonado.requestFocus();
					tfAbonado.selectAll();
				}
			}
		});
		btnBuscarDeuda.setToolTipText("Buscar deuda");
		btnBuscarDeuda.setActionCommand("BuscarDeuda");
		btnBuscarDeuda.setBounds(192, 9, 94, 23);
		getPanelFormulario().add(btnBuscarDeuda);
		
		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		btnRemover.setActionCommand("RemoverDeuda");
		btnRemover.setBounds(296, 9, 94, 23);
		getPanelFormulario().add(btnRemover);

	}
	
//-----------------------------METODO PARA CALCULAR EL VUELTO DE LA COBRANZA--------------------------------------------------------
	private void calcularVuelto() {
		tfVuelto.setText((Double.parseDouble(tfAbonado.getText()) - Double.parseDouble(tfMontoTotal.getText()))+"");
	}
	
	
	public JTable getTablaCobranza() {
		return getTable();
	}
	public JLabel getLblFechaCobro() {
		return lblFechaCobro;
	}
	public JFormattedTextField getTfFechaCobro() {
		return tfFechaCobro;
	}
	public JLabel getLblMontoACobrar() {
		return lblMontoACobrar;
	}
	public JLabel getLblMontoAbonado() {
		return lblMontoAbonado;
	}
	public JLabel getLblVuelto() {
		return lblVuelto;
	}
	public NumberTextField getTfAbonado() {
		return tfAbonado;
	}
	public NumberTextField getTfMontoTotal() {
		return tfMontoTotal;
	}
	public NumberTextField getTfVuelto() {
		return tfVuelto;
	}
	public JTextField getTfBuscarDeuda() {
		return tfBuscarDeuda;
	}
	public JButton getBtnRemover() {
		return btnRemover;
	}
	public void setBtnRemover(JButton btnRemover) {
		this.btnRemover = btnRemover;
	}
	public JTable getTablaDeuda() {
		return tablaDeuda;
	}
	public void setTablaDeuda(JTable tablaDeuda) {
		this.tablaDeuda = tablaDeuda;
	}
	public JButton getBtnBuscarDeuda() {
		return btnBuscarDeuda;
	}
	public void setBtnBuscarDeuda(JButton btnBuscarDeuda) {
		this.btnBuscarDeuda = btnBuscarDeuda;
	}
	public void setLblFechaCobro(JLabel lblFechaCobro) {
		this.lblFechaCobro = lblFechaCobro;
	}
	public void setTfFechaCobro(JFormattedTextField tfFechaCobro) {
		this.tfFechaCobro = tfFechaCobro;
	}
	public void setLblMontoACobrar(JLabel lblMontoACobrar) {
		this.lblMontoACobrar = lblMontoACobrar;
	}
	public void setLblMontoAbonado(JLabel lblMontoAbonado) {
		this.lblMontoAbonado = lblMontoAbonado;
	}
	public void setLblVuelto(JLabel lblVuelto) {
		this.lblVuelto = lblVuelto;
	}
	public void setTfAbonado(NumberTextField tfAbonado) {
		this.tfAbonado = tfAbonado;
	}
	public void setTfMontoTotal(NumberTextField tfMontoTotal) {
		this.tfMontoTotal = tfMontoTotal;
	}
	public void setTfVuelto(NumberTextField tfVuelto) {
		this.tfVuelto = tfVuelto;
	}
	public void setTfBuscarDeuda(JTextField tfBuscarDeuda) {
		this.tfBuscarDeuda = tfBuscarDeuda;
	}

}
