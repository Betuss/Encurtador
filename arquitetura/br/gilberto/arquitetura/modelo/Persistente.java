package br.gilberto.arquitetura.modelo;

import java.io.Serializable;


public interface Persistente extends Serializable {

	public Long getId();
	
	public void setId(Long id);
}
