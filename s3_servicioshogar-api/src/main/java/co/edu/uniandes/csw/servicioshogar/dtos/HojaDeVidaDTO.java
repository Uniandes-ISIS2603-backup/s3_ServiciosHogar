/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Daniela Rocha Torres
 */
public class HojaDeVidaDTO implements Serializable{

    /**
     * Atributo que representa la trayectoria (experiencia) del prestador.
     */
    private String trayectoria; 
    /**
     * Atributo que representa la fecha de nacimiento del prestador.
     */
    private Date fechaNacimiento;
    /**
     * Atributo que representa el email del prestador.
     */
    private String email;
    /**
     * Atributo que representa el teléfono del prestador.
     */
    private Integer telefono;
    /**
     * Atributo que representa la dirección del prestador.
     */
    private String direccion;
    /**
     * Atributo que representa el nivel educativo del prestador.
     */
    private String formacion;
    
    public HojaDeVidaDTO(){      
    }
    
    public HojaDeVidaDTO(HojaDeVidaEntity hojaDeVidaEntity){
        if(hojaDeVidaEntity!=null)
        {
            this.trayectoria = hojaDeVidaEntity.getTrayectoria();
            this.fechaNacimiento = hojaDeVidaEntity.getFechaNacimiento();
            this.email = hojaDeVidaEntity.getEmail();
            this.telefono = hojaDeVidaEntity.getTelefono();
            this.direccion = hojaDeVidaEntity.getDireccion();
            this.formacion = hojaDeVidaEntity.getFormacion();
        }
    }
    
    public HojaDeVidaEntity toEntity(){
        HojaDeVidaEntity hojaDeVida = new HojaDeVidaEntity();
        
            hojaDeVida.setTrayectoria(this.trayectoria);
            hojaDeVida.setFechaNacimiento(this.fechaNacimiento);
            hojaDeVida.setEmail(this.email);
            hojaDeVida.setTelefono(this.telefono);
            hojaDeVida.setDireccion(this.direccion);
            hojaDeVida.setFormacion(formacion);
            
            return hojaDeVida;
    }
    
     public String getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
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
    
}
