package com.sysdt.lock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.SupervisorEntidadMapper;
import com.sysdt.lock.dao.UsuarioMapper;
import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Cliente;
import com.sysdt.lock.model.SupervisorEntidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.model.UsuarioExample;
import com.sysdt.lock.model.UsuarioExample.Criteria;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.Security;
import com.sysdt.lock.util.Utilerias;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioMapper usuarioMapper;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private HistoricoService historicoService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SupervisorEntidadService supervisorEntidadService;
	
	public void actualizarPassword(String username, String password, String nuevoPassword, String nombreCliente) throws Exception{
		Usuario usuario = validarUsuario(username, password, nuevoPassword);
		Cliente cliente = clienteService.obtenerClientePorId(usuario.getIdCliente());
		if(!cliente.getNombre().contentEquals(nombreCliente.toUpperCase())){
			throw new Exception("No fue posible actualizar el password. Revise su dirección de acceso o consulte a su proveedor");
		}
		usuario.setPassword(nuevoPassword);
		usuario.setCaducidadpassword(Utilerias.fechaSumarDias(cliente.getPeriodovalidacion()));
		actualizarUsuario(usuario);
	}
	
	private Usuario validarUsuario(String username, String password, String nuevoPassword)throws Exception{
		Usuario usuario = obtenerUsuarioPorUsername(username);
		if(usuario == null){
			throw new Exception("Usuario invalido");
		}else if(!usuario.getEnabled()){
			throw new Exception("Su cuenta esta deshabilitada. Por favor consulte a su proveedor");
		}else if(!usuario.getPassword().contentEquals(password)){
			throw new Exception("Password incorrecto");
		}else if(nuevoPassword != null && usuario.getPassword().contentEquals(nuevoPassword)){
			throw new Exception("El nuevo password no puede ser igual al anterior");
		}
		return usuario;
	}
	
	public UsuarioDTO obtenerYvalidarUsuario(String username, String password, String nombreCliente) throws Exception{
		Usuario usuario = validarUsuario(username, password, null);
		Cliente cliente = clienteService.obtenerClientePorId(usuario.getIdCliente());
		if(!cliente.getNombre().contentEquals(nombreCliente.toUpperCase())){
			throw new Exception("No tiene los permisos necesarios. Revise su dirección de acceso o consulte a su proveedor");
		}
		if(cliente.getPeriodovalidacion() > 0 && Utilerias.excedeVigencia(usuario.getCaducidadpassword()) ){
			throw new Exception("Debe actualizar su password para poder acceder al sistema");
		}
		return copiarUsuario(usuario, cliente);
	}
	
	public boolean guardarUsuario(Usuario usuario, String email, int clienteCaducidad)throws Exception{
		usuarioTrim(usuario);
		Usuario user = obtenerUsuarioPorUsername(usuario.getUsername());
		if(user == null){
			usuario.setCaducidadpassword(Utilerias.fechaSumarDias(clienteCaducidad));
			insertarUsuario(usuario);
			if(!email.trim().isEmpty()){
				emailService.insertarEmail(email.trim(), usuario.getUsername());
			}
			return true;
		}
		return false;
	}
	
	public void cambiarEstadoCuentasDeUsuario(int idCliente, boolean estado) throws Exception{
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdClienteEqualTo(idCliente)
		.andIdTipousuarioNotEqualTo(Constantes.TipoUsuario.ADMINISTRADOR).andIdTipousuarioNotEqualTo(Constantes.TipoUsuario.MASTER);
		Usuario user = new Usuario();
		user.setEnabled(estado);
		usuarioMapper.updateByExampleSelective(user, exUser);
	}
	
	public List<Usuario> obtenerUsuariosPorTipoYidCliente(int idCliente, int idTipoUsuario){
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdClienteEqualTo(idCliente).andIdTipousuarioEqualTo(idTipoUsuario);
		return usuarioMapper.selectByExample(exUser);
	}
	
	public List<Usuario> obtenerUsuariosPorIdClienteSinPass(int idCliente){
		List<Usuario> usuarios = obtenerUsuariosPorIdCliente(idCliente);
		for (Usuario usuario : usuarios) {
			usuario.setPassword("");
		}
		return usuarios;
	}
	
	public String generarCodigo(String clave1, String clave2, String username, String placasEco, int idCliente, int idChofer, String nomUsuario, String nomChofer)throws Exception {
		String codigo = "";
		try {
			Security security = new Security();
			codigo = security.convertirLlaves(clave1, clave2, idCliente);
			if(codigo != null){
				historicoService.insertarHistoricoDeGeneracionCodigos(username, placasEco, true, idChofer, idCliente, nomUsuario, nomChofer);
			}else{
				historicoService.insertarHistoricoDeGeneracionCodigos(username, placasEco, false, idChofer, idCliente, nomUsuario, nomChofer);
				codigo = "Generar nuevo codigo";
			}
		} catch (Exception e) {
			historicoService.insertarHistoricoDeGeneracionCodigos(username, placasEco, false, idChofer, idCliente, nomUsuario, nomChofer);
			throw new Exception("Error al convertir claves");
		}
		
		return codigo;
	}
	
	public void actualizarUsuarioCompleto(Usuario usuario, String email) throws Exception{
		actualizarUsuario(usuario);
		emailService.eliminarEmailsPorUsername(usuario.getUsername());
		if(!email.trim().isEmpty()){
			emailService.insertarEmail(email, usuario.getUsername());
		}
	}
	
	public int obtenerNumeroCuentasPorTipo(int idCliente, int idTipoUsuario){
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdTipousuarioEqualTo(idTipoUsuario).andIdClienteEqualTo(idCliente);
		return usuarioMapper.countByExample(exUser);
	}
	
	public List<Usuario> obtenerUsuariosPorIdClienteSinAdmin(int idCliente){
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdClienteEqualTo(idCliente).andIdTipousuarioNotEqualTo(Constantes.TipoUsuario.ADMINISTRADOR)
		.andIdTipousuarioNotEqualTo(Constantes.TipoUsuario.MASTER);
		return usuarioMapper.selectByExample(exUser);
	}
	
	public void eliminarUsuarioCompletoPorUsernameYidCliente(String username, int idCliente)throws Exception{
		emailService.eliminarEmailsPorUsername(username);
		supervisorEntidadService.eliminarRelacionesPorUsername(username);
		eliminarUsuarioPorIdYidCliente(username, idCliente);
	}
	
	//********* METODOS SIMPLES ******///
	
	private void insertarUsuario(Usuario usuario) throws Exception{
		usuario.setEnabled(true);
		usuarioMapper.insert(usuario);
	}
	
	private void actualizarUsuario(Usuario usuario) throws Exception{
		usuarioTrim(usuario);
		usuarioMapper.updateByPrimaryKey(usuario);
	}
	
	private void eliminarUsuarioPorIdYidCliente(String username, int idCliente) throws Exception{
		UsuarioExample exUSER = new UsuarioExample();
		exUSER.createCriteria().andUsernameEqualTo(username).andIdClienteEqualTo(idCliente);
		usuarioMapper.deleteByExample(exUSER);
	}
	
	private void eliminarUsuarioPorId(String username) throws Exception{
		usuarioMapper.deleteByPrimaryKey(username);
	}
	
	public List<Usuario> obtenerUsuariosPorIdCliente(int idCliente){
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andIdClienteEqualTo(idCliente);
	//	exUser.setOrderByClause("username ASC");
		return usuarioMapper.selectByExample(exUser);
	}
	
	private Usuario obtenerUsuarioPorUsername(String username){
		return usuarioMapper.selectByPrimaryKey(username);
	}	
	
	private UsuarioDTO copiarUsuario(Usuario usuario, Cliente cliente){
		UsuarioDTO usuarioDTO =new UsuarioDTO();
		usuarioDTO.setIdCliente(usuario.getIdCliente());
		usuarioDTO.setEnabled(usuario.getEnabled());
		usuarioDTO.setIdTipousuario(usuario.getIdTipousuario());
		usuarioDTO.setUsername(usuario.getUsername());
		usuarioDTO.setCliente(cliente);
		usuarioDTO.setNombre(usuario.getNombre());
		usuarioDTO.setApaterno(usuario.getApaterno());
		usuarioDTO.setAmaterno(usuario.getAmaterno());
		return usuarioDTO;
	}
	
	private void usuarioTrim(Usuario usuario){
		usuario.setUsername(usuario.getUsername().trim().toLowerCase());
		usuario.setPassword(usuario.getPassword().trim().toLowerCase());
		usuario.setNombre(usuario.getNombre().trim().toUpperCase());
		usuario.setApaterno(usuario.getApaterno().trim().toUpperCase());
		usuario.setAmaterno(usuario.getAmaterno() != null ? usuario.getAmaterno().trim().toUpperCase() : "");
	}

	public List<Usuario> obtenerOperadoresPorSupervisor(String usernameSup, int idCliente) {
		List<Usuario> usuarios = obtenerUsuariosPorTipoYidCliente(idCliente, Constantes.TipoUsuario.OPERADOR);
		List<SupervisorEntidad> relaciones = supervisorEntidadService.obtenerRelacionSupervisorEntidad(usernameSup, Constantes.TipoUsuario.OPERADOR);
		List<Usuario> operadores = new ArrayList<Usuario>();
		for(SupervisorEntidad relacion : relaciones){
			for(Usuario usuario : usuarios){
				if(usuario.getUsername().contentEquals(relacion.getIdentidad())){
					operadores.add(usuario);
					break;
				}
			}
		}
		return operadores;
	}
	
	public List<Usuario> relacionarOperadores(List<Usuario> operadores, List<SupervisorEntidad> entidades){
		List<Usuario> operadoresRel = new ArrayList<Usuario>();
		for(SupervisorEntidad entidad : entidades){
			for(Usuario operador : operadores){
				if(operador.getUsername().contentEquals(entidad.getIdentidad())){
					operadoresRel.add(operador);
					break;
				}
			}
		}
		return operadoresRel;
	}
	
}
