package gob.mx.imss.mspad.oauth.service.impl;

import gob.mx.imss.mspad.oauth.dao.AplicacionRepository;
import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;
import gob.mx.imss.mspad.oauth.service.AplicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
@Service
public class AplicacionServiceImpl implements AplicacionService {
    @Autowired
	private  AplicacionRepository aplicacionDAO;
	@Override
	public Aplicacion findByNombreAplicacion(String nombreAplicacion) {
		return aplicacionDAO.findByNombreAplicacion(nombreAplicacion);
	}

}
