package py.edu.facitec.psmsystem.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import py.edu.facitec.psmsystem.buscador.BuscadorCobranza;
import py.edu.facitec.psmsystem.dao.CobranzaDao;
import py.edu.facitec.psmsystem.entidad.Cobranza;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorCobranza;
import py.edu.facitec.psmsystem.tabla.TablaCobranza;

public class BuscadorCobranzaControlador implements KeyListener{

	private InterfazBuscadorCobranza interfaz;
	private BuscadorCobranza bCobranza;
	private TablaCobranza tCobranza;
	private CobranzaDao dao;
	private List<Cobranza> lista;

	public void setInterfaz(InterfazBuscadorCobranza interfaz) {
		this.interfaz = interfaz;
	}
	
	public BuscadorCobranzaControlador (BuscadorCobranza bCobranza) {
		this.bCobranza = bCobranza;

		tCobranza = new TablaCobranza();

		this.bCobranza.getTable().setModel(tCobranza);

		dao = new CobranzaDao();

		setUpEvents();

		recuperarPorFiltro();
	}
	
	private void setUpEvents() {
		bCobranza.gettBuscador().addKeyListener(this);
		bCobranza.getTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					seleccionarRegistro(bCobranza.getTable().getSelectedRow());
				}
			}
		});
	}
	
	private void recuperarPorFiltro() {

		lista = dao.recuperarPorFiltro(bCobranza.gettBuscador().getText());
		tCobranza.setLista(lista);
		tCobranza.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		Cobranza cobranza = lista.get(posicion);
		interfaz.setCobranza(cobranza);
		bCobranza.dispose();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
