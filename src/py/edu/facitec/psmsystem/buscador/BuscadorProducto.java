package py.edu.facitec.psmsystem.buscador;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorProductoControlador;

public class BuscadorProducto extends BuscadorGenerico {

	private BuscadorProductoControlador controlador;
	
	public void setUpController() {
		controlador = new BuscadorProductoControlador(this);
	}
	
	public BuscadorProductoControlador getControlador( ){
		return controlador;
	}

	public BuscadorProducto(){
		
	}

}
