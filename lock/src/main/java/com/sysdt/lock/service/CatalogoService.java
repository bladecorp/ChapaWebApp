package com.sysdt.lock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.TipoEventoMapper;
import com.sysdt.lock.dao.TipoUsuarioMapper;
import com.sysdt.lock.model.TipoEvento;
import com.sysdt.lock.model.TipoUsuario;
import com.sysdt.lock.model.TipoUsuarioExample;
import com.sysdt.lock.util.Constantes;

@Service
@Transactional
public class CatalogoService {

	@Autowired
	private TipoUsuarioMapper tipoUsuarioMapper;
	
	@Autowired
	private TipoEventoMapper tipoEventoMapper;
	
	public List<TipoUsuario> obtenerTiposUsuario(){
		return tipoUsuarioMapper.selectByExample(null);
	}
	
	public List<TipoUsuario> obtenerTiposUsuarioSinAdmin(){
		TipoUsuarioExample exTU = new TipoUsuarioExample();
		exTU.createCriteria().andIdNotEqualTo(Constantes.TipoUsuario.ADMINISTRADOR)
		.andIdNotEqualTo(Constantes.TipoUsuario.MASTER);
		return tipoUsuarioMapper.selectByExample(exTU);
	}
	
	public List<TipoEvento> obtenerTiposEvento(){
		return tipoEventoMapper.selectByExample(null);
	}
	
}
