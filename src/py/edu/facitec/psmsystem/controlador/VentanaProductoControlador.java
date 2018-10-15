package py.edu.facitec.psmsystem.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import py.edu.facitec.psmsystem.abm.VentanaProducto;
import py.edu.facitec.psmsystem.dao.ProductoDao;
import py.edu.facitec.psmsystem.entidad.Producto;
import py.edu.facitec.psmsystem.interfaz.AccionesABM;
import py.edu.facitec.psmsystem.tabla.TablaProducto;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaProductoControlador implements AccionesABM, KeyListener, ActionListener {

	private VentanaProducto vProducto;
	private ProductoDao dao;
	private String accion;
	private Producto producto;
	private List<Producto> lista;
	private TablaProducto mtProducto;

	public VentanaProductoControlador(VentanaProducto vProducto) {
		this.vProducto = vProducto;

		// Pasamos las implementaciones de AccionesABM a MiToolbar
		this.vProducto.getMiToolBar().setAcciones(this);

		mtProducto = new TablaProducto();
		this.vProducto.getTable().setModel(mtProducto);

		estadoInicialCampos(true);

		dao = new ProductoDao();

		recuperarTodo();

		setUpEvents();
	}

	private void setUpEvents() {
		vProducto.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarFormulario(vProducto.getTable().getSelectedRow());
			}
		});
		vProducto.gettBuscador().addKeyListener(this);
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodo();
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();
		
		TablaUtil.resizeTableColumnWidth(vProducto.getTable());

	}

	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(vProducto.gettBuscador().getText());
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();
		
		TablaUtil.resizeTableColumnWidth(vProducto.getTable());

	}

	private void cargarFormulario(int posicion) {
		if (posicion < 0) {
			return;
		}
		producto = lista.get(posicion);	// Recupera un Producto por la posicion recibida
		
		vProducto.gettfDescripcion().setText(producto.getDescripcion());	// Carga los datos del Producto al formulario
		vProducto.gettfPrecioCompra().setValue(producto.getPrecioCompra());
		vProducto.gettfPrecioVenta().setValue(producto.getPrecioVenta());
		vProducto.gettfDetalle().setText(producto.getDetalle());
		vProducto.getCbEstado().setSelectedIndex(producto.getEstado());

		this.vProducto.getMiToolBar().estadoInicialToolBar(true,3);
		estadoInicialCampos(true);
		estadoInicialCampos2(false);

	}

	private void estadoInicialCampos(boolean b) {
		this.vProducto.gettfDescripcion().setEnabled(b);
		this.vProducto.gettfPrecioCompra().setEnabled(b);
		this.vProducto.gettfPrecioVenta().setEnabled(b);
		this.vProducto.gettfDetalle().setEnabled(b);

		this.vProducto.getTable().clearSelection();

	}

	private void estadoInicialCampos2(boolean b) {
		this.vProducto.gettfDescripcion().setEditable(b);
		this.vProducto.gettfPrecioCompra().setEditable(b);
		this.vProducto.gettfPrecioVenta().setEditable(b);
		this.vProducto.gettfDetalle().setEditable(b);

		this.vProducto.getTable().clearSelection();

	}

	private void vaciarFormulario() {
		vProducto.gettfDescripcion().setText("");
		vProducto.gettfPrecioCompra().setText("");
		vProducto.gettfPrecioVenta().setText("");
		vProducto.gettfDetalle().setText("");
		vProducto.getCbEstado().setSelectedIndex(0);

	}

	@Override
	public void nuevo() {
		vaciarFormulario();
		this.vProducto.getMiToolBar().estadoInicialToolBar(true,1);
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		accion = "NUEVO";
		vProducto.gettfDescripcion().requestFocus();
		this.vProducto.getCbEstado().setEnabled(true);
		this.vProducto.getTable().setEnabled(false);
	}

	@Override
	public void modificar() {
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		this.vProducto.getMiToolBar().estadoInicialToolBar(true,1);
		accion = "MODIFICAR";
		vProducto.gettfDescripcion().requestFocus();
		vProducto.gettfDescripcion().selectAll();
		this.vProducto.getCbEstado().setEnabled(true);
		this.vProducto.getTable().setEnabled(false);

	}

	@Override
	public void eliminar() {
		int posicion = vProducto.getTable().getSelectedRow();
		if (producto == null) {		//VERIFICA QUE SE SELECCIONO UN REGISTRO
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
		} else {
			int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el producto: \n" + producto.getDescripcion(), "ATENCIÓN", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				try {
					dao.eliminar(producto);
					dao.commit();
					recuperarTodo();
					this.vProducto.getMiToolBar().estadoInicialToolBar(true,2);
					vaciarFormulario();
				} catch (Exception e) {
					dao.rollback();
					JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro \n Producto vinculado a un empeño", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	@Override
	public void guardar() {
		if (!validarCampos())	{	// Si no se validan los campos para la ejecucion
			return;
		}
		if(!validarPrecio())	{	//VALIDAR PRECIO DE VENTA
			return;
		}
		if (accion.equals("NUEVO")) {// Si la accion es NUEVO (o ALTA) se instancia un nuevo Producto
			producto = new Producto();
		}
		// Se cargan los valores obtenidos del formulario al objeto Producto
		producto.setDescripcion(vProducto.gettfDescripcion().getText());
		producto.setDetalle(vProducto.gettfDetalle().getText());
		producto.setEstado(vProducto.getCbEstado().getSelectedIndex());

		producto.setPrecioCompra(vProducto.gettfPrecioCompra().getValue());

		producto.setPrecioVenta(vProducto.gettfPrecioVenta().getValue());

		try {
			if(accion.equals("NUEVO")){
				dao.insertar(producto);
			} else {
				dao.modificar(producto);
			}
			dao.commit();
			vaciarFormulario();
			this.vProducto.getMiToolBar().estadoInicialToolBar(true,2);
			estadoInicialCampos(false);
			estadoInicialCampos2(false);
			recuperarTodo();

		} catch (Exception e) {
			dao.rollback();
			JOptionPane.showMessageDialog(null, "Se produjo un error al guardar", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		this.vProducto.getCbEstado().setEnabled(false);
		this.vProducto.getLblVerificarPrecio().setVisible(false);
		this.vProducto.getTable().setEnabled(true);

	}	

	@Override
	public void cancelar() {
		this.vProducto.getMiToolBar().estadoInicialToolBar(true,2);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		this.vProducto.getCbEstado().setEnabled(false);
		vaciarFormulario();
		this.vProducto.getTable().setEnabled(true);


	}

//-----------------------------------VALIDAR CAMPOS OBLIGATORIOS------------------------------------------

	private boolean validarCampos() {
		if (vProducto.gettfDescripcion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El campo \"DESCRIPCIÓN\" es obligatorio!");
			vProducto.gettfDescripcion().requestFocus();
			return false;
		}

		return true;// Si no se encuentran problemas

	}

//----------------------------------PARA VALIDAR EL PRECIO DE VENTA---------------------------------

	private boolean validarPrecio() {
		double compra = Double.parseDouble(vProducto.gettfPrecioCompra().getText());
		double venta = Double.parseDouble(vProducto.gettfPrecioVenta().getText());
		if (venta < compra) {
			vProducto.getLblVerificarPrecio().setVisible(true);
			vProducto.gettfPrecioVenta().requestFocus();
			vProducto.gettfPrecioVenta().selectAll();
			return false;
		}
		return true;
	}

//-----------------------------------INICIALIZAR BASE DE DATOS-------------------------------------

	public void inicializarProducto() {
		String tabla = "tb_producto";
		dao.eliminarTodos(tabla);
		try {
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
		}
	}

//----------------------------------------------------------------------------------------------------------

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == vProducto.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
