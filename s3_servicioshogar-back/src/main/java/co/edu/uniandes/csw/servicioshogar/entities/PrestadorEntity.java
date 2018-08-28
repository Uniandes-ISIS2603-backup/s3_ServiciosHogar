/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class PrestadorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private Integer cedula;

    @PodamExclude
    @OneToMany(mappedBy = "prestador")
    /**
     * Lista de habilidades del prestador
     */
    private List<String>habilities = new ArrayList<String>();
    
    /**
     * Hoja de vida del prestador
     */
    private String curriculumVitae = new String();
    
    /**
     * Modifica el nombre del prestador
     * @param name El nombre a cambiar
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve el nombre de la editorial
     * @return El nombre
     */
    public String getName() {
        return name;
    }
   
    /**
     * Modifica las habilidades del prestador
     * @param habilities Nuevas habilidades
     */
    public void setHabilities(List<String> habilities) {
        this.habilities = habilities;
    }

    /**
     * Modifica la hoja de vida del prestador
     * @param curriculumVitae Nueva hoja de vida
     */
    public void setCurriculumVitae(String curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    /**
     * Devuelbe las habilidades del prestador
     * @return Habilidades
     */
    public List<String> getHabilities() {
        return habilities;
    }

    /**
     * Devuelve la hoja de vida del prestador
     * @return Hoja de vida
     */
    public String getCurriculumVitae() {
        return curriculumVitae;
    }
    
    /**
     * Modifica la cédula.
     * @param cedula Cédula nueva
     */
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    /**
     * Devuelve la cédula del prestador.
     * @return La cédula
     */
    public Integer getCedula() {
        return cedula;
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
        if (!(object instanceof PrestadorEntity)) {
            return false;
        }
        PrestadorEntity other = (PrestadorEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity[ id=" + id + " ]";
    }
    
}