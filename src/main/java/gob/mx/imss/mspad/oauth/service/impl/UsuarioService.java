package gob.mx.imss.mspad.oauth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
import gob.mx.imss.mspad.oauth.model.bean.FuncionalidadRequest;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.service.IUsuarioService;
import gob.mx.imss.mspad.oauth.util.Constants;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 28 abr. 2022
 * @IMSS
 */
@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public String passwordAux;
	public Integer idSistema;

	public FuncionalidadRequest funcionalidadRequest;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void setPasswordAux(String passwordAux) {
		this.passwordAux = passwordAux;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername");

		List<GrantedAuthority> authorities = new ArrayList<>();
		LOGGER.info("loadUserByUsername");
		UsuarioEntity usuarioEntity = usuarioRepository.findByNumMatricula(Long.valueOf(username));

		if (usuarioEntity == null) {

			throw new UsernameNotFoundException(Constants.USUARIO_INVALIDO);

		} else {

			if (usuarioEntity.getIndActivo().booleanValue()) {

				if (usuarioEntity.getIndNumIntentos() == null) {

					throw new UsernameNotFoundException(Constants.NUM_INTENTOS_NULO);

				} else {

					if (usuarioEntity.getIndNumIntentos()>=3 || !usuarioEntity.getIndActivo().booleanValue()) {

						throw new UsernameNotFoundException(Constants.USUARIO_BLOQUEADO);

					}

					if (!usuarioRepository.existsByNumMatriculaAndDesPassword(Long.parseLong(username), passwordAux)) {
						if (usuarioEntity != null && usuarioEntity.getIndNumIntentos() <= 3) {
							int numIntentos = usuarioEntity.getIndNumIntentos().intValue() + 1;
							usuarioEntity.setIndNumIntentos((long) numIntentos);

							usuarioRepository.update3Reintentos(Long.valueOf(numIntentos), usuarioEntity.getId());

						}
						if (usuarioEntity.getIndNumIntentos() == 3) {
							usuarioRepository.updateActivoInactivoUSer(false, usuarioEntity.getId());
							throw new UsernameNotFoundException(Constants.NUMERO_INTENTOS_SUPERADO);

						}

						throw new UsernameNotFoundException(Constants.CREDENCIALES_INCORRECTAS);

					}

				}

			} else {
				throw new UsernameNotFoundException(Constants.USUARIO_INACTIVO);
			}
		}
		return new CustomUser(username, passwordEncoder.encode(passwordAux), true, true, true, true, authorities);

	}

	@Override
	public UsuarioEntity findByCorreo(String correo) {
		LOGGER.info("########## findUserByEmailUsername  ##########");
		Optional<UsuarioEntity> usuario = usuarioRepository.findBydesEmail(correo);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException(Constants.CORREO_NO_REGISTRADO );

		}

		return usuario.get();
	}

	@Override
	public UsuarioEntity findByNomCuentaMetro(String nomCuentaMetro) {

		return null;

	}

	@Override
	public UsuarioEntity findByNumMatricula(Long matricula) {
		return usuarioRepository.findByNumMatricula(matricula);
	}

	@Override
	public UsuarioEntity findByNombre(String aliasUsuario) {
		return usuarioRepository.findByNomUsuario(aliasUsuario);
	}

	@Override
	public void setCveSistema(Integer idSistema) {
		this.idSistema = idSistema;

	}

	@Override
	public Integer getCveSistema() {
		return this.idSistema;

	}

	@Override
	@Transactional
	public Integer updatePasswordByCorreo(String correo, String password) {
		// TODO Auto-generated method stub
		LOGGER.info("########## Update Password  by Email ##########");

		return usuarioRepository.updatePassword(password, correo);

	}

}
