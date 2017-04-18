package com.sysdt.lock.util;

import java.util.regex.Pattern;

public class Constantes {
	
	public static final int LISTA_UNIDADES_VACIA = -1;
	public static final int TIEMPO_MAXIMO_ESPERA_EN_SEGUNDOS = 30;
	public static final Pattern EMAIL_VALIDATOR = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public static final int DIAS_FUTURO = 3;
	
	public class TipoUsuario {
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
		
	}

}
