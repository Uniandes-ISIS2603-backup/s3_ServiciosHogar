/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "descripcion": string,
 *      "requerimientos": string
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *   {
 *      "descripción": "Cambiar las cerraduras de tres puertas",
        "requerimientos": "CERRAJERIA"
 *   }
 * </pre>
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class ServicioDTO {
    
    private Long id;
    private String descripcion, requerimientos;
    
    /*
    * Relación a una solicitud  
    * dado que esta tiene cardinalidad 1.
     */
    private SolicitudDTO solicitud;
    
    /**
     * Constructor por defecto.
     */
    public ServicioDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param servicioEntity: Es la entidad que se va a convertir a DTO
     */
    public ServicioDTO(ServicioEntity servicioEntity){
        if(servicioEntity!=null){
            this.id=servicioEntity.getId();
            this.descripcion=servicioEntity.getDescripcion();
            this.requerimientos=servicioEntity.getRequerimientos();
        }
    }

    /**
     * Devuelve el ID del servicio.
     * @return ID del servicio.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del servicio.
     * @param id. Nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la descripcion del servicio.
     * @return descripcion del servicio.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modificar la descripcion del servicio.
     * @param descripcion. Nueva descripcion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve los requerimientos del servicio.
     * @return requerimientos del servicio.
     */
    public String getRequerimientos() {
        return requerimientos;
    }

    /**
     * Modifica los requerimientos del servicio.
     * @param requerimientos. Nuevos requerimientos.
     */
    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }
    
        /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ServicioEntity toEntity(){
        ServicioEntity servicioEntity = new ServicioEntity();
        servicioEntity.setId(this.id);
        servicioEntity.setDescripcion(this.descripcion);
        servicioEntity.setRequerimientos(this.requerimientos);
        return servicioEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}