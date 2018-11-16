package py.edu.facitec.psmsystem.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import py.edu.facitec.psmsystem.buscador.BuscadorDeudaCliente;
import py.edu.facitec.psmsystem.dao.DeudaClienteDao;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorDeudaCliente;
import py.edu.facitec.psmsystem.tabla.TablaDeudaCliente;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class BuscadorDeudaClienteControlador implements KeyListener{

	private BuscadorDeudaCliente bDeuda;
	private TablaDeudaCliente tDeuda;
	private List<DeudaCliente> lista;
	private DeudaClienteDao dao;
	private InterfazBuscadorDeudaCliente interfaz;

	public void setInterfaz(InterfazBuscadorDeudaCliente interfaz) {
		this.interfaz = interfaz;
	}

	public BuscadorDeudaClienteControlador (BuscadorDeudaCliente bDeuda) {
		this.bDeuda = bDeuda;

		tDeuda = new TablaDeudaCliente();

		this.bDeuda.getTable().setModel(tDeuda);

		dao = new DeudaClienteDao();

		setUpEvents();

		recuperarPorFiltro();
	}

	private void setUpEvents() {
		bDeuda.gettBuscador().addKeyListener(this);
		bDeuda.getTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					seleccionarRegistro(bDeuda.getTable().getSelectedRow());
				}
			}
		});
	}

	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bDeuda.gettBuscador().getText());
		tDeuda.setLista(lista);
		tDeuda.fireTableDataChanged();

		TablaUtil.resizeTableColumnWidth(bDeuda.getTable());
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		DeudaCliente deudaCliente = lista.get(posicion);
		interfaz.setDeudaCliente(deudaCliente);
		bDeuda.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == bDeuda.gettBuscador() && e.getKeyCode()==KeyEvent.VK_ENTER){
			recuperarPorFiltro();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

}
