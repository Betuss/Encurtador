package br.gilberto.arquitetura.dados;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import br.gilberto.arquitetura.excecao.*;
import br.gilberto.arquitetura.modelo.Persistente;


@Repository(value="genericDao")
public class GenericDao extends HibernateDaoSupport{
	
	private JdbcTemplate template;
	
	@Autowired
	public GenericDao(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	public GenericDao(){};
	
	public void addEntity(final Persistente objeto) throws BDException{
		this.getHibernateTemplate().clear(); 
		this.getHibernateTemplate().saveOrUpdate(objeto);
		this.getHibernateTemplate().flush();
	}

	public void updateEntity(final Persistente objeto) throws BDException{
		this.getHibernateTemplate().clear(); 
		this.getHibernateTemplate().update(objeto);
	}

	public void removeEntity(Persistente objeto) throws BDException{
		this.getHibernateTemplate().delete(objeto);
		//this.getHibernateTemplate().refresh(objeto);
		this.getHibernateTemplate().flush();
	}

	public <T extends Persistente> List<T> listAll(final Class<T> classe){
		return this.getHibernateTemplate().loadAll(classe);
	}
	

	public void flush() {
		this.getHibernateTemplate().flush();
		
	}
	
	protected SimpleExpression eq(String propriedade,Object valor) throws HibernateException{
		return Restrictions.eq(propriedade, valor);
	}
	protected Order asc(String propriedade) throws HibernateException{
		return Order.asc(propriedade);
	}
	
	protected Criteria getCriteria(Class< ? extends Persistente> classe){
		return getSession().createCriteria(classe);
	}
	
	protected Restrictions ilike(String propriedade,Object valor){
		return (Restrictions) Restrictions.ilike(propriedade,valor);
	}
	
	protected JdbcTemplate getJdbcTemplate(DataSource dataSource){
		if(template == null)
			template = new JdbcTemplate(dataSource);
		
		return template;
	}
	
	public JdbcTemplate getJdbcTemplate(){
		if(template == null)
			template = new JdbcTemplate();
		
		return template;
	}
}
