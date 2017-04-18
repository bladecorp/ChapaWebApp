package com.sysdt.lock.dto;

import java.util.List;

import com.sysdt.lock.model.Cliente;

public class ClienteEmail {

	private Cliente cliente;
	private List<UsuarioEmail> usuarios;
	
	public ClienteEmail(){}
	
	public ClienteEmail(Cliente cliente, List<UsuarioEmail>usuarios){
		this.cliente = cliente;
		this.usuarios = usuarios;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<UsuarioEmail> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioEmail> usuarios) {
		this.usuarios = usuarios;
	}
}
