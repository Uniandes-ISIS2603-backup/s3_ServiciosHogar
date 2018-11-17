/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author Maria Ocampo
 */
@Entity
public class HabilidadEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * El id atogenrado
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
  
    /**
     * El tipo de la habilidad
     */
    private String tipo;
    
    /**
     * La descripción de la habilidad
     */
    private String descripcion;
 
    /**
     * La relación muchos a uno con prestador
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PrestadorEntity prestador;
    
    /**
     * Modifica el tipo de la habildiad
     * @param tipo El nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Modifica la descripción de la habilidad
     * @param descripcion La nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Modifica el prestador asocidado a la habildiad
     * @param prestador El nuevo prestador
     */
    public void setPrestador(PrestadorEntity prestador) {
        this.prestador = prestador;
    }
    
    /**
     * Retorna el tipo de la habilidad
     * @return El tipo
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Retorna la descripción de la habilidad
     * @return La descripción
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Retorna el prestador asociado a la habilidad
     * @return El prestador asociado
     */
    public PrestadorEntity getPrestador() {
        return prestador;
    }
    
    /**
     * Retorna el id autogenerado de la habilidad
     * @return El id autogenrado
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id de la habilidad
     * @param id El nuevo id
     */
    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof HabilidadEntity)) {
            return false;
        }
        HabilidadEntity other = (HabilidadEntity) object;
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
