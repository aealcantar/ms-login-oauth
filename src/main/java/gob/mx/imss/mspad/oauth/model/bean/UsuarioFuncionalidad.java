package gob.mx.imss.mspad.oauth.model.bean;

import java.util.ArrayList;
import lombok.Data;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
@Data
public class UsuarioFuncionalidad {
	
	private Integer cveUsuario;
	private String nomUsuario;
	private String paternoUsuario;
	private String maternoUsuario;
	private String correoUsuario;
	
	private ArrayList<Dependencia> dependencias;	
}