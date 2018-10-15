package py.edu.facitec.psmsystem.componente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class VentanaGenerica extends JDialog {

	private MiToolBar miToolBar;
	public JTable table;
	private JTextField tBuscador;
	private JPanel panelFormulario;

	public VentanaGenerica() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGenerica.class.getResource("/img/ventanas/icono.png")));
		setMinimumSize(new Dimension(700, 500));
		setMaximumSize(new Dimension(800, 600));
		setBounds(100, 100, 890, 500);
		getContentPane().setLayout(null);

		//Centrar todas las ventanas que hereden de VentanaGenerica
		setLocationRelativeTo(this);

		//Evitar que se pierda el foco de la ventana
		setModal(true);

		miToolBar = new MiToolBar();
		miToolBar.setBounds(10, 11, 400, 66);
		getContentPane().add(miToolBar);

		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(Color.BLACK));
		panelFormulario.setBounds(10, 81, 400, 369);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);

		JLabel lblBuscador = new JLabel("Buscador");
		lblBuscador.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblBuscador.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBuscador.setBounds(420, 11, 441, 33);
		getContentPane().add(lblBuscador);

		tBuscador = new JTextField();
		tBuscador.setBounds(420, 50, 441, 20);
		getContentPane().add(tBuscador);
		tBuscador.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(420, 81, 441, 369);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) {

				}
			}
		});
		scrollPane.setViewportView(table);
	}

	public MiToolBar getMiToolBar() {
		return miToolBar;
	}

	public JTable getTable() {
		return table;
	}

	public JTextField gettBuscador() {
		return tBuscador;
	}

	public JPanel getPanelFormulario() {
		return panelFormulario;
	}
}