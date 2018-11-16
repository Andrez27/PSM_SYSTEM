package py.edu.facitec.psmsystem.dao;

import java.util.List;

import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.entidad.Cliente;

public class ClienteDao extends GenericDao<Cliente> {

	public ClienteDao() {
		super (Cliente.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Cliente where UPPER(nombre) like :descri " 
				+ "or documento = :docu "
				+ "or id = :id order by id ";

		Query<Cliente> query = getSession().createQuery(sql);
		query.setParameter("descri", "%" + filtro.toUpperCase() + "%");
		query.setParameter("docu", filtro);

		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {}
		query.setParameter("id", id);

		List<Cliente> lista = query.getResultList();
		commit();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public Boolean validarDocumento(int id, String documento){
		getSession().beginTransaction();

		String sql = "from Cliente where documento = :docu";

		Query<Cliente> query = getSession().createQuery(sql);
		query.setParameter("docu", documento);

		Cliente cliente = query.getSingleResult();

		if(cliente != null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> recuperarPorRangos(int idDesde, int idHasta, String nDesde, String nHasta, int indiceOrden) {
		String[] opcionesOrden = {"id", "nombre"};
		getSession().beginTransaction();
		String sql = "from Cliente where nombre BETWEEN :nDesde and :nHasta "
				+ "and id BETWEEN :idDesde and :idHasta "
				+ "order by "+opcionesOrden[indiceOrden];

		Query<Cliente> query = getSession().createQuery(sql);
		query.setParameter("idDesde", idDesde);
		query.setParameter("idHasta", idHasta);
		query.setParameter("nDesde", nDesde);
		query.setParameter("nHasta", nHasta);

		List<Cliente> lista = query.getResultList();
		commit();
		return lista;
	}
}