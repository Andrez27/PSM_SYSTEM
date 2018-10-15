package py.edu.facitec.psmsystem.buscador;

import java.awt.EventQueue;

import javax.swing.JDialog;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorClienteControlador;

public class BuscadorCliente extends BuscadorGenerico {

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
