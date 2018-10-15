package py.edu.facitec.psmsystem.dao;

import java.util.List;

import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.entidad.DeudaCliente;

public class DeudaClienteDao extends GenericDao<DeudaCliente> {
	
	public DeudaClienteDao() {
		super (DeudaCliente.class);
	}
	
	public List<DeudaCliente> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from DeudaCliente where UPPER(empeno.cliente.nombre) like :descri and estado < 2 "
				+"or id = :id "
				+"and estado < 2 order by id";
		
		Query<DeudaCliente> query = getSession().createQuery(sql);
		
		query.setParameter("descri", "%" + filtro.toUpperCase() + "%");


		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {}
		query.setParameter("id", id);
		List<DeudaCliente> lista = query.getResultList();
		commit();
		return lista;
	}
	
	

}
