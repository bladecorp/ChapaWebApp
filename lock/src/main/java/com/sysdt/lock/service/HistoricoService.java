package com.sysdt.lock.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.HistoricoMapper;
import com.sysdt.lock.model.Chofer;
import com.sysdt.lock.model.Historico;
import com.sysdt.lock.model.HistoricoExample;
import com.sysdt.lock.model.HistoricoExample.Criteria;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.Utilerias;

@Service
@Transactional
public class HistoricoService {
	
	private final int FECHA_INICIO = 1;
	private final int FECHA_FIN = 2;
	
	@Autowired
	private HistoricoMapper historicoMapper;
	
	@Autowired
	private ChoferService choferService;
	
	public List<Historico> obtenerHistoricoPorUsuarioFechaYTipo(int idCliente, String username, Date fechaIni, Date fechaFin, int tipo, String eco) throws Exception{
		HistoricoExample exHist = new HistoricoExample();
		Criteria criteria = exHist.createCriteria();
		if(username != null && !username.trim().isEmpty()){
			criteria.andUsernameEqualTo(username);
		}
		criteria.andFechaBetween(generarFecha(fechaIni, FECHA_INICIO), generarFecha(fechaFin, FECHA_FIN)).andIdclienteEqualTo(idCliente);
		if(tipo == Constantes.TipoEvento.GENERACION_CODIGO){
			criteria.andLatitudIsNull().andLongitudIsNull().andIdtipoeventoEqualTo(tipo);
		}else if(tipo == Constantes.TipoEvento.APERTURA_CHAPA){
			criteria.andLatitudIsNotNull().andLongitudIsNotNull().andIdtipoeventoEqualTo(tipo);
		}else if(tipo == 3){
			criteria.andPlacasEcoEqualTo(eco);
		}
		return historicoMapper.selectByExample(exHist);
	//	return historicoConChofer(idCliente, historicoMapper.selectByExample(exHist));
	}
	
	public void insertarHistoricoDeGeneracionCodigos(String username, String placasEco, boolean estado, int idChofer, int idCliente, String nomUsuario, String nomChofer) throws Exception{
		Historico historico = new Historico();
		historico.setFecha(Utilerias.fechaLocale(new Date()));
		historico.setUsername(username);
		historico.setNomusuario(nomUsuario);
		historico.setPlacasEco(placasEco.toUpperCase());
		historico.setEstado(estado);
		historico.setIdchofer(idChofer);
		historico.setNomchofer(nomChofer);
		historico.setIdcliente(idCliente);
		historico.setIdtipoevento(Constantes.TipoEvento.GENERACION_CODIGO);
		insertarHistorico(historico);
	}
	
	public void insertarHistorico(Historico historico)throws Exception{
		historicoMapper.insert(historico);
	}
	
/*	private List<Historico> historicoConChofer(int idCliente, List<Historico> historicos){
		Map<Integer, Chofer> choferes = choferService.obtenerChoferesPorIdClienteComoMapa(idCliente, false, false);
		for (Historico historico : historicos) {
			Chofer chofer = choferes.get(historico.getIdchofer());
			if(chofer != null){
				historico.setNomchofer(chofer.getNombre()+" "+chofer.getApaterno());
			}else{
				historico.setNomchofer("N/D");
			}
		}
		return historicos;
	}  */
	
	private Date generarFecha(Date fecha, int tipoFecha){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"),new Locale("es_MX"));
		cal.setTime(fecha);
		if(tipoFecha == FECHA_INICIO){
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
		}else if(tipoFecha == FECHA_FIN){
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		return cal.getTime();
	}

}
