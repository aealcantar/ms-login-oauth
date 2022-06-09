package gob.mx.imss.mspad.oauth.business;


import gob.mx.imss.mspad.oauth.model.bean.RecuperarPassword;

public interface AdmonPasswordService {

	RecuperarPassword generarCorreoPassword(String correo);
	RecuperarPassword actualizarPassword(String correo,String password);
	
}
