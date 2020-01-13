package py.edu.facitec.psmsystem.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import py.edu.facitec.psmsystem.buscador.BuscadorCliente;
import py.edu.facitec.psmsystem.dao.ClienteDao;
import py.edu.facitec.psmsystem.entidad.Cliente;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorCliente;
import py.edu.facitec.psmsystem.tabla.TablaCliente;
import py.edu.facitec.psmsystem.util.TablaUtil;

public class BuscadorClienteControlador implements KeyListener {

	private BuscadorCliente bCliente;
	private InterfazBuscadorCliente interfaz;
	private TablaCliente tCliente;
	private ClienteDao dao;
	private List<Cliente> lista;

	public void setInterfaz(InterfazBuscadorCliente interfaz) {
		this.interfaz = interfaz;
	}

	public BuscadorClienteControlador(BuscadorCliente bCliente) {
		this.bCliente = bCliente;

		tCliente = new TablaCliente();

		this.bCliente.getTable().setModel(tCliente);

		dao = new ClienteDao();

		setUpEvents();

		recuperarPorFiltro();
	}

	private void setUpEvents() {
		bCliente.gettBuscador().addKeyListener(this);
		bCliente.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					seleccionarRegistro(bCliente.getTable().getSelectedRow());
				}
			}
		});
	}

	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bCliente.gettBuscador().getText());
		tCliente.setLista(lista);
		tCliente.fireTableDataChanged();

		TablaUtil.resizeTableColumnWidth(bCliente.getTable());
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		Cliente cliente = lista.get(posicion);
		interfaz.setCliente(cliente);
		bCliente.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == bCliente.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
