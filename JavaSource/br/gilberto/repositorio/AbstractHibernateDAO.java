package br.gilberto.repositorio;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

import br.gilberto.arquitetura.dados.GenericDao;
import br.gilberto.arquitetura.modelo.Persistente;

public abstract class AbstractHibernateDAO<T extends Persistente> extends GenericDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract List<T> listaEspecifica(Map<String, Object> filters, int inicio, int pageSize, String sortField, SortOrder sortOrder);

	public abstract Integer quantidadeTotal(Map<String, Object> filters);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}