package com.sysdt.lock.view;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.model.Historico;
import com.sysdt.lock.model.Unidad;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.HistoricoService;
import com.sysdt.lock.service.UnidadService;
import com.sysdt.lock.service.UsuarioService;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class SuperView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{manejoSesionView}")
	private ManejoSesionView manejoSesionView;
	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	@ManagedProperty("#{historicoService}")
	private HistoricoService historicoService;
	@ManagedProperty("#{unidadService}")
	private UnidadService unidadService;
	
	private List<Usuario> usuarios;
	private List<Historico> historicos;
	private Usuario usuario;
	private UsuarioDTO usuarioDTO;
	private List<Unidad> unidades;
	private String eco;
	
	private Date fechaIni;
	private Date fechaFin;
	private int tipoBisqueda;
	private int codigosExito;
	private int codigosError;
	private int registros;
	private String titulo;
	private String tituloReporte;
	
	private MapModel modeloMapa;
	private Historico historicoSel;
	private String latitud;
	private String longitud;
	
	private final int BUSQUEDA_POR_CODIGO = 1;
	private final int BUSQUEDA_POR_APERTURA = 2;
	private final int BUSQUEDA_POR_UNIDAD = 3;
	
	@PostConstruct
	public void init(){
		if(manejoSesionView.validarPerfiles(Constantes.TipoUsuario.ADMINISTRADOR, Constantes.TipoUsuario.MASTER, Constantes.TipoUsuario.SUPERVISOR)){
			usuarioDTO = manejoSesionView.obtenerUsuarioEnSesion();
			titulo = "";
			historicos = new ArrayList<Historico>();
			unidades = new ArrayList<Unidad>();
			Calendar cal = Calendar.getInstance();
			fechaIni = cal.getTime();
			fechaFin = cal.getTime();
			if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.ADMINISTRADOR ){
				usuarios = usuarioService.obtenerUsuariosPorIdClienteSinPass(usuarioDTO.getIdCliente());
			}else if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.MASTER){
				usuarios = usuarioService.obtenerUsuariosPorIdClienteSinAdmin(usuarioDTO.getIdCliente());
			}else {
				usuarios = usuarioService.obtenerOperadoresPorSupervisor(usuarioDTO.getUsername(), usuarioDTO.getIdCliente());
			}
			modeloMapa = new DefaultMapModel();
			establecerMapaInicial();
		}else{
			manejoSesionView.cerrarSesionUsuario();
		}
		
	}
	
	public void buscarHistorico(){
		if(tipoBisqueda != BUSQUEDA_POR_UNIDAD){
			if(usuario == null || usuario.getUsername() == null){
				MensajeGrowl.mostrar("Debe seleccionar una cuenta de usuario", FacesMessage.SEVERITY_ERROR);
				return;
			}
		}else{
			if(eco == null || eco.trim().isEmpty()){
				MensajeGrowl.mostrar("Debe seleccionar una unidad", FacesMessage.SEVERITY_ERROR);
				return;
			}
		}
		if(fechaIni.after(fechaFin)){
			MensajeGrowl.mostrar("La fecha final no puede ser anterior a la fecha inicial", FacesMessage.SEVERITY_ERROR);
			return;
		}
	
		try{
			String username = usuario != null ? (tipoBisqueda != BUSQUEDA_POR_UNIDAD? usuario.getUsername() : ""):"";
			historicos = historicoService.obtenerHistoricoPorUsuarioFechaYTipo(usuarioDTO.getIdCliente(),username, fechaIni, fechaFin, tipoBisqueda, eco);
			registros = historicos.size();
			calcularRegistros();
			String tituloContenido = nombreArchivo();
			titulo = "PERIODO "+tituloContenido+"   TIPO: "+(tipoBisqueda==1?"CODIGOS":tipoBisqueda==2?"APERTURAS":tipoBisqueda==3?"UNIDAD":"TODO");
			tituloReporte = "CODIGOS DEL "+tituloContenido+"_"+(usuario != null ? usuario.getUsername():eco);
			if(registros == 0){
				MensajeGrowl.mostrar("No se encontraron registros en esa fecha", FacesMessage.SEVERITY_WARN);
			}
		}catch(Exception ex){
			MensajeGrowl.mostrar("Ocurrió una excepción al recuperar el historial", FacesMessage.SEVERITY_FATAL);
		}
		establecerMapaInicial();
	}
	
	public void cambioUsuario(){
		historicos.clear();
	}
	
	public void procesarPDF(Object documento){
		try{
			Document pdf = (Document)documento;
			pdf.open();
			pdf.setPageSize(PageSize.LETTER);
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + 
	        		"imgs" + File.separator + usuarioDTO.getCliente().getLogo();
	         
			//Add Image
		    Image image1 = Image.getInstance(logo);
		    //Fixed Positioning
		    image1.setAbsolutePosition(350f, 725f); //100 550 //150 650
		    //Scale to new height and new width of image
		    image1.scaleAbsolute(150, 75);
		    //Add to document
		    pdf.add(image1);
			pdf.add(new Paragraph("REPORTE DEL "+nombreArchivo(), FontFactory.getFont(FontFactory.TIMES_BOLD,16,Color.DARK_GRAY)));
			pdf.add(new Paragraph("TOTAL CODIGOS: "+registros, FontFactory.getFont(FontFactory.TIMES_BOLD,12,Color.DARK_GRAY)));
			pdf.add(new Paragraph("CORRECTOS: "+codigosExito, FontFactory.getFont(FontFactory.TIMES_BOLD,12,Color.DARK_GRAY)));
			pdf.add(new Paragraph("FALLIDOS: "+codigosError, FontFactory.getFont(FontFactory.TIMES_BOLD,12,Color.DARK_GRAY)));
			pdf.add(new Paragraph(" "));
		}catch(Exception ex){
			MensajeGrowl.mostrar("Error al generar el reporte", FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public void calcularRegistros(){
		codigosExito = 0;
		codigosError = 0;
		for(Historico historico : historicos){
			if(historico.getEstado()){
				codigosExito += 1;
			}else{
				codigosError += 1;
			}
		}
	}
	
	public void cambioOpcionBusqueda(){
		if(tipoBisqueda == BUSQUEDA_POR_UNIDAD){
			if(usuarioDTO.getIdTipousuario() == Constantes.TipoUsuario.SUPERVISOR){
				unidades = unidadService.obtenerUnidadesPorSupervisor(usuarioDTO.getUsername(), usuarioDTO.getIdCliente());
			}else{
				unidades = unidadService.obtenerUnidadesPorIdCliente(usuarioDTO.getIdCliente(), Constantes.OrderBy.UNIDAD_ECO);
			}
		}else{
			unidades.clear();
		}
	}
	
	public String unidadSeleccionada(){
		String msj = historicoSel.getPlacasEco();
		msj += " -> "+convertirFecha(historicoSel.getFecha());
		msj += " "+convertirHora(historicoSel.getFecha());
		return msj;
	}
	
	public String convertirFecha(Date fecha){
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		return (day<10?"0"+day:day)+"/"+ (month<10?"0"+month:month) +"/"+cal.get(Calendar.YEAR);
	}
	
	public String convertirHora(Date fecha){
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int min = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int second = cal.get(Calendar.SECOND);
		return (hour<10?"0"+hour:hour)+":"+ (min<10?"0"+min:min)+ ":"+(second<10?"0"+second:second);
	}
		
	public String nombreArchivo(){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fechaIni);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaFin);
		String periodo = cal1.get(Calendar.DAY_OF_MONTH)+"-";
		periodo += (cal1.get(Calendar.MONTH)+1)+"-";
		periodo += cal1.get(Calendar.YEAR);
		periodo += " AL "+cal2.get(Calendar.DAY_OF_MONTH)+"-";
		periodo += (cal2.get(Calendar.MONTH)+1)+"-";
		periodo += cal2.get(Calendar.YEAR);
		return periodo;
	}

	public void seleccionarFechaInicial(){
		if(fechaIni.after(fechaFin)){
			fechaFin = fechaIni;
		}
	}
	
	public void actualizarMapa(){
		modeloMapa = new DefaultMapModel();
		
		if(historicoSel == null || historicoSel.getLatitud() == null || historicoSel.getLongitud() == null){
			establecerMapaInicial();
			return;
		}
		
		try{
			double lat = Double.parseDouble(historicoSel.getLatitud());
			double lon = Double.parseDouble(historicoSel.getLongitud());
			latitud = historicoSel.getLatitud();
			longitud = historicoSel.getLongitud();
			LatLng coordenada = new LatLng(lat, lon);
			modeloMapa.addOverlay(new Marker(coordenada, "Unidad: "+historicoSel.getPlacasEco()));
		}catch(Exception e){
			establecerMapaInicial();
			MensajeGrowl.mostrar("Ocurrió un error al obtener las coordenadas", FacesMessage.SEVERITY_FATAL);
		}
		RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
	}
	
	private void establecerMapaInicial(){
		//poner coordenadas estandar
		latitud = Constantes.Coordenadas.LATITUD_INICIAL;
		longitud = Constantes.Coordenadas.LONGITUD_INICIAL;
	}
	
	
	public ManejoSesionView getManejoSesionView() {
		return manejoSesionView;
	}

	public void setManejoSesionView(ManejoSesionView manejoSesionView) {
		this.manejoSesionView = manejoSesionView;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public HistoricoService getHistoricoService() {
		return historicoService;
	}

	public void setHistoricoService(HistoricoService historicoService) {
		this.historicoService = historicoService;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getRegistros() {
		return registros;
	}

	public void setRegistros(int registros) {
		this.registros = registros;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTituloReporte() {
		return tituloReporte;
	}

	public void setTituloReporte(String tituloReporte) {
		this.tituloReporte = tituloReporte;
	}

	public int getTipoBisqueda() {
		return tipoBisqueda;
	}

	public void setTipoBisqueda(int tipoBisqueda) {
		this.tipoBisqueda = tipoBisqueda;
	}

	public MapModel getModeloMapa() {
		return modeloMapa;
	}

	public void setModeloMapa(MapModel modeloMapa) {
		this.modeloMapa = modeloMapa;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Historico getHistoricoSel() {
		return historicoSel;
	}

	public void setHistoricoSel(Historico historicoSel) {
		this.historicoSel = historicoSel;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public String getEco() {
		return eco;
	}

	public void setEco(String eco) {
		this.eco = eco;
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}


}
