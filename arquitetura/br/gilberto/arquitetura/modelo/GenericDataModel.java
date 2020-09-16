package br.gilberto.arquitetura.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gilberto.arquitetura.modelo.Persistente;
import br.gilberto.repositorio.AbstractHibernateDAO;

public class GenericDataModel<T extends Persistente> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	protected AbstractHibernateDAO<T> dao;

	private Class<T> classe;

	public GenericDataModel(AbstractHibernateDAO<T> dao, Class<T> classe) {
		this.dao = dao;
		this.classe = classe;
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> retorno = new ArrayList<T>();

		retorno = dao.listaEspecifica(filters, first, pageSize, sortField, sortOrder);
		this.setRowCount(dao.quantidadeTotal(filters).intValue());
		return retorno;
	}

	public AbstractHibernateDAO<T> getDao() {
		return dao;
	}

	public void setDao(AbstractHibernateDAO<T> dao) {
		this.dao = dao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
