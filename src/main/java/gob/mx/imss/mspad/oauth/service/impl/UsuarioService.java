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
import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.service.IUsuarioService;

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
        LOGGER.info("########## loadUserByUsername  ##########");

        List<GrantedAuthority> authorities = new ArrayList<>();
        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);

        if (usuarioEntity == null) {

            throw new UsernameNotFoundException("Error :  ¡Usuario inválido!");

        } else {

            if (usuarioEntity.getIndNumIntentos().equals(3) || usuarioEntity.getIndActivo().equals(0)) {
                throw new UsernameNotFoundException(
                        "Usuario bloqueado por número de intentos excedidos, favor de contactar al administrador.");

            }

            if (!usuarioRepository.existsByUsernameAndPassword(username, passwordAux)) {
                if (usuarioEntity != null && usuarioEntity.getIndNumIntentos() <= 3) {
                    int numIntentos = usuarioEntity.getIndNumIntentos().intValue() + 1;
                    usuarioEntity.setIndNumIntentos((long) numIntentos);
                    LOGGER.info("########## Num intentos  ##########" + numIntentos);

                    usuarioRepository.update3Reintentos(numIntentos, usuarioEntity.getId());

                }
                if (usuarioEntity.getIndNumIntentos() == 3) {
                    usuarioRepository.updateActivoInactivoUSer(1, usuarioEntity.getId());
                    throw new UsernameNotFoundException(
                            "¡Ha superado el número de intentos! Su cuenta se ha bloquedo Intente recuperar su contraseña.");

                }

                throw new UsernameNotFoundException(
                        "¡Credenciales incorrectas. Volver a intentar! Solo tiene 3 intentos");

            }

        }

        return new CustomUser(username, passwordEncoder.encode(this.passwordAux), true, true, true, true, authorities);

    }

    @Override
    public UsuarioEntity findByCorreo(String correo) {
        LOGGER.info("########## findUserByEmailUsername  ##########");
        Optional<UsuarioEntity> usuario = usuarioRepository.findByEmail(correo);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Error :  ¡Correo no registrado!");

        }

        return usuario.get();
    }

    @Override
    public UsuarioEntity findByNomCuentaMetro(String nomCuentaMetro) {

        return null;
    }

    @Override
    public UsuarioEntity findByNombre(String aliasUsuario) {
        return usuarioRepository.findByUsername(aliasUsuario);
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

