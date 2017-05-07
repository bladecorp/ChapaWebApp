package com.sysdt.lock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.UnidadMapper;
import com.sysdt.lock.model.SupervisorEntidad;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.UnidadExample;
import com.sysdt.lock.util.Constantes;

@Service
@Transactional
public class UnidadService {

	@Autowired
	private UnidadMapper unidadMapper;
	@Autowired
	private SupervisorEntidadService supervisorEntidadService;
	
	public int obtenerCountUnidadesPorIdCliente(int idCliente){
		UnidadExample exUnit = new UnidadExample();
		exUnit.createCriteria().andIdclienteEqualTo(idCliente);
		return unidadMapper.countByExample(exUnit);
	}
	
	public List<Unidad> obtenerUnidadesPorIdCliente(int idCliente, String orderBy){
		UnidadExample exUnit = new UnidadExample();
		exUnit.createCriteria().andIdclienteEqualTo(idCliente);
		exUnit.setOrderByClause(orderBy);
		return unidadMapper.selectByExample(exUnit);
	}
	
	public List<Unidad> obtenerUnidadesPorSupervisor(String usernameSup, int idCliente){
		List<Unidad> unidades = obtenerUnidadesPorIdCliente(idCliente, Constantes.OrderBy.UNIDAD_ECO);
		List<SupervisorEntidad> relaciones = supervisorEntidadService.obtenerRelacionSupervisorEntidad(usernameSup, Constantes.TipoEntidad.UNIDAD);
		return relacionarUnidades(unidades, relaciones);
	}
	
	public void guardarNuevaUnidad(Unidad unidad){
		insertarUnidad(unidad);
	}
	
	private boolean insertarUnidad(Unidad unidad){
		unidad.setEco(unidad.getEco().trim().toUpperCase());
		unidad.setSerie(unidad.getSerie().trim().toUpperCase());
		unidadMapper.insert(unidad);
		return true;
	}
	
	public boolean actualizarUnidad(Unidad unidad){
		unidad.setEco(unidad.getEco().trim().toUpperCase());
		unidad.setSerie(unidad.getSerie().trim().toUpperCase());
		return unidadMapper.updateByPrimaryKeySelective(unidad) > 0;
	}
	
	public boolean eliminarUnidad(int idUnidad){
		supervisorEntidadService.eliminarRelacionesPorTipoEntidadDestinoYid(idUnidad, Constantes.TipoEntidad.UNIDAD);
		return unidadMapper.deleteByPrimaryKey(idUnidad) > 0;
	}
	
	public List<Unidad> relacionarUnidades(List<Unidad> unidades, List<SupervisorEntidad> entidades){
		List<Unidad> unidadesRel = new ArrayList<Unidad>();
		for (SupervisorEntidad entidad : entidades) {
			for(Unidad unidad : unidades){
				if(Integer.parseInt(entidad.getIdentidad()) == unidad.getId()){
					unidadesRel.add(unidad);
					break;
				}
			}
		}
		return unidadesRel;
	}
	
}
