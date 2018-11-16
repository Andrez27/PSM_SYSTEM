package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorClienteControlador;

public class BuscadorCliente extends BuscadorGenerico {
	private static final long serialVersionUID = 1L;
	
	private BuscadorClienteControlador controlador;
	
	public void setUpController() {
		controlador = new BuscadorClienteControlador(this);
	}
	
	public BuscadorClienteControlador getControlador( ){
		return controlador;
	}

	public BuscadorCliente(){
		
	}
	
}
