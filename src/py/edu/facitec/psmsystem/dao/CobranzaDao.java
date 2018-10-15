package py.edu.facitec.psmsystem.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.entidad.Cobranza;
import py.edu.facitec.psmsystem.entidad.DeudaCliente;
import py.edu.facitec.psmsystem.entidad.Empeno;

public class CobranzaDao extends GenericDao<Cobranza> {

	public CobranzaDao() {
		super(Cobranza.class);
	}
	
	public List<Cobranza> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Cobranza where id in (select cobranza.id from DeudaCliente where UPPER(empeno.cliente.nombre) like :descri)"
				+ "or id = :id order by id ";
		
		Query<Cobranza> query = getSession().createQuery(sql);
		
		query.setParameter("descri", "%" + filtro.toUpperCase() + "%");


		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {}
		query.setParameter("id", id);
		List<Cobranza> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Cobranza> recuperarPorRangos(int idDesde, int idHasta, Date fDesde, Date fHasta, int indiceOrden) {
		String[] opcionesOrden = {"id", "fechaCobro"};
		getSession().beginTransaction();
		String sql = "from Cobranza where id BETWEEN :idDesde and :idHasta "
				+ "and (fechaCobro >= :fDesde or :fDesdeNula = true)  "
				+ "and (fechaCobro <= :fHasta or :fHastaNula = true)  "
				+ "order by "+opcionesOrden[indiceOrden];
		
			Query<Cobranza> query = getSession().createQuery(sql);
			query.setParameter("idDesde", idDesde);
			query.setParameter("idHasta", idHasta);
			query.setParameter("fDesde", fDesde);
			query.setParameter("fDesdeNula", fDesde == null);
			query.setParameter("fHasta", fHasta);
			query.setParameter("fHastaNula", fHasta  == null);
		
		List<Cobranza> lista = query.getResultList();
		commit();
		return lista;
	}
	
}
