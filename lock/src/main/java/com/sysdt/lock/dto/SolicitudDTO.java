package com.sysdt.lock.dto;

import java.io.Serializable;

public class SolicitudDTO implements Serializable{

	private static final long serialVersionUID = 4357669810836595990L;
	private int idChofer;
	private String latitud;
	private String longitud;
	
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public int getIdChofer() {
		return idChofer;
	}
	public void setIdChofer(int idChofer) {
		this.idChofer = idChofer;
	}
	
}
