package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.sysdt.lock.dto.RelacionesDTO;
import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.AperturaService;
import com.sysdt.lock.service.ChoferService;
import com.sysdt.lock.service.SupervisorEntidadService;
import com.sysdt.lock.service.UnidadService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;
import com.sysdt.lock.util.Security;

@ManagedBean
@ViewScoped
public class UserView implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty("#{unidadService}")
	private UnidadService unidadService;
	
	@ManagedProperty("#{aperturaService}")
	private AperturaService aperturaService;
	
	@ManagedProperty("#{choferService}")
	private ChoferService choferService;
	
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	
	@ManagedProperty("#{supervisorEntidadService}")
	private SupervisorEntidadService supervisorEntidadService;
	
	private String clave1;
	private String clave2;
	private String codigo;
	private UsuarioDTO usuarioDTO;
	private List<Unidad> unidades;
	private List<Chofer> choferes;
	private int idUnidad;
	private int idChofer;
	private int tipoServicio;
	
	@PostConstruct
	public void init(){
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.TODOS)){
			usuarioDTO = manejoSesionView.obtenerUsuarioEnSesion();
			if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.ADMINISTRADOR || usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.MASTER){
				unidades = unidadService.obtenerUnidadesPorIdCliente(usuarioDTO.getIdCliente(), Constantes.OrderBy.UNIDAD_ECO);
				choferes = choferService.obtenerChoferesPorIdCliente(usuarioDTO.getIdCliente(), true, Constantes.EstadoChofer.ACTIVO, Constantes.OrderBy.CHOFER_NOMBRE);
			} else {
				List<Unidad> units = unidadService.obtenerUnidadesPorIdCliente(usuarioDTO.getIdCliente(),Constantes.OrderBy.UNIDAD_ECO);
				List<Chofer> chofs = choferService.obtenerChoferesPorIdCliente(usuarioDTO.getIdCliente(), true, Constantes.EstadoChofer.ACTIVO, Constantes.OrderBy.CHOFER_NOMBRE);
				List<Usuario> ops = new ArrayList<Usuario>();
				RelacionesDTO relacionInicial = null;
				if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.SUPERVISOR){
					relacionInicial = supervisorEntidadService.obtenerRelacionesPorSupervisor(usuarioDTO.getUsername(), usuarioDTO.getIdCliente(), ops, units, chofs);
				} else{
					relacionInicial = supervisorEntidadService.obtenerRelacionesPorOperador(usuarioDTO.getUsername(), usuarioDTO.getIdCliente(), units, chofs);
				}
				unidades = relacionInicial.getUnidadesRel();
				choferes = relacionInicial.getChoferesRel();
			}
				
		//	unidades = unidadService.obtenerUnidadesPorIdCliente(usuarioDTO.getIdCliente());
			if(unidades.isEmpty()){
				unidades.add(generarUnidadVacia());
			}
	//		choferes = choferService.obtenerChoferesPorIdCliente(usuarioDTO.getIdCliente(), true, Constantes.EstadoChofer.ACTIVO);
			if(choferes.isEmpty()){
				choferes.add(generarChoferVacio());
			}
			if(usuarioDTO.getIdTipousuario() == 1){tipoServicio=1;}else{tipoServicio=2;}
		}else{
			manejoSesionView.cerrarSesionUsuario();
		}
	}
	
	public void abrirChapa(){
		Unidad unit = obtenerUnidadPorId();
		if(unit == null){
			MensajeGrowl.mostrar("Debe seleccionar una unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		Chofer chofer = obtenerChoferPorId();
		if(chofer == null){
			MensajeGrowl.mostrar("Debe seleccionar un chofer", FacesMessage.SEVERITY_WARN);
			return;
		}
		
		try {
			chofer = choferService.obtenerChoferPorId(idChofer);
			if(chofer.getToken() == null || chofer.getToken().trim().isEmpty()){
				MensajeGrowl.mostrar("El chofer no está habilitado para realizar la apertura remota", FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			if(validarClaves()){
				Security security = new Security();
				codigo = security.convertirLlaves(clave1, clave2, usuarioDTO.getIdCliente());
				RequestContext.getCurrentInstance().execute("PF('dlg').show();");
				if(codigo != null && !codigo.trim().isEmpty()){
					aperturaService.enviarSolicitudDeApertura(chofer, unit, usuarioDTO.getUsername(), 
						usuarioDTO.getCliente().getIswialon(), codigo, nombreUsuario(), usuarioDTO.getIdCliente());
					MensajeGrowl.mostrar("La solicitud de apertura fue enviada, "
					+ "pero esta sujeta a la cobertura de la compania de telefonia celular", FacesMessage.SEVERITY_INFO);
				}else{
					MensajeGrowl.mostrar("Las claves son incorrectas", FacesMessage.SEVERITY_ERROR);
				}
			}
			
		} catch (Exception e) {
			MensajeGrowl.mostrar("No fue posible enviar la solicitud de apertura remota", FacesMessage.SEVERITY_FATAL);
		}
		
	}
	
	public void generarCodigo(){
		Unidad unidad = obtenerUnidadPorId();
		if(unidad == null){
			MensajeGrowl.mostrar("Debe seleccionar una unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		Chofer chofer = obtenerChoferPorId();
		if(chofer == null){
			MensajeGrowl.mostrar("Debe seleccionar un chofer", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(validarClaves()){
			try {
				codigo = usuarioService.generarCodigo(clave1, clave2, usuarioDTO.getUsername(), unidad.getEco() , usuarioDTO.getIdCliente(), chofer.getId());
				RequestContext.getCurrentInstance().execute("PF('dlg').show();");
			} catch (Exception e) {
				MensajeGrowl.mostrar("Ocurrió un error al generar el código: "+e.getMessage(), FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	private boolean validarClaves(){
		if(clave1.trim().isEmpty() || clave2.trim().isEmpty()){
			MensajeGrowl.mostrar("Debe ingresar las dos claves", FacesMessage.SEVERITY_WARN);
			return false;
		}
	
		return true;
	}
	
	private Unidad obtenerUnidadPorId(){
		if(idUnidad == Constantes.LISTA_UNIDADES_VACIA){
			return null;
		}
		for(Unidad unidad : unidades){
			if(unidad.getId() == idUnidad){
				return unidad;
			}
		}
		return null;
	}
	
	private Chofer obtenerChoferPorId(){
		if(idChofer == Constantes.LISTA_UNIDADES_VACIA){
			return null;
		}
		for(Chofer chofer : choferes){
			if(chofer.getId() == idChofer){
				return chofer;
			}
		}
		return null;
	}
	
	private Unidad generarUnidadVacia(){
		Unidad unidad = new Unidad();
		unidad.setId(Constantes.LISTA_UNIDADES_VACIA);
		unidad.setEco("No hay unidades disponibles");
		return unidad;
	}
	
	private Chofer generarChoferVacio(){
		Chofer chofer = new Chofer();
		chofer.setId(Constantes.LISTA_UNIDADES_VACIA);
		chofer.setNombre("No hay choferes disponibles");
		chofer.setApaterno("");
		chofer.setAmaterno("");
		return chofer;
	}
	
	private String nombreUsuario(){
		return usuarioDTO.getNombre()+" "+usuarioDTO.getApaterno()+" "+(usuarioDTO.getAmaterno()!=null?usuarioDTO.getAmaterno():"");
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public String getClave1() {
		return clave1;
	}

	public void setClave1(String clave1) {
		this.clave1 = clave1;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ManejoSesionView getManejoSesionView() {
		return manejoSesionView;
	}

	public void setManejoSesionView(ManejoSesionView manejoSesionView) {
		this.manejoSesionView = manejoSesionView;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public int getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public AperturaService getAperturaService() {
		return aperturaService;
	}

	public void setAperturaService(AperturaService aperturaService) {
		this.aperturaService = aperturaService;
	}

	public List<Chofer> getChoferes() {
		return choferes;
	}

	public void setChoferes(List<Chofer> choferes) {
		this.choferes = choferes;
	}

	public ChoferService getChoferService() {
		return choferService;
	}

	public void setChoferService(ChoferService choferService) {
		this.choferService = choferService;
	}

	public int getIdChofer() {
		return idChofer;
	}

	public void setIdChofer(int idChofer) {
		this.idChofer = idChofer;
	}

	public SupervisorEntidadService getSupervisorEntidadService() {
		return supervisorEntidadService;
	}

	public void setSupervisorEntidadService(
			SupervisorEntidadService supervisorEntidadService) {
		this.supervisorEntidadService = supervisorEntidadService;
	}

	public int getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(int tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	
}
