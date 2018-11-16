package py.edu.facitec.psmsystem.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import py.edu.facitec.psmsystem.util.Factory;

public class GenericDao <T>{
	Class<T> clase;
	protected CriteriaBuilder builder;
	protected CriteriaQuery<T> criteriaQuery;
	protected Root<T> root;
	
	protected Session getSession() {
		return Factory.getSessionFactory().getCurrentSession();
	}
	
	public GenericDao(Class<T> clase) {
		this.clase = clase;
	}
	
	public void insertarOModificar(T entity){
		if(entity == null){
			System.out.println("entidad nula");
		}
		getSession().beginTransaction();
		getSession().saveOrUpdate(entity);
	}
	
	public void insertar(T entity) throws Exception{
		getSession().beginTransaction();
		getSession().save(entity);
	}
	
	public void persistir(T entity) {
		getSession().beginTransaction();
		getSession().persist(entity);
	}
	
	public void modificar(T entity) throws Exception{
		getSession().beginTransaction();
		getSession().update(entity);
	}

	public void eliminar(T entity) throws Exception {
		getSession().beginTransaction();
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> recuperarTodo(){
		getSession().beginTransaction();
		Query<T> query = getSession().createQuery("from "+clase.getName()+" order by id");
		List<T> results = query.getResultList();
		commit();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public int recuperarSiguienteId(){
		getSession().beginTransaction();
		Query<T> query = getSession().createQuery("select max(id) from "+clase.getName());
		int id = 0;
		try {
			id = (int) query.getSingleResult();
		} catch (Exception e) {
		}
		commit();
		return id+1;
	}
	
	public T recuperarPorId(int id){
		getSession().beginTransaction();
		T result = getSession().get(clase, id);
		commit();
		return result;
	}
	
	public void commit() {
		getSession().getTransaction().commit();
	}

	public void rollback() {
		getSession().getTransaction().rollback();
	}
	
	public void cerrar(){
		getSession().close();
	}
	
	@SuppressWarnings("rawtypes")
	public void eliminarTodos(String tabla){
		getSession().getTransaction().begin();
		String deleteAll= "TRUNCATE TABLE "+tabla+ " cascade";
		Query query = getSession().createNativeQuery(deleteAll);
		query.executeUpdate();
	}
	
}
