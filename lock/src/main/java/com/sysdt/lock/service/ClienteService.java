package com.sysdt.lock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.ClienteMapper;
import com.sysdt.lock.dao.UsuarioMapper;
import com.sysdt.lock.dto.ClienteEmail;
import com.sysdt.lock.dto.UsuarioEmail;
import com.sysdt.lock.model.Cliente;
import com.sysdt.lock.model.ClienteExample;
import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.model.UsuarioExample;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.Utilerias;

@Service
@Transactional
public class ClienteService {

	@Autowired
	private ClienteMapper clienteMapper;
	@Autowired
	private UsuarioMapper usuarioMapper;
	@Autowired
	private EmailService emailService;
	
	public Cliente obtenerClientePorId(int id){
		return clienteMapper.selectByPrimaryKey(id);
	}
	
	public List<Cliente> obtenerClientes(){
		return clienteMapper.selectByExample(null);
	}
	
	public Cliente obtenerClientePorNombre(String nombreCliente) {
		ClienteExample exCliente = new ClienteExample();
		exCliente.createCriteria().andNombreEqualTo(nombreCliente);
		 List<Cliente> clientes = clienteMapper.selectByExample(exCliente);
		 return clientes.size()>0?clientes.get(0):null;
	}
	
	public void insertarCliente(Cliente cliente)throws Exception{
		clienteTrim(cliente);
		cliente.setAlto("140");
		cliente.setAncho(320);
		clienteMapper.insert(cliente);
	}
	private void clienteTrim(Cliente cliente){
		cliente.setNombre(cliente.getNombre().trim().toUpperCase());
		cliente.setLogo(cliente.getLogo().trim().toLowerCase());
	}

	public List<ClienteEmail> obtenerListaCorreo(){
		List<ClienteEmail> clientesEmail = new ArrayList<ClienteEmail>();
		List<Cliente> clientes = obtenerClientesCorreo();
		for (Cliente cliente : clientes) {
			List<UsuarioEmail> usuariosEmail = obtenerUsuarioEmailPorCliente(cliente.getId());
			if(usuariosEmail.size() > 0){
				ClienteEmail clienteEmail = new ClienteEmail(cliente, usuariosEmail);
				clientesEmail.add(clienteEmail);
			}
		}
		return clientesEmail;
	}
	
	private List<Cliente> obtenerClientesCorreo(){
		ClienteExample exCliente = new ClienteExample();
		exCliente.createCriteria().andPeriodovalidacionNotEqualTo(0);
		return clienteMapper.selectByExample(exCliente);
	}
	
	private List<UsuarioEmail> obtenerUsuarioEmailPorCliente(int idCliente){
		List<UsuarioEmail> usersEmail = new ArrayList<UsuarioEmail>();
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdClienteEqualTo(idCliente).andEnabledEqualTo(true)
		.andCaducidadpasswordBetween(Utilerias.fechaHoyLocaleEnCeros(), Utilerias.fechaSumarDias(Constantes.DIAS_FUTURO));
		List<Usuario> usuarios = usuarioMapper.selectByExample(exUser);
		for (Usuario usuario : usuarios) {
			usuario.setPassword("");
			List<Email> correos = emailService.obtenerCorreosPorUsername(usuario.getUsername());
			UsuarioEmail usuarioEmail = new UsuarioEmail(usuario, correos);
			usersEmail.add(usuarioEmail);
		}
		return usersEmail;
	}
}
