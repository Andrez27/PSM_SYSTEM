package py.edu.facitec.psmsystem.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class TelefonoUtil {
	private static MaskFormatter mascara;

	public static MaskFormatter getMascara() {
		if (mascara == null) {
			try {
				mascara = new MaskFormatter("+(###)###-###-###");
				mascara.setPlaceholderCharacter('_');
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return mascara;
	}

}