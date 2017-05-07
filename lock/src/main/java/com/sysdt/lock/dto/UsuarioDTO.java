package com.sysdt.lock.dto;

import java.io.Serializable;

import com.sysdt.lock.model.Cliente;

public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1228694890109458290L;

	private String username;

    private Boolean enabled;

    private Integer idCliente;

    private Integer idTipousuario;
    
    private Cliente cliente;
    
    private int opcionSel;
    
    private String nombre;
    
    private String apaterno;
    
    private String amaterno;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdTipousuario() {
		return idTipousuario;
	}

	public void setIdTipousuario(Integer idTipousuario) {
		this.idTipousuario = idTipousuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getOpcionSel() {
		return opcionSel;
	}

	public void setOpcionSel(int opcionSel) {
		this.opcionSel = opcionSel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApaterno() {
		return apaterno;
	}

	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getAmaterno() {
		return amaterno;
	}

	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}
    
    
    
}
