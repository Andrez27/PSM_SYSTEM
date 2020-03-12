package py.edu.facitec.psmsystem.componente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BotonIconoPrincipal extends JButton{

	public BotonIconoPrincipal(){
		setMaximumSize(new Dimension(100, 100));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setFont(new Font("Tahoma", Font.BOLD, 16));
		setFocusPainted(false);
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
