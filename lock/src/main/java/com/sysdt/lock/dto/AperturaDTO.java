package com.sysdt.lock.dto;

import java.io.Serializable;

public class AperturaDTO implements Serializable{

	private static final long serialVersionUID = 5474728287541454227L;
	private String usuario;
	private String nomUsuario;
	private String nomChofer;
	private String eco;
	private String serie;
	private long tiempo;
	private String codigo;
	private boolean isWialon;
	private int idCliente;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEco() {
		return eco;
	}
	public void setEco(String eco) {
		this.eco = eco;
	}
	public long getTiempo() {
		return tiempo;
	}
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	public boolean isWialon() {
		return isWialon;
	}
	public void setWialon(boolean isWialon) {
		this.isWialon = isWialon;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	public String getNomChofer() {
		return nomChofer;
	}
	public void setNomChofer(String nomChofer) {
		this.nomChofer = nomChofer;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	
	
}
