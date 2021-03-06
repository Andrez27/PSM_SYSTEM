package py.edu.facitec.psmsystem.tabla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.edu.facitec.psmsystem.entidad.Cobranza;

public class TablaCobranza extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnas = { "ID", "FECHA COBRO", "VALOR TOTAL", "ESTADO"};
	private List<Cobranza> lista = new ArrayList<>();

	public void setLista(List<Cobranza> lista) {
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
			return lista.get(rowIndex).getFechaCobro();
		case 2:
			return lista.get(rowIndex).getValorCobro();
		case 3:
			if(lista.get(rowIndex).isEstado() == true){
				return "Cobrado";
			} else {
				return "Anulado";
			}
		default:
			break;
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 1) return Date.class;
		if(columnIndex == 2) return Double.class;
		return super.getColumnClass(columnIndex);
	}
}
