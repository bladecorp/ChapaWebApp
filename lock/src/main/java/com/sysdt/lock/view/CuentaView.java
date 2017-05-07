package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.TipoUsuario;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.CatalogoService;
import com.sysdt.lock.service.EmailService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class CuentaView implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	@ManagedProperty("#{catalogoService}")
	private CatalogoService catalogoService;
	@ManagedProperty("#{emailService}")
	private EmailService emailService;
	
	private UsuarioDTO userDTO ;
	private List<TipoUsuario> tiposUsuario;
	private List<Usuario> usuarios;
	private Usuario usuario;
	private Usuario usuarioSel;
	private String email;
	private String emailSel;
	
	@PostConstruct
	public void init(){
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.ADMINISTRADOR, Constantes.TipoUsuario.MASTER)){
			userDTO = manejoSesionView.obtenerUsuarioEnSesion();
			usuario = new Usuario();
			resetUsuarioSel();
			tiposUsuario = catalogoService.obtenerTiposUsuarioSinAdmin();
			usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(userDTO.getIdCliente());
		}else{
			manejoSesionView.cerrarSesionUsuario();
		}
	}
	
	public void guardarUsuario(){
		if(validarUsuario()){
			try {
				int periodoValidacion = userDTO.getCliente().getPeriodovalidacion() != null ? userDTO.getCliente().getPeriodovalidacion() : 0;
				usuario.setIdCliente(userDTO.getIdCliente());
				boolean exito = usuarioService.guardarUsuario(usuario, email, periodoValidacion);
				if(exito){
					usuario = new Usuario();
					usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(userDTO.getIdCliente());
					MensajeGrowl.mostrar("El usuario se guardó exitosamente", FacesMessage.SEVERITY_INFO);
				}else{
					MensajeGrowl.mostrar("El nombre de usuario ya existe", FacesMessage.SEVERITY_ERROR);
				}
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al guardar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void actualizarUsuario(){
		if(validarUsuarioSel()){
			try {
				usuarioService.actualizarUsuarioCompleto(usuarioSel, emailSel);
				usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(userDTO.getIdCliente());
				MensajeGrowl.mostrar("Usuario actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al actualizar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void eliminarUsuario(){
		if(validarUsuarioSel()){
			try {
				usuarioService.eliminarUsuarioCompletoPorUsernameYidCliente(usuarioSel.getUsername(), userDTO.getIdCliente());
				usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(userDTO.getIdCliente());
				resetUsuarioSel();
				MensajeGrowl.mostrar("Usuario eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al eliminar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	/*
	public void cambiarEstatusCuentas(boolean estado){
		String msj = estado?"habilitadas":"deshabilitadas";
		String msj2 = estado?"habilitar":"deshabilitar";
		try {
			usuarioService.cambiarEstadoCuentasDeUsuario(userDTO.getIdCliente(), estado);
			usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(userDTO.getIdCliente());
			MensajeGrowl.mostrar("Todas las cuentas han sido "+msj, FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MensajeGrowl.mostrar("Error al "+msj2+" las cuentas de usuario", FacesMessage.SEVERITY_FATAL);
		}
	}  */
	
	public boolean validarUsuario(){
		if(usuario.getUsername().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el nombre de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuario.getPassword().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el password", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuario.getNombre().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el nombre", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuario.getApaterno().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el apellido paterno", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuario.getAmaterno() == null || usuario.getAmaterno().trim().isEmpty()){
			usuario.setAmaterno("");
		}
		if(usuario.getIdTipousuario() == null){
			MensajeGrowl.mostrar("Debe indicar el tipo de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(!email.trim().isEmpty() && !Constantes.EMAIL_VALIDATOR.matcher(email.trim()).matches()){
			MensajeGrowl.mostrar("El email es inválido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(!validarMaxCuentas(usuario)){
			return false;
		}
		
		return true;
	}
	
	public boolean validarUsuarioSel(){
		if(usuarioSel.getIdCliente() == null){
			MensajeGrowl.mostrar("Debe seleccionar un usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getUsername().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el nombre de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getPassword().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el password", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getNombre().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el nombre", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getApaterno().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el apellido paterno", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getAmaterno() == null || usuarioSel.getAmaterno().trim().isEmpty()){
			usuario.setAmaterno("");
		}
		if(!emailSel.trim().isEmpty() && !Constantes.EMAIL_VALIDATOR.matcher(emailSel.trim()).matches()){
			MensajeGrowl.mostrar("El email es inválido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getIdTipousuario() == null){
			MensajeGrowl.mostrar("Debe indicar el tipo de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(!validarMaxCuentas(usuarioSel)){
			return false;
		}
		
		return true;
	}
	
	private boolean validarMaxCuentas(Usuario us){
		
		if(us.getIdTipousuario() == Constantes.TipoUsuario.SUPERVISOR){
			int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(userDTO.getIdCliente(), Constantes.TipoUsuario.SUPERVISOR);
			int max = userDTO.getCliente().getMaxsupervisores();
			if(cuentas >= max){
				MensajeGrowl.mostrar("Solo tiene contratadas "+max+" cuentas de supervisor", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		}
		
		if(us.getIdTipousuario() == Constantes.TipoUsuario.OPERADOR){
			int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(userDTO.getIdCliente(), Constantes.TipoUsuario.OPERADOR);
			int max = userDTO.getCliente().getMaxoperadores();
			if(cuentas >= max){
				MensajeGrowl.mostrar("Solo tiene contratadas "+max+" cuentas de operador", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		}
		
		return true;
	}
	
	public void obtenerCorreos(){
		List<Email> correos = emailService.obtenerCorreosPorUsername(usuarioSel.getUsername());
		emailSel = correos.size() > 0 ? correos.get(0).getDireccion() : "";
	}
	
	private void resetUsuarioSel(){
		usuarioSel = new Usuario();
		usuarioSel.setUsername("");
		usuarioSel.setPassword("");
		emailSel = "";
	}

	public ManejoSesionView getManejoSesionView() {
		return manejoSesionView;
	}

	public void setManejoSesionView(ManejoSesionView manejoSesionView) {
		this.manejoSesionView = manejoSesionView;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public CatalogoService getCatalogoService() {
		return catalogoService;
	}

	public void setCatalogoService(CatalogoService catalogoService) {
		this.catalogoService = catalogoService;
	}

	public UsuarioDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UsuarioDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<TipoUsuario> getTiposUsuario() {
		return tiposUsuario;
	}

	public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
		this.tiposUsuario = tiposUsuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailSel() {
		return emailSel;
	}

	public void setEmailSel(String emailSel) {
		this.emailSel = emailSel;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
}
