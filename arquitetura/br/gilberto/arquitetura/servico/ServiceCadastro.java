package br.gilberto.arquitetura.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gilberto.arquitetura.dados.GenericDao;
import br.gilberto.arquitetura.excecao.BDException;
import br.gilberto.arquitetura.modelo.Persistente;

/**
 * Processador padrão deve ser estendido por todos os processadores criados.
 * É o responsável por realizar a persistência dos dados em um ambiente transacional.
 */
@Service
public class ServiceCadastro{
	
	@Autowired
	@Qualifier("genericDao")
	protected GenericDao dao;
	
    /**
     * Incluir objeto no repositório
     * @param objeto
     * @throws java.lang.Exception
     */
	@Transactional(readOnly=false)
    public void salvar(Persistente objeto) throws BDException{
    	dao.addEntity(objeto);
    }

    /**
     * Atualizar objeto do repositório
     * @param objeto
     * @throws java.lang.Exception
     */
	@Transactional(readOnly=false)
	public void atualizar(Persistente objeto) throws BDException{
    	dao.updateEntity(objeto);
    }
    

    /**
     * Excluir objeto do repositório
     * @param objeto
     * @throws java.lang.Exception
     */
	@Transactional(readOnly=false)
	public void excluir(Persistente objeto) throws BDException{		
		dao.removeEntity(objeto);		
    }

}
