package py.edu.facitec.psmsystem.componente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BotonesTolbarABM extends JButton {

	public BotonesTolbarABM() {
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

	public void setIcono(String nombreIcono) {
		setIcon(new ImageIcon(BotonesTolbarABM.class
				.getResource("/py/edu/facitec/psmsystem/img/32bits/" + nombreIcono.toLowerCase() + ".png")));

	}
}
