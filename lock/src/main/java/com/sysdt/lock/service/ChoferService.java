package com.sysdt.lock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.ChoferMapper;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.ChoferExample;
import com.sysdt.lock.model.ChoferExample.Criteria;
import com.sysdt.lock.model.SupervisorEntidad;
import com.sysdt.lock.util.Constantes;

@Service
@Transactional
public class ChoferService {

	@Autowired
	private ChoferMapper choferMapper;
	
	public List<Chofer> obtenerChoferesPorIdCliente(int idCliente, boolean buscarPorEstado, boolean estado, String orderBy){
		ChoferExample exChofer = new ChoferExample();
		Criteria criteria = exChofer.createCriteria();
		criteria.andIdclienteEqualTo(idCliente);
		if(buscarPorEstado){
			criteria.andEnabledEqualTo(estado);
		}
		exChofer.setOrderByClause(orderBy);
		return choferMapper.selectByExample(exChofer);
	}
	
	public Map<Integer,Chofer> obtenerChoferesPorIdClienteComoMapa(int idCliente, boolean buscarPorEstado, boolean estado){
		List<Chofer> choferes = obtenerChoferesPorIdCliente(idCliente, buscarPorEstado, estado, Constantes.OrderBy.CHOFER_NOMBRE);
		Map<Integer, Chofer> chofs = new HashMap<Integer, Chofer>();
		for(Chofer chofer : choferes){
			chofs.put(chofer.getId(), chofer);
		}
		return chofs;
	}
	
	public Chofer obtenerChoferPorId(int idChofer){
		return choferMapper.selectByPrimaryKey(idChofer);
	}
	
	public void insertarChofer(Chofer chofer)throws Exception{
		convertirMayusculas(chofer);
		chofer.setEnabled(true);
		chofer.setToken("");
		choferMapper.insert(chofer);
	}
	
	public boolean actualizarChofer(Chofer chofer)throws Exception{
		convertirMayusculas(chofer);
		chofer.setToken(null);
		return choferMapper.updateByPrimaryKeySelective(chofer) == 1;
	}
	
	public boolean actualizarToken(int idChofer, String token){
		Chofer chofer = new Chofer();
		chofer.setId(idChofer);
		chofer.setToken(token);
		return choferMapper.updateByPrimaryKeySelective(chofer) == 1;
	}
	
	public boolean habilitar(int idChofer) throws Exception{
		return cambiarEstado(idChofer, true);
	}
	
	public boolean deshabilitar(int idChofer) throws Exception{
		return cambiarEstado(idChofer, false);
	}
	
	private boolean cambiarEstado(int idChofer, boolean estado)throws Exception{
		Chofer chofer = new Chofer();
		chofer.setId(idChofer);
		chofer.setEnabled(estado);
		return choferMapper.updateByPrimaryKeySelective(chofer) == 1;
	}
	
	private void convertirMayusculas(Chofer chofer){
		chofer.setNombre(chofer.getNombre().trim().toUpperCase());
		chofer.setApaterno(chofer.getApaterno().trim().toUpperCase());
		chofer.setAmaterno(chofer.getAmaterno().trim().toUpperCase());
	}
	
	public List<Chofer> relacionarChoferes(List<Chofer> choferes, List<SupervisorEntidad> entidades){
		List<Chofer> choferesRel = new ArrayList<Chofer>();
		for(SupervisorEntidad entidad : entidades){
			for(Chofer chofer : choferes){
				if(chofer.getId() == Integer.parseInt(entidad.getIdentidad() ) ){
					choferesRel.add(chofer);
					break;
				}
			}
		}
		return choferesRel;
	}
	
}
