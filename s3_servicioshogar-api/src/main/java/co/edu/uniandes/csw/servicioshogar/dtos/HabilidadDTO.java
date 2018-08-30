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
 *
 * @author estudiante
 */
public class HabilidadDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String descripcion;
    
    private String tipo;
    
    private PrestadorDTO prestador;

    public HabilidadDTO()    {
        
    }
    
     public HabilidadDTO(HabilidadEntity habilidadEntity)    {
        if(habilidadEntity != null)
        {
            this.id = habilidadEntity.getId();
            this.descripcion = habilidadEntity.getDescripcion();
            this.tipo = habilidadEntity.getTipo();
            this.prestador = new PrestadorDTO(habilidadEntity.getPrestador());
        }
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrestador(PrestadorDTO prestador) {
        this.prestador = prestador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public PrestadorDTO getPrestador() {
        return prestador;
    }

    public Long getId() {
        return id;
    }

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
