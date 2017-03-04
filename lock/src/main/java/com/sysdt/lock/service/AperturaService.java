package com.sysdt.lock.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import com.sysdt.lock.dto.AperturaDTO;
import com.sysdt.lock.dto.PushBotsDTO;
import com.sysdt.lock.dto.RespuestaDTO;
import com.sysdt.lock.dto.SolicitudDTO;
import com.sysdt.lock.model.Historico;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.ws.AperturaWS;

@Service
@Transactional
public class AperturaService{

	private ConcurrentSkipListMap<Integer, AperturaDTO> aperturas = new ConcurrentSkipListMap<Integer, AperturaDTO>();
	public static final PushBotsDTO PUSHBOTS = obtenerParametrosPushBots();
	
	@Autowired
	private HistoricoService historicoService;
	
	@Autowired
	private ChoferService choferService;
	
	public void enviarSolicitudDeApertura(int idChofer, String token, Unidad unidad, String usuario, boolean isWialon, String codigo)throws Exception{
		int resp = EnviarHttpPush(token, idChofer, unidad.getSerie());
		if(resp != HttpStatus.SC_OK){
			throw new Exception("Error al enviar mensaje. HTTP CODE: "+resp);
		}
		agregarAperturaAlistaDeEspera(idChofer, unidad.getEco(), unidad.getSerie(), usuario, isWialon, codigo);
	}
	
	private void agregarAperturaAlistaDeEspera(int idChofer, String eco, String serie, String usuario, boolean isWialon, String codigo)throws Exception{
		AperturaDTO apertura = new AperturaDTO();
		apertura.setUsuario(usuario);
		apertura.setEco(eco);
		apertura.setSerie(serie);
		apertura.setWialon(isWialon);
		apertura.setCodigo(codigo);
		apertura.setTiempo(System.currentTimeMillis());
		aperturas.put(idChofer, apertura);
	}
	
	private int EnviarHttpPush(String token, int idChofer, String serie)throws Exception{
		String url = "https://api.pushbots.com/push/one";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("x-pushbots-appid", PUSHBOTS.getAppId());
		post.setHeader("x-pushbots-secret", PUSHBOTS.getAppSecret());
		StringEntity entity = new StringEntity("{\"platform\":\"1\","
												+ "\"token\":\""+token+"\","
												+ "\"msg\":\""+PUSHBOTS.getMensaje()+"\","
												+ "\"payload\":{\"idChofer\":\""+idChofer+"\", \"serie\":\""+serie+"\"},"
												+ "\"sound\":\"\"}");   
		entity.setContentType("application/json");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		return response.getStatusLine().getStatusCode(); 
	}
	
	public RespuestaDTO registrarToken(int idChofer, String token) {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setAutorizacion(false);
		try{
			if(idChofer == 0){
				respuesta.setMensaje("El id del chofer llego vacio");
				return respuesta;
			}
			if(token == null || token.trim().isEmpty()){
				respuesta.setMensaje("El token llego vacio");
				return respuesta;
			}
			
			boolean exito = choferService.actualizarToken(idChofer, token);
			if(exito){
				respuesta.setAutorizacion(true);
				respuesta.setMensaje("Token actualizado correctamente");
			}else{
				respuesta.setMensaje("No se encontro el registro del chofer");
			}
		}catch(Exception e){
			respuesta.setMensaje("Ocurrio una excepcion: "+e.getMessage());
		}
		return respuesta;
	}

	public RespuestaDTO consultarApertura(SolicitudDTO solicitud) {
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setAutorizacion(false);
		try{
			AperturaDTO aperturaDTO = aperturas.get(solicitud.getIdChofer());
			if(aperturaDTO == null){
				respuesta.setMensaje("El chofer no tiene aperturas pendientes");
				return respuesta;
			}
			aperturas.remove(solicitud.getIdChofer());
			if(!validarSolicitudApertura(solicitud, respuesta)){
				return respuesta;
			}
			
			long tiempoActual = System.currentTimeMillis();
			if( ((tiempoActual - aperturaDTO.getTiempo())/1000) >  Constantes.TIEMPO_MAXIMO_ESPERA_EN_SEGUNDOS){
		//		aperturas.remove(solicitud.getIdUnidad());
				respuesta.setMensaje("Tiempo de espera agotado");
				return respuesta;
			} 
		
		//	aperturas.remove(solicitud.getIdUnidad());
			if(!aperturaDTO.isWialon()){
				Historico historico = new Historico();
				historico.setUsername(aperturaDTO.getUsuario());
				historico.setPlacasEco(aperturaDTO.getEco());
				historico.setFecha(new Date());
				historico.setEstado(true);
				historico.setLatitud(solicitud.getLatitud().trim());
				historico.setLongitud(solicitud.getLongitud().trim());
				historico.setIdtipoevento(Constantes.TipoEvento.APERTURA_CHAPA);
				historico.setIdchofer(solicitud.getIdChofer());
				historicoService.insertarHistoricoConCoordenadas(historico);
			}
			
			respuesta.setAutorizacion(true);
			respuesta.setMensaje(aperturaDTO.getCodigo());
		}catch(Exception e){
			respuesta.setMensaje("Ocurrio una excepcion: "+e.getMessage());
		}
		return respuesta;
	}
	
	private boolean validarSolicitudApertura(SolicitudDTO solicitud, RespuestaDTO respuesta){
		
		if(solicitud.getIdChofer() == 0){
			respuesta.setMensaje("El chofer es invalido. Valor: 0");
			return false;
		}
		
		if(solicitud.getLatitud() == null || solicitud.getLatitud().trim().isEmpty()){
			respuesta.setMensaje("La latitud llego vacia");
			return false;
		}
		
		try{
			BigDecimal lat = new BigDecimal(solicitud.getLatitud());
		}catch(NumberFormatException e){
			respuesta.setMensaje("El valor de latitud es invalido");
			return false;
		}
		
		if(solicitud.getLongitud() == null || solicitud.getLongitud().trim().isEmpty()){
			respuesta.setMensaje("La longitud llego vacia");
			return false;
		}
		
		try{
			BigDecimal lon = new BigDecimal(solicitud.getLongitud());
		}catch(NumberFormatException e){
			respuesta.setMensaje("El valor de longitud es invalido");
			return false;
		}
		
		return true;
	}

	private static PushBotsDTO obtenerParametrosPushBots(){
		PushBotsDTO pushBotsDTO = new PushBotsDTO();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(AperturaService.class.getClassLoader().getResourceAsStream("parametrosPushBots.xml"));
			pushBotsDTO.setAppId(document.getElementsByTagName("AppId").item(0).getTextContent());
			pushBotsDTO.setAppSecret(document.getElementsByTagName("AppSecret").item(0).getTextContent());
			pushBotsDTO.setMensaje(document.getElementsByTagName("Mensaje").item(0).getTextContent());
		}catch(Exception e){
			System.out.println("*** ERROR AL CARGAR VARIABLES DE PUSHBOTS *** : "+e.getMessage());
			pushBotsDTO.setAppId("");
			pushBotsDTO.setAppSecret("");
			pushBotsDTO.setMensaje("");
		}
		return pushBotsDTO;
	}
	
}
