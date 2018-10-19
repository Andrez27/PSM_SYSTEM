package py.edu.facitec.psmsystem.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jtattoo.plaf.DecorationHelper;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import net.sf.jasperreports.engine.JRException;
import py.edu.facitec.psmsystem.componente.LoadingPanel;
import py.edu.facitec.psmsystem.util.Factory;
import py.edu.facitec.psmsystem.util.ReportesUtil;

public class Cargando extends JFrame {

	private LoadingPanel contentPane;
	private JLabel lblVersion;
	private JLabel lblGif;

	public static void main(String[] args) {
		try {
			//PARA QUITAR EL TEXTO DEL JTATTOO
			Properties props = new Properties();
			props.put("logoString", "");
			GraphiteLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel(GraphiteLookAndFeel.class.getName());
			//PARA QUITAR EL BORDE DEL TEMA JTATTOO
			DecorationHelper.decorateWindows(false);
		} catch (Exception e) {
		}
		try {
			Cargando frame = new Cargando();
			frame.setVisible(true);
			Factory.setUp();
			for (int i = 1; i <= 100; i++) {
				Thread.sleep(10);
				if (i == 100) {
					DecorationHelper.decorateWindows(true);
					new VentanaPrincipal().setVisible(true);
				}
			}
			frame.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Cargando() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 280);
		contentPane = new LoadingPanel();
		contentPane.setEnabled(false);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(this);		
		contentPane.setLayout(null);

		lblGif = new JLabel("");
		lblGif.setForeground(Color.DARK_GRAY);
		lblGif.setVerticalAlignment(SwingConstants.BOTTOM);
		lblGif.setHorizontalAlignment(SwingConstants.LEFT);
		lblGif.setIcon(new ImageIcon(Cargando.class.getResource("/img/ventanas/gif.gif")));
		lblGif.setBounds(8, 208, 135, 68);
		contentPane.add(lblGif);

		lblVersion = new JLabel("v1.9");
		lblVersion.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVersion.setBounds(393, 250, 46, 30);
		lblVersion.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblVersion.setForeground(Color.DARK_GRAY);
		contentPane.add(lblVersion);
		conectarReporte();
	}
	
	public void abrirMenu(){
		Factory.setUp();
		conectarReporte();
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.setVisible(true);
		dispose();
	}
	
	public void conectarReporte(){
		ReportesUtil<VentanaPrincipal> conexionReportes = new ReportesUtil<VentanaPrincipal>();
		try {
			conexionReportes.primeraConexion();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
