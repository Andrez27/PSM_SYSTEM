package py.edu.facitec.psmsystem.util;

import java.awt.Dialog.ModalExclusionType;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportesUtil<E>{
	private static JasperPrint print;
	private static JasperViewer viewer;
	
	public static void GenerarInforme(List<?> lista, Map<String, Object> map, String reporte){
		//PARA MPRIMIR LOGO DEL SISTEMA
		InputStream logo = ReportesUtil.class.getResourceAsStream("/img/ventanas/icono.png");
		map.put("logo", logo);
		
		String urlReporte = "/py/edu/facitec/psmsystem/informe/"+reporte+".jrxml";
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
		
		try {
			InputStream stream = ReportesUtil.class.getResourceAsStream(urlReporte);
			JasperReport report= JasperCompileManager.compileReport(stream);
			print = JasperFillManager.fillReport(report, map, dataSource);
			viewer = new JasperViewer(print, false);
			viewer.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			viewer.setVisible(true);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public void primeraConexion() throws JRException{
		InputStream stream = ReportesUtil.class.getResourceAsStream("/py/edu/facitec/psmsystem/informe/ListadoClientes.jrxml");
		JasperReport report= JasperCompileManager.compileReport(stream);
		JasperPrint print = JasperFillManager.fillReport(report, null,new JRBeanCollectionDataSource(null));
	}
}
