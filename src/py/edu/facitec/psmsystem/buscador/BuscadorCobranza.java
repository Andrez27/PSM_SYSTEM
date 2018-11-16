package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorCobranzaControlador;

public class BuscadorCobranza extends BuscadorGenerico {
	private static final long serialVersionUID = 1L;

	private BuscadorCobranzaControlador controlador;

	public void setUpController() {
		controlador = new BuscadorCobranzaControlador(this);
	}

	public BuscadorCobranzaControlador getControlador( ){
		return controlador;
	}

	public BuscadorCobranza(){

	}

}
