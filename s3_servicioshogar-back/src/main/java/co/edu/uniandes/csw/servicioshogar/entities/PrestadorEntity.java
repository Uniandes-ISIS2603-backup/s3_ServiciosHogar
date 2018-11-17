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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria Ocampo
 */
@Entity
public class PrestadorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * El id autogenerado del prestador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * La cédula del prestador
     */
    private Integer cedula;
    
    /**
     * El nombre del prestador
     */
    private String nombre;
 
    /**
     * La relación uno a muchos con habilidad
     */
    @PodamExclude
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HabilidadEntity> habilidades = new ArrayList<HabilidadEntity>();
    
    /**
     * La relación uno a uno con hoja de vida
     */
    @PodamExclude
    @OneToOne(mappedBy = "prestador", fetch = FetchType.LAZY)
    private HojaDeVidaEntity hojaDeVida;
    
    /**
     * La relación uno a muchos con servicios
     */
    @PodamExclude
    @OneToMany(mappedBy = "prestador")
    private List<ServicioEntity> servicios;

    /**
     * Modifica los servicios asociados al prestador
     * @param servicios Los nuevos servicios
     */
    public void setServicios(List<ServicioEntity> servicios) {
        this.servicios = servicios;
    }

    /**
     * Retorna los servicios asociados al prestador
     * @return Los servicios asociados al prestador
     */
    public List<ServicioEntity> getServicios() {
        return servicios;
    }

    /**
     * Modifica la hoja de vida asociada al prestador
     * @param hojaDeVida La hoja de vida del prestador
     */
    public void setHojaDeVida(HojaDeVidaEntity hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
    
    /**
     * Retorna la hoja de vida asociada con el presatador
     * @return La hoja de vida asociada con el presatador
     */
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
