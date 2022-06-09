package gob.mx.imss.mspad.oauth.model.bean;

import lombok.Data;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
@Data
public class FuncionalidadRequest {

	private String correoUsuario;
	private int cveUsuario;
	private int cveDependencia;
	private int cveSistema;
	private int cveRol;
	private int cveSubrol;
	private int cveModulo;
}
