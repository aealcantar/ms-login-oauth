package gob.mx.imss.mspad.oauth.service;

import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   26 abr. 2022
 * @IMSS
 */
public interface AplicacionService {
Aplicacion	findByNombreAplicacion(String nombreAplicacion);
}
