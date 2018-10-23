package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorEmpenoControlador;

public class BuscadorEmpeno extends BuscadorGenerico {

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
