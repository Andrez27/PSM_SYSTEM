package py.edu.facitec.psmsystem.controlador;

import py.edu.facitec.psmsystem.buscador.BuscadorProducto;
import py.edu.facitec.psmsystem.tabla.TablaProducto;

public class BuscadorProductoControlador {

	private BuscadorProducto bProducto;
	private TablaProducto tProducto;
	
	public BuscadorProductoControlador (BuscadorProducto bProducto) {
		this.bProducto = bProducto;
		
		tProducto = new TablaProducto();
		
		this.bProducto.getTable().setModel(tProducto);
	}
}
