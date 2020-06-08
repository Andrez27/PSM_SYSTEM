package py.edu.facitec.psmsystem.componente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;

import py.edu.facitec.psmsystem.interfaz.AccionesABM;

@SuppressWarnings("serial")
public class ToolBarABM extends JToolBar implements ActionListener {

	public BotonesTolbarABM btncnNuevo;
	public BotonesTolbarABM btncnModificar;
	public BotonesTolbarABM btncnEliminar;
	public BotonesTolbarABM btncnGuardar;
	public BotonesTolbarABM btncnCancelar;

	private AccionesABM acciones;

	public void setAcciones(AccionesABM acciones) {
		this.acciones = acciones;
	}

	public ToolBarABM() {
		setFloatable(false);

		btncnNuevo = new BotonesTolbarABM();
//		btncnNuevo.setIcon(new ImageIcon(ToolBarABM.class.getResource("/py/edu/facitec/psmsystem/img/32bits/nuevo.png")));
		btncnNuevo.setText("Nuevo");
		add(btncnNuevo);

		btncnModificar = new BotonesTolbarABM();
//		btncnModificar.setIcon(new ImageIcon(ToolBarABM.class.getResource("/py/edu/facitec/psmsystem/img/32bits/modificar.png")));
		btncnModificar.setEnabled(false);
		btncnModificar.setText("Modificar");
		add(btncnModificar);

		btncnEliminar = new BotonesTolbarABM();
//		btncnEliminar.setIcon(new ImageIcon(ToolBarABM.class.getResource("/py/edu/facitec/psmsystem/img/32bits/eliminar.png")));
		btncnEliminar.setEnabled(false);
		btncnEliminar.setText("Eliminar");
		add(btncnEliminar);

		btncnGuardar = new BotonesTolbarABM();
//		btncnGuardar.setIcon(new ImageIcon(ToolBarABM.class.getResource("/py/edu/facitec/psmsystem/img/32bits/guardar.png")));
		btncnGuardar.setEnabled(false);
		btncnGuardar.setText("Guardar");
		add(btncnGuardar);

		btncnCancelar = new BotonesTolbarABM();
//		btncnCancelar.setIcon(new ImageIcon(ToolBarABM.class.getResource("/py/edu/facitec/psmsystem/img/32bits/cancelar.png")));
		btncnCancelar.setEnabled(false);
		btncnCancelar.setText("Cancelar");
		add(btncnCancelar);

		setUpEvents();
	}

	private void setUpEvents() {
		btncnNuevo.addActionListener(this);
		btncnModificar.addActionListener(this);
		btncnEliminar.addActionListener(this);
		btncnGuardar.addActionListener(this);
		btncnCancelar.addActionListener(this);
	}

	public void estadoInicialToolBar(boolean b, int a) {
		if (a == 1) {
			btncnNuevo.setEnabled(!b);
			btncnModificar.setEnabled(!b);
			btncnEliminar.setEnabled(!b);
			btncnGuardar.setEnabled(b);
			btncnCancelar.setEnabled(b);
		}
		if (a == 2) {
			btncnNuevo.setEnabled(b);
			btncnModificar.setEnabled(!b);
			btncnEliminar.setEnabled(!b);
			btncnGuardar.setEnabled(!b);
			btncnCancelar.setEnabled(!b);
		}
		if (a == 3) {
			btncnNuevo.setEnabled(!b);
			btncnModificar.setEnabled(b);
			btncnEliminar.setEnabled(b);
			btncnGuardar.setEnabled(!b);
			btncnCancelar.setEnabled(b);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Nuevo":
			acciones.nuevo();
			break;
		case "Modificar":
			acciones.modificar();
			break;
		case "Eliminar":
			acciones.eliminar();
			break;
		case "Guardar":
			acciones.guardar();
			break;
		case "Cancelar":
			acciones.cancelar();
			break;
		}
	}

}