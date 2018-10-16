package py.edu.facitec.psmsystem.abm;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.app.VentanaPrincipal;
import py.edu.facitec.psmsystem.dao.ConfiguracionDao;
import py.edu.facitec.psmsystem.entidad.Configuracion;

public class VentanaConfiguracion extends JDialog {
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
	private JComponent ventanaConfiguracion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(GraphiteLookAndFeel.class.getName());
					VentanaConfiguracion dialog = new VentanaConfiguracion();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setUpControlador() {
	}

	public VentanaConfiguracion() {
		setTitle("Configuraci�n");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaConfiguracion.class.getResource("/img/ventanas/icono.png")));
		setBounds(100, 100, 396, 276);
		getContentPane().setLayout(null);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);

		JLabel lblNombre = new JLabel("Nombre o raz\u00F3n social       :");
		lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(10, 14, 176, 20);
		getContentPane().add(lblNombre);

		JLabel lblRuc = new JLabel("R.U.C.                               :");
		lblRuc.setHorizontalAlignment(SwingConstants.LEFT);
		lblRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRuc.setBounds(10, 48, 176, 20);
		getContentPane().add(lblRuc);

		JLabel lblTelefono = new JLabel("Tel\u00E9fono                            :");
		lblTelefono.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefono.setBounds(10, 82, 176, 20);
		getContentPane().add(lblTelefono);

		JLabel lblEmail = new JLabel("Email                                 :");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(10, 116, 176, 20);
		getContentPane().add(lblEmail);
		
		JLabel lblTazaInteres = new JLabel("Taza de  inter\u00E9s                 :");
		lblTazaInteres.setHorizontalAlignment(SwingConstants.LEFT);
		lblTazaInteres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTazaInteres.setBounds(10, 150, 176, 20);
		getContentPane().add(lblTazaInteres);
		
		JLabel label = new JLabel("%");
		label.setBounds(230, 155, 27, 14);
		getContentPane().add(label);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
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
					e.consume();
				}
			}
		});
		tfNombre.setBounds(185, 14, 195, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);

		tfRuc = new JTextField();
		tfRuc.addKeyListener(new KeyAdapter() {
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
		tfTelefono.addKeyListener(new KeyAdapter() {
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
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfInteres.requestFocus();
					tfInteres.selectAll();
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
						"Atenci�n!",
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
			JOptionPane.showMessageDialog(null, "Informe valor del \"Interes\" \nInteres m�nimo es de 1%");
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