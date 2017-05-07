package com.sysdt.lock.view;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.sysdt.lock.dto.UsuarioDTO;
import com.sysdt.lock.util.Constantes;
import com.sysdt.lock.util.MensajeGrowl;

@ManagedBean
@ViewScoped
public class ManejoSesionView implements Serializable {

	private static final long serialVersionUID = 1L;

	// @ManagedProperty("#{userDTO}")
	// private UserDTO userDTO;

	private UsuarioDTO usuarioDTO;
	private String nombreCliente;
	@PostConstruct
	public void init() {
		obtenerUsuario();
		if(usuarioDTO == null || usuarioDTO.getUsername() == null || usuarioDTO.getUsername().trim().isEmpty()){
	/*		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				ec.redirect("login.xhtml?"+nombreCliente);
				return;
			} catch (IOException e) {
				System.out.println("ERROR AL REDIRIGIR USERDTO");
			}  */
	/*		String accesoRepetido = obtenerCookie(Constantes.ClienteCookie.ACCESO_REPETIDO);
			if(accesoRepetido.isEmpty()){
				crearCookieRepeticion(-1);
				cerrarSesionUsuario();
			} else {
				crearCookieRepeticion(0);
			}  */
			
		}
	}

	public UsuarioDTO obtenerUsuarioEnSesion() {
		return usuarioDTO;
	}
	
	private UsuarioDTO obtenerUsuario(){
		System.out.println("Entro a obtenerUsuarioEnSesion");
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		if (!ec.isResponseCommitted()) {
//			try {
				Map<String, Object> session = ec.getSessionMap();
				usuarioDTO = (UsuarioDTO) session.get("usuario");
		/*		if (usuarioDTO == null) {
					ec.invalidateSession();
					ec.redirect("login.xhtml");
				}  */
/*			} catch (IOException e) {
				MensajeGrowl
						.mostrar(
								"No se pudo obtener al usuario y ocurrió un error al redirigir",
								FacesMessage.SEVERITY_FATAL);
			}   */
		}
	
		/*nombreCliente = ec.getRequestParameterMap().get("c");
		for(Map.Entry<String, String> entrada : ec.getRequestParameterMap().entrySet()){
			System.out.println("Clave: "+entrada.getKey());
			System.out.println("Valor: "+entrada.getValue());
		}  */
		nombreCliente = obtenerCookie(Constantes.ClienteCookie.NOMBRE_CLIENTE);
		return usuarioDTO;
	}

	public void cerrarSesionUsuario() {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		if (!ec.isResponseCommitted()) {
			nombreCliente = nombreCliente != null ? "?c="+nombreCliente.toUpperCase() : "";
			if(usuarioDTO!=null && usuarioDTO.getCliente()!=null && usuarioDTO.getCliente().getNombre()!=null){
				nombreCliente = usuarioDTO.getCliente().getNombre().toUpperCase();
				nombreCliente = !nombreCliente.contentEquals("SYSDT") ? "?c=" + nombreCliente : "";
			}
	//		ec.invalidateSession();
			try {
				MensajeGrowl.mostrar("La sesión se cerró exitosamente",
						FacesMessage.SEVERITY_INFO);
				ec.getFlash().setKeepMessages(true);
				String url = "login.xhtml" + nombreCliente;
				ec.redirect(url);
			} catch (IOException e) {
				MensajeGrowl.mostrar("Ocurrió un error al redirigir al Login",
						FacesMessage.SEVERITY_FATAL);
			}
		}
	}

	public void redirigir(int idRedirect) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			usuarioDTO.setOpcionSel(idRedirect);
			String cliente = usuarioDTO.getCliente().getNombre();
			cliente = cliente != null ? cliente : "";
			if (idRedirect == Constantes.Vistas.VISTA_CODIGOS) {
				context.redirect("codigos.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_SUPERVISION) {
				context.redirect("supervision.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_ADMINISTRACION) {
				context.redirect("admin.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_CHOFERES) {
				context.redirect("chofer.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_CUENTAS) {
				context.redirect("cuenta.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_UNIDADES) {
				context.redirect("unidad.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_SUPERVISORES) {
				context.redirect("masterSup.xhtml?"+cliente);
			} else if (idRedirect == Constantes.Vistas.VISTA_OPERADORES) {
				context.redirect("supOp.xhtml?"+cliente);
			} else {
				context.redirect("login.xhtml?"+cliente);
			}
		} catch (IOException e) {
			MensajeGrowl.mostrar("Error al redirigir",
					FacesMessage.SEVERITY_FATAL);
		}
	}
	
	private String obtenerCookie(String nombreCookie){
		try{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		Cookie cookie = (Cookie) context.getRequestCookieMap().get(nombreCookie);
		if(cookie != null){
			return URLDecoder.decode(cookie.getValue(), "UTF-8");
		}
		return "";
		} catch(Exception e){
			return "";
		}
	}
	
	private void crearCookieRepeticion(int tiempoVida){
		String name = Constantes.ClienteCookie.ACCESO_REPETIDO;
		String valor = Constantes.ClienteCookie.VALOR_ACCESO_REPETIDO;
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("maxAge", tiempoVida);
		properties.put("path", "/");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.addResponseCookie(name, URLEncoder.encode(valor, "UTF-8"), properties);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ERROR AL CREAR COOKIE");
		}
	}
	
	public boolean validarPerfiles(int ... perfiles){
		if(usuarioDTO == null || usuarioDTO.getUsername() == null || usuarioDTO.getUsername().trim().isEmpty()){
			return false;
		}
		if(perfiles[0] == Constantes.TipoUsuario.TODOS){
			return true;
		}
		for (int perfil : perfiles) {
			if(perfil == usuarioDTO.getIdTipousuario()){
				return true;
			}
		}
		return false;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}


}
