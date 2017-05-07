package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.service.UnidadService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class UnidadView implements Serializable{

	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{unidadService}")
	private UnidadService unidadService;
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	
	private UsuarioDTO userDTO;
	private List<Unidad> unidades;
	private Unidad unidad;
	
	@PostConstruct
	public void init(){
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.ADMINISTRADOR, Constantes.TipoUsuario.MASTER)){
			userDTO = manejoSesionView.obtenerUsuarioEnSesion();
			unidad = new Unidad();
			unidades = new ArrayList<Unidad>();
			cargarUnidadesDelCliente();
		}else{
			manejoSesionView.cerrarSesionUsuario();
		}
	}
	
	public boolean validarMaxUnidades(){
		int count = unidadService.obtenerCountUnidadesPorIdCliente(userDTO.getIdCliente());
		int max = userDTO.getCliente().getMaxunidades();
		if(count >= max){
			MensajeGrowl.mostrar("Solo tiene contratadas "+max+" unidades", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	public void guardarUnidad(){
		if(userDTO.getIdCliente() == 0){
			MensajeGrowl.mostrar("Debe seleccionar un cliente", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad.getEco() == null || unidad.getEco().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe escribir el número económico o las placas de la unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad.getSerie() == null || unidad.getSerie().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe escribir el número de serie de la unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(!validarMaxUnidades()){
			return;
		}
		
		unidad.setId(0);
		unidad.setEco(unidad.getEco().trim());
		unidad.setSerie(unidad.getSerie().trim());
		unidad.setIdcliente(userDTO.getIdCliente());
		try{
			unidadService.guardarNuevaUnidad(unidad);
			cargarUnidadesDelCliente();
			MensajeGrowl.mostrar("La unidad fue agregada exitosamente", FacesMessage.SEVERITY_INFO);
		}catch(Exception e){
			MensajeGrowl.mostrar("Ocurrió un error al guardar la unidad", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void actualizarUnidad(){
		if(userDTO.getIdCliente() == 0){
			MensajeGrowl.mostrar("Debe seleccionar un cliente", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidades.size() == Constantes.LISTA_UNIDADES_VACIA){
			MensajeGrowl.mostrar("No hay unidades vinculadas a la cuenta", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad == null || unidad.getId() == null || unidad.getId() == 0){
			MensajeGrowl.mostrar("Debe seleccionar una unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad.getEco() == null || unidad.getEco().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe escribir el nuevo número económico o las placas de la unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad.getSerie() == null || unidad.getSerie().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe escribir el número de serie de la unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		
		try{
			unidad.setEco(unidad.getEco().trim());
			unidad.setSerie(unidad.getSerie().trim());
			unidad.setIdcliente(userDTO.getIdCliente());
			
			boolean exito = unidadService.actualizarUnidad(unidad);
			if(exito){
				cargarUnidadesDelCliente();
				MensajeGrowl.mostrar("La unidad fue actualizada exitosamente", FacesMessage.SEVERITY_INFO);
			}else{
				MensajeGrowl.mostrar("No se pudo actualizar porque no se encontró el registro de la unidad", FacesMessage.SEVERITY_ERROR);
			}
			
		}catch(Exception e){
			MensajeGrowl.mostrar("Ocurrió un error al guardar la unidad", FacesMessage.SEVERITY_FATAL);
		}
		
	}
	
	public void eliminarUnidad(){
		if(unidades.size() == Constantes.LISTA_UNIDADES_VACIA){
			MensajeGrowl.mostrar("No hay unidades vinculadas a la cuenta", FacesMessage.SEVERITY_WARN);
			return;
		}
		if(unidad == null || unidad.getId() == null || unidad.getId() == 0){
			MensajeGrowl.mostrar("Debe seleccionar una unidad", FacesMessage.SEVERITY_WARN);
			return;
		}
		try{
			boolean exito = unidadService.eliminarUnidad(unidad.getId());
			if(exito){
				unidad = new Unidad();
				cargarUnidadesDelCliente();
				MensajeGrowl.mostrar("La unidad fue eliminada exitosamente", FacesMessage.SEVERITY_INFO);
			}else{
				MensajeGrowl.mostrar("No se pudo eliminar porque no se encontró el registro de la unidad", FacesMessage.SEVERITY_FATAL);
			}
		}catch(Exception e){
			MensajeGrowl.mostrar("Ocurrió un error al eliminar la unidad", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void cargarUnidadesDelCliente(){
		unidades = unidadService.obtenerUnidadesPorIdCliente(userDTO.getIdCliente(),Constantes.OrderBy.UNIDAD_ECO);
		
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public ManejoSesionView getManejoSesionView() {
		return manejoSesionView;
	}

	public void setManejoSesionView(ManejoSesionView manejoSesionView) {
		this.manejoSesionView = manejoSesionView;
	}

	public UsuarioDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UsuarioDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	
}
