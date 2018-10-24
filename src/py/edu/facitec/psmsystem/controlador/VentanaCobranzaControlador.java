package py.edu.facitec.psmsystem.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import py.edu.facitec.psmsystem.buscador.BuscadorDeudaCliente;
import py.edu.facitec.psmsystem.dao.CobranzaDao;
import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.entidad.Cobranza;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.interfaz.AccionesABM;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorDeudaCliente;
import py.edu.facitec.psmsystem.tabla.TablaCobranza;
import py.edu.facitec.psmsystem.tabla.TablaDeudaCliente;
import py.edu.facitec.psmsystem.transaccion.VentanaCobranza;
import py.edu.facitec.psmsystem.util.FechaUtil;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class VentanaCobranzaControlador implements AccionesABM, KeyListener, ActionListener, InterfazBuscadorDeudaCliente{
	private VentanaCobranza vCobranza;
	private CobranzaDao dao;
	private TablaCobranza mtCobranza;
	private TablaDeudaCliente mtDeudaCliente;
	private List<Cobranza> listaCobranza;
	private List<DeudaCliente> listaDeuda;
	private Cobranza cobranza;
	private String accion;

	public VentanaCobranzaControlador(VentanaCobranza vCobranza) {
		this.vCobranza = vCobranza;

		this.vCobranza.getMiToolBar().setAcciones(this);

		mtCobranza = new TablaCobranza();
		this.vCobranza.getTable().setModel(mtCobranza);

		mtDeudaCliente = new TablaDeudaCliente();
		this.vCobranza.getTablaDeuda().setModel(mtDeudaCliente);

		estadoInicialCampos(false);

		dao = new CobranzaDao();

		recuperarTodo();

		setUpEvents();
	}
	private void setUpEvents() {
		vCobranza.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarFormulario(vCobranza.getTable().getSelectedRow());
			}
		});
		vCobranza.getBtnBuscarDeuda().addActionListener(this);
		vCobranza.getBtnRemover().addActionListener(this);
		vCobranza.getBtnRemover().setEnabled(false);
		vCobranza.getBtnBuscarDeuda().setEnabled(false);
		vCobranza.gettBuscador().addKeyListener(this);
		vCobranza.getTfBuscarDeuda().addKeyListener(this);
	}
	private void recuperarTodo() {
		listaCobranza = dao.recuperarTodo();
		mtCobranza.setLista(listaCobranza);
		mtCobranza.fireTableDataChanged();
		//Ajusta el ancho de las columnas
		TablaUtil.resizeTableColumnWidth(vCobranza.getTablaCobranza());
	}
	private void recuperarPorFiltro() {
		listaCobranza = dao.recuperarPorFiltro(vCobranza.gettBuscador().getText());
		mtCobranza.setLista(listaCobranza);
		mtCobranza.fireTableDataChanged();
		//Ajusta el ancho de las columnas
		TablaUtil.resizeTableColumnWidth(vCobranza.getTablaCobranza());
	}
	private void buscarDeudaPorId() {
		DeudaClienteDao dao = new DeudaClienteDao();
		DeudaCliente deu = dao.recuperarPorId(Integer.parseInt(vCobranza.getTfBuscarDeuda().getText()));
		if (deu == null) {
			JOptionPane.showMessageDialog(null, "Deuda "+vCobranza.getTfBuscarDeuda().getText()+" no encontrada", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (deu.getEstado() > 2) {
			JOptionPane.showMessageDialog(null, "Deuda "+vCobranza.getTfBuscarDeuda().getText()+" no encontrada", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(verificarDeudaDuplicada(deu)) return;
		listaDeuda.add(deu);
		mtDeudaCliente.setLista(listaDeuda);
		mtDeudaCliente.fireTableDataChanged();
		TablaUtil.resizeTableColumnWidth(vCobranza.getTablaDeuda());
		vCobranza.getTfMontoTotal().setValue(vCobranza.getTfMontoTotal().getValue() + deu.getValor());
	}
	private void cargarFormulario(int posicion) {
		if (posicion < 0) {
			return;
		}
		cobranza = listaCobranza.get(posicion);
		vCobranza.getTfFechaCobro().setValue(FechaUtil.convertirDateUtilAString(cobranza.getFechaCobro()));
		vCobranza.getTfMontoTotal().setValue(cobranza.getValorCobro());
		listaDeuda = cobranza.getDeudaClientes();
		mtDeudaCliente.setLista(listaDeuda);
		mtDeudaCliente.fireTableDataChanged();
		TablaUtil.resizeTableColumnWidth(vCobranza.getTablaDeuda());
		this.vCobranza.getMiToolBar().estadoInicialToolBar(true,3);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
	}
	@Override
	public void nuevo() {	
		vaciarFormulario();
		estadoInicialCampos(true);
		estadoInicialCampos2(true);
		accion = "NUEVO";
		vCobranza.getTfBuscarDeuda().requestFocus();
		this.vCobranza.getMiToolBar().estadoInicialToolBar(true,1);
		this.vCobranza.getTable().setEnabled(false);
		vCobranza.getTfFechaCobro().setValue(FechaUtil.convertirDateUtilAString(new Date()));
		listaDeuda = new ArrayList<>();
	}
	@Override
	public void modificar() {}
	
	@Override
	public void eliminar() {
		if (cobranza==null) {//VERIFICA QUE SE SELECCIONO UN REGISTRO
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
		} else {
			if (cobranza.isEstado() == true){
				int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que deseas anular la cobranza \n" + cobranza.getId() +" de "+ cobranza.getDeudaClientes().get(0).getEmpeno().getCliente().getNombre(),
								"Atención!", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					cobranza.setEstado(false);
					try {
						dao.modificar(cobranza);
						dao.commit();
						anularDeudas();
						recuperarTodo();
						this.vCobranza.getMiToolBar().estadoInicialToolBar(true,2);
						vaciarFormulario();
					} catch (Exception e) {
						dao.rollback();
						JOptionPane.showMessageDialog(null, "No se pudo anular la cobranza \n" + cobranza.getId() +" "+ cobranza.getDeudaClientes().get(0).getEmpeno().getCliente().getNombre(),
						"Error!", JOptionPane.ERROR_MESSAGE);
					}
				} 
			} else {
				JOptionPane.showMessageDialog(null, "Cobranza ya anulada", "Atención!", JOptionPane.INFORMATION_MESSAGE);
				dao.rollback();
			}
		}
	}
	@Override
	public void guardar() {
		if (validarCampos())	{
			return;
		}
		if (accion.equals("NUEVO")) {
			cobranza = new Cobranza();
		}
		cobranza.setFechaCobro(FechaUtil.convertirStringADateUtil(vCobranza.getTfFechaCobro().getText()));
		cobranza.setValorCobro(vCobranza.getTfMontoTotal().getValue());
		cobranza.setEstado(true);
		try {
			dao.insertar(cobranza);
			dao.commit();
			modificarDeuda();
			this.vCobranza.getMiToolBar().estadoInicialToolBar(true,2);
			this.vCobranza.getTable().setEnabled(true);
			estadoInicialCampos(false);
			estadoInicialCampos2(false);
			recuperarTodo();
			vaciarFormulario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void modificarDeuda() {
		for (int i = 0; i < listaDeuda.size(); i++) {
			DeudaCliente deudaCliente = new DeudaCliente();
			deudaCliente = listaDeuda.get(i);
			deudaCliente.setCobranza(cobranza);
			deudaCliente.setEstado(2);
			DeudaClienteDao deudaClienteDao = new DeudaClienteDao();
			try {
				deudaClienteDao.modificar(deudaCliente);
				deudaClienteDao.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void cancelar() {
		this.vCobranza.getMiToolBar().estadoInicialToolBar(true,2);
		estadoInicialCampos(false);
		estadoInicialCampos2(false);
		vaciarFormulario();
		this.vCobranza.getTable().setEnabled(true);
	}
	public void estadoInicialCampos(boolean b) {
		this.vCobranza.getTfBuscarDeuda().setEditable(b);
		this.vCobranza.getTfAbonado().setEnabled(b);
		this.vCobranza.getBtnBuscarDeuda().setEnabled(b);
		this.vCobranza.getBtnRemover().setEnabled(b);
		this.vCobranza.getTable().clearSelection();
	}
	public void estadoInicialCampos2(boolean b) {
		this.vCobranza.getTfAbonado().setEditable(b);
		this.vCobranza.getTfBuscarDeuda().setEnabled(b);
		this.vCobranza.getTable().clearSelection();
	}
	public void vaciarFormulario() {
		vCobranza.getTfAbonado().setText("");
		vCobranza.getTfMontoTotal().setText("");
		vCobranza.getTfVuelto().setText("");
		vCobranza.getTfBuscarDeuda().setText("");
		vCobranza.getTfFechaCobro().setValue(null);
		mtDeudaCliente.setLista(new ArrayList<>());
		mtDeudaCliente.fireTableDataChanged();
	}
	public void removerDeuda() {
		vCobranza.getTfMontoTotal().setValue(vCobranza.getTfMontoTotal().getValue() - listaDeuda.get(vCobranza.getTablaDeuda().getSelectedRow()).getValor());
		listaDeuda.remove(vCobranza.getTablaDeuda().getSelectedRow());
		mtDeudaCliente.setLista(listaDeuda);
		mtDeudaCliente.fireTableDataChanged();
	}
	@Override
	public void setDeudaCliente(DeudaCliente deudaCliente) {
		if(verificarDeudaDuplicada(deudaCliente)) return;
		listaDeuda.add(deudaCliente);
		mtDeudaCliente.setLista(listaDeuda);
		mtDeudaCliente.fireTableDataChanged();
		TablaUtil.resizeTableColumnWidth(vCobranza.getTablaDeuda());
		vCobranza.getTfMontoTotal().setValue(vCobranza.getTfMontoTotal().getValue() + deudaCliente.getValor());
	}
	private boolean verificarDeudaDuplicada(DeudaCliente d) {
		if (listaDeuda == null) return false;
		for (int i = 0; i < listaDeuda.size(); i++) {
			if(listaDeuda.get(i).getId() == d.getId()) {
				JOptionPane.showMessageDialog(null, "La deuda "+d.getId()+" ya ha sido agregada", "Atención!", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}
	private void anularDeudas() {
		DeudaClienteDao deudaClienteDao;
		for (int i = 0; i < cobranza.getDeudaClientes().size(); i++) {
			cobranza.getDeudaClientes().get(i).setEstado(0);
			deudaClienteDao = new DeudaClienteDao();
			try {
				deudaClienteDao.modificar(cobranza.getDeudaClientes().get(i));
				deudaClienteDao.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//-----------------------------------INICIALIZAR BASE DE DATOS-------------------------------------
	public void inicializarCobranza() {
		String tabla = "tb_cobranza";
		dao.eliminarTodos(tabla);
		try {
			dao.commit();
		} catch (Exception e) {
			dao.rollback();
		}
	}
	//----------------------------------ACCION DE LOS BUSCADORES-------------------------------------------
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == vCobranza.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}
		if (e.getSource() == vCobranza.getTfBuscarDeuda() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			buscarDeudaPorId();
		}
	}
	public void abrirBuscarDeuda() {
		BuscadorDeudaCliente buscador = new BuscadorDeudaCliente();
		buscador.setUpController();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "BuscarDeuda":
			abrirBuscarDeuda();
			break;
		default:
			break;
		}
		switch (e.getActionCommand()) {
		case "RemoverDeuda":
			removerDeuda();
			break;
		default:
			break;
		}
	}
	//-----------------------------------VALIDAR CAMPOS OBLIGATORIOS------------------------------------------q
	private boolean validarCampos() {
		if (listaDeuda.size() == 0) {
			JOptionPane.showMessageDialog(null, "Selecione una deuda", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			vCobranza.getTfBuscarDeuda().requestFocus();
			return true;
		}
		if (vCobranza.getTfAbonado().getText().isEmpty() || vCobranza.getTfAbonado().getValue() == 0) {
			JOptionPane.showMessageDialog(null, "Informe monto a abonar", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			vCobranza.getTfAbonado().requestFocus();
			return true;
		}
		if (vCobranza.getTfAbonado().getValue() < vCobranza.getTfMontoTotal().getValue()) {
			JOptionPane.showMessageDialog(null, "Valor abonado es menor que el monto de la deuda", "Atención!", JOptionPane.INFORMATION_MESSAGE);
			vCobranza.getTfAbonado().requestFocus();
			vCobranza.getTfAbonado().selectAll();
			return true;
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}