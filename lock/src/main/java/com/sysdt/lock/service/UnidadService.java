package com.sysdt.lock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.UnidadMapper;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.UnidadExample;

@Service
@Transactional
public class UnidadService {

	@Autowired
	private UnidadMapper unidadMapper;
	
	public List<Unidad> obtenerUnidadesPorIdCliente(int idCliente){
		UnidadExample exUnit = new UnidadExample();
		exUnit.createCriteria().andIdclienteEqualTo(idCliente);
		exUnit.setOrderByClause("eco ASC");
		return unidadMapper.selectByExample(exUnit);
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
		return unidadMapper.deleteByPrimaryKey(idUnidad) > 0;
	}
	
}
