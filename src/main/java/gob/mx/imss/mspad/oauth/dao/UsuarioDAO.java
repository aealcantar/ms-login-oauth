package gob.mx.imss.mspad.oauth.dao;

import org.springframework.data.repository.CrudRepository;

import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   27 abr. 2022
 * @IMSS
 */
public interface UsuarioDAO extends CrudRepository<UsuarioEntity, Integer> {



}
