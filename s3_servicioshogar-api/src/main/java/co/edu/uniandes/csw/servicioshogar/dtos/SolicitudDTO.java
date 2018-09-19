/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "fecha": date,
 *      "direccion": string
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *   {
 *      "fecha": "2000-08-20T00:00:00-05:00",
 *      "direccion": "Cale 22 No. 22-22",
 *   }
 * </pre>
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class SolicitudDTO implements Serializable{
    
    private Long id;
    private String direccion;
    private Date fecha;

    /**
     * Constructor por defecto.
     */
    public SolicitudDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param solicitudEntity: Es la entidad que se va a convertir a DTO
     */
    public SolicitudDTO(SolicitudEntity solicitudEntity){
        if(solicitudEntity!=null){
            this.id=solicitudEntity.getId();
            this.fecha=solicitudEntity.getFecha();
            this.direccion=solicitudEntity.getDireccion();
        }
    }

    /**
     * Devuelve el ID de la solicitud.
     * @return ID de la solicitud.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la solicitud.
     * @param id. nuevo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha de la solicitud.
     * @return fecha de la solicitud.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Modifica la fecha de la solicitud.
     * @param fecha. nueva fecha.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la dirrecion de la solicitud.
     * @return direccion de la solicitud
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica la direccion de la solicitud
     * @param direccion. nueva direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SolicitudEntity toEntity(){
        SolicitudEntity solicitudEntity = new SolicitudEntity();
        solicitudEntity.setId(this.id);
        solicitudEntity.setDireccion(this.direccion);
        solicitudEntity.setFecha(this.fecha);
        return solicitudEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
