package com.sysdt.lock.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.sysdt.lock.dto.RespuestaDTO;
import com.sysdt.lock.dto.SolicitudDTO;

@WebService
public interface AperturaWS {

	@WebResult(name="respuesta")
	RespuestaDTO consultarApertura(@WebParam(name="solicitud")SolicitudDTO solicitud);
	
	@WebResult(name="respuesta")
	RespuestaDTO registrarToken(@WebParam(name="idChofer")int idChofer, @WebParam(name="token") String token);
}
