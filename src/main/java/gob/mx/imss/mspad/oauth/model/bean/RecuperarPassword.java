package gob.mx.imss.mspad.oauth.model.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecuperarPassword implements Serializable {

	private String status;
	private String message;

}
