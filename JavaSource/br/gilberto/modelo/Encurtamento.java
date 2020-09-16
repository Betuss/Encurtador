package br.gilberto.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.gilberto.arquitetura.modelo.Persistente;

/**
 * Classe para objetos do tipo {@link Encurtamento}
 * 
 * Autor: Gilberto Soares
 */

@Entity
public class Encurtamento implements Persistente {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_encurtamento")
	@SequenceGenerator(name = "seq_encurtamento", sequenceName = "seq_encurtamento", allocationSize = 1)
	private Long id;
	
	private LocalDateTime dataCadastro;
	
	private String urlOriginal;
	
	private String codigo;
	
	@ManyToOne
	private Usuario usuario;
	
	
	/***********************
	 * 	Getters & Setters  *
	 **********************/
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
