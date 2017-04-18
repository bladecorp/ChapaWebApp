package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Cliente;
import com.sysdt.lock.service.ClienteService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class LoginView implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty("#{clienteService}")
	private ClienteService clienteService;
	
	private String username;
	private String password;
	private UsuarioDTO usuarioDTO;
	private String nombreCliente;
	private Cliente cliente;
	
	//para cambiar password
	private boolean editar;
	private String passNuevo;
	private String passConf;
	
	@PostConstruct
	public void init(){ 
	//	userDTO = new UserDTO();
	}
	
	public void cambiarPassword(boolean seEdita){
		editar = seEdita;
		username = "";
		password = "";
		passNuevo = "";
		passConf = "";
	}
	
	private boolean validarDatosActualizacion(){
		if(username.trim().isEmpty()){
			MensajeGrowl.mostrar("Indique el usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(password.trim().isEmpty()){
			MensajeGrowl.mostrar("Indique el password actual", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(passNuevo.trim().isEmpty()){
			MensajeGrowl.mostrar("Indique el nuevo password", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(passConf.trim().isEmpty()){
			MensajeGrowl.mostrar("Confirme el nuevo password", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(!passNuevo.contentEquals(passConf)){
			MensajeGrowl.mostrar("El nuevo password y la confirmación no coinciden", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		return true;
	}
	
	public void actualizarPassword(){
		if(validarDatosActualizacion()){
			try {
				usuarioService.actualizarPassword(username, password, passNuevo.trim());
				MensajeGrowl.mostrar("Password actualizado exitosamente", FacesMessage.SEVERITY_INFO);
				editar = false;
			} catch (Exception e) {
				MensajeGrowl.mostrar(e.getMessage(), FacesMessage.SEVERITY_ERROR);
			}
		}
		RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
	}
	
	public void ingresar(){
		if(username.trim().length() > 0 && password.trim().length() > 0){
			try {
				String clientName = nombreCliente!=null?nombreCliente.trim():"SYSDT";
				usuarioDTO = usuarioService.obtenerYvalidarUsuario(username.trim(), password.trim(), clientName);
				guardarUsuarioEnSesion();
			} catch (Exception e) {
				RequestContext.getCurrentInstance().addCallbackParam("mensaje", e.getMessage());
				MensajeGrowl.mostrar(e.getMessage(), FacesMessage.SEVERITY_ERROR);
				RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
			}
		}else{
			MensajeGrowl.mostrar("Debe escribir usuario y password", FacesMessage.SEVERITY_ERROR);
			RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
		}
	}
	
	private void guardarUsuarioEnSesion(){
		try{
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("usuario",usuarioDTO);
			if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.ADMINISTRADOR){
				externalContext.redirect("admin.xhtml");
			}else if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.SUPERVISOR ||
							usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.MASTER){
				externalContext.redirect("supervision.xhtml");
			}else if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.OPERADOR){
				externalContext.redirect("codigos.xhtml");
			}else{
				externalContext.redirect("login.xhtml");
			}
			
		}catch(Exception e){
		//	RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
			MensajeGrowl.mostrar("Ocurrió una excepción: "+e.getMessage(), FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void recibirParametro(){
		System.out.println("Nombre cliente: "+nombreCliente);
		if(nombreCliente != null){
			cliente = clienteService.obtenerClientePorNombre(nombreCliente);
			if(cliente == null){
				inicializarCliente();
			}
		}else{
			inicializarCliente();
		}
	}
	
	private void inicializarCliente(){
		cliente = new Cliente();
		cliente.setAlto("140");
		cliente.setAncho(320);
		cliente.setNombre("SYSDT");
		cliente.setLogo("sysdt.png");
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
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

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getPassNuevo() {
		return passNuevo;
	}

	public void setPassNuevo(String passNuevo) {
		this.passNuevo = passNuevo;
	}

	public String getPassConf() {
		return passConf;
	}

	public void setPassConf(String passConf) {
		this.passConf = passConf;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
}
