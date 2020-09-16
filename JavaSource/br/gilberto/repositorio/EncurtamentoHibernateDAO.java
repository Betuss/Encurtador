package br.gilberto.repositorio;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.gilberto.modelo.Encurtamento;

@Repository(value = "encurtamentoHibernateDAO")
public class EncurtamentoHibernateDAO extends HibernateDaoSupport implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	public EncurtamentoHibernateDAO(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	public Encurtamento findByCodigo(String codigo) {
		Criteria c = getSession().createCriteria(Encurtamento.class);
		c.add(Restrictions.eq("codigo", codigo));

		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Encurtamento) c.uniqueResult();
	}
	
	public boolean isCodigoJaUsado(String codigo) {
		Criteria c = getSession().createCriteria(Encurtamento.class);
		c.add(Restrictions.eq("codigo", codigo));

		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return c.list().size() > 0;
	}
}