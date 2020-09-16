package br.gilberto.arquitetura.dados;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.gilberto.arquitetura.modelo.Persistente;
import br.gilberto.repositorio.AbstractHibernateDAO;

@Repository
public class GenericHibernateDAO<T extends Persistente> extends AbstractHibernateDAO<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Class<T> classe;
	
	@Autowired
	public GenericHibernateDAO(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listaEspecifica(Map<String, Object> filters,	int inicio, int pageSize, String sortField, SortOrder sortOrder) {
		
		Criteria c = getSession().createCriteria(classe);

		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String filterProperty = it.next();
			String filterValue = (String) filters.get(filterProperty);
			c.add(Restrictions.like(filterProperty, "%" + filterValue.toUpperCase() + "%").ignoreCase());
			
		}
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if(sortField != null){
			if(sortOrder == SortOrder.ASCENDING)
				c.addOrder(Order.asc(sortField));
			else
				c.addOrder(Order.desc(sortField));
		}
		c.setFirstResult(inicio);
		c.setMaxResults(pageSize);
		return c.list();
	}

	@Override
	public Integer quantidadeTotal(Map<String, Object> filters) {
		Criteria c = getSession().createCriteria(classe);
		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String filterProperty = it.next();
			String filterValue = (String) filters.get(filterProperty);

				c.add(Restrictions.like(filterProperty, "%" + filterValue.toUpperCase() + "%").ignoreCase());
			
		}
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.setProjection(Projections.rowCount());
		Integer i = (Integer) c.uniqueResult();
		return i;
	}

	public void setClasse(Class<T> classe) {
		this.classe = classe;
	}
	
}
