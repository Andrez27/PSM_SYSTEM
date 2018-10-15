package py.edu.facitec.psmsystem.componente;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
	public PanelFondo() {
	}
	//estira la imagen desde la carpeta
	URL url = getClass().getResource("/img/ventanas/fondo.png");
	Image image = new ImageIcon(url).getImage();
	
	//pasa la imagen a la pantalla
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}

}