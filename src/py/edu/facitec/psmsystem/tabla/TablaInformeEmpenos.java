package py.edu.facitec.psmsystem.tabla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.psmsystem.entidad.Empeno;

public class TablaInformeEmpenos extends AbstractTableModel {

	private String[] columnas = { "ID", "FECHA EMPEÑO", "CLIENTE", "PRODUCTO", "VALOR TOTAL", "ESTADO"};

	private List<Empeno> lista = new ArrayList<>();

	public void setLista(List<Empeno> lista) {
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
			return lista.get(rowIndex).getFechaDia();
		case 2:
			return lista.get(rowIndex).getCliente().getNombre();
		case 3:
			return lista.get(rowIndex).getProducto().getDescripcion();
		case 4:
			return lista.get(rowIndex).getValorTotal();
		case 5:
			if(lista.get(rowIndex).getEstado() == 0){
				return "Activo";
			} else {
				if(lista.get(rowIndex).getEstado() == 1){
					return "Vencido";
				} else {
					if(lista.get(rowIndex).getEstado() == 2){
						return "Cobrado";
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
		if(columnIndex == 1) return Date.class;
		if(columnIndex == 4) return Double.class;
		return super.getColumnClass(columnIndex);
	}

}
