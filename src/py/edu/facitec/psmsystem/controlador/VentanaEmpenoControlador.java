package py.edu.facitec.psmsystem.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import py.edu.facitec.psmsystem.buscador.BuscadorCliente;
import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.dao.EmpenoDao;
import py.edu.facitec.psmsystem.entidad.Cliente;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.entidad.Empeno;
import py.edu.facitec.psmsystem.entidad.Producto;
import py.edu.facitec.psmsystem.interfaz.AccionesABM;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorCliente;
import py.edu.facitec.psmsystem.tabla.TablaEmpeno;
import py.edu.facitec.psmsystem.transaccion.VentanaEmpeno;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaEmpenoControlador implements AccionesABM, KeyListener, ActionListener, InterfazBuscadorCliente{

	private VentanaEmpeno vEmpeno;
	private TablaEmpeno mtEmpeno;
	private EmpenoDao dao;
	private List<Empeno> lista;
	private String accion;
	private Empeno empeno;
	public Cliente cliente;
	public Producto producto;
	private List<DeudaCliente> deudaCliente;

	public VentanaEmpenoControlador(VentanaEmpeno vEmpeno) {
		this.vEmpeno = vEmpeno;

		this.vEmpeno.getMiToolBar().setAcciones(this);

		mtEmpeno = new TablaEmpeno();
		this.vEmpeno.getTable().setModel(mtEmpeno);

		estadoInicialCampos(true);

		dao = new EmpenoDao();

		recuperarTodo();

		setUpEvents();
	}

	private void setUpEvents() {
		vEmpeno.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarFormulario(vEmpeno.getTable().getSelectedRow());
			}
		});
		//PARA LLAMAR LA ACCION DEL BOTON BUSCAR CLIENTE
		vEmpeno.getBtnBuscarCliente().addActionListener(this);
		vEmpeno.getBtnBuscarCliente().setEnabled(false);

		//Evento del buscador
		vEmpeno.gettBuscador().addKeyListener(this);

	}

	private void recuperarTodo() {
		lista = dao.recuperarTodo();
		mtEmpeno.setLista(lista);
		mtEmpeno.fireTableDataChanged();

		TablaUtil.resizeTableColumnWidth(vEmpeno.getTable());

	}

	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(vEmpeno.gettBuscador().getText());
		mtEmpeno.setLista(lista);
		mtEmpeno.fireTableDataChanged();

		TablaUtil.resizeTableColumnWidth(vEmpeno.getTable());

	}

	private void cargarFormulario(int posicion) {
		if (posicion < 0) {
			return;
		}
		empeno = lista.get(posicion);

		vEmpeno.getTfId().setText(empeno.getId()+"");
		vEmpeno.getTfFechaRegistro().setValue(FechaUtil.convertirDateUtilAString(empeno.getFechaDia()));
		vEmpeno.getTfFechaVencimiento().setValue(FechaUtil.convertirDateUtilAString(empeno.getFechaVencimiento()));
		vEmpeno.getTfCliente().setText(empeno.getCliente().getNombre());
		vEmpeno.getTfObs().setText(empeno.getObservacion());

		vEmpeno.getTfCuota().setText(empeno.getDeudaClientes().size()+"");
		vEmpeno.getTfValorEmpeno().setText(empeno.getProducto().getPrecioCompra()+"");
		vEmpeno.getTfValorTotal().setValue(empeno.getValorTotal());
		vEmpeno.getTfDescripcion().setText(empeno.getProducto().getDescripcion());
		vEmpeno.getTfDetalle().setText(empeno.getProducto().getDetalle());
		vEmpeno.getCbEstado().setSelectedIndex(empeno.getEstado());

		this.cliente = empeno.getCliente();
		this.producto = empeno.getProducto();

		TablaUtil.resizeTableColumnWidth(vEmpeno.getTable());

		this.vEmpeno.getMiToolBar().estadoInicialToolBar(true,3);
		estadoInicialCampos(true);
		estadoInicialCampos2(false);
	}

	private void estadoInicialCampos(boolean b) {
		this.vEmpeno.getTfFechaRegistro().setEnabled(b);
		this.vEmpeno.getTfCliente().setEnabled(b);
		this.vEmpeno.getBtnBuscarCliente().setEnabled(b);
		this.vEmpeno.getTfCuota().setEnabled(b);
		this.vEmpeno.getTfObs().setEnabled(b);
		this.vEmpeno.getTfDescripcion().setEnabled(b);
		this.vEmpeno.getTfValorEmpeno().setEnabled(b);
		this.vEmpeno.getTfDetalle().setEnabled(b);

		this.vEmpeno.getTable().clearSelection();
	}
	private void estadoInicialCampos2(boolean b) {
		this.vEmpeno.getTfFechaRegistro().setEditable(b);
		this.vEmpeno.getTfCuota().setEditable(b);
		this.vEmpeno.getTfObs().setEditable(b);
		this.vEmpeno.getTfDescripcion().setEditable(b);
		this.vEmpeno.getTfValorEmpeno().setEditable(b);
		this.vEmpeno.getTfDetalle().setEditable(b);

		this.vEmpeno.getTable().clearSelection();
	}

	private void vaciarFormulario() {
		vEmpeno.getTfId().setText("");
		vEmpeno.getTfFechaRegistro().setValue(null);
		vEmpeno.getTfFechaVencimiento().setValue(null);
		vEmpeno.getTfCliente().setText("");
		vEmpeno.getTfValorTotal().setText("");
		vEmpeno.getTfCuota().setText("");
		vEmpeno.getTfObs().setText("");
		vEmpeno.getTfDescripcion().setText("");
		vEmpeno.getTfValorEmpeno().setText("");
		vEmpeno.getTfDetalle().setText("");

		vEmpeno.getCbEstado().setSelectedIndex(0);
	}

	@Override
	public void nuevo() {
		vaciarFormulario();
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		accion = "NUEVO";
		vEmpeno.getTfFechaRegistro().requestFocus();
		this.vEmpeno.getMiToolBar().estadoInicialToolBar(true,1);
		this.vEmpeno.getCbEstado().setEnabled(true);
		this.vEmpeno.getTable().setEnabled(false);
		vEmpeno.getTfFechaRegistro().setValue(FechaUtil.convertirDateUtilAString(new Date()));
		vEmpeno.getTfFechaRegistro().selectAll();

		vEmpeno.getTfId().setText(dao.recuperarSiguienteId()+"");

	}

	@Override
	public void modificar() {
	}

	@Override
	public void eliminar() {
		if (empeno==null) {		//VERIFICA QUE SE SELECCIONO UN REGISTRO
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
		} else {
			if (vEmpeno.getCbEstado().getSelectedIndex() != 3){
				int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea anular el empeño: \n" + empeno.getId() +" "+ empeno.getCliente().getNombre(),
						"ATENCIÓN", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					empeno.setEstado(3);
					producto.setEstado(3);
					try {
						dao.modificar(empeno);
						dao.commit();
						anularDeuda();
						recuperarTodo();
						this.vEmpeno.getMiToolBar().estadoInicialToolBar(true,2);
						vaciarFormulario();
					} catch (Exception e) {
						dao.rollback();
						JOptionPane.showMessageDialog(null, "No se pudo anular el empeno: \n" + empeno.getId() +" "+ empeno.getCliente().getNombre(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				} 
			} else {
				JOptionPane.showMessageDialog(null, "Empeño ya anulado");
				dao.rollback();
			}
		}
	}


	@Override
	public void guardar() {
		if (!validarCampos()) {
			return;
		}
		if (accion.equals("NUEVO")) {
			empeno = new Empeno();
			guardarProducto();
		}
		empeno.setFechaDia(FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaRegistro().getText()));
		empeno.setFechaVencimiento(FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaVencimiento().getText()));
		empeno.setValorTotal(vEmpeno.getTfValorTotal().getValue());
		empeno.setEstado(vEmpeno.getCbEstado().getSelectedIndex());
		empeno.setObservacion(vEmpeno.getTfObs().getText());
		empeno.setCliente(cliente);
		empeno.setProducto(producto);
		empeno.setDeudaClientes(deudaCliente);
		try {
			if(accion.equals("NUEVO")){
				dao.persistir(empeno);
				dao.commit();
				guardarDeuda();
			} else {
				dao.modificar(empeno);
				dao.commit();
			}

			this.vEmpeno.getMiToolBar().estadoInicialToolBar(true,2);
			estadoInicialCampos(false);
			estadoInicialCampos2(false);
			this.vEmpeno.getCbEstado().setEnabled(false);
			this.vEmpeno.getTable().setEnabled(true);
			recuperarTodo();
			vaciarFormulario();

		} catch (Exception e) {
			e.printStackTrace();
			dao.rollback();
			JOptionPane.showMessageDialog(null, "Se produjo un error al guardar", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void guardarProducto() {
		producto = new Producto();
		producto.setDescripcion(vEmpeno.getTfDescripcion().getText());
		producto.setDetalle(vEmpeno.getTfDetalle().getText());
		producto.setEstado(0);
		producto.setPrecioCompra(Double.parseDouble(vEmpeno.getTfValorEmpeno().getText()));
		producto.setPrecioVenta(Double.parseDouble(vEmpeno.getTfValorTotal().getText()));
		producto.setEmpeno(empeno);
	}

	private void guardarDeuda() {
		for (int i = 1; i <= Integer.parseInt(vEmpeno.getTfCuota().getText()); i++) {
			DeudaCliente deudaCliente = new DeudaCliente();
			deudaCliente.setFechaInicio(FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaRegistro().getText()));
			deudaCliente.setEmpeno(empeno);
			deudaCliente.setEstado(0);
			deudaCliente.setFechaVencimiento(FechaUtil.sumarMes(deudaCliente.getFechaInicio(), i));
			deudaCliente.setValor(Double.parseDouble(vEmpeno.getTfValorTotal().getText()) / Integer.parseInt(vEmpeno.getTfCuota().getText()));

			DeudaClienteDao deudaClienteDao = new DeudaClienteDao();
			try {
				deudaClienteDao.insertar(deudaCliente);
				deudaClienteDao.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void anularDeuda() {
		DeudaClienteDao deudaClienteDao;
		for (int i = 0; i < empeno.getDeudaClientes().size(); i++) {
			empeno.getDeudaClientes().get(i).setEstado(3);
			deudaClienteDao = new DeudaClienteDao();
			try {
				deudaClienteDao.modificar(empeno.getDeudaClientes().get(i));
				deudaClienteDao.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void cancelar() {
		this.vEmpeno.getMiToolBar().estadoInicialToolBar(true,2);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		this.vEmpeno.getCbEstado().setEnabled(false);
		vaciarFormulario();
		this.vEmpeno.getTable().setEnabled(true);
		this.vEmpeno.lblValidarCuota.setVisible(false);

	}

	//-----------------------------------VALIDAR CAMPOS OBLIGATORIOS------------------------------------------

	private boolean validarCampos() {
		if (vEmpeno.getTfCliente().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debes seleccionar un \"Cliente\" ");
			vEmpeno.getBtnBuscarCliente().requestFocus();
			return false;
		}
		if (Integer.parseInt(vEmpeno.getTfCuota().getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Informar cantidad de Cuotas");
			vEmpeno.getTfCuota().requestFocus();
			vEmpeno.getTfCuota().selectAll();

			return false;
		}
		if (vEmpeno.getTfDescripcion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe informar un producto");
			vEmpeno.getTfDescripcion().requestFocus();
			return false;
		}
		if (FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaVencimiento().getText()) == null) {
			JOptionPane.showMessageDialog(null, "Informar cantidad de Cuotas");
			vEmpeno.getTfCuota().requestFocus();
			return false;

		}
		//SI LA FECHA NO ES OBLIGATORIA. SI ES OBLIGATORIA SE OBVIA LA VALIDACION:
		//v!Empeno.gettFechaRegistro().getText().equals("__/__/____") &&
		if (!vEmpeno.getTfFechaRegistro().getText().equals("__/__/____") && FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaRegistro().getText()) == null) {
			JOptionPane.showMessageDialog(null, "Ingrese una fecha valida");
			vEmpeno.getTfFechaRegistro().requestFocus();
			return false;

		}
		return true;

	}

	//-----------------------------------INICIALIZAR BASE DE DATOS-------------------------------------
	public void inicializarEmpeno() {
		String tabla = "tb_empeno";
		dao.eliminarTodos(tabla);
		try {
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
		}
	}

	//---------------------METODO PARA GENERAR FECHA DE VENCIMIENTO A CADA MES----------------------------------------
	public void cargarVencimiento() {
		vEmpeno.getTfFechaVencimiento().setValue(FechaUtil.convertirDateUtilAString(FechaUtil.sumarMes(FechaUtil.convertirStringADateUtil(vEmpeno.getTfFechaRegistro().getText()), Integer.parseInt(vEmpeno.getTfCuota().getText()) )));
	}

	//---------------------------------ACCIÓNES DEL BOTON BUSCADOR------------------------------------------------------
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == vEmpeno.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}

	}

	public void abrirBuscarCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpController();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	@Override
	public void setCliente(Cliente cliente) {
		vEmpeno.getTfCliente().setText(cliente.getNombre());
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "BuscarCliente":
			abrirBuscarCliente();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
