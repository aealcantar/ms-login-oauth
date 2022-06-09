package gob.mx.imss.mspad.oauth.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
@Table(name = "adtsc_usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = "id_usuario"),
		@UniqueConstraint(columnNames = "des_email") })
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(max = 20)
	@Column(name = "ID_USUARIO")
	private Long cvePersonalId;

	@OneToOne
	@JoinColumn(name = "ID_ROL")
	private RolEntity role;
	
	@OneToOne
	@JoinColumn(name = "ID_PUESTO")
	private PuestoEntity puesto;

	@Size(max = 45)
  	@Column(name = "CVE_USUARIO")
  	private String cedulaProfesional;
	
	@Size(max = 50)
	@Column(name = "NOM_USUARIO")
	private String nombre;
	
	@Size(max = 50)
	@Column(name = "NOM_PRIMER_APELLIDO")
	private String primerApellido;

	@Size(max = 50)
	@Column(name = "NOM_SEGUNDO_APELLIDO")
	private String segundoApellido;

	@Size(max = 45)
	@Column(name = "NOM_NOMBRE_COMPLETO")
	private String nombreCompleto;
	
	@Size(max = 50)
	@Email
	@Column(name = "DES_EMAIL")
	private String email;
	
	@Size(max = 120)
	@Column(name = "DES_PASSWORD")
	private String password;
	
	@Size(max = 45)
	@Column(name = "DES_MATRICULA")
	private String username;

	@Size(max = 45)
	@Column(name = "DES_UNIDAD_MEDICA")
	private String unidad;
	
	@Column(name = "IND_NUM_INTENTOS")
	private Integer numIntentos;
	
	@Column(name = "IND_ACTIVO")
	private Integer activo;

}
