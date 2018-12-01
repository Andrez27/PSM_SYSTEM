package py.edu.facitec.psmsystem.abm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.app.VentanaPrincipal;
import py.edu.facitec.psmsystem.dao.ConfiguracionDao;
import py.edu.facitec.psmsystem.entidad.Configuracion;

public class VentanaConfiguracion extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField tfNombre;
	private JTextField tfRuc;
	private JTextField tfTelefono;
	private JTextField tfEmail;
	private Configuracion configuracion;
	private ConfiguracionDao dao;
	private List<Configuracion> configuraciones;
	private JButton btnActualizar;
	public NumberTextField tfInteres;
	private JButton btnCancelar;
	private JButton btnBorrar;
	private List<Configuracion> campos;
	private JLabel lblValidarTelefono;
	private JLabel lblValidarRuc;
	private JLabel lblValidarNombre;

	protected void setUpControlador() {
	}

	public VentanaConfiguracion() {
		setTitle("Configuración");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaConfiguracion.class.getResource("/img/icono.png")));
		setBounds(100, 100, 396, 276);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);

		JLabel lblNombre = new JLabel("Nombre o raz\u00F3n social : ");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(10, 14, 176, 20);
		getContentPane().add(lblNombre);

		JLabel lblRuc = new JLabel("R.U.C. : ");
		lblRuc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRuc.setBounds(10, 48, 176, 20);
		getContentPane().add(lblRuc);

		JLabel lblTelefono = new JLabel("Tel\u00E9fono : ");
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefono.setBounds(10, 82, 176, 20);
		getContentPane().add(lblTelefono);

		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(10, 116, 176, 20);
		getContentPane().add(lblEmail);

		JLabel lblTazaInteres = new JLabel("Taza de  inter\u00E9s : ");
		lblTazaInteres.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTazaInteres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTazaInteres.setBounds(10, 150, 176, 20);
		getContentPane().add(lblTazaInteres);

		JLabel label = new JLabel("%");
		label.setBounds(230, 155, 27, 14);
		getContentPane().add(label);

		tfNombre = new JTextField();
		tfNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarNombre.setVisible(false);
			}
		});
		tfNombre.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfRuc.requestFocus();
					tfRuc.selectAll();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfNombre.getText().length() == 40) {
					lblValidarNombre.setVisible(true);
					e.consume();
				}
			}
		});
		tfNombre.setBounds(185, 14, 195, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);

		tfRuc = new JTextField();
		tfRuc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarRuc.setVisible(false);
			}
		});
		tfRuc.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int k = (int) e.getKeyChar();
				if (!Character.isDigit(c) & c != e.VK_ENTER & c != e.VK_BACK_SPACE & k !=45) {
					e.consume();
					lblValidarRuc.setVisible(true);
				}else{
					lblValidarRuc.setVisible(false);
				}
				if (tfRuc.getText().length() == 20) {
					e.consume();
				}
			}
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfTelefono.requestFocus();
					tfTelefono.selectAll();
				}
			}
		});
		tfRuc.setBounds(185, 48, 195, 20);
		getContentPane().add(tfRuc);
		tfRuc.setColumns(10);

		tfTelefono = new JTextField();
		tfTelefono.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarTelefono.setVisible(false);
			}
		});
		tfTelefono.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int k = (int) e.getKeyChar();										//32= ESPACIO, 40= (, 41= ), 43= +, 45= -
				if (!Character.isDigit(c) & c != e.VK_ENTER & c != e.VK_BACK_SPACE & k !=32 & k !=43 & k !=40 & k !=41 & k !=45) {
					e.consume();
					lblValidarTelefono.setVisible(true);
				}else{
					lblValidarTelefono.setVisible(false);
				}
				if (tfTelefono.getText().length() == 20) {
					e.consume();
				}
			}
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfEmail.requestFocus();
					tfEmail.selectAll();
				}

			}
		});
		tfTelefono.setBounds(185, 82, 195, 20);
		getContentPane().add(tfTelefono);
		tfTelefono.setColumns(10);

		tfEmail = new JTextField();
		tfEmail.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfInteres.requestFocus();
					tfInteres.selectAll();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfEmail.getText().length() == 50) {
					e.consume();
				}
			}
		});
		tfEmail.setText("");
		tfEmail.setBounds(185, 116, 195, 20);
		getContentPane().add(tfEmail);
		tfEmail.setColumns(10);

		tfInteres = new NumberTextField();
		tfInteres.setHorizontalAlignment(SwingConstants.RIGHT);
		tfInteres.setBounds(185, 150, 44, 20);
		tfInteres.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					btnActualizar.requestFocus();
				}
			}
		});
		getContentPane().add(tfInteres);
		tfInteres.setColumns(10);


		btnActualizar = new JButton("Actualizar");
		btnActualizar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, 
						"Estas seguro que deseas actualizar los datos de la empresa?",
						"Atención!",
						JOptionPane.YES_NO_OPTION);
				if (respuesta==JOptionPane.YES_OPTION) {
					actualizar();
				}
			}
		});
		btnActualizar.setBounds(24, 194, 97, 34);
		getContentPane().add(btnActualizar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vaciarFormulario();
			}
		});
		btnBorrar.setBounds(145, 194, 97, 34);
		getContentPane().add(btnBorrar);


		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(266, 194, 97, 34);
		getContentPane().add(btnCancelar);

		lblValidarTelefono = new JLabel("*Solo n\u00FAmeros");
		lblValidarTelefono.setVisible(false);
		lblValidarTelefono.setForeground(Color.RED);
		lblValidarTelefono.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValidarTelefono.setBounds(185, 101, 178, 14);
		getContentPane().add(lblValidarTelefono);

		lblValidarRuc = new JLabel("*Caracter no permitido");
		lblValidarRuc.setVisible(false);
		lblValidarRuc.setForeground(Color.RED);
		lblValidarRuc.setBounds(184, 68, 154, 15);
		getContentPane().add(lblValidarRuc);

		lblValidarNombre = new JLabel("*No se permite m\u00E1s caracteres");
		lblValidarNombre.setVisible(false);
		lblValidarNombre.setForeground(Color.RED);
		lblValidarNombre.setBounds(185, 32, 195, 20);
		getContentPane().add(lblValidarNombre);

		datosActuales();

	}

	//-------------------------FIN DEL CONSTRUCTOR--------------------------------
	private void cargarDatos() {
		configuracion = new Configuracion();
		configuracion.setId(1);
		configuracion.setNombre(tfNombre.getText());
		configuracion.setRuc(tfRuc.getText());
		configuracion.setTelefono(tfTelefono.getText());
		configuracion.setEmail(tfEmail.getText());
		configuracion.setInteres(Double.parseDouble(tfInteres.getText()));
	}

	private void actualizar(){
		if(validarCampos()) {
			return;
		}
		cargarDatos();
		dao = new ConfiguracionDao();
		dao.insertarOModificar(configuracion);
		try {
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			dao.rollback();
		}
		actualizarPantalla();
		dispose();
	}

	private void actualizarPantalla(){
		dao = new ConfiguracionDao();
		configuracion = dao.recuperarPorId(1);
		VentanaPrincipal.lblNombre.setText(configuracion.getNombre());
		VentanaPrincipal.lblRuc.setText(configuracion.getRuc());
		VentanaPrincipal.lblTelefono.setText(configuracion.getTelefono());
		VentanaPrincipal.lblEmail.setText(configuracion.getEmail());
	}

	private void datosActuales() {
		dao = new ConfiguracionDao();
		configuraciones = dao.recuperarTodo();
		if (configuraciones.size()==0) return;
		tfNombre.setText(configuraciones.get(0).getNombre());
		tfRuc.setText(configuraciones.get(0).getRuc());
		tfTelefono.setText(configuraciones.get(0).getTelefono());
		tfEmail.setText(configuraciones.get(0).getEmail());
		tfInteres.setText(configuraciones.get(0).getInteres()+"");
	}

	private void vaciarFormulario() {
		dao = new ConfiguracionDao();
		tfNombre.setText("");
		tfRuc.setText("");
		tfTelefono.setText("");
		tfEmail.setText("");
		tfInteres.setText("");
	}

	//-----------------------------------VALIDAR CAMPOS-------------------------------------
	private boolean validarCampos() {
		dao = new ConfiguracionDao();
		campos = dao.recuperarTodo();
		if (tfInteres.getText().isEmpty() || tfInteres.getValue() < 1){
			JOptionPane.showMessageDialog(null, "Informe valor del \"Interés\" \nInterés mínimo es de 1%", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			tfInteres.requestFocus();
			tfInteres.setValue((double) 1);
			tfInteres.selectAll();
			return true;
		}
		return false;
	}

	//-----------------------------------INICIALIZAR BASE DE DATOS-------------------------------------
	public void inicializarConfiguracion() {
		String tabla = "tb_configuracion";
		dao.eliminarTodos(tabla);
		try {
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
		}
		VentanaPrincipal.lblNombre.setText("");
		VentanaPrincipal.lblRuc.setText("");
		VentanaPrincipal.lblTelefono.setText("");
		VentanaPrincipal.lblEmail.setText("");

	}

	public JTextField gettfNombre() {
		return tfNombre;
	}
	public JTextField gettfRuc() {
		return tfRuc;
	}
	public JTextField gettfTelefono() {
		return tfTelefono;
	}
	public JTextField gettfEmail() {
		return tfEmail;
	}
	public JTextField getTfNombre() {
		return tfNombre;
	}
	public JTextField getTfRuc() {
		return tfRuc;
	}
	public JTextField getTfTelefono() {
		return tfTelefono;
	}
	public JTextField getTfEmail() {
		return tfEmail;
	}
	public Configuracion getConfiguracion() {
		return configuracion;
	}
	public ConfiguracionDao getDao() {
		return dao;
	}
	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}
	public JButton getBtnActualizar() {
		return btnActualizar;
	}
	public NumberTextField getTfInteres() {
		return tfInteres;
	}
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	public JButton getBtnBorrar() {
		return btnBorrar;
	}
	public List<Configuracion> getCampos() {
		return campos;
	}
	public void setCampos(List<Configuracion> campos) {
		this.campos = campos;
	}
	public void setTfNombre(JTextField tfNombre) {
		this.tfNombre = tfNombre;
	}
	public void setTfRuc(JTextField tfRuc) {
		this.tfRuc = tfRuc;
	}
	public void setTfTelefono(JTextField tfTelefono) {
		this.tfTelefono = tfTelefono;
	}
	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}
	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}
	public void setDao(ConfiguracionDao dao) {
		this.dao = dao;
	}
	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	public void setBtnActualizar(JButton btnActualizar) {
		this.btnActualizar = btnActualizar;
	}
	public void setTfInteres(NumberTextField tfInteres) {
		this.tfInteres = tfInteres;
	}
	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	public void setBtnBorrar(JButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}
}
