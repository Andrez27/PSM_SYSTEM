package py.edu.facitec.psmsystem.componente;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {

	URL url = getClass().getResource("/img/ventanas/cargando.png");
	Image image = new ImageIcon(url).getImage();
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}
}
