package gob.mx.imss.mspad.oauth.model.bean;

import lombok.Data;

import java.util.ArrayList;

/**
 * @Author Antonio Esteban Alcantar Valencia
 * @Date 6 junio 2022
 * @IMSS
 */
@Data
public class Rol {

	private Long idRol;
	private String nombreRol;

	private ArrayList<Subrol> subroles;	
}
