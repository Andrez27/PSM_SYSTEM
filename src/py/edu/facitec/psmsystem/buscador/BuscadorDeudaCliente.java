package py.edu.facitec.psmsystem.buscador;

import java.awt.EventQueue;

import javax.swing.JDialog;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorDeudaClienteControlador;

public class BuscadorDeudaCliente extends BuscadorGenerico {

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
