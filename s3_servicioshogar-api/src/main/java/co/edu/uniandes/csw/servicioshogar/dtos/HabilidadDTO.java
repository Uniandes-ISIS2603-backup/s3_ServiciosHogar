/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * HabilidadDTO Objeto de transferencia de datos de Habilidades. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "tipo": string,
 *      "descripcion": string,
 *      "prestador": {@link PrestadorDTO}
 *   }
 * </pre> Por ejemplo una habilidad se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 123,
 *      "tipo": CERRAJERIA",
 *      "descripcion": "Duplicado de llaves",
 *      "prestador":
 *      {
 *          "id": 123,
 *          "nombre": "María Ocampo",
 *          "cedula": "1007784099"
 *      }
 *   }
 *
 * </pre>
 *
 * @author María Ocampo
 */
public class HabilidadDTO implements Serializable {

//----------------------ATRIBUTOS ------------------------
    private Long id; 
    private String descripcion;
    private String tipo;
    
    /**
     * relación con un Prestador dado que tiene cardinalidad 1.
     */
    private PrestadorDTO prestador;

    /**
     * Constructor por defecto.
     */
    public HabilidadDTO()    {
        
    }
    
    /**
     * Constructor a partir de una entidad
     * @param habilidadEntity La entidad a la cual se construye el DTO
     */
    public HabilidadDTO(HabilidadEntity habilidadEntity)    {
        if(habilidadEntity != null)
        {
            this.id = habilidadEntity.getId();
            this.descripcion = habilidadEntity.getDescripcion();
            this.tipo = habilidadEntity.getTipo();
            if(habilidadEntity.getPrestador() != null)
                this.prestador = new PrestadorDTO(habilidadEntity.getPrestador());
            else
                this.prestador = null;
        }
    }
    
    /**
     * Modifica la descripcion de la habildiad
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Modifica el tipo de la habilidad
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Modifica el prestador  asociado a esta habilidad
     * @param prestador the prestador to set
     */
    public void setPrestador(PrestadorDTO prestador) {
        this.prestador = prestador;
    }

    /**
     * Devuelve la descripcion de la habilidad
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve el tipo de  la habilidad
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devuelve el prestador asociado a esta habilidad
     * @return the prestador
     */
    public PrestadorDTO getPrestador() {
        return prestador;
    }

    /**
     * Devuelve el id de la habilidad
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id de la habilidad
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

     /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public HabilidadEntity toEntity() {
        HabilidadEntity habilidadEntity = new HabilidadEntity();
        habilidadEntity.setId(this.id);
        habilidadEntity.setDescripcion(this.descripcion);
        habilidadEntity.setTipo(this.tipo);
        
        if(this.prestador != null)
        {  
            habilidadEntity.setPrestador(this.prestador.toEntity());
        }
        return habilidadEntity;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HabilidadDTO)) {
            return false;
        }
        HabilidadDTO other = (HabilidadDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
