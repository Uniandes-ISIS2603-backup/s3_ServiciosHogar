
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author María José Ocampo Vargas
 */
@Entity
public class PrestadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private Integer cedula;
    
    public PrestadorDTO()    {
        
    }
    
     public PrestadorDTO(PrestadorEntity prestadorEntity)    {
        if(prestadorEntity != null)
        {
            this.id = prestadorEntity.getId();
            this.name = prestadorEntity.getName();
            this.cedula = prestadorEntity.getCedula();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    public PrestadorEntity toEntity() {
        PrestadorEntity prestadorEntity = new PrestadorEntity();
        prestadorEntity.setId(this.id);
        prestadorEntity.setName(this.name);
        return prestadorEntity;
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
        if (!(object instanceof PrestadorDTO)) {
            return false;
        }
        PrestadorDTO other = (PrestadorDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO[ id=" + id + " ]";
    }
    
}
