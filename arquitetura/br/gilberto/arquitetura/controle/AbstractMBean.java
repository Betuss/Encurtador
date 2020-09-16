package br.gilberto.arquitetura.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.gilberto.arquitetura.dados.GenericDao;
import br.gilberto.arquitetura.modelo.Persistente;

public abstract class AbstractMBean<T extends Persistente> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected T obj;
	protected GenericDao dao;
	
	@ManagedProperty(value="#{roleVoter}")
	private RoleVoter roleVoter;
	
	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	/**
	 * Get FacesContext.
	 * 
	 * @return FacesContext
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Get FacesContext.
	 * 
	 * @return FacesContext
	 */
	public Application getApplication() {
		return getFacesContext().getApplication();
	}
	
	/**
	 * Acessa o external context do JavaServer Faces
	 **/
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	/**
	 * Recupera um parâmetro em Reques
	 */
	public String getParameter(String param) {
		return getCurrentRequest().getParameter(param);
	}
	
	
	/**
	 * Recupera um parâmetro em Request e retorna como Integer
	 */
	public Integer getParameterInt(String param) {
		String value = getParameter(param);
		Integer result = null;

		try {
			result = Integer.parseInt(value);
		} catch(Exception e) {	}
		
		return result;
	}

	/**
	 * Recupera um parâmetro em Request e retorna como Boolean
	 */
	public Boolean getParameterBoolean(String param) {
		return Boolean.valueOf(getParameter(param));
	}
	
	/**
	 * Possibilita o acesso ao HttpServletRequest.
	 */
	public HttpServletRequest getCurrentRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	/**
	 * Possibilita o acesso ao HttpServletRequest.
	 */
	public HttpServletResponse getCurrentResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	public boolean isExisteMensagens(){
		if (FacesContext.getCurrentInstance().getMessages().hasNext()){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * Remove all jsf mnessages
	 * 
	 * @param msg
	 *            info message string
	 */
	public  void removeAllMessagens() {
		FacesContext ctx = getFacesContext();
		Iterator<FacesMessage> it = ctx.getMessages();
		while(it.hasNext()){
			it.next();
			it.remove();
		}
	}
	
	/**
	 * Add JSF info message.
	 * 
	 * @param msg
	 *            info message string
	 */
	public  void addFacesInformationMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}
	
	/**
	 * Add global JSF info message.
	 * 
	 * @param msg
	 */
	public  void addGlobalInformationMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
		ctx.addMessage(null, fm);
	}

	/**
	 * Add JSF error message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	/**
	 * Add JSF error message for a specific attribute.
	 * 
	 * @param attrName
	 *            name of attribute
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String attrName, String msg) {
		// TODO: Need a way to associate attribute specific messages
		// with the UIComponent's Id! For now, just using the view id.
		// TODO: make this use the internal getMessageFromBundle?
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				attrName, msg);
		ctx.addMessage(getRootViewComponentId(), fm);
	}
	
	/**
	 * Add JSF error message for a specific attribute
	 * and a specific component
	 * 
	 * @param attrName
	 *            name of attribute
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String componentId, String attrName, String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				attrName, msg);
		ctx.addMessage(componentId, fm);
	}

	/**
	 * Add JSF warn message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesWarnMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	/**
	 * Add JSF warn message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesFatalMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	// Informational getters

	/**
	 * Get view id of the view root.
	 * 
	 * @return view id of the view root
	 */
	public  String getRootViewId() {
		return getFacesContext().getViewRoot().getViewId();
	}

	/**
	 * Get component id of the view root.
	 * 
	 * @return component id of the view root
	 */
	public  String getRootViewComponentId() {
		return getFacesContext().getViewRoot().getId();
	}

	/**
	 * Retorna o contexto atual da aplicação do Spring, onde com isso será possível acessar DAO, MBeans e Processadores
	 * @return
	 */
	public ApplicationContext getApplicationContext(){
		WebApplicationContext appContext =  WebApplicationContextUtils.getRequiredWebApplicationContext(getCurrentRequest().getSession().getServletContext());
		
		return appContext;
	}
	
	/**
	 * Retorna um dao a partir de sua classe
	 * 
	 * @param daoName
	 * @return
	 */
	public Object getBean(String beanName){
		return getApplicationContext().getBean(beanName);
	}
	
	public void checkRole(String ... roles) {
		
		List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(roles.length);

        for(String token : roles) {
            attributes.add(new SecurityConfig(token));
        }
        
        int valor = roleVoter.vote(SecurityContextHolder.getContext().getAuthentication(), this, attributes);
		
		if(valor == AccessDecisionVoter.ACCESS_DENIED)
			throw new AccessDeniedException("Usuário não autorizado a realizar a operação");
	}
	
	public void setRoleVoter(RoleVoter roleVoter) {
		this.roleVoter = roleVoter;
	}

	public GenericDao getDao() {
		if(dao == null)
			dao = (GenericDao) getBean("genericDao");
		return dao;
	}

	public void setDao(GenericDao dao) {
		this.dao = dao;
	}
	
	/** Author: Marcel. Se der merda, a culpa foi dele
	 * Permite alcançar o IP real do cliente. Cuidado com spoofings **/
	
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  if (ip == null) {
	    	ip = request.getRemoteAddr();
	    }
	    return ip;  
	}
}
