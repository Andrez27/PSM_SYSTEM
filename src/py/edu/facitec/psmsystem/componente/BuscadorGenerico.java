package py.edu.facitec.psmsystem.componente;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BuscadorGenerico extends JDialog {
	private JTextField tfBuscador;
	private JTable table;
	
	public BuscadorGenerico() {
		setBounds(100, 100, 600, 400);
				
		// Centra la ventana
		setLocationRelativeTo(this);
		
		// Evita que la ventana pierda el foco
		setModal(true);
		
		getContentPane().setLayout(null);
		
		tfBuscador = new JTextField();
		tfBuscador.setBounds(10, 11, 564, 20);
		getContentPane().add(tfBuscador);
		tfBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 564, 308);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
	}

	public JTextField gettBuscador() {
		return tfBuscador;
	}

	public JTable getTable() {
		return table;
	}
}
