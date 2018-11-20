/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniela Rocha Torres
 */
@Entity
public class HojaDeVidaEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToOne
    private PrestadorEntity prestador;

    @PodamExclude
    @OneToMany(mappedBy = "hojaDeVida")
    private List<ReferenciaEntity> referencias = new ArrayList<>();

    /**
     * Atributo que representa la trayectoria (experiencia) del prestador.
     */
    private String trayectoria;
    /**
     * Atributo que representa la fecha de nacimiento del prestador.
     */
    private String fechaNacimiento;
    /**
     * Atributo que representa el email del prestador.
     */
    private String email;
    /**
     * Atributo que representa el teléfono del prestador.
     */
    private Long telefono;
    /**
     * Atributo que representa la dirección del prestador.
     */
    private String direccion;
    /**
     * Atributo que representa el nivel educativo del prestador.
     */
    private String formacion;

    public PrestadorEntity getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorEntity prestador) {
        this.prestador = prestador;
    }

    public String getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public List<ReferenciaEntity> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<ReferenciaEntity> referencias) {
        this.referencias = referencias;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.prestador);
        hash = 61 * hash + Objects.hashCode(this.referencias);
        hash = 61 * hash + Objects.hashCode(this.trayectoria);
        hash = 61 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 61 * hash + Objects.hashCode(this.email);
        hash = 61 * hash + Objects.hashCode(this.telefono);
        hash = 61 * hash + Objects.hashCode(this.direccion);
        hash = 61 * hash + Objects.hashCode(this.formacion);
        return hash;
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
        final HojaDeVidaEntity other = (HojaDeVidaEntity) obj;
        if (!Objects.equals(this.trayectoria, other.trayectoria)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.formacion, other.formacion)) {
            return false;
        }
        if (!Objects.equals(this.prestador, other.prestador)) {
            return false;
        }
        if (!Objects.equals(this.referencias, other.referencias)) {
            return false;
        }
        return Objects.equals(this.telefono, other.telefono);
    }
    
    
}
