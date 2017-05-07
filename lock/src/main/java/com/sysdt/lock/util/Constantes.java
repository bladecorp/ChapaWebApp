package com.sysdt.lock.util;

import java.util.regex.Pattern;

public class Constantes {
	
	public static final int LISTA_UNIDADES_VACIA = -1;
	public static final int TIEMPO_MAXIMO_ESPERA_EN_SEGUNDOS = 30;
	public static final Pattern EMAIL_VALIDATOR = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public static final int DIAS_FUTURO = 3;
	
	public class TipoUsuario {
		public static final int TODOS = -1;
		public static final int ADMINISTRADOR = 1;
		public static final int SUPERVISOR = 2;
		public static final int OPERADOR = 3;
		public static final int MASTER = 4;
	}
	
	public class TipoEvento {
		public static final int TODO = 0;
		public static final int GENERACION_CODIGO = 1;
		public static final int APERTURA_CHAPA = 2;
	}
	
	public class Coordenadas {
		public static final String LATITUD_INICIAL = "19.432700";
		public static final String LONGITUD_INICIAL = "-99.133386";
	}
	
	public class EstadoChofer{
		public static final int TODOS = 0;
		public static final boolean ACTIVO = true;
		public static final boolean INACTIVO = true;
	}
	
	public class Vistas{
		public static final int VISTA_CODIGOS = 1;
		public static final int VISTA_SUPERVISION = 2;
		public static final int VISTA_ADMINISTRACION = 3;
		public static final int VISTA_CHOFERES = 4;
		public static final int VISTA_CUENTAS = 5;
		public static final int VISTA_UNIDADES = 6;
		public static final int VISTA_SUPERVISORES = 7;
		public static final int VISTA_OPERADORES = 8;
	}
	
	public class TipoMax{
		public static final int MAX_OPERADORES = 1;
		public static final int MAX_SUPERVISORES = 2;
		public static final int MAX_CHOFERES = 3;
		public static final int MAX_UNIDADES = 4;
	}
	
	public class TipoEntidad{
		public static final int SUPERVISOR = 1;
		public static final int OPERADOR = 2;
		public static final int UNIDAD = 3;
		public static final int CHOFER = 4;
	}
	
	public class ClienteCookie{
		public static final String NOMBRE_CLIENTE = "nomCliente";
		public static final String ACCESO_REPETIDO = "repetido";
		public static final String VALOR_ACCESO_REPETIDO = "1";
	}
	
	public class OrderBy{
		public static final String CHOFER_NOMBRE = "nombre ASC";
		public static final String UNIDAD_ECO = "eco ASC";
		public static final String USUARIO_USERNAME = "username ASC";
		public static final String USUARIO_NOMBRE = "nombre ASC";
	}
	
	public class TipoServicio{
		public static final int COMPLETO = 1;
		public static final int BASICO = 2;
	}

}
