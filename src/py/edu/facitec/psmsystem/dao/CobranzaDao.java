package py.edu.facitec.psmsystem.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.entidad.Cobranza;

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
	
	public List<Cobranza> recuperarPorRangos(Date fDesde, Date fHasta) {
		getSession().beginTransaction();
		String sql = "from Cobranza where (fechaCobro >= :fDesde or :fDesdeNula = true)  "
				+ "and (fechaCobro <= :fHasta or :fHastaNula = true)  ";
		
			Query<Cobranza> query = getSession().createQuery(sql);
			query.setParameter("fDesde", fDesde);
			query.setParameter("fDesdeNula", fDesde == null);
			query.setParameter("fHasta", fHasta);
			query.setParameter("fHastaNula", fHasta  == null);
		
		List<Cobranza> lista = query.getResultList();
		commit();
		return lista;
	}
	
}
