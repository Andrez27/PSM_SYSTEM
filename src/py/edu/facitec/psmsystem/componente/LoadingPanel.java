package py.edu.facitec.psmsystem.componente;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	URL url = getClass().getResource("/img/cargando.png");
	Image image = new ImageIcon(url).getImage();
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}
}
