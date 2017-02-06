package com.sysdt.lock.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.service.ChoferService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class ChoferView implements Serializable{
	
	@ManagedProperty("#{choferService}")
	private ChoferService choferService;
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	
	private UsuarioDTO userDTO ;
	
	private Chofer choferSel;
	private Chofer chofer;
	private List<Chofer> choferes;
	private int estado;
	
	@PostConstruct
	public void init(){
		userDTO = manejoSesionView.obtenerUsuarioEnSesion();
		if(userDTO == null || userDTO.getIdTipousuario() != Constantes.TipoUsuario.ADMINISTRADOR){
			manejoSesionView.cerrarSesionUsuario();
		}else{
			estado = 1;
			chofer = new Chofer();
			choferSel = new Chofer();
			choferes = choferService.obtenerChoferesPorIdCliente(userDTO.getIdCliente(), true, Constantes.EstadoChofer.ACTIVO);
		}
	}
	
	public void guardarNuevoChofer(){
		if(validar(false)){
			chofer.setId(0);
			chofer.setIdcliente(userDTO.getIdCliente());
			try {
				choferService.insertarChofer(chofer);
				recargarTabla();
				MensajeGrowl.mostrar("El registro se guardo exitosamente", FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				MensajeGrowl.mostrar("Ocurrio un error al guardar el registro", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void actualizarChofer(){
		
		if(validar(true)){
			try {
				boolean exito = choferService.actualizarChofer(chofer);
				if(exito){
					recargarTabla();
					MensajeGrowl.mostrar("El registro se actualizo exitosamente", FacesMessage.SEVERITY_INFO);
				}else{
					MensajeGrowl.mostrar("No fue posible actualizar el registro", FacesMessage.SEVERITY_ERROR);
				}
			} catch (Exception e) {
				MensajeGrowl.mostrar("Ocurrio un error al guardar el registro", FacesMessage.SEVERITY_FATAL);
			}
		}
	}
	
	public void recargarTabla(){
		if(estado == Constantes.EstadoChofer.TODOS){
			choferes = choferService.obtenerChoferesPorIdCliente(userDTO.getIdCliente(),false, false);
		}else{
			boolean nuevoEstado = estado == 1;
			choferes = choferService.obtenerChoferesPorIdCliente(userDTO.getIdCliente(), true, nuevoEstado);
		}
		if(chofer != null){
			chofer.setId(0);
			limpiarCampos();
		}
	}
	
	private boolean validar(boolean isEditar){
		if(isEditar){
			if(chofer.getId() == null || chofer.getId() == 0){
				MensajeGrowl.mostrar("Debe seleccionar un chofer", FacesMessage.SEVERITY_ERROR);
				return false;
			}
		}
		if(chofer.getNombre() == null || chofer.getNombre().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el nombre", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(chofer.getApaterno() == null || chofer.getApaterno().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el apellido paterno", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if(chofer.getAmaterno() == null || chofer.getAmaterno().trim().isEmpty()){
			MensajeGrowl.mostrar("Debe indicar el apellido materno", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	public void limpiarCampos(){
		chofer.setNombre("");
		chofer.setApaterno("");
		chofer.setAmaterno("");
	}
	
	public void copiarChofer(){
		chofer.setId(choferSel.getId());
		chofer.setNombre(choferSel.getNombre());
		chofer.setApaterno(choferSel.getApaterno());
		chofer.setAmaterno(choferSel.getAmaterno());
		chofer.setEnabled(choferSel.getEnabled());
		chofer.setIdcliente(choferSel.getIdcliente());
	}

	public ChoferService getChoferService() {
		return choferService;
	}

	public void setChoferService(ChoferService choferService) {
		this.choferService = choferService;
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

	public Chofer getChofer() {
		return chofer;
	}

	public void setChofer(Chofer chofer) {
		this.chofer = chofer;
	}

	public List<Chofer> getChoferes() {
		return choferes;
	}

	public void setChoferes(List<Chofer> choferes) {
		this.choferes = choferes;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Chofer getChoferSel() {
		return choferSel;
	}

	public void setChoferSel(Chofer choferSel) {
		this.choferSel = choferSel;
	}
	
	

}
