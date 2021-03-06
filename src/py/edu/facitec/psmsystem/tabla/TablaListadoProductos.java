package py.edu.facitec.psmsystem.tabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.psmsystem.entidad.Producto;

public class TablaListadoProductos extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnas = { "ID", "DESCRIPCIÓN", "PRECIO COMPRA", "PRECIO VENTA", "ESTADO" };
	private List<Producto> lista = new ArrayList<>();

	public void setLista(List<Producto> lista) {
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
			return lista.get(rowIndex).getDescripcion();
		case 2:
			return lista.get(rowIndex).getPrecioCompra();
		case 3:
			return lista.get(rowIndex).getPrecioVenta();
		case 4:
			if(lista.get(rowIndex).getEstado() == 0){
				return "Activo";
			} else {
				if(lista.get(rowIndex).getEstado() == 1){
					return "En venta";
				} else {
					if(lista.get(rowIndex).getEstado() == 2){
						return "Vendido";
					} else {
						if(lista.get(rowIndex).getEstado() == 3){
							return "Anulado";
						} else {
						}
					}
				}
			}

		default:
			break;
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) return Integer.class;
		if(columnIndex == 2) return Double.class;
		if(columnIndex == 3) return Double.class;

		return super.getColumnClass(columnIndex);
	}
}