/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.*;
import uk.co.jemos.podam.common.PodamExclude;

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
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Modifica el id de la habilidad
     * @param id El nuevo id
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HabilidadEntity other = (HabilidadEntity) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.prestador, other.prestador);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.tipo);
        hash = 31 * hash + Objects.hashCode(this.descripcion);
        hash = 31 * hash + Objects.hashCode(this.prestador);
        return hash;
    }
    
    
}
