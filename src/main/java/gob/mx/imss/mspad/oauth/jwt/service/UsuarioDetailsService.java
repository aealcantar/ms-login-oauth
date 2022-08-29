package gob.mx.imss.mspad.oauth.jwt.service;

import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
import gob.mx.imss.mspad.oauth.jwt.exception.JwtException;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.util.Constants;
import gob.mx.imss.mspad.oauth.util.Crypto;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDetailsService.class);
    public String passwordAux;


    public void setPasswordAux(String passwordAux) {
        this.passwordAux = passwordAux;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Crypto crypto = new Crypto();
        UsuarioEntity usuarioEntity = usuarioRepository.findByNumMatricula(Long.valueOf(username));
        String rol = usuarioEntity.getNomUsuario();

        User.UserBuilder userBuilder = User.withUsername(username);
        String encryptedPassword = new BCryptPasswordEncoder().encode(crypto.decrypt(usuarioEntity.getDesPassword()));
        userBuilder.password(encryptedPassword).roles(rol);
        return userBuilder.build();
    }

    public void addIntento(String usuario) throws JwtException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByNumMatricula(Long.valueOf(usuario));

        if (usuarioEntity != null) {

            Long numIntentos = usuarioEntity.getIndNumIntentos();
            Long totalIntentos = numIntentos + 1L;

            usuarioRepository.update3Reintentos(totalIntentos, usuarioEntity.getId());

            if (totalIntentos >= 3L) {

                throw new JwtException(Constants.USUARIO_BLOQUEADO);

            }

        }

        throw new JwtException(Constants.CREDENCIALES_INCORRECTAS);

    }


    /**
     * Aqui comienzan los metodos de ms-login
     */

    public UsuarioEntity findByCorreo(String correo) {

        Optional<UsuarioEntity> usuario = usuarioRepository.findBydesEmail(correo);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException(Constants.CORREO_NO_REGISTRADO);
        }
        return usuario.get();
    }

    @Transactional
    public Integer updatePasswordByCorreo(String correo, String password) {
        // TODO Auto-generated method stub
        LOGGER.info("########## Update Password  by Email ##########");
        return usuarioRepository.updatePassword(password, correo);
    }

    public UsuarioEntity findByNumMatricula(Long matricula) {
        return usuarioRepository.findByNumMatricula(matricula);
    }
}
