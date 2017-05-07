package com.sysdt.lock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.SupervisorEntidadMapper;
import com.sysdt.lock.dto.RelacionesDTO;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.SupervisorEntidad;
import com.sysdt.lock.model.SupervisorEntidadExample;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.util.Constantes;

@Service
@Transactional
public class SupervisorEntidadService {

	@Autowired
	private SupervisorEntidadMapper supervisorEntidadMapper;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UnidadService unidadService;
	@Autowired
	private ChoferService choferService;
	
	public RelacionesDTO obtenerRelacionesPorOperador(String usernameOp, int idCliente, List<Unidad> unidades, List<Chofer> choferes){
		int idTipoUnidad = Constantes.TipoEntidad.UNIDAD;
		int idTipoChofer = Constantes.TipoEntidad.CHOFER;
		
		List<SupervisorEntidad> relacionesOperador = obtenerRelacionesPorUsernameYtipoUsuario(usernameOp, Constantes.TipoUsuario.OPERADOR);
		List<SupervisorEntidad> relUnidadesOp = new ArrayList<SupervisorEntidad>();
		List<SupervisorEntidad> relChoferesOp = new ArrayList<SupervisorEntidad>();
		for (SupervisorEntidad supervisorEntidad : relacionesOperador) {
			if(supervisorEntidad.getIdtipoentidad() == idTipoUnidad){
				relUnidadesOp.add(supervisorEntidad);
			}else if(supervisorEntidad.getIdtipoentidad() == idTipoChofer){
				relChoferesOp.add(supervisorEntidad);
			}
		}
		
		List<Unidad> unidadesRelOp = unidadService.relacionarUnidades(unidades, relUnidadesOp);
		List<Chofer> choferesRelOp = choferService.relacionarChoferes(choferes, relChoferesOp);
		
		RelacionesDTO relaciones = new RelacionesDTO();
		relaciones.setUnidadesRel(unidadesRelOp);
		relaciones.setChoferesRel(choferesRelOp);
		return relaciones;
	}
	
	public RelacionesDTO obtenerRelacionesPorSupervisor(String username, int idCliente, List<Usuario> operadores, List<Unidad> unidades, List<Chofer> choferes){
		List<SupervisorEntidad> relacionesSupervisor = obtenerRelacionesPorUsernameYtipoUsuario(username, Constantes.TipoUsuario.SUPERVISOR);
		int idTipoUnidad = Constantes.TipoEntidad.UNIDAD;
		int idTipoChofer = Constantes.TipoEntidad.CHOFER;
		int idTipoOperador = Constantes.TipoEntidad.OPERADOR;
		
		List<SupervisorEntidad> relOperadores = new ArrayList<SupervisorEntidad>();
		List<SupervisorEntidad> relUnidades = new ArrayList<SupervisorEntidad>();
		List<SupervisorEntidad> relChoferes = new ArrayList<SupervisorEntidad>();
		for (SupervisorEntidad supervisorEntidad : relacionesSupervisor) {
			if(supervisorEntidad.getIdtipoentidad() == idTipoUnidad){
				relUnidades.add(supervisorEntidad);
			}else if(supervisorEntidad.getIdtipoentidad() == idTipoChofer){
				relChoferes.add(supervisorEntidad);
			}else if(supervisorEntidad.getIdtipoentidad() == idTipoOperador){
				relOperadores.add(supervisorEntidad);
			}
		}
		List<Usuario> operadoresRel = usuarioService.relacionarOperadores(operadores, relOperadores);
		List<Unidad> unidadesRel = unidadService.relacionarUnidades(unidades, relUnidades);
		List<Chofer> choferesRel = choferService.relacionarChoferes(choferes, relChoferes);
		
		RelacionesDTO relaciones = new RelacionesDTO();
		relaciones.setOperadoresRel(operadoresRel);
		relaciones.setUnidadesRel(unidadesRel);
		relaciones.setChoferesRel(choferesRel);
		return relaciones;
	}
	
	public List<SupervisorEntidad> obtenerRelacionSupervisorEntidad(String usernameSup, int idTipoEntidad){
		SupervisorEntidadExample exSup = new SupervisorEntidadExample();
		exSup.createCriteria().andIdtipousuarioEqualTo(Constantes.TipoUsuario.SUPERVISOR)
		.andUsernameEqualTo(usernameSup).andIdtipoentidadEqualTo(idTipoEntidad);
		return supervisorEntidadMapper.selectByExample(exSup);
	}
	
	private List<SupervisorEntidad> obtenerRelacionesPorUsernameYtipoUsuario(String username, int idTipoUsuario){
		SupervisorEntidadExample exSup = new SupervisorEntidadExample();
		exSup.createCriteria().andIdtipousuarioEqualTo(idTipoUsuario).andUsernameEqualTo(username);
		return supervisorEntidadMapper.selectByExample(exSup);
	}
	
	public void insertarRelacionesOperadorChofer(List<Chofer> choferes, String userOp){
		eliminarRelacionesDeUsuarioConUnTipoDeEntidad(userOp, Constantes.TipoEntidad.CHOFER);
		int tipoUsuario = Constantes.TipoUsuario.OPERADOR;
		int tipoEntidad = Constantes.TipoEntidad.CHOFER;
		for(Chofer chofer : choferes){
			insertarRelacion(armarRelacion(userOp, tipoUsuario, String.valueOf(chofer.getId()), tipoEntidad));
		}
	}
	
	public void insertarRelacionesOperadorUnidad(List<Unidad> unidades, String userOp){
		eliminarRelacionesDeUsuarioConUnTipoDeEntidad(userOp, Constantes.TipoEntidad.UNIDAD);
		int tipoUsuario = Constantes.TipoUsuario.OPERADOR;
		int tipoEntidad = Constantes.TipoEntidad.UNIDAD;
		for(Unidad unidad : unidades){
			insertarRelacion(armarRelacion(userOp, tipoUsuario, String.valueOf(unidad.getId()), tipoEntidad));
		}
	}
	
	public void insertarRelacionesSupervisorChofer(List<Chofer> choferes, String userSup){
		eliminarRelacionesDeUsuarioConUnTipoDeEntidad(userSup, Constantes.TipoEntidad.CHOFER);
		int tipoUsuario = Constantes.TipoUsuario.SUPERVISOR;
		int tipoEntidad = Constantes.TipoEntidad.CHOFER;
		for(Chofer chofer : choferes){
			insertarRelacion(armarRelacion(userSup, tipoUsuario, String.valueOf(chofer.getId()), tipoEntidad));
		}
	}
	
	public void insertarRelacionesSupervisorUnidad(List<Unidad> unidades, String userSup){
		eliminarRelacionesDeUsuarioConUnTipoDeEntidad(userSup, Constantes.TipoEntidad.UNIDAD);
		int tipoUsuario = Constantes.TipoUsuario.SUPERVISOR;
		int tipoEntidad = Constantes.TipoEntidad.UNIDAD;
		for(Unidad unidad : unidades){
			insertarRelacion(armarRelacion(userSup, tipoUsuario, String.valueOf(unidad.getId()), tipoEntidad));
		}
	}
	
	public void insertarRelacionesSupervisorOperador(List<Usuario> operadores, String userSup){
		eliminarRelacionesDeUsuarioConUnTipoDeEntidad(userSup, Constantes.TipoEntidad.OPERADOR);
		int tipoUsuario = Constantes.TipoUsuario.SUPERVISOR;
		int tipoEntidad = Constantes.TipoEntidad.OPERADOR;
		for(Usuario usuario : operadores){
			insertarRelacion(armarRelacion(userSup, tipoUsuario, usuario.getUsername(), tipoEntidad));
		}
	}
	
	private void insertarRelacion(SupervisorEntidad supervisorEntidad){
		supervisorEntidad.setId(0);
		supervisorEntidadMapper.insert(supervisorEntidad);
	}
	
	public void eliminarRelacionesPorUsername(String username){
		SupervisorEntidadExample exSup = new SupervisorEntidadExample();
		exSup.createCriteria().andUsernameEqualTo(username);
		supervisorEntidadMapper.deleteByExample(exSup);
	}
	
	public void eliminarRelacionesPorTipoEntidadDestinoYid(int idEntidad, int idTipoEntidad){
		SupervisorEntidadExample exSup = new SupervisorEntidadExample();
		exSup.createCriteria().andIdtipoentidadEqualTo(idTipoEntidad).andIdentidadEqualTo(String.valueOf(idEntidad));
		supervisorEntidadMapper.deleteByExample(exSup);
	}
	
	public void eliminarRelacionesDeUsuarioConUnTipoDeEntidad(String username, int idTipoEntidad){
		SupervisorEntidadExample exSup = new SupervisorEntidadExample();
		exSup.createCriteria().andUsernameEqualTo(username).andIdtipoentidadEqualTo(idTipoEntidad);
		supervisorEntidadMapper.deleteByExample(exSup);
	}
	
	private SupervisorEntidad armarRelacion(String username, int idTipoUsuario, String idEntidad, int idTipoEntidad ){
		SupervisorEntidad sup = new SupervisorEntidad();
		sup.setIdtipousuario(idTipoUsuario);
		sup.setUsername(username);
		sup.setIdtipoentidad(idTipoEntidad);
		sup.setIdentidad(idEntidad);
		return sup;
	}
	
}
