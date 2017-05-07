package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sysdt.lock.dto.RelacionesDTO;
import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.ChoferService;
import com.sysdt.lock.service.SupervisorEntidadService;
import com.sysdt.lock.service.UnidadService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class MasterSupView implements Serializable{

	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	@ManagedProperty("#{unidadService}")
	private UnidadService unidadService;
	@ManagedProperty("#{choferService}")
	private ChoferService choferService;
	@ManagedProperty("#{supervisorEntidadService}")
	private SupervisorEntidadService supervisorEntidadService;
	
	private UsuarioDTO userDTO;
	private List<Usuario> supervisores = new ArrayList<Usuario>();
	private String usernameSupervisor;
	private List<Usuario> operadores = new ArrayList<Usuario>();;
	private List<Usuario> operadoresSel = new ArrayList<Usuario>();;
	private List<Unidad> unidades = new ArrayList<Unidad>();;
	private List<Unidad> unidadesSel = new ArrayList<Unidad>();;
	private List<Chofer> choferes = new ArrayList<Chofer>();;
	private List<Chofer> choferesSel = new ArrayList<Chofer>();;
	
	private boolean hayCambiosEnOperadores = false;
	private boolean hayCambiosEnUnidades = false;
	private boolean hayCambiosEnChoferes  = false;
	
	@PostConstruct
	public void init(){
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.ADMINISTRADOR, Constantes.TipoUsuario.MASTER)){
			userDTO = manejoSesionView.obtenerUsuarioEnSesion();
			supervisores = usuarioService.obtenerUsuariosPorTipoYidCliente(userDTO.getIdCliente(), Constantes.TipoUsuario.SUPERVISOR);
			operadores = usuarioService.obtenerUsuariosPorTipoYidCliente(userDTO.getIdCliente(), Constantes.TipoUsuario.OPERADOR);
			unidades = unidadService.obtenerUnidadesPorIdCliente(userDTO.getIdCliente(), Constantes.OrderBy.UNIDAD_ECO);
			choferes = choferService.obtenerChoferesPorIdCliente(userDTO.getIdCliente(), true, Constantes.EstadoChofer.ACTIVO,Constantes.OrderBy.CHOFER_NOMBRE);
			Usuario sinUsuario = new Usuario();
			sinUsuario.setUsername("");
			sinUsuario.setNombre("Seleccione una opción");
			sinUsuario.setApaterno("");
			supervisores.add(0, sinUsuario);
		}else{
			manejoSesionView.cerrarSesionUsuario();
		}
		
	}
	
	public void guardarOperadores(){
		if(!validarSupervisor()){
			return;
		}
		try {
			supervisorEntidadService.insertarRelacionesSupervisorOperador(operadoresSel, usernameSupervisor);
			hayCambiosEnOperadores = false;
			MensajeGrowl.mostrar("Los operadores se guardaron exitosamente", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MensajeGrowl.mostrar("Ocurrió un error al intentar guardar operadores", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void guardarUnidades(){
		if(!validarSupervisor()){
			return;
		}
		try {
			supervisorEntidadService.insertarRelacionesSupervisorUnidad(unidadesSel, usernameSupervisor);
			hayCambiosEnUnidades = false;
			MensajeGrowl.mostrar("Las unidades se guardaron exitosamente", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MensajeGrowl.mostrar("Ocurrió un error al intentar guardar unidades", FacesMessage.SEVERITY_FATAL);
		}
	}

	public void guardarChoferes(){
		if(!validarSupervisor()){
			return;
		}
		try {
			supervisorEntidadService.insertarRelacionesSupervisorChofer(choferesSel, usernameSupervisor);
			hayCambiosEnChoferes = false;
			MensajeGrowl.mostrar("Los choferes se guardaron exitosamente", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MensajeGrowl.mostrar("Ocurrió un error al intentar guardar choferes", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void cambioSupervisor(){
		operadoresSel.clear();
		unidadesSel.clear();
		choferesSel.clear();
		deshabilitarBotonesGuardado();
		if(usernameSupervisor != null && !usernameSupervisor.trim().isEmpty()){
			RelacionesDTO relaciones = supervisorEntidadService.obtenerRelacionesPorSupervisor(usernameSupervisor, userDTO.getIdCliente(), operadores, unidades, choferes);
			operadoresSel = relaciones.getOperadoresRel();
			unidadesSel = relaciones.getUnidadesRel();
			choferesSel = relaciones.getChoferesRel();
		}
	}
	
/*	public void llenarRelaciones(){
		relacionService.obtenerRelaciones(us, idTipoEntidad);
	}  */
	
	public void activarCambio(int cambio){
		if(usernameSupervisor == null || usernameSupervisor.trim().isEmpty()){
			MensajeGrowl.mostrar("Debe seleccionar un supervisor", FacesMessage.SEVERITY_ERROR);
			return;
		}
		if(cambio == 1){
			hayCambiosEnOperadores = true;
		} else if(cambio == 2){
			hayCambiosEnUnidades = true;
		} else if(cambio == 3){
			hayCambiosEnChoferes = true;
		}
	}
	
	private boolean validarSupervisor(){
		if(usernameSupervisor == null || usernameSupervisor.trim().isEmpty()){
			MensajeGrowl.mostrar("Debe seleccionar a un supervisor", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private void revisarListaSupervisores(){
		if(supervisores == null || supervisores.size() == 0){
			usernameSupervisor = "No hay supervisores disponibles";
		}
	}
	
	private void deshabilitarBotonesGuardado(){
		hayCambiosEnOperadores = false;
		hayCambiosEnUnidades = false;
		hayCambiosEnChoferes = false;
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

	public UsuarioDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UsuarioDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<Usuario> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Usuario> supervisores) {
		this.supervisores = supervisores;
	}

	public String getUsernameSupervisor() {
		return usernameSupervisor;
	}

	public void setUsernameSupervisor(String usernameSupervisor) {
		this.usernameSupervisor = usernameSupervisor;
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public ChoferService getChoferService() {
		return choferService;
	}

	public void setChoferService(ChoferService choferService) {
		this.choferService = choferService;
	}

	public List<Usuario> getOperadores() {
		return operadores;
	}

	public void setOperadores(List<Usuario> operadores) {
		this.operadores = operadores;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public List<Chofer> getChoferes() {
		return choferes;
	}

	public void setChoferes(List<Chofer> choferes) {
		this.choferes = choferes;
	}

	public List<Usuario> getOperadoresSel() {
		return operadoresSel;
	}

	public void setOperadoresSel(List<Usuario> operadoresSel) {
		this.operadoresSel = operadoresSel;
	}

	public List<Unidad> getUnidadesSel() {
		return unidadesSel;
	}

	public void setUnidadesSel(List<Unidad> unidadesSel) {
		this.unidadesSel = unidadesSel;
	}

	public List<Chofer> getChoferesSel() {
		return choferesSel;
	}

	public void setChoferesSel(List<Chofer> choferesSel) {
		this.choferesSel = choferesSel;
	}

	public SupervisorEntidadService getSupervisorEntidadService() {
		return supervisorEntidadService;
	}

	public void setSupervisorEntidadService(
			SupervisorEntidadService supervisorEntidadService) {
		this.supervisorEntidadService = supervisorEntidadService;
	}

	public boolean isHayCambiosEnOperadores() {
		return hayCambiosEnOperadores;
	}

	public void setHayCambiosEnOperadores(boolean hayCambiosEnOperadores) {
		this.hayCambiosEnOperadores = hayCambiosEnOperadores;
	}

	public boolean isHayCambiosEnUnidades() {
		return hayCambiosEnUnidades;
	}

	public void setHayCambiosEnUnidades(boolean hayCambiosEnUnidades) {
		this.hayCambiosEnUnidades = hayCambiosEnUnidades;
	}

	public boolean isHayCambiosEnChoferes() {
		return hayCambiosEnChoferes;
	}

	public void setHayCambiosEnChoferes(boolean hayCambiosEnChoferes) {
		this.hayCambiosEnChoferes = hayCambiosEnChoferes;
	}
	
}
