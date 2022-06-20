package gob.mx.imss.mspad.oauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.mx.imss.mspad.oauth.model.entity.PlantillaEntity;


/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   28 abr. 2022
 * @IMSS
 */
@Repository
public interface PlantillaRepository extends JpaRepository<PlantillaEntity, Long> {

	PlantillaEntity findByDesClave(String clave);
}
