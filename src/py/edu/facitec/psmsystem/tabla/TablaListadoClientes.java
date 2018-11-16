package py.edu.facitec.psmsystem.tabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.psmsystem.entidad.Cliente;

public class TablaListadoClientes extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnas = { "ID", "NOMBRE Y APELLIDO", "TELEFONO", "EMAIL" };
	private List<Cliente> lista = new ArrayList<>();

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	@Override
	public String getColumnName(int column) {
		return columnas[column];
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return lista.get(rowIndex).getId();
		case 1:
			return lista.get(rowIndex).getNombre();
		case 2:
			return lista.get(rowIndex).getTelefono();
		case 3:
			return lista.get(rowIndex).getEmail();
		default:
			break;
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) return Integer.class;

		return super.getColumnClass(columnIndex);
	}
}
