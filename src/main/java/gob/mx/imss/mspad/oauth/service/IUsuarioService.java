package gob.mx.imss.mspad.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;



/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   26 abr. 2022
 * @IMSS
 */
public interface IUsuarioService {
	
	public UsuarioEntity findByNomCuentaMetro(String cveCorreo);	
	public UsuarioEntity findByCorreo(String correo);
	public Integer updatePasswordByCorreo(String correo,String password);
	public void setPasswordAux(String passwordAux);
	public void setCveSistema(Integer idSistema);
	public Integer getCveSistema();
	public UserDetails loadUserByUsername(String username);
	public UsuarioEntity findByNombre(String aliasUsuario);
	UsuarioEntity findByNumMatricula(Long matricula);

}
