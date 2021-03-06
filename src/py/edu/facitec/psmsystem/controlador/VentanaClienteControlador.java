package py.edu.facitec.psmsystem.controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import py.edu.facitec.psmsystem.abm.VentanaCliente;
import py.edu.facitec.psmsystem.dao.ClienteDao;
import py.edu.facitec.psmsystem.entidad.Cliente;
import py.edu.facitec.psmsystem.interfaz.AccionesABM;
import py.edu.facitec.psmsystem.tabla.TablaCliente;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaClienteControlador implements AccionesABM {

	private VentanaCliente vCliente;
	private String accion;
	private Cliente cliente;
	private ClienteDao dao;
	private List<Cliente> lista;
	private TablaCliente tCliente;

	public VentanaClienteControlador(VentanaCliente vCliente) {
		this.vCliente = vCliente;

		tCliente = new TablaCliente();

		this.vCliente.getMiToolBar().setAcciones(this);

		this.vCliente.getTable().setModel(tCliente);

		dao = new ClienteDao();

		estadoInicialCampos(true);

		recuperarTodo();

		setUpEvents();
	}

	private void setUpEvents() {
		vCliente.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarFormulario(vCliente.getTable().getSelectedRow());
			}
		});

		vCliente.gettBuscador().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getSource() == vCliente.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					recuperarPorFiltro();
				}
			}
		});
	}

	@Override
	public void nuevo() {
		accion = "NUEVO";
		vaciarFormulario();
		this.vCliente.getMiToolBar().estadoInicialToolBar(true, 1);
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		vCliente.gettfNombre().requestFocus();
		this.vCliente.getTable().setEnabled(false);
	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		this.vCliente.getMiToolBar().estadoInicialToolBar(true, 1);
		vCliente.gettfNombre().requestFocus();
		vCliente.gettfNombre().selectAll();
		this.vCliente.getTable().setEnabled(false);
	}

	@Override
	public void eliminar() {
		if (cliente == null) { // VERIFICA QUE SE SELECCIONE UN REGISTRO
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
		} else {
			int respuesta = JOptionPane.showConfirmDialog(null,
					"Esta seguro que desea eliminar el cliente: \n" + cliente.getId() + " - " + cliente.getNombre(),
					"Atenci�n!", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				try {
					dao.eliminar(cliente);
					dao.commit();
				} catch (Exception e) {
					dao.rollback();
					JOptionPane.showMessageDialog(null,
							"No se pudo eliminar el registro \nEl cliente esta vinculado a un empe�o", "Error!",
							JOptionPane.ERROR_MESSAGE);
				}
				recuperarTodo();
				this.vCliente.getMiToolBar().estadoInicialToolBar(true, 2);
				vaciarFormulario();
			}
		}
	}

	@Override
	public void guardar() {
		if (verificarDocumento()) {
			return;
		}

		if (!validarCampos()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			cliente = new Cliente();
		}
		cliente.setNombre(vCliente.gettfNombre().getText());
		cliente.setDocumento(vCliente.gettfDocumento().getText());
		cliente.setTelefono(vCliente.gettfTelefono().getText());
		cliente.setDomicilio(vCliente.gettfDomicilio().getText());
		cliente.setEmail(vCliente.gettfEmail().getText());
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(cliente);
			} else {
				dao.modificar(cliente);
			}
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
			JOptionPane.showMessageDialog(null, "Se produjo un error al guardar", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		vaciarFormulario();
		this.vCliente.getMiToolBar().estadoInicialToolBar(true, 2);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		recuperarTodo();
		this.vCliente.getTable().setEnabled(true);
	}

	@Override
	public void cancelar() {
		this.vCliente.getMiToolBar().estadoInicialToolBar(true, 2);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		vaciarFormulario();
		this.vCliente.getTable().setEnabled(true);
	}

//------------------------------------------------------------------------
	private void recuperarTodo() {
		lista = dao.recuperarTodo();
		tCliente.setLista(lista);
		tCliente.fireTableDataChanged();
		TablaUtil.resizeTableColumnWidth(vCliente.getTable());
	}

	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(vCliente.gettBuscador().getText());
		tCliente.setLista(lista);
		tCliente.fireTableDataChanged();
		TablaUtil.resizeTableColumnWidth(vCliente.getTable());
	}

	private void cargarFormulario(int posicion) {
		if (posicion < 0) {
			return;
		}
		cliente = lista.get(posicion);
		vCliente.gettfNombre().setText(cliente.getNombre());
		vCliente.gettfDocumento().setText(cliente.getDocumento());
		vCliente.gettfTelefono().setText(cliente.getTelefono());
		vCliente.gettfDomicilio().setText(cliente.getDomicilio());
		vCliente.gettfEmail().setText(cliente.getEmail());
		estadoInicialCampos(true);
		estadoInicialCampos2(false);
		this.vCliente.getMiToolBar().estadoInicialToolBar(true, 3);
	}

	private void estadoInicialCampos(boolean b) {
		this.vCliente.gettfNombre().setEnabled(b);
		this.vCliente.gettfDocumento().setEnabled(b);
		this.vCliente.gettfTelefono().setEnabled(b);
		this.vCliente.gettfDomicilio().setEnabled(b);
		this.vCliente.gettfEmail().setEnabled(b);
		this.vCliente.getTable().clearSelection();
		this.vCliente.getlblDocumentoDuplicado().setVisible(false);
		this.vCliente.getlblValidarNombre().setVisible(false);
		this.vCliente.getlblValidarTelefono().setVisible(false);
	}

	private void estadoInicialCampos2(boolean b) {
		this.vCliente.gettfNombre().setEditable(b);
		this.vCliente.gettfDocumento().setEditable(b);
		this.vCliente.gettfTelefono().setEditable(b);
		this.vCliente.gettfDomicilio().setEditable(b);
		this.vCliente.gettfEmail().setEditable(b);
		this.vCliente.getTable().clearSelection();
	}

	private void vaciarFormulario() {
		vCliente.gettfNombre().setText("");
		vCliente.gettfDocumento().setText("");
		vCliente.gettfTelefono().setText("");
		vCliente.gettfDomicilio().setText("");
		vCliente.gettfEmail().setText("");
		vCliente.getlblDocumentoDuplicado().setVisible(false);
	}

	// ------------------------VERIFICAR DOCUMENTO---------------------------
	private boolean verificarDocumento() {
		if (vCliente.gettfDocumento().getText().isEmpty()) {
			vCliente.getlblDocumentoDuplicado().setVisible(false);
			return false;
		}
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				boolean mismo = (!accion.equals("NUEVO") && cliente.getId() == lista.get(i).getId());
				if (vCliente.gettfDocumento().getText().equals(lista.get(i).getDocumento()) && !mismo) {
					vCliente.getlblDocumentoDuplicado().setVisible(true);
					vCliente.getlblDocumentoDuplicado().setText("*Documento duplicado");
					vCliente.gettfDocumento().requestFocus();
					vCliente.gettfDocumento().selectAll();
					return true;
				}
			}
			return false;
		}
		return false;
	}

	// --------------------------------VALIDAR
	// CAMPOS---------------------------------------
	private boolean validarCampos() {
		if (vCliente.gettfNombre().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo \"Nombre\" es obligatorio", "Atenci�n!",
					JOptionPane.INFORMATION_MESSAGE);
			vCliente.gettfNombre().requestFocus();
			return false;
		}
		if (vCliente.gettfDocumento().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo \"Documento\" es obligatorio", "Atenci�n!",
					JOptionPane.INFORMATION_MESSAGE);
			vCliente.gettfDocumento().requestFocus();
			return false;
		}
		if (vCliente.gettfTelefono().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo \"Telefono\" es obligatorio", "Atenci�n!",
					JOptionPane.INFORMATION_MESSAGE);
			vCliente.gettfTelefono().requestFocus();
			return false;
		}
		return true;
	}

	// ---------------------------------------------INICIALIZAR BASE DE
	// DATOS-------------------------------------
	public void inicializarCliente() {
		String tabla = "tb_cliente";
		dao.eliminarTodos(tabla);
		try {
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
		}
	}

}