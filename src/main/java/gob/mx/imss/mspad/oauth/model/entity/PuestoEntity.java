package gob.mx.imss.mspad.oauth.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "ADTSC_PUESTOS")
public class PuestoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PUESTO", length = 20)
	private Long idPuesto;

	@Column(name = "NOM_PUESTO", length = 45)
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