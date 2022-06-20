package gob.mx.imss.mspad.oauth.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.mx.imss.mspad.oauth.business.AdmonPasswordService;
import gob.mx.imss.mspad.oauth.dao.PlantillaRepository;
import gob.mx.imss.mspad.oauth.model.bean.RecuperarPassword;
import gob.mx.imss.mspad.oauth.model.entity.PlantillaEntity;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;
import gob.mx.imss.mspad.oauth.service.IUsuarioService;
import gob.mx.imss.mspad.oauth.service.MailService;
import gob.mx.imss.mspad.oauth.util.Constants;
import gob.mx.imss.mspad.oauth.util.Crypto;
import gob.mx.imss.mspad.oauth.util.FechaUtil;

@Component
public class AdmonPasswordServiceImpl implements AdmonPasswordService {


    private static final Logger LOGGER = LoggerFactory.getLogger(AdmonPasswordServiceImpl.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PlantillaRepository plantillaRepository;

    @Override
    public RecuperarPassword generarCorreoPassword(String correo) {
        // TODO Auto-generated method stub
        RecuperarPassword recPass = new RecuperarPassword();
        try {
            LOGGER.info("Se verifica que exista el correo ingresado en el sistema" + correo);

            UsuarioEntity usuario = usuarioService.findByCorreo(correo);

            List<String> correos = new ArrayList<String>();
            correos.add(usuario.getDesEmail());


            PlantillaEntity plantillaEntity = plantillaRepository.findByDesClave("conf_reset_password");


            String htmlText = null;
            String htmlText2 = null;
            String htmlText3 = null;
            String plantillaString = null;
//        String cadenaDondeBuscar = "DATA_FECHA";
            String loQueQuieroBuscar = "DATA_FECHA";
            String[] palabras = loQueQuieroBuscar.split(" ");
            for (String palabra : palabras) {
                if (plantillaEntity.getDesPlantilla().contains(palabra)) {
                    htmlText = plantillaEntity.getDesPlantilla().replaceAll("DATA_FECHA", FechaUtil.fechaCompleta());
                    htmlText2 = htmlText.replaceAll("DATA_NOMBRE", usuario.getNomNombreCompleto());
                    htmlText3 = htmlText2.replaceAll("DATA_CORREO", correo);
                    plantillaString = htmlText3.replaceAll("DATA_FORMAT2", FechaUtil.fechaHoy());

                }
            }


            mailService.sendMail(correos, plantillaEntity.getDesAsunto(), plantillaString, null, null, usuario.getNomNombreCompleto(), usuario.getDesEmail());

            recPass.setStatus("200");
            return recPass;

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            recPass.setStatus(ExceptionUtils.getFullStackTrace(e));
        }

        return recPass;
    }

    @Override
    public RecuperarPassword actualizarPassword(String correo, String password) {
        // TODO Auto-generated method stub
        LOGGER.info("Actualizar nuevo password en el sistema" + correo);
        RecuperarPassword recuperarPassword = new RecuperarPassword();
        try {
            Crypto crypto = new Crypto();
            String passEncript = crypto.encrypt(password);
            Integer result = usuarioService.updatePasswordByCorreo(correo, passEncript);
            if (result > 0) recuperarPassword.setStatus("200");


        } catch (Exception e) {

            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            recuperarPassword.setStatus(ExceptionUtils.getFullStackTrace(e));
        }
        return recuperarPassword;
    }

}
