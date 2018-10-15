package py.edu.facitec.psmsystem.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import py.edu.facitec.psmsystem.buscador.BuscadorEmpeno;
import py.edu.facitec.psmsystem.dao.EmpenoDao;
import py.edu.facitec.psmsystem.entidad.Empeno;
import py.edu.facitec.psmsystem.interfaz.InterfazBuscadorEmpeno;
import py.edu.facitec.psmsystem.tabla.TablaEmpeno;

public class BuscadorEmpenoControlador implements KeyListener{

	private BuscadorEmpeno bEmpeno;
	private TablaEmpeno tEmpeno;
	private List<Empeno> lista;
	private EmpenoDao dao;
	private InterfazBuscadorEmpeno interfaz;

	public void setInterfaz(InterfazBuscadorEmpeno interfaz) {
		this.interfaz = interfaz;
	}

	public BuscadorEmpenoControlador (BuscadorEmpeno bEmpeno) {
		this.bEmpeno = bEmpeno;

		tEmpeno = new TablaEmpeno();

		this.bEmpeno.getTable().setModel(tEmpeno);

		dao = new EmpenoDao();

		setUpEvents();

		recuperarPorFiltro();
	}

	private void setUpEvents() {
		bEmpeno.gettBuscador().addKeyListener(this);
		bEmpeno.getTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					seleccionarRegistro(bEmpeno.getTable().getSelectedRow());
				}
			}
		});
	}
	
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bEmpeno.gettBuscador().getText());
		tEmpeno.setLista(lista);
		tEmpeno.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		Empeno empeno = lista.get(posicion);
		interfaz.setEmpeno(empeno);
		bEmpeno.dispose();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		System.out.println("ok");
		if(e.getSource() == bEmpeno.gettBuscador() && e.getKeyCode()==KeyEvent.VK_ENTER){
			recuperarPorFiltro();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
