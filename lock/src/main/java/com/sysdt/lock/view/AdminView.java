package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.springframework.beans.BeanUtils;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Cliente;
import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.TipoUsuario;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.CatalogoService;
import com.sysdt.lock.service.ChoferService;
import com.sysdt.lock.service.ClienteService;
import com.sysdt.lock.service.EmailService;
import com.sysdt.lock.service.UnidadService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class AdminView implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	@ManagedProperty("#{clienteService}")
	private ClienteService clienteService;
	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	@ManagedProperty("#{catalogoService}")
	private CatalogoService catalogoService;
	@ManagedProperty("#{unidadService}")
	private UnidadService unidadService;
	@ManagedProperty("#{emailService}")
	private EmailService emailService;
	@ManagedProperty("#{choferService}")
	private ChoferService choferService;
//	@ManagedProperty("#{userDTO}")
//	private UserDTO userDTO;
	
	private List<Cliente> clientes;
	private List<TipoUsuario> tiposUsuario;
	private List<Usuario> usuarios;
//	private Cliente cliente;
	private int idClienteTemp;
	private Cliente clienteAct;
	private int idCliente;
	private Usuario usuario;
	private Usuario usuarioSel;
	private int clienteSel;
	
	//Seccion Email
	private String email;
	private String emailSel;
	
	@PostConstruct
	public void init(){
		UsuarioDTO userDTO = null;
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.ADMINISTRADOR)){
			userDTO = manejoSesionView.obtenerUsuarioEnSesion();
			usuario = new Usuario();
			resetUsuarioSel();
			clientes = clienteService.obtenerClientes();
			tiposUsuario = catalogoService.obtenerTiposUsuario();
			clienteSel = clientes.get(0).getId();
			usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
			idCliente = clientes.size() > 0 ? clientes.get(0).getId():0;
			clienteAct = new Cliente();
			BeanUtils.copyProperties(clientes.get(0), clienteAct);
//		}
	//	UsuarioDTO userDTO = manejoSesionView.obtenerUsuarioEnSesion();
//		if(userDTO == null || userDTO.getUsername() == null ||  userDTO.getIdTipousuario() != Constantes.TipoUsuario.ADMINISTRADOR){
//			manejoSesionView.cerrarSesionUsuario();
		}else{
	//		cliente = new Cliente();
			manejoSesionView.cerrarSesionUsuario();
		}
	}
	
	public void guardarUsuario(){
		if(validarUsuario()){
			try {
				Cliente cliente = buscarClientePorId(usuario.getIdCliente());
				int periodoValidacion = cliente.getPeriodovalidacion() != null ? cliente.getPeriodovalidacion() : 0;
				boolean exito = usuarioService.guardarUsuario(usuario, email, periodoValidacion);
				if(exito){
					usuario = new Usuario();
					usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
					MensajeGrowl.mostrar("El usuario se guardó exitosamente", FacesMessage.SEVERITY_INFO);
				}else{
					MensajeGrowl.mostrar("El nombre de usuario ya existe", FacesMessage.SEVERITY_ERROR);
				}
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al guardar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}

	public void guardarCliente(){
		if(validarClienteAct()){
			try {
				generarClienteNuevo();
				clienteService.insertarCliente(clienteAct);
				clientes = clienteService.obtenerClientes();
//				cliente = new Cliente();
				MensajeGrowl.mostrar("El cliente se guardó exitosamente", FacesMessage.SEVERITY_INFO);;
			} catch (Exception e) {
				MensajeGrowl.mostrar(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void actualizarCliente(){
		if(validarClienteAct()){
			try {
				clienteService.actualizarCliente(clienteAct);
				clientes = clienteService.obtenerClientes();
			//	BeanUtils.copyProperties(clientes.get(0),clienteAct);
				MensajeGrowl.mostrar("El cliente se actualizó exitosamente", FacesMessage.SEVERITY_INFO);;
			} catch (Exception e) {
				MensajeGrowl.mostrar(e.getMessage(), FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void actualizarUsuario(){
		if(validarUsuarioSel()){
			try {
				usuarioService.actualizarUsuarioCompleto(usuarioSel, emailSel);
				usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
				MensajeGrowl.mostrar("Usuario actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al actualizar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void eliminarUsuario(){
		if(validarUsuarioSel()){
			try {
				usuarioService.eliminarUsuarioCompletoPorUsernameYidCliente(usuarioSel.getUsername(), usuarioSel.getIdCliente());
				usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
				resetUsuarioSel();
				MensajeGrowl.mostrar("Usuario eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				MensajeGrowl.mostrar("Error al eliminar usuario", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void cambiarEstatusCuentas(boolean estado){
		String msj = estado?"habilitadas":"deshabilitadas";
		String msj2 = estado?"habilitar":"deshabilitar";
		try {
			usuarioService.cambiarEstadoCuentasDeUsuario(clienteSel, estado);
			usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
			MensajeGrowl.mostrar("Todas las cuentas han sido "+msj, FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MensajeGrowl.mostrar("Error al "+msj2+" las cuentas de usuario", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void obtenerCorreos(){
		List<Email> correos = emailService.obtenerCorreosPorUsername(usuarioSel.getUsername());
		emailSel = correos.size() > 0 ? correos.get(0).getDireccion() : "";
	}
	
	public void cambioCliente(){
		usuarios = usuarioService.obtenerUsuariosPorIdCliente(clienteSel);
		resetUsuarioSel();
	}
	
	public void cambioClienteAct(){
		clienteAct = clienteService.obtenerClientePorId(idClienteTemp);
	/*	for(Cliente cl : clientes){
			if(idClienteTemp == cl.getId()){
				BeanUtils.copyProperties(cl, clienteAct);
				break;
			}
		}*/
	}
	
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
		if(usuario.getAmaterno().trim().isEmpty()){
			usuario.setAmaterno("");
		}
		if(usuario.getIdCliente() == null){
			MensajeGrowl.mostrar("Debe indicar el cliente", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuario.getIdTipousuario() == null){
			MensajeGrowl.mostrar("Debe indicar el tipo de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(!email.trim().isEmpty() && !Constantes.EMAIL_VALIDATOR.matcher(email.trim()).matches()){
			MensajeGrowl.mostrar("El email es inválido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(!verificarAumentoCuentas(usuario.getIdCliente(), usuario.getIdTipousuario())){
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
		if(usuarioSel.getAmaterno().trim().isEmpty()){
			usuarioSel.setAmaterno("");
		}
		if(!emailSel.trim().isEmpty() && !Constantes.EMAIL_VALIDATOR.matcher(emailSel.trim()).matches()){
			MensajeGrowl.mostrar("El email es inválido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(usuarioSel.getIdTipousuario() == null){
			MensajeGrowl.mostrar("Debe indicar el tipo de usuario", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private boolean validarClienteAct(){
		if(clienteAct.getNombre().trim().isEmpty() || clienteAct.getLogo().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe escribir el nombre del cliente y de la imagen", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private void resetUsuarioSel(){
		usuarioSel = new Usuario();
		usuarioSel.setUsername("");
		usuarioSel.setPassword("");
		usuarioSel.setNombre("");
		usuarioSel.setApaterno("");
		usuarioSel.setAmaterno("");
		emailSel = "";
	}
	
	public void sumarCantidad(int tipoMax){
		if(tipoMax == Constantes.TipoMax.MAX_OPERADORES){
			clienteAct.setMaxoperadores(clienteAct.getMaxoperadores() + 1);
		}else if(tipoMax == Constantes.TipoMax.MAX_SUPERVISORES){
			clienteAct.setMaxsupervisores(clienteAct.getMaxsupervisores() + 1);
		}else if(tipoMax == Constantes.TipoMax.MAX_CHOFERES){
			clienteAct.setMaxchoferes(clienteAct.getMaxchoferes() + 1);
		}else if(tipoMax == Constantes.TipoMax.MAX_UNIDADES){
			clienteAct.setMaxunidades(clienteAct.getMaxunidades() + 1);
		}else if(tipoMax == Constantes.TipoMax.MAX_MASTERS){
			clienteAct.setMaxmasters(clienteAct.getMaxmasters() + 1);
		}
	}
	
	public void restarCantidad(int tipoMax){
		if(tipoMax == Constantes.TipoMax.MAX_OPERADORES){
			if(clienteAct.getMaxoperadores() > 0) {
				int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(clienteAct.getId(), Constantes.TipoUsuario.OPERADOR);
				if(clienteAct.getMaxoperadores() <= cuentas){
					int res = (cuentas - clienteAct.getMaxoperadores())+1;
					MensajeGrowl.mostrar("Para restar, primero debe eliminar "+res+" cuenta(s) de operador", FacesMessage.SEVERITY_ERROR);
					return;
				}
				clienteAct.setMaxoperadores(clienteAct.getMaxoperadores() - 1);
			}
		}else if(tipoMax == Constantes.TipoMax.MAX_SUPERVISORES){
			if(clienteAct.getMaxsupervisores() > 0){
				int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(clienteAct.getId(), Constantes.TipoUsuario.SUPERVISOR);
				if(clienteAct.getMaxsupervisores() <= cuentas){
					int res = (cuentas - clienteAct.getMaxsupervisores())+1;
					MensajeGrowl.mostrar("Para restar, primero debe eliminar "+res+" cuenta(s) de supervisor", FacesMessage.SEVERITY_ERROR);
					return;
				}
				clienteAct.setMaxsupervisores(clienteAct.getMaxsupervisores() - 1);
			}
		}else if(tipoMax == Constantes.TipoMax.MAX_CHOFERES){
			if(clienteAct.getMaxchoferes() > 0) {
				int cuentas = choferService.obtenerCountChoferesPorIdCliente(clienteAct.getId());
				if(clienteAct.getMaxchoferes() <= cuentas){
					int res = (cuentas - clienteAct.getMaxchoferes())+1;
					MensajeGrowl.mostrar("Para restar, primero debe eliminar "+res+" chofer(es)", FacesMessage.SEVERITY_ERROR);
					return;
				}
				clienteAct.setMaxchoferes(clienteAct.getMaxchoferes() - 1);
			}
				
		}else if(tipoMax == Constantes.TipoMax.MAX_UNIDADES){
			if(clienteAct.getMaxunidades() > 0){
				int cuentas = unidadService.obtenerCountUnidadesPorIdCliente(clienteAct.getId());
				if(clienteAct.getMaxunidades() <= cuentas){
					int res = (cuentas - clienteAct.getMaxunidades())+1;
					MensajeGrowl.mostrar("Para restar, primero debe eliminar "+res+" unidad(es)", FacesMessage.SEVERITY_ERROR);
					return;
				}
				clienteAct.setMaxunidades(clienteAct.getMaxunidades() - 1);
			}
		}else if(tipoMax == Constantes.TipoMax.MAX_MASTERS){
			if(clienteAct.getMaxmasters() > 0){
				int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(clienteAct.getId(), Constantes.TipoUsuario.MASTER);
				if(clienteAct.getMaxmasters() <= cuentas){
					int res = (cuentas - clienteAct.getMaxmasters())+1;
					MensajeGrowl.mostrar("Para restar, primero debe eliminar "+res+" cuenta(s) master", FacesMessage.SEVERITY_ERROR);
					return;
				}
				clienteAct.setMaxmasters(clienteAct.getMaxmasters() - 1);
			}
		}
	}
	
	private Cliente buscarClientePorId(int idCliente){
		for(Cliente cliente : clientes){
			if(cliente.getId() == idCliente){
				return cliente;
			}
		}
		return null;
	}
	
	private void verificarReduccionCuentas(int idCliente, int idTipoCuenta){
		Cliente cliente = clienteService.obtenerClientePorId(idCliente);
		if(idTipoCuenta == Constantes.TipoEntidad.OPERADOR){
			
		}
	}
	
	/**
	 * Metodo que se utiliza para verificar la disponibilidad de cuentas de una entidad(supervisor, operador, chofer, unidad)
	 * @return
	 */
	private boolean verificarDisponibilidadCuentas(){
		return false;
	}
	
	/**
	 * Metodo que se utiliza cuando se quiere guardar una cuenta nueva de operador o supervisor.
	 * @return
	 */
	private boolean verificarAumentoCuentas(int idCliente, int idTipoUsuario){
		Cliente cliente = clienteService.obtenerClientePorId(idCliente);
		int cuentas = usuarioService.obtenerNumeroCuentasPorTipo(idCliente, idTipoUsuario);
		if(idTipoUsuario == Constantes.TipoUsuario.SUPERVISOR){
			if(cuentas >= cliente.getMaxsupervisores()){
				MensajeGrowl.mostrar("El cliente ya tiene el máximo de cuentas de supervisor autorizadas", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		} else if(idTipoUsuario == Constantes.TipoUsuario.OPERADOR){
			if(cuentas >= cliente.getMaxoperadores()){
				MensajeGrowl.mostrar("El cliente ya tiene el máximo de cuentas de operador autorizadas", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		} else if(idTipoUsuario == Constantes.TipoUsuario.MASTER){
			if(cuentas >= cliente.getMaxmasters()){
				MensajeGrowl.mostrar("El cliente ya tiene el máximo de cuentas master autorizadas", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		} else{
			MensajeGrowl.mostrar("Tipo de usuario no reconocido", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private void generarClienteNuevo(){
		clienteAct.setId(0);
		clienteAct.setMaxchoferes(0);
		clienteAct.setMaxunidades(0);
		clienteAct.setMaxoperadores(0);
		clienteAct.setMaxsupervisores(0);
		clienteAct.setMaxmasters(0);
	}
	
	public ManejoSesionView getManejoSesionView() {
		return manejoSesionView;
	}

	public void setManejoSesionView(ManejoSesionView manejoSesionView) {
		this.manejoSesionView = manejoSesionView;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
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

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<TipoUsuario> getTiposUsuario() {
		return tiposUsuario;
	}

	public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
		this.tiposUsuario = tiposUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getClienteSel() {
		return clienteSel;
	}

	public void setClienteSel(int clienteSel) {
		this.clienteSel = clienteSel;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}


	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public int getIdClienteTemp() {
		return idClienteTemp;
	}

	public void setIdClienteTemp(int idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}

	public Cliente getClienteAct() {
		return clienteAct;
	}

	public void setClienteAct(Cliente clienteAct) {
		this.clienteAct = clienteAct;
	}

	public ChoferService getChoferService() {
		return choferService;
	}

	public void setChoferService(ChoferService choferService) {
		this.choferService = choferService;
	}
	

}
