package br.gilberto.repositorio;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.gilberto.modelo.Usuario;

@Repository(value = "usuarioHibernateDAO")
public class UsuarioHibernateDAO extends HibernateDaoSupport implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	public UsuarioHibernateDAO(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	public Usuario findByUsernameAndPassword(String username, String password) {
		Criteria c = getSession().createCriteria(Usuario.class);
		c.add(Restrictions.eq("username", username));
		c.add(Restrictions.eq("password", password));

		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Usuario) c.uniqueResult();
	}
	
	public Usuario findByUsername(String username) {
		Criteria c = getSession().createCriteria(Usuario.class);
		c.add(Restrictions.eq("username", username));

		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Usuario) c.uniqueResult();
	}
}
