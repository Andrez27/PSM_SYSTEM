package py.edu.facitec.psmsystem.componente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BotonIconoPrincipal extends JButton{
	private static final long serialVersionUID = 1L;

	public BotonIconoPrincipal(){
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setPreferredSize(new Dimension(100, 100));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setMaximumSize(new Dimension(100, 100));
		setFont(new Font("Tahoma", Font.BOLD, 16));
		setForeground(Color.DARK_GRAY);
		setBackground(Color.WHITE);
	}
	public void setText(String text) {
		setIcono(text);
		super.setText(text);
	}

	public void setIcono(String nombreIcono){
		try {
			URL url = BotonIconoPrincipal.class.getResource("/py/edu/facitec/psmsystem/img/64bits/"+nombreIcono.toLowerCase()+".png");
			setIcon(new ImageIcon(url));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
