package gob.mx.imss.mspad.oauth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Antonio Esteban Alcantar Valencia
 * @Date 6 junio 2022
 * @IMSS
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADTSC_ROLES")
public class RolEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ROL", length = 20)
	private Long idRol;

	@Column(name = "NOM_ROL", length = 45)
	private String nombre;

	@Column(name = "FEC_EXPIRA")
	private Date fechaExpira;

	@Column(name = "FEC_ALTA")
	private Date fechAlta;

	@Column(name = "FEC_ACTUALIZACION")
	private Date fechActualizacion;
	
	@Column(name = "FEC_BAJA")
	private Date fechaBaja;
	
	@Column(name = "IND_ACTIVO")
	private Integer activo;

}