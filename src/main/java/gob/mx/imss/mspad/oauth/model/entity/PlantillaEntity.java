package gob.mx.imss.mspad.oauth.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ADTSC_PLANTILLA_NOTIFICACION")

public class PlantillaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PLANTILLA_NOTIFICACION", nullable = false)
    private Long id;

    @Column(name = "DES_ASUNTO")
    private String desAsunto;

    @Column(name = "DES_CLAVE", length = 100)
    private String desClave;

    @Column(name = "DES_PLANTILLA", length = 10000)
    private String desPlantilla;

    @Column(name = "FEC_EXPIRA")
    private Date fecExpira;

    @Column(name = "FEC_ALTA")
    private Date fecAlta;

    @Column(name = "FEC_ACTUALIZACION")
    private Date fecActualizacion;

    @Column(name = "FEC_BAJA")
    private Date fecBaja;

    @Column(name = "IND_ACTIVO")
    private Boolean indActivo;

}