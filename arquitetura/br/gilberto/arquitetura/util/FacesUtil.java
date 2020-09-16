package br.gilberto.arquitetura.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void addMensagem(String titulo) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(titulo));
	}
	
	public static void adicionarMensagem(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(titulo, mensagem));
	}
	
	public static void adicionarMensagem(String titulo, String mensagem, Severity severity) {
		adicionarMensagem(null, titulo, mensagem, severity);
	}
	
	public static void adicionarMensagem(String elementoId, String titulo, String mensagem, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(elementoId, 
				new FacesMessage(severity,titulo, mensagem));
	}
	
	public static void limparMensagens() {
		FacesContext.getCurrentInstance().getMessageList().clear();
	}
	
	public static void manterMensagens(boolean b) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(b);
	}
	public static void redirecionar(String pagina) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}