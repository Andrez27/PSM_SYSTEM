package py.edu.facitec.psmsystem.componente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BotonIconoVentana extends JButton{
	
	private static final long serialVersionUID = -5135218231501823013L;

	public BotonIconoVentana(){
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
			URL url = BotonIconoVentana.class.getResource("/img/32bits/"+nombreIcono.toLowerCase()+".png");
			setIcon(new ImageIcon(url));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
