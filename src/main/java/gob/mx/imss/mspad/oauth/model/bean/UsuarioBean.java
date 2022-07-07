package gob.mx.imss.mspad.oauth.model.bean;

import java.math.BigInteger;

import lombok.Data;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
@Data
public class UsuarioBean {
	
	private BigInteger idUsuario;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String aliasNombre;
	private Long matricula;
	private String cedulaProfesional;
	private Puesto puesto;
	private String unidadMedica;
	private Rol rol;
	
	
}