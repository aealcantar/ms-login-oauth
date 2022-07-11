package gob.mx.imss.mspad.oauth.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gob.mx.imss.mspad.oauth.business.AdmonPasswordService;
import gob.mx.imss.mspad.oauth.model.bean.AdmonPasswordRequest;
import gob.mx.imss.mspad.oauth.model.bean.AplicacionBean;
import gob.mx.imss.mspad.oauth.model.bean.Puesto;
import gob.mx.imss.mspad.oauth.model.bean.RecuperarPassword;
import gob.mx.imss.mspad.oauth.model.bean.Rol;
import gob.mx.imss.mspad.oauth.model.bean.UsuarioBean;
import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;
import gob.mx.imss.mspad.oauth.model.entity.PuestoEntity;
import gob.mx.imss.mspad.oauth.model.entity.RolEntity;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.service.AplicacionService;
import gob.mx.imss.mspad.oauth.service.impl.UsuarioService;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 27 abr. 2022
 * @IMSS
 */
@RestController
@RequestMapping("/api/aplicacion")
@CrossOrigin
public class AplicacionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AplicacionController.class);
	@Autowired
	private AplicacionService aplicacionService;
	@Autowired
	private AdmonPasswordService admonPasswordService;
	@Autowired
	private UsuarioService usuarioService;

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

			UsuarioEntity usuario = usuarioService.findByNombre(aliasUsuario);

			if (usuario != null) {
				RolEntity rolEntity = usuario.getRole();
				// PuestoEntity puestoEntity = usuario.getPuesto();
				LOGGER.info("Response :{}", usuario);
				UsuarioBean usuarioDTO = new UsuarioBean();
				usuarioDTO.setAliasNombre(usuario.getNomUsuario());
				usuarioDTO.setNombre(usuario.getNomNombreCompleto());
				usuarioDTO.setApellidoPaterno(usuario.getNomPrimerApellido());
				usuarioDTO.setApellidoMaterno(usuario.getNomSegundoApellido());
				usuarioDTO.setCorreo(usuario.getDesEmail());
				usuarioDTO.setMatricula(Long.valueOf(usuario.getNumMatricula()));
				usuarioDTO.setCedulaProfesional(String.valueOf(usuario.getNumMatricula()));
				usuarioDTO.setUnidadMedica(usuario.getDesUnidadMedica());
				Rol rolDTO = new Rol();
				rolDTO.setIdRol(rolEntity.getIdRol());
				rolDTO.setNombreRol(rolEntity.getNombre());
				usuarioDTO.setRol(rolDTO);
				Puesto puestoDTO = new Puesto();
				// puestoDTO.setIdPuesto(puestoEntity.getIdPuesto());
				puestoDTO.setNombrePuesto(usuario.getDesPuesto());
				usuarioDTO.setPuesto(puestoDTO);
				return usuarioDTO;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}