package gob.mx.imss.mspad.oauth.util;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 2 may. 2022
 * @IMSS
 */
public class Constants {

	private Constants() {
	}

	public static final String APLICACION = "aplicacion";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "username";
	public static final String GRANT_TYPE = "grant_type";
	public static final String APPID = "appId";
	public static final String CONTENTTYPE = "Content-Type";
	public static final String USUARIO_INVALIDO = "Error :  ¡Usuario inválido!";
	public static final String NUM_INTENTOS_NULO = "El valor IndNumIntentos se encuentra nulo.";
	public static final String USUARIO_BLOQUEADO = "Usuario bloqueado por número de intentos excedidos, favor de contactar al administrador.";
	public static final String NUMERO_INTENTOS_SUPERADO = "¡Ha superado el número de intentos! Su cuenta se ha bloquedo Intente recuperar su contraseña.";
	public static final String CREDENCIALES_INCORRECTAS = "¡Credenciales incorrectas. Volver a intentar! Solo tiene 3 intentos";
	public static final String USUARIO_INACTIVO = "Usuario inactivo, favor de contactar al administrador.";
	public static final String CORREO_NO_REGISTRADO = "Error :  ¡Correo no registrado!";
}
