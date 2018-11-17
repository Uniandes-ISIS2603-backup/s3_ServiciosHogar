/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    private String 
            nombre /*Nombre del cliente*/, 
            direccion /*Direccion del Cliente*/, 
            correo /*Correo del Cliente*/ ;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SolicitudEntity> solicitudes = new ArrayList<SolicitudEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TarjetaCreditoEntity> tarjetas = new ArrayList<TarjetaCreditoEntity>();
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------
    /**
     * Devuelve el nombre del Cliente.
     * @return nombre del cliente.
     */
    public String getNombre() {return nombre;}

    /**
     * Asigna el nombre del cliente segun el 'nombre' ingresado por parametro.
     * @param nombre. Nombre del cliente a obtener.
     */
    public void setNombre(String nombre) {this.nombre = nombre;}
    
    /**
     * Devuelve la direccion del cliente.
     * @return direccion del cliente.
     */
    public String getDireccion() {return direccion;}
    
    /**
     * Asigna la direccion del cliente segun la 'direccion' ingresada por parametro.
     * @param direccion. Direccion del cliente.
     */
    public void setDireccion(String direccion) {this.direccion = direccion;}

    /**
     * Devuelve el correo del cliente.
     * @return correo del cliente.
     */
    public String getCorreo() {return correo;}

    /**
     * Asigna el correo del cliente segun el 'corre' ingresado por parametro.
     * @param correo. Correo electronico del cliente.
     */
    public void setCorreo(String correo) {this.correo = correo;} 

    public List<SolicitudEntity> getSolicitudes() {return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntity> solicitudes) {this.solicitudes = solicitudes;}
    
    public List<TarjetaCreditoEntity> getTarjetas() {return tarjetas;}
    
    public void setTarjetas(List<TarjetaCreditoEntity> tarjetas) {this.tarjetas = tarjetas;}
    
}
