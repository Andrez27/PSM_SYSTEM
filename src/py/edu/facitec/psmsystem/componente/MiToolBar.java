package py.edu.facitec.psmsystem.componente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import py.edu.facitec.psmsystem.interfaz.AccionesABM;

public class MiToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public BotonIconoVentana btncnNuevo;
	public BotonIconoVentana btncnModificar;
	public BotonIconoVentana btncnEliminar;
	public BotonIconoVentana btncnGuardar;
	public BotonIconoVentana btncnCancelar;

	private AccionesABM acciones;

	public void setAcciones(AccionesABM acciones) {
		this.acciones = acciones;
	}

	public MiToolBar() {
		setFloatable(false);

		btncnNuevo = new BotonIconoVentana();
		btncnNuevo.setIcon(new ImageIcon(MiToolBar.class.getResource("/img/32bits/nuevo.png")));
		btncnNuevo.setText("Nuevo");
		add(btncnNuevo);

		btncnModificar = new BotonIconoVentana();
		btncnModificar.setIcon(new ImageIcon(MiToolBar.class.getResource("/img/32bits/modificar.png")));
		btncnModificar.setEnabled(false);
		btncnModificar.setText("Modificar");
		add(btncnModificar);

		btncnEliminar = new BotonIconoVentana();
		btncnEliminar.setIcon(new ImageIcon(MiToolBar.class.getResource("/img/32bits/eliminar.png")));
		btncnEliminar.setEnabled(false);
		btncnEliminar.setText("Eliminar");
		add(btncnEliminar);

		btncnGuardar = new BotonIconoVentana();
		btncnGuardar.setIcon(new ImageIcon(MiToolBar.class.getResource("/img/32bits/guardar.png")));
		btncnGuardar.setEnabled(false);
		btncnGuardar.setText("Guardar");
		add(btncnGuardar);

		btncnCancelar = new BotonIconoVentana();
		btncnCancelar.setIcon(new ImageIcon(MiToolBar.class.getResource("/img/32bits/cancelar.png")));
		btncnCancelar.setEnabled(false);
		btncnCancelar.setText("Cancelar");
		add(btncnCancelar);

		setUpEvents();
	}

	private void setUpEvents() {
		btncnGuardar.addActionListener(this);
		btncnModificar.addActionListener(this);
		btncnNuevo.addActionListener(this);
		btncnEliminar.addActionListener(this);
		btncnCancelar.addActionListener(this);
	}
	
	public void estadoInicialToolBar(boolean b, int a) {
		if (a==1) {
			btncnNuevo.setEnabled(!b);
			btncnModificar.setEnabled(!b);
			btncnEliminar.setEnabled(!b);
			btncnGuardar.setEnabled(b);
			btncnCancelar.setEnabled(b);
		}
		if (a==2) {
			btncnNuevo.setEnabled(b);
			btncnModificar.setEnabled(!b);
			btncnEliminar.setEnabled(!b);
			btncnGuardar.setEnabled(!b);
			btncnCancelar.setEnabled(!b);
		}
		if (a==3) {
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