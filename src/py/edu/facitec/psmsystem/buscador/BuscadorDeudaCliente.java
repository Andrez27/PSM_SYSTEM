package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorDeudaClienteControlador;

public class BuscadorDeudaCliente extends BuscadorGenerico {
	private static final long serialVersionUID = 1L;

	private BuscadorDeudaClienteControlador controlador;

	public void setUpController() {
		controlador = new BuscadorDeudaClienteControlador(this);
	}

	public BuscadorDeudaClienteControlador getControlador( ){
		return controlador;
	}

	public BuscadorDeudaCliente(){

	}

}
