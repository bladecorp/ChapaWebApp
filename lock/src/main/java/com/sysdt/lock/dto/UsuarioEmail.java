package com.sysdt.lock.dto;

import java.util.List;

import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.Usuario;

public class UsuarioEmail {
	
	private Usuario usuario;
	private List<Email> correos;
	
	public UsuarioEmail(){
	}
	
	public UsuarioEmail(Usuario usuario, List<Email> correos){
		this.usuario = usuario;
		this.correos = correos;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Email> getCorreos() {
		return correos;
	}
	public void setCorreos(List<Email> correos) {
		this.correos = correos;
	}
	
}
