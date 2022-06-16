package gob.mx.imss.mspad.oauth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ADTSC_USUARIOS")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_ROL")
    private RolEntity role;

    @Column(name = "DES_PUESTO", length = 45)
    private String desPuesto;

    @Column(name = "CVE_USUARIO", length = 45)
    private String cveUsuario;

    @Column(name = "NOM_USUARIO", length = 50)
    private String nomUsuario;

    @Column(name = "NOM_PRIMER_APELLIDO", length = 50)
    private String nomPrimerApellido;

    @Column(name = "NOM_SEGUNDO_APELLIDO", length = 50)
    private String nomSegundoApellido;

    @Column(name = "NOM_NOMBRE_COMPLETO", length = 45)
    private String nomNombreCompleto;

    @Column(name = "DES_EMAIL", length = 50)
    private String desEmail;

    @Column(name = "DES_PASSWORD", length = 120)
    private String desPassword;

    @Column(name = "NUM_MATRICULA")
    private Long numMatricula;

    @Column(name = "DES_UNIDAD_MEDICA", length = 45)
    private String desUnidadMedica;

    @Column(name = "DES_ESCUELA_PROCEDENCIA", length = 45)
    private String desEscuelaProcedencia;

    @Column(name = "IND_NUM_INTENTOS")
    private Long indNumIntentos;

    @Column(name = "FEC_EXPIRA")
    private LocalDate fecExpira;

    @Column(name = "FEC_ALTA")
    private Instant fecAlta;

    @Column(name = "FEC_ACTUALIZACION")
    private Instant fecActualizacion;

    @Column(name = "FEC_BAJA")
    private Instant fecBaja;

    @Column(name = "IND_ACTIVO")
    private Boolean indActivo;



}