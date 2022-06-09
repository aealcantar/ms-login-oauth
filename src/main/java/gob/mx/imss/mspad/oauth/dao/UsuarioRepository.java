package gob.mx.imss.mspad.oauth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gob.mx.imss.mspad.oauth.model.entity.UsuarioEntity;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 27 abr. 2022
 * @IMSS
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	UsuarioEntity findByUsername(String desMatricula);
	Optional<UsuarioEntity> findByEmail(String email);
	
	Boolean existsByUsername(String desMatricula);

	@Transactional
	@Modifying
	@Query("update UsuarioEntity u set u.numIntentos = :numIntentos where cvePersonalId = :cvePersonalId")
	public void update3Reintentos(@Param("numIntentos") int numIntentos,@Param("cvePersonalId") Long cvePersonalId);
	
	@Transactional
	@Modifying
	@Query("update UsuarioEntity u set u.activo = :activo where cvePersonalId = :cvePersonalId")
	public void updateActivoInactivoUSer(@Param("activo") int activo,@Param("cvePersonalId") Long cvePersonalId);
	
	
	Boolean existsByUsernameAndPassword(String desMatricula, String password);
	
	@Modifying
	@Query("update UsuarioEntity usr set usr.password =?1, usr.numIntentos=0, usr.activo=1  where usr.email= ?2")
	Integer updatePassword(String password,String  email);


}
	