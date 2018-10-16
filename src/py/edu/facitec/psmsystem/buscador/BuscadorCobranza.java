package py.edu.facitec.psmsystem.buscador;

import java.awt.EventQueue;

import javax.swing.JDialog;

import py.edu.facitec.psmsystem.componente.BuscadorGenerico;
import py.edu.facitec.psmsystem.controlador.BuscadorCobranzaControlador;

public class BuscadorCobranza extends BuscadorGenerico {

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