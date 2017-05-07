package com.sysdt.lock.dto;

import java.util.ArrayList;
import java.util.List;

import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;

public class RelacionesDTO {
	
	private List<Usuario> operadoresRel;
	private List<Unidad> unidadesRel;
	private List<Chofer> choferesRel;
	
	public List<Usuario> getOperadoresRel() {
		if(operadoresRel == null){
			return new ArrayList<Usuario>();
		}
		return operadoresRel;
	}
	public void setOperadoresRel(List<Usuario> operadoresRel) {
		this.operadoresRel = operadoresRel;
	}
	public List<Unidad> getUnidadesRel() {
		if(unidadesRel == null){
			return new ArrayList<Unidad>();
		}
		return unidadesRel;
	}
	public void setUnidadesRel(List<Unidad> unidadesRel) {
		this.unidadesRel = unidadesRel;
	}
	public List<Chofer> getChoferesRel() {
		if(choferesRel == null){
			return new ArrayList<Chofer>();
		}
		return choferesRel;
	}
	public void setChoferesRel(List<Chofer> choferesRel) {
		this.choferesRel = choferesRel;
	}
	
}
