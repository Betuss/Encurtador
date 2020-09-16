package br.gilberto.controle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.gilberto.arquitetura.excecao.BDException;
import br.gilberto.arquitetura.servico.ServiceCadastro;
import br.gilberto.arquitetura.util.EncurtadorUtil;
import br.gilberto.arquitetura.util.FacesUtil;
import br.gilberto.modelo.Encurtamento;
import br.gilberto.modelo.Usuario;
import br.gilberto.repositorio.EncurtamentoHibernateDAO;
import br.gilberto.repositorio.UsuarioHibernateDAO;

/**
 * Bean para tratar a tela principal do sistema, bem como o redirecionamento.
 * 
 * Autor: Gilberto Soares
 */

@ManagedBean
@ViewScoped
public class MBEncurtamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Encurtamento encurtamento;
	
	private Usuario usuario;
	
	private String urlOriginal;

	private String codigoParametro;
	
	private String codigoEncurtado;
	
	private EncurtadorUtil encurtador;
	
	@ManagedProperty(value = "#{usuarioHibernateDAO}")
	private UsuarioHibernateDAO usuarioHibernateDAO;
	
	@ManagedProperty(value = "#{encurtamentoHibernateDAO}")
	private EncurtamentoHibernateDAO encurtamentoHibernateDAO;
	
	@ManagedProperty(value = "#{serviceCadastro}")
	private ServiceCadastro serviceCadastro;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		codigoParametro = request.getParameter("c");
		
		if(codigoParametro == null) {
			if(!caminhoAtual().contains("restrito")) {
				FacesUtil.redirecionar("restrito");
			}
			encurtador = new EncurtadorUtil(6);
			definirUsuario();
			limpar();
			return;
		}
		
		encurtamento = encurtamentoHibernateDAO.findByCodigo(codigoParametro);
		
		if(encurtamento != null) {
			FacesUtil.redirecionar(encurtamento.getUrlOriginal());
		}
	}
	
	public String caminhoAtual() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		return request.getRequestURL().toString();
	}
	
	public void definirUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		usuario = usuarioHibernateDAO.findByUsername(username);
	}
	
	public void limpar() {
		urlOriginal = null;
		codigoEncurtado = null;
		encurtamento = new Encurtamento();
	}

	public void encurtarUrl() {
		if(!urlOriginal.contains("http://") && !urlOriginal.contains("https://")) {
			urlOriginal = "http://" + urlOriginal;
		}
		codigoEncurtado = encurtador.nextString();
		
		while(encurtamentoHibernateDAO.isCodigoJaUsado(codigoEncurtado)) {
			codigoEncurtado = encurtador.nextString();
		}
		
		encurtamento.setDataCadastro(LocalDateTime.now());
		encurtamento.setUrlOriginal(urlOriginal);
		encurtamento.setCodigo(codigoEncurtado);
		encurtamento.setUsuario(usuario);
		
		try {
			serviceCadastro.salvar(encurtamento);
			definirUsuario();
		} catch (BDException e) {
			FacesMessage msg = new FacesMessage("Erro ao encurtar o URL.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
	}
	
	public String getCaminhoEncurtado(String codigo) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		return (request.getRequestURL().toString() + "?c=" + codigo).replace("restrito/", "").replace("index.jsf", "");
	}
	
	public String getUltimoEncurtamento() {
		return getCaminhoEncurtado(codigoEncurtado);
	}
	
	public String dataFormatada(LocalDateTime data) {
		DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");
		return data.format(formatadorData) + " às " + data.format(formatadorHora);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Encurtamento getEncurtamento() {
		return encurtamento;
	}

	public void setEncurtamento(Encurtamento encurtamento) {
		this.encurtamento = encurtamento;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getCodigoParametro() {
		return codigoParametro;
	}

	public String getCodigoEncurtado() {
		return codigoEncurtado;
	}

	public void setCodigoEncurtado(String codigoEncurtado) {
		this.codigoEncurtado = codigoEncurtado;
	}


	public void setUsuarioHibernateDAO(UsuarioHibernateDAO usuarioHibernateDAO) {
		this.usuarioHibernateDAO = usuarioHibernateDAO;
	}
	
	public UsuarioHibernateDAO getUsuarioHibernateDAO() {
		return usuarioHibernateDAO;
	}

	public void setEncurtamentoHibernateDAO(EncurtamentoHibernateDAO encurtamentoHibernateDAO) {
		this.encurtamentoHibernateDAO = encurtamentoHibernateDAO;
	}

	public void setServiceCadastro(ServiceCadastro serviceCadastro) {
		this.serviceCadastro = serviceCadastro;
	}
}