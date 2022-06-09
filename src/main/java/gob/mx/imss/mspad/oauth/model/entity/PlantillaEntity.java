package gob.mx.imss.mspad.oauth.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   28 abr. 2022
 * @IMSS
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "adts_plantillas_notificaciones")
public class PlantillaEntity {
    /** Identificador de la tabla. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantillanot")
    private Long id;

    /** Asunto. */
    @Column(name = "des_asunto")
    private String asunto;

    /** Asunto. */
    @Column(name = "des_plantilla")
    private String plantilla;
    
    /** Clave. */
    @Column(name = "des_clave")
    private String clave;
}