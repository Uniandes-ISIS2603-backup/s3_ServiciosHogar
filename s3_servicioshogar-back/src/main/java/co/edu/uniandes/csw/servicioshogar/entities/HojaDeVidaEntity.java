/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniela Rocha Torres
 */
@Entity
public class HojaDeVidaEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @OneToOne
    private PrestadorEntity prestador;
    
    @PodamExclude
    @OneToMany(mappedBy = "hojaDeVida")
    private List<ReferenciaEntity> referencias = new ArrayList<ReferenciaEntity>();
    
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
   
       public List<ReferenciaEntity> getReferencias() {return referencias;
    }

    public void setReferencias(List<ReferenciaEntity> referencias) {this.referencias = referencias;}
    
  
}
