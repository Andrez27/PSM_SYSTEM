package py.edu.facitec.psmsystem.abm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.com.cs.xnumberfield.component.NumberTextField;
import py.edu.facitec.psmsystem.componente.VentanaGenerica;
import py.edu.facitec.psmsystem.controlador.VentanaClienteControlador;

public class VentanaCliente extends VentanaGenerica {
	private JTextField tfNombre;
	private JTextField tfDocumento;
	private JTextField tfTelefono;
	private JTextField tfDomicilio;
	private JTextField tfEmail;
	private JLabel lblValidarNombre;
	private JLabel lblValidarTelefono;
	private JLabel lblDocumentoDuplicado;
	
	public void setUpControlador() {
		new VentanaClienteControlador(this);
	}

	public VentanaCliente() {
		table.setLocation(0, 53);
		table.setSize(441, 0);
		gettBuscador().setToolTipText("Buscar por ID, nombre o documento");
		getPanelFormulario().setSize(400, 370);
		getPanelFormulario().setLocation(10, 80);
		setTitle("Registro Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCliente.class.getResource("/img/ventanas/icono.png")));
		gettBuscador().setLocation(420, 55);
		getMiToolBar().setBounds(new Rectangle(10, 10, 400, 65));
		getMiToolBar().setOpaque(false);
		setLocationRelativeTo(this);
		setModal(true);
		setResizable(false);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(21, 43, 73, 14);
		getPanelFormulario().add(lblNombre);

		JLabel lblDocumento = new JLabel("Documento:");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDocumento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDocumento.setBounds(0, 100, 94, 14);
		getPanelFormulario().add(lblDocumento);

		JLabel lblTelefono = new JLabel("Tel\u00E9fono:");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefono.setBounds(21, 157, 73, 14);
		getPanelFormulario().add(lblTelefono);

		JLabel lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDomicilio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDomicilio.setBounds(21, 214, 73, 14);
		getPanelFormulario().add(lblDomicilio);

		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(21, 271, 73, 14);
		getPanelFormulario().add(lblEmail);

		tfNombre = new JTextField();
		tfNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarNombre.setVisible(false);
			}
		});
		tfNombre.setToolTipText("");
		tfNombre.setEnabled(false);
		tfNombre.setEditable(false);
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
					if (Character.isDigit(c) & c!= e.VK_ENTER) {
						e.consume();
						lblValidarNombre.setText("*Solo letras");
						lblValidarNombre.setVisible(true);
					}else{
						lblValidarNombre.setVisible(false);
					}
					if (tfNombre.getText().length() == 60) {
						e.consume();
						lblValidarNombre.setText("*No se permiten mas caracteres");
						lblValidarNombre.setVisible(true);
					}
				}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfDocumento.requestFocus();
					tfDocumento.selectAll();
				}
			}
		});
		tfNombre.setBounds(100, 37, 290, 20);
		getPanelFormulario().add(tfNombre);
		tfNombre.setColumns(10);

		tfDocumento = new JTextField();
		tfDocumento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblDocumentoDuplicado.setVisible(false);
			}
		});
		tfDocumento.setEditable(false);
		tfDocumento.setEnabled(false);
		tfDocumento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int k = (int) e.getKeyChar();
				if (!Character.isDigit(c) & c != e.VK_ENTER & c != e.VK_BACK_SPACE & k !=45) {
					e.consume();
					lblDocumentoDuplicado.setVisible(true);
				}else{
					lblDocumentoDuplicado.setVisible(false);
				}
				if (tfDocumento.getText().length() == 10) {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfTelefono.requestFocus();
					tfTelefono.selectAll();
				}
			}
		});
		tfDocumento.setBounds(100, 94, 105, 20);
		getPanelFormulario().add(tfDocumento);
		tfDocumento.setColumns(10);

		tfTelefono = new JTextField();
		tfTelefono.setEditable(false);
		tfTelefono.setEnabled(false);
		tfTelefono.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblValidarTelefono.setVisible(false);
			}
		});
		tfTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				int k = (int) e.getKeyChar();										//32= ESPACIO, 40= "(", 41= ")", 43= +, 45= -
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
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfDomicilio.requestFocus();
					tfDomicilio.selectAll();
				}
			}
		});
		tfTelefono.setBounds(100, 154, 139, 20);
		getPanelFormulario().add(tfTelefono);
		tfTelefono.setColumns(10);

		tfDomicilio = new JTextField();
		tfDomicilio.setEditable(false);
		tfDomicilio.setEnabled(false);
		tfDomicilio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == e.VK_ENTER) {
					tfEmail.requestFocus();
					tfEmail.selectAll();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfDomicilio.getText().length() == 60) {
					e.consume();
				}
			}
		});
		tfDomicilio.setColumns(10);
		tfDomicilio.setBounds(100, 212, 290, 20);
		getPanelFormulario().add(tfDomicilio);

		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setEnabled(false);
		tfEmail.setBounds(100, 270, 290, 20);
		getPanelFormulario().add(tfEmail);
		tfEmail.setColumns(10);

		lblValidarNombre = new JLabel("*Solo letras");
		lblValidarNombre.setVisible(false);
		lblValidarNombre.setForeground(Color.RED);
		lblValidarNombre.setBounds(93, 57, 290, 14);
		getPanelFormulario().add(lblValidarNombre);

		lblDocumentoDuplicado = new JLabel("*Solo n\u00FAmeros");
		lblDocumentoDuplicado.setBounds(93, 114, 158, 14);
		getPanelFormulario().add(lblDocumentoDuplicado);
		lblDocumentoDuplicado.setVisible(false);
		lblDocumentoDuplicado.setForeground(Color.RED);

		lblValidarTelefono = new JLabel("*Caracter no v\u00E1lido");
		lblValidarTelefono.setVisible(false);
		lblValidarTelefono.setForeground(Color.RED);
		lblValidarTelefono.setBounds(93, 173, 151, 14);
		getPanelFormulario().add(lblValidarTelefono);

	}

	public JTextField gettfNombre() {
		return tfNombre;
	}
	public void settfNombre(JTextField tfNombre) {
		this.tfNombre = tfNombre;
	}
	public JTextField gettfDocumento() {
		return tfDocumento;
	}
	public void settfDocumento(JTextField tfDocumento) {
		this.tfDocumento = tfDocumento;
	}
	public JTextField gettfTelefono() {
		return tfTelefono;
	}
	public void settfTelefono(JFormattedTextField tfTelefono) {
		this.tfTelefono = tfTelefono;
	}
	public JTextField gettfDomicilio() {
		return tfDomicilio;
	}
	public void setTfDomicilo(JTextField tfDomicilo) {
		this.tfDomicilio = tfDomicilo;
	}
	public JTextField gettfEmail() {
		return tfEmail;
	}
	public void settfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}
	public JLabel getlblValidarNombre() {
		return lblValidarNombre;
	}
	public void setlblValidarNombre(JLabel lblValidarNombre) {
		this.lblValidarNombre = lblValidarNombre;
	}
	public JLabel getlblValidarTelefono() {
		return lblValidarTelefono;
	}
	public void setlblValidarTelefono(JLabel lblValidarTelefono) {
		this.lblValidarTelefono = lblValidarTelefono;
	}
	public JLabel getlblDocumentoDuplicado() {
		return lblDocumentoDuplicado;
	}
	public void setlblDocumentoDuplicado(JLabel lblDocumentoDuplicado) {
		this.lblDocumentoDuplicado = lblDocumentoDuplicado;
	}

}