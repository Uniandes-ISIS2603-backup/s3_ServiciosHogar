/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * PrestadorDTO Objeto de transferencia de datos de Prestadores. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "cedula": number
 *   }
 * </pre> Por ejemplo un prestador se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 123,
 *      "nombre": "Maria Ocampo",
 *      "cedula": 1007784099
 *   }
 *
 * </pre>
 *
 * @author Maria Ocampo
 * @author Carlos Robles / 3er Ciclo.
 */
public class PrestadorDTO implements Serializable {
    
    private Long id /*Id del prestador*/;
    private String nombre /*Nombre del prestador*/;
    private String correo /*Correo del prestador*/ ;
    private String contrasena /*Contrasena del prestador*/;
    private Integer cedula /*Cedula del prestador*/;
    private HojaDeVidaDTO hojaDeVida /*Hoja de vide del prestador*/;
    
    /**
     * Constructor por defecto
     */
    public PrestadorDTO(){}
    
    /**
     * Constructor a partir de una entidad
     * @param prestadorEntity La entidad del prestador
     */
    public PrestadorDTO(PrestadorEntity prestadorEntity)
    {
        if(prestadorEntity != null)
        {
            this.id = prestadorEntity.getId();
            this.nombre = prestadorEntity.getNombre();
            this.cedula = prestadorEntity.getCedula();
            this.contrasena = prestadorEntity.getContrasena();
            this.correo = prestadorEntity.getCorreo();
            this.hojaDeVida=new HojaDeVidaDTO(prestadorEntity.getHojaDeVida());
        }
    }

    /**
     * Modifica la cedula del prestador
     * @param cedula the cedula to set
     */
    public void setCedula(Integer cedula) {this.cedula = cedula;}

    /**
     * Devuelve la cedula del prestador
     * @return the cedula
     */
    public Integer getCedula() {return cedula;}

    /**
     * Modifica el nombre del prestador
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {this.nombre = nombre;}

    /**
     * Devuelve el nombre del prestador
     * @return the nombre
     */
    public String getNombre() {return nombre;}

    /**
     * Devuelve el id del prestador
     * @return the id
     */
    public Long getId() {return id;}

    /**
     * Modifica el id del prestador
     * @param id the id to set
     */
    public void setId(Long id) {this.id = id;}
    
    /**
     * Devuelve el correo del cliente.
     * @return correo del cliente.
     */
    public String getCorreo() {return correo;}

    /**
     * Asigna el correo del cliente segun el 'corre' ingresado por parametro.
     * @param correo Correo electronico del cliente.
     */
    public void setCorreo(String correo) {this.correo = correo;}     

    /**
     * Devuelve la contrasena del prestador.
     * @return la contrasena del cliente.
     */
    public String getContrasena() {return contrasena;}

    /**
     * Asigna la contrasena al prestador segun la 'contrasena' ingresada por parametro.
     * @param contrasena 
     */
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}  

    /**
     * Retorna la hoja de vida del prestador
     * @return the hoja de vida
     */
    public HojaDeVidaDTO getHojaDeVida() {return hojaDeVida;}

    /**
     * Modifica la hoja de vida del prestador
     * @param hojaDeVida the hoja de vida to set
     */
    public void setHojaDeVida(HojaDeVidaDTO hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
    
     /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PrestadorEntity toEntity() {
        PrestadorEntity prestadorEntity = new PrestadorEntity();
        prestadorEntity.setId(this.id);
        prestadorEntity.setNombre(this.nombre);
        prestadorEntity.setCedula(this.cedula);
        if(this.hojaDeVida != null)
            prestadorEntity.setHojaDeVida(this.hojaDeVida.toEntity());
        return prestadorEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
