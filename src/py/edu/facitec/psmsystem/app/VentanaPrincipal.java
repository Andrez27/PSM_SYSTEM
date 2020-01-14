package py.edu.facitec.psmsystem.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import py.edu.facitec.psmsystem.abm.VentanaCliente;
import py.edu.facitec.psmsystem.abm.VentanaConfiguracion;
import py.edu.facitec.psmsystem.abm.VentanaProducto;
import py.edu.facitec.psmsystem.componente.BotonIconoPrincipal;
import py.edu.facitec.psmsystem.componente.PanelFondo;
import py.edu.facitec.psmsystem.controlador.VentanaClienteControlador;
import py.edu.facitec.psmsystem.controlador.VentanaCobranzaControlador;
import py.edu.facitec.psmsystem.controlador.VentanaEmpenoControlador;
import py.edu.facitec.psmsystem.controlador.VentanaProductoControlador;
import py.edu.facitec.psmsystem.dao.ConfiguracionDao;
import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.entidad.Configuracion;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.informe.VentanaInformeCobranzas;
import py.edu.facitec.psmsystem.informe.VentanaInformeDeudas;
import py.edu.facitec.psmsystem.informe.VentanaInformeEmpenos;
import py.edu.facitec.psmsystem.informe.VentanaListadoClientes;
import py.edu.facitec.psmsystem.informe.VentanaListadoProductos;
import py.edu.facitec.psmsystem.transaccion.VentanaCobranza;
import py.edu.facitec.psmsystem.transaccion.VentanaEmpeno;

public class VentanaPrincipal extends JFrame implements KeyEventDispatcher {
	private static final long serialVersionUID = 1L;

	private PanelFondo contentPane;
	public static JLabel lblNombre, lblRuc, lblTelefono, lblEmail;
	private List<Configuracion> configuracion;
	private ConfiguracionDao configuracionDao;
	private JPanel jPanelConfig;

	public VentanaPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/icono.png")));
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

		setTitle("PSMSystem v1.9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 759);
		setLocationRelativeTo(this);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(this);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnProcesos = new JMenu("Procesos");
		menuBar.add(mnProcesos);

		JMenuItem mntmEmpeno = new JMenuItem("Empe\u00F1o");
		mntmEmpeno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfiguracionDao dao = new ConfiguracionDao();
				Configuracion a = new Configuracion();
				a = dao.recuperarPorId(1);
				if (a == null | a.getInteres() == 0) {
					JOptionPane.showMessageDialog(null, "Informe porcentaje % de interes");
					abrirFormularioConfiguracion();

				} else {
					abrirFormularioEmpeno();
				}
			}
		});
		mnProcesos.add(mntmEmpeno);

		JMenuItem mntmCobranza = new JMenuItem("Cobranza");
		mntmCobranza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioCobranza();
			}
		});

		JSeparator separator = new JSeparator();
		mnProcesos.add(separator);
		mnProcesos.add(mntmCobranza);

		JMenu mnTablas = new JMenu("Tablas");
		menuBar.add(mnTablas);

		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.setEnabled(true);
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioCliente();
			}
		});
		mnTablas.add(mntmCliente);

		JMenuItem mntmProducto = new JMenuItem("Producto");
		mntmProducto.setEnabled(true);
		mntmProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioProducto();
			}
		});

		JSeparator separator_1 = new JSeparator();
		mnTablas.add(separator_1);
		mnTablas.add(mntmProducto);

		JMenu mnInformes = new JMenu("Informes");
		menuBar.add(mnInformes);

		JMenuItem mntmListadoDeClientes = new JMenuItem("Listado de Clientes");
		mntmListadoDeClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirListadoClientes();
			}
		});
		mnInformes.add(mntmListadoDeClientes);

		JMenuItem mntmListadoDeProductos = new JMenuItem("Listado de Productos");
		mntmListadoDeProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirListadoProductos();
			}
		});
		mnInformes.add(mntmListadoDeProductos);

		JSeparator separator_3 = new JSeparator();
		mnInformes.add(separator_3);

		JMenuItem mntmInformeDeEmpenos = new JMenuItem("Informe de Empe\u00F1os");
		mntmInformeDeEmpenos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirInformeEmpenos();
			}
		});
		mnInformes.add(mntmInformeDeEmpenos);

		JMenuItem mntmInformeDeCobranzas = new JMenuItem("Informe de Cobranzas");
		mntmInformeDeCobranzas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirInformeCobranzas();
			}
		});
		mnInformes.add(mntmInformeDeCobranzas);

		JMenuItem mntmInformeDeDeudas = new JMenuItem("Informe de Deudas");
		mntmInformeDeDeudas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirInformeDeudas();
			}
		});
		mnInformes.add(mntmInformeDeDeudas);

		JMenu mnUtilidades = new JMenu("Utilidades");
		menuBar.add(mnUtilidades);

		JMenuItem mntmInicializacinDeDatos = new JMenuItem("Inicializaci\u00F3n de Datos");
		mntmInicializacinDeDatos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null,
						"Desea restablecer base de datos?\nSerán eliminado permanentemente todos los datos almacenados",
						"Atención!", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					inicializarBaseDeDatos();
					JOptionPane.showMessageDialog(null, "Base de datos restablecida!");
				} else {
					JOptionPane.showMessageDialog(null, "Operación cancelada", "Atención!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnUtilidades.add(mntmInicializacinDeDatos);

		JMenuItem mntmConfiguraciones = new JMenuItem("Configuraciones");
		mntmConfiguraciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioConfiguracion();
			}
		});

		JSeparator separator_2 = new JSeparator();
		mnUtilidades.add(separator_2);
		mnUtilidades.add(mntmConfiguraciones);

		contentPane = new PanelFondo();
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);
		toolBar.setForeground(new Color(240, 240, 240));
		toolBar.setEnabled(false);
		toolBar.setOpaque(false);
		toolBar.setOpaque(false);// transparente
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		BotonIconoPrincipal btncnEmpeno = new BotonIconoPrincipal();
		btncnEmpeno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfiguracionDao dao = new ConfiguracionDao();
				Configuracion a = new Configuracion();
				a = dao.recuperarPorId(1);
				if (a == null) {
					JOptionPane.showMessageDialog(null, "Informe porcentaje % de interés");
					abrirFormularioConfiguracion();

				} else {
					abrirFormularioEmpeno();
				}
			}
		});
		btncnEmpeno.setIcon(new ImageIcon(
				VentanaPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/64bits/empe\u00F1o.png")));
		btncnEmpeno.setText("Empe\u00F1o");
		toolBar.add(btncnEmpeno);

		BotonIconoPrincipal btncnCobranza = new BotonIconoPrincipal();
		btncnCobranza.setIcon(
				new ImageIcon(VentanaPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/64bits/cobranza.png")));
		btncnCobranza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioCobranza();
			}
		});

		btncnCobranza.setText("Cobranza");
		toolBar.add(btncnCobranza);

		BotonIconoPrincipal btncnCliente = new BotonIconoPrincipal();
		btncnCliente.setIcon(
				new ImageIcon(VentanaPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/64bits/cliente.png")));
		btncnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFormularioCliente();
			}
		});
		btncnCliente.setText("Cliente");
		toolBar.add(btncnCliente);

		BotonIconoPrincipal btncnSalir = new BotonIconoPrincipal();
		btncnSalir.setIcon(
				new ImageIcon(VentanaPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/64bits/salir.png")));
		btncnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btncnSalir.setText("Salir");
		toolBar.add(btncnSalir);

		jPanelConfig = new JPanel();
		jPanelConfig.setOpaque(false);
		contentPane.add(jPanelConfig, BorderLayout.SOUTH);
		GridBagLayout gbl_jPanelConfig = new GridBagLayout();
		gbl_jPanelConfig.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_jPanelConfig.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_jPanelConfig.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_jPanelConfig.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		jPanelConfig.setLayout(gbl_jPanelConfig);

		lblNombre = new JLabel("");
		lblNombre.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblNombre.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombre.setForeground(Color.DARK_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 33));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.gridheight = 2;
		gbc_lblNombre.insets = new Insets(0, 0, 8, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		jPanelConfig.add(lblNombre, gbc_lblNombre);

		lblRuc = new JLabel("");
		lblRuc.setHorizontalTextPosition(SwingConstants.LEFT);
		lblRuc.setHorizontalAlignment(SwingConstants.LEFT);
		lblRuc.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblRuc.setForeground(Color.DARK_GRAY);
		lblRuc.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 28));
		GridBagConstraints gbc_lblRuc = new GridBagConstraints();
		gbc_lblRuc.anchor = GridBagConstraints.WEST;
		gbc_lblRuc.gridheight = 2;
		gbc_lblRuc.insets = new Insets(0, 0, 5, 5);
		gbc_lblRuc.gridx = 1;
		gbc_lblRuc.gridy = 4;
		jPanelConfig.add(lblRuc, gbc_lblRuc);

		lblTelefono = new JLabel("");
		lblTelefono.setForeground(Color.DARK_GRAY);
		lblTelefono.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 28));
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.WEST;
		gbc_lblTelefono.gridheight = 2;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 6;
		jPanelConfig.add(lblTelefono, gbc_lblTelefono);

		lblEmail = new JLabel("");
		lblEmail.setForeground(Color.DARK_GRAY);
		lblEmail.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 28));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.gridheight = 2;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 8;
		jPanelConfig.add(lblEmail, gbc_lblEmail);

		JLabel label = new JLabel("Desarrollado Por: Andres Ramiro Garcia y Luis Enrique Arias ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.DARK_GRAY);
		label.setFont(new Font("Arial", Font.BOLD, 17));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.SOUTHEAST;
		gbc_label.gridx = 3;
		gbc_label.gridy = 10;
		jPanelConfig.add(label, gbc_label);

		cargarConfiguracion();
		verificarFechasVencimiento();

	}

	// ---------------FORMULARIOS-------------------------------------------------------------
	public void abrirFormularioConfiguracion() {
		VentanaConfiguracion ventanaConfiguracion = new VentanaConfiguracion();
		ventanaConfiguracion.setVisible(true);
	}

	private void abrirFormularioCliente() {
		VentanaCliente ventanaCliente = new VentanaCliente();
		ventanaCliente.setUpControlador();
		ventanaCliente.setVisible(true);
	}

	private void abrirFormularioProducto() {
		VentanaProducto ventanaProducto = new VentanaProducto();
		ventanaProducto.setUpControlador();
		ventanaProducto.setVisible(true);
	}

	private void abrirFormularioEmpeno() {
		VentanaEmpeno ventanaEmpeno = new VentanaEmpeno();
		ventanaEmpeno.setUpControlador();
		ventanaEmpeno.setVisible(true);
	}

	private void abrirFormularioCobranza() {
		VentanaCobranza ventanaCobranza = new VentanaCobranza();
		ventanaCobranza.setUpControlador();
		ventanaCobranza.setVisible(true);
	}

	private void abrirListadoClientes() {
		VentanaListadoClientes ventanaListadoClientes = new VentanaListadoClientes();
		ventanaListadoClientes.setVisible(true);
	}

	private void abrirListadoProductos() {
		VentanaListadoProductos ventanaListadoProductos = new VentanaListadoProductos();
		ventanaListadoProductos.setVisible(true);
	}

	private void abrirInformeEmpenos() {
		VentanaInformeEmpenos ventanaInformeEmpenos = new VentanaInformeEmpenos();
		ventanaInformeEmpenos.setVisible(true);
	}

	private void abrirInformeCobranzas() {
		VentanaInformeCobranzas ventanaInformeCobranzas = new VentanaInformeCobranzas();
		ventanaInformeCobranzas.setVisible(true);
	}

	private void abrirInformeDeudas() {
		VentanaInformeDeudas ventanaInformeDeudas = new VentanaInformeDeudas();
		ventanaInformeDeudas.setVisible(true);
	}

	// ----------------DESACTIVAR FALLA TECLA
	// F10------------------------------------------
	@SuppressWarnings("static-access")
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == e.KEY_PRESSED) {
			if (e.getKeyCode() == e.VK_F10 | e.getKeyCode() == e.VK_SPACE) {
				e.consume();
			}
		}
		return false;
	}

	// -----------------CONFIGURACION EMPRESA--------------------------------------
	public void cargarConfiguracion() {
		configuracionDao = new ConfiguracionDao();
		configuracion = configuracionDao.recuperarTodo();
		if (configuracion.size() == 0)
			return;
		lblNombre.setText(configuracion.get(0).getNombre());
		lblRuc.setText(configuracion.get(0).getRuc());
		lblTelefono.setText(configuracion.get(0).getTelefono());
		lblEmail.setText(configuracion.get(0).getEmail());
	}

	// ----------------VERIFICAR VENCIMIENTO
	// DEUDAS------------------------------------------
	private void verificarFechasVencimiento() {
		DeudaClienteDao deudaClienteDao = new DeudaClienteDao();
		List<DeudaCliente> deudas = deudaClienteDao.comprobarDeudasVencidas();
		for (int i = 0; i < deudas.size(); i++) {
			deudaClienteDao = new DeudaClienteDao();
			deudas.get(i).setEstado(1);
			try {
				deudaClienteDao.modificar(deudas.get(i));
				deudaClienteDao.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ----------------INICIALIZAR BASE DE
	// DATOS------------------------------------------
	public void inicializarBaseDeDatos() {
		VentanaCliente a = new VentanaCliente();
		VentanaClienteControlador ventanaClienteControlador = new VentanaClienteControlador(a);
		ventanaClienteControlador.inicializarCliente();

		VentanaProducto b = new VentanaProducto();
		VentanaProductoControlador ventanaProductoControlador = new VentanaProductoControlador(b);
		ventanaProductoControlador.inicializarProducto();

		VentanaConfiguracion ventanaConfiguracion = new VentanaConfiguracion();
		ventanaConfiguracion.inicializarConfiguracion();

		VentanaEmpeno d = new VentanaEmpeno();
		VentanaEmpenoControlador ventanaEmpenoControlador = new VentanaEmpenoControlador(d);
		ventanaEmpenoControlador.inicializarEmpeno();

		VentanaCobranza c = new VentanaCobranza();
		VentanaCobranzaControlador ventanaCobranzaControlador = new VentanaCobranzaControlador(c);
		ventanaCobranzaControlador.inicializarCobranza();
	}
}
