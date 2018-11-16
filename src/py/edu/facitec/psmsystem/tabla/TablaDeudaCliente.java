package py.edu.facitec.psmsystem.tabla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.psmsystem.entidad.DeudaCliente;

public class TablaDeudaCliente extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnas = { "ID", "CLIENTE", "FECHA VENCIMIENTO", "MONTO", "ESTADO"};
	private List<DeudaCliente> lista = new ArrayList<>();

	public void setLista(List<DeudaCliente> lista) {
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
			return lista.get(rowIndex).getEmpeno().getCliente().getNombre();
		case 2:
			return lista.get(rowIndex).getFechaVencimiento();
		case 3:
			return lista.get(rowIndex).getValor();
		case 4:
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
		if(columnIndex == 2) return Date.class;
		if(columnIndex == 3) return Double.class;
		return super.getColumnClass(columnIndex);
	}
}
