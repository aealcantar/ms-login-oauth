package gob.mx.imss.mspad.oauth.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name="ADTST_APLICACIONES")
public class Aplicacion implements Serializable {
	
	private static final long serialVersionUID = -8337640745343203538L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_APLICACION")
	private Long cveId;
	
	@NotNull
	@NotEmpty
	@Column(name = "DES_APLICACION")
	private String nombreAplicacion; 
	
	
	@NotNull
	@NotEmpty
	@Column(name = "DES_CLAVE_APLICACION")
	private String cveAplicacion; 
	
	@NotNull
	@NotEmpty
	@Column(name = "DES_ID_USUARIO")
	private String cveUsuario;
	

	@NotNull
	@NotEmpty
	@Column(name = "DES_ID_PASSWORD")
	private String cvePassword;
	

	@NotNull
	@NotEmpty
	@Column(name = "DES_ID_LLAVE_PUB")
	private String cveLLavePub;
	
	@NotNull
	@NotEmpty
	@Column(name = "DES_ID_LLAVE_PRIVADA")
	private String cveLLavePriv;
	
	@Column(name = "FEC_EXPIRA")
	private Date fechaExpira;
	
	@Column(name = "FEC_ALTA")
	private Date fechaAlta;
	
	@Column(name = "FEC_ACTUALIZACION")
	private Date fechaActualizacion;
	
	@Column(name = "FEC_BAJA")
	private Date fechaBaja;
	
	@NotNull
	@NotEmpty
	@Column(name = "IND_ACTIVO")
	private Integer actiVO;
	
}
