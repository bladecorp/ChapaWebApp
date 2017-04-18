package com.sysdt.lock.schedule;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sysdt.lock.dto.ClienteEmail;
import com.sysdt.lock.dto.UsuarioEmail;
import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.Usuario;
import com.sysdt.lock.service.ClienteService;
import com.sysdt.lock.util.Utilerias;

@Component
public class ValidarVigenciaPassword implements ResourceLoaderAware{

	@Autowired
	private ClienteService clienteService;
	@Autowired
	private JavaMailSender sender;
	private ResourceLoader resourceLoader;
	
	public void validarVigencias(){
		List<ClienteEmail> clientes = clienteService.obtenerListaCorreo();
		for (ClienteEmail cliente : clientes) {
			enviarCorreo(cliente);
		}
	}
	
	private void enviarCorreo(ClienteEmail cliente){
		try {
			for(UsuarioEmail usuario : cliente.getUsuarios()){
				try{
					MimeMessage message = sender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setSubject("Renovación de Password");
					Resource resource = resourceLoader.getResource("resources/imgs/"+cliente.getCliente().getLogo());
					String[] direcciones = new String[usuario.getCorreos().size()];
					int contador = 0;
					for(Email email : usuario.getCorreos()){
						direcciones[contador] = email.getDireccion();
						contador++;
					}
					helper.setTo(direcciones);
					helper.setText(generarContenido(usuario.getUsuario(), cliente.getCliente().getNombre()), true);
					helper.addInline("imagen", resource);
					sender.send(message);
				}catch(Exception e){
					System.out.println("ERROR AL ENVIAR CORREOS DEL USUARIO: "+usuario.getUsuario().getUsername());
				}
			}
			
			System.out.println("<<< "+cliente.getUsuarios().size()+" CORREOS DE "+cliente.getCliente().getNombre()+" ENVIADOS >>>");
		} catch (Exception e) {
			System.out.println("ERROR AL ENVIAR CORREOS DEL CLIENTE: "+cliente.getCliente().getNombre());
			System.out.println(e.getMessage());
		}
		
	}
	
	private String generarContenido(Usuario usuario, String cliente){
		StringBuilder contenido = new StringBuilder();
		contenido.append("<html><body> "+
				"<br> Apreciable Usuario: <strong>"+usuario.getUsername()+"</strong> <br><br>Su password expira el <strong>"+Utilerias.fechaEnString(usuario.getCaducidadpassword())+"</strong> "
				+ "<br><br> Ingrese en la siguiente liga para realizar la renovaci&oacute;n.<br><br>"
				+ "<div style='width:100%;text-align:center;'>"
				+ "<a style='font-size:25px;text-decoration:none;font-family:fantasy;' href='https://lockwebapp.herokuapp.com/?c="+cliente+"'>Sistema Lock</a>"
				+ "<br><br><img style='width:180px;height:100px;' src='cid:imagen' /></div>"
				+"</body></html>");
		return contenido.toString();
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
