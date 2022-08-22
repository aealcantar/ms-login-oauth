package gob.mx.imss.mspad.oauth.dao;

import gob.mx.imss.mspad.oauth.model.entity.Aplicacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
public interface AplicacionRepository extends JpaRepository<Aplicacion, Integer> {

 public Aplicacion findByNombreAplicacion(String nombreAplicacion);
}
