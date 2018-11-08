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
 *<pre>
 * Json:
 * {
 * "id": "1",
 * "trayectoria": "7 meses",
 * "fechaNacimiento": "1998/02/03",
 * "email": "caro@gmail.com",
 * "telefono": 3102569876,
 * "direccion": "Cll. 30 #25-02",
 * "formacion": "bachiller"
 * }
 * 
 * 
 * @author Daniela Rocha Torres
 */
public class HojaDeVidaDTO implements Serializable{

    private Long id;
    /**
     * Atributo que representa la trayectoria (experiencia) del prestador.
     */
    private String trayectoria; 
    /**
     * Atributo que representa la fecha de nacimiento del prestador.
     * FORMATO: DD/MM/YYYY
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
    
    
    public HojaDeVidaDTO(){      
    }
    
    public HojaDeVidaDTO(HojaDeVidaEntity hojaDeVidaEntity){
        if(hojaDeVidaEntity!=null)
        {
            this.id = hojaDeVidaEntity.getId();
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
        
            hojaDeVida.setId(this.id);
            hojaDeVida.setTrayectoria(this.trayectoria);
            hojaDeVida.setFechaNacimiento(this.fechaNacimiento);
            hojaDeVida.setEmail(this.email);
            hojaDeVida.setTelefono(this.telefono);
            hojaDeVida.setDireccion(this.direccion);
            hojaDeVida.setFormacion(formacion);
            
            return hojaDeVida;
    }
    
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id){
        this.id=id;
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
    
}
