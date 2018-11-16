package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorEmpenoControlador;

public class BuscadorEmpeno extends BuscadorGenerico {
	private static final long serialVersionUID = 1L;

	private BuscadorEmpenoControlador controlador;

	public void setUpController() {
		controlador = new BuscadorEmpenoControlador(this);
	}

	public BuscadorEmpenoControlador getControlador( ){
		return controlador;
	}

	public BuscadorEmpeno(){

	}

}
