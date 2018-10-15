package py.edu.facitec.psmsystem.util;

import java.awt.Component;

import javax.swing.JTable;
//import javax.swing.SwingConstants;
//import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class TablaUtil{

	public static void resizeTableColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    
	} 	
	
//	 DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
//		
//  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//	  cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//	  table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
//	       
//	  return getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//	  }

}
