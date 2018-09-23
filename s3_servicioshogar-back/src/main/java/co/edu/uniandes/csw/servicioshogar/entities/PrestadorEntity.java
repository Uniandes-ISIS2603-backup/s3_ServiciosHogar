/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Maria Ocampo
 */
@Entity
public class PrestadorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer cedula;
    private String nombre;
 
    @PodamExclude
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HabilidadEntity> habilidades = new ArrayList<HabilidadEntity>();
    
    @PodamExclude
    @OneToOne(mappedBy = "prestador", fetch = FetchType.LAZY)
    private HojaDeVidaEntity hojaDeVida;
    
    @PodamExclude
    @OneToMany(mappedBy = "prestador")
    private List<ServicioEntity> servicios;

    public void setServicios(List<ServicioEntity> servicios) {
        this.servicios = servicios;
    }

    public List<ServicioEntity> getServicios() {
        return servicios;
    }

    public void setHojaDeVida(HojaDeVidaEntity hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }

    public HojaDeVidaEntity getHojaDeVida() {
        return hojaDeVida;
    }
    /**
     * Modifica la cedula del prestador
     * @param cedula the cedula to set
     */
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    /**
     * Modifica el nombre del prestador
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Modifica la lista de habilidades
     * @param habilidades the habilidades to set
     */
    public void setHabilidades(List<HabilidadEntity> habilidades) {
        this.habilidades = habilidades;
    }
    
    /**
     * Devuelve la cedula del prestador
     * @return the cedula
     */
    public Integer getCedula() {
        return cedula;
    }
    
    /**
     * Devuelve el nombre del prestador
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve las habilidades del prestador
     * @return the habilidades
     */
    public List<HabilidadEntity> getHabilidades() {
        return habilidades;
    }
    
    /**
     * Devuelve el id del prestador
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Modifica el id el prestador
     * @param id the id to set
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
