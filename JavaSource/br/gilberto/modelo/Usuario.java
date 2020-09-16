package br.gilberto.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.gilberto.arquitetura.modelo.Persistente;

/**
 * Classe para objetos do tipo {@link Usuario}
 * 
 * Autor: Gilberto Soares
 */

@Entity
public class Usuario implements Persistente {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "usuario_id")
	private List<Encurtamento> encurtamentos;

	
	
	/***********************
	 * 	Getters & Setters  *
	 **********************/
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Encurtamento> getEncurtamentos() {
		return encurtamentos;
	}

	public void setEncurtamentos(List<Encurtamento> encurtamentos) {
		this.encurtamentos = encurtamentos;
	}
}
