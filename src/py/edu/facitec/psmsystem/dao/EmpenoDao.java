 package py.edu.facitec.psmsystem.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.entidad.Cliente;
import py.edu.facitec.psmsystem.entidad.Empeno;
import py.edu.facitec.psmsystem.entidad.Producto;

public class EmpenoDao extends GenericDao<Empeno> {
	
	public EmpenoDao() {
		super(Empeno.class);
	}
	
	public List<Empeno> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Empeno where UPPER(cliente.nombre) like :descri "
				+"or id = :id order by id ";
		
		Query<Empeno> query = getSession().createQuery(sql);
		
		query.setParameter("descri", "%" + filtro.toUpperCase() + "%");


		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {}
		query.setParameter("id", id);
		List<Empeno> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Empeno> recuperarPorRangos(Date fDesde, Date fHasta) {
		getSession().beginTransaction();
		String sql = "from Empeno where fechaDia BETWEEN :fDesde and :fHasta "
				+ "and (fechaDia >= :fDesde or :fDesdeNula = true)  "
				+ "and (fechaDia <= :fHasta or :fHastaNula = true)  ";
		
			Query<Empeno> query = getSession().createQuery(sql);
			query.setParameter("fDesde", fDesde);
			query.setParameter("fDesdeNula", fDesde == null);
			query.setParameter("fHasta", fHasta);
			query.setParameter("fHastaNula", fHasta  == null);
		
		List<Empeno> lista = query.getResultList();
		commit();
		return lista;
	}
}