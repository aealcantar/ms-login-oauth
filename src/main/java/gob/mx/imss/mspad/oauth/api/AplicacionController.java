package gob.mx.imss.mspad.oauth.api;

import gob.mx.imss.mspad.oauth.business.AdmonPasswordService;
import gob.mx.imss.mspad.oauth.jwt.modelJwt.AuthenticationReq;
import gob.mx.imss.mspad.oauth.jwt.modelJwt.TokenInfo;
import gob.mx.imss.mspad.oauth.jwt.service.JwtUtilService;
import gob.mx.imss.mspad.oauth.jwt.service.UsuarioDetailsService;
import gob.mx.imss.mspad.oauth.model.bean.*;
import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;
import gob.mx.imss.mspad.oauth.model.entity.RolEntity;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.service.AplicacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 27 abr. 2022
 * @IMSS
 */
@RestController
@RequestMapping("")
@CrossOrigin
public class AplicacionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AplicacionController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService usuarioDetailsService;

	@Autowired
	private JwtUtilService jwtUtilService;

	@Autowired
	private AdmonPasswordService admonPasswordService;
	@Autowired
	private AplicacionService aplicacionService;

	@Autowired
	UsuarioDetailsService usuarioService;



	@PostMapping("/publico/authenticate")
	public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) throws Exception {
		LOGGER.info("Autenticando al usuario {}", authenticationReq.getUsuario());
		usuarioService.setPasswordAux(authenticationReq.getClave());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
						authenticationReq.getClave()));
		final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
				authenticationReq.getUsuario());
		final String jwt = jwtUtilService.generateToken(userDetails);
		TokenInfo tokenInfo = new TokenInfo(jwt);
		return ResponseEntity.ok(tokenInfo);
	}

	/**
	 * Aqui comienzan los metodos de ms-login
	 */

	@GetMapping("/app")
	public AplicacionBean get(@RequestParam(value = "appName") String appName) {
		LOGGER.info("Logging Request :{}", appName);
		Aplicacion aplicacion = aplicacionService.findByNombreAplicacion(appName);

		LOGGER.info("Logging Response :{}", aplicacion.toString());

		AplicacionBean aplicacionDTO = new AplicacionBean();
		aplicacionDTO.setCveUsuario(aplicacion.getCveUsuario());
		aplicacionDTO.setCvePassword(aplicacion.getCvePassword());
		aplicacionDTO.setData(aplicacion.getCveLLavePub());
		return aplicacionDTO;
	}

	@GetMapping("/recuperarPassword/{correo}/")
	public ResponseEntity<RecuperarPassword> recuperarPassword(
			@PathVariable(value = "correo", required = true) String correo) {
		LOGGER.info("Logging Request :{}", correo);
		RecuperarPassword status = admonPasswordService.generarCorreoPassword(correo);
		if (status.getStatus().equals("200"))
			return new ResponseEntity<>(status, HttpStatus.OK);
		else
			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/actualizarPassword/")
	public ResponseEntity<RecuperarPassword> actualizarPassword(@RequestBody AdmonPasswordRequest request) {
		LOGGER.info("update password Request by email:{}", request.getEmail());
		RecuperarPassword res = admonPasswordService.actualizarPassword(request.getEmail(), request.getPassword());
		if (res.getStatus() != null && res.getStatus().equals("200")) {
			return new ResponseEntity<RecuperarPassword>(res, HttpStatus.OK);
		}
		return new ResponseEntity<RecuperarPassword>(res, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/getUserSession")
	public UsuarioBean getUserSession(@RequestParam(value = "aliasUsuario") String aliasUsuario) {
		LOGGER.info("Request :{}", aliasUsuario);
		try {
			if (aliasUsuario != null) {
				UsuarioEntity usuario = usuarioService.findByNumMatricula(Long.valueOf(aliasUsuario));
				if (usuario != null) {
					RolEntity rolEntity = usuario.getRole();
					// PuestoEntity puestoEntity = usuario.getPuesto();
					LOGGER.info("Response :{}", usuario);
					UsuarioBean usuarioDTO = new UsuarioBean();
					usuarioDTO.setIdUsuario(usuario.getId());
					usuarioDTO.setAliasNombre(usuario.getNomUsuario());
					usuarioDTO.setNombre(usuario.getNomNombreCompleto());
					usuarioDTO.setApellidoPaterno(usuario.getNomPrimerApellido());
					usuarioDTO.setApellidoMaterno(usuario.getNomSegundoApellido());
					usuarioDTO.setCorreo(usuario.getDesEmail());
					usuarioDTO.setMatricula(Long.valueOf(usuario.getNumMatricula()));
					usuarioDTO.setCedulaProfesional(String.valueOf(usuario.getNumMatricula()));
					usuarioDTO.setUnidadMedica(usuario.getDesUnidadMedica());
					Rol rolDTO = new Rol();

					if (rolEntity != null) {
						rolDTO.setIdRol(rolEntity.getIdRol());
						rolDTO.setNombreRol(rolEntity.getNombre());
						usuarioDTO.setRol(rolDTO);
					}

					Puesto puestoDTO = new Puesto();
					// puestoDTO.setIdPuesto(puestoEntity.getIdPuesto());
					puestoDTO.setNombrePuesto(usuario.getDesPuesto());
					usuarioDTO.setPuesto(puestoDTO);
					return usuarioDTO;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}