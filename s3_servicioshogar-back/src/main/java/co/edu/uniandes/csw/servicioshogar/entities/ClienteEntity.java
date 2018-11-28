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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

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
    private String nombre /*Nombre del cliente*/;
    private String direccion /*Direccion del Cliente*/; 
    private String correo /*Correo del Cliente*/ ;
    private String contrasena /*Contrasena del cliente*/;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SolicitudEntity> solicitudes = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TarjetaCreditoEntity> tarjetas = new ArrayList<>();
    
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
     * Asigna el correo del cliente segun el 'correo' ingresado por parametro.
     * @param correo. Correo electronico del cliente.
     */
    public void setCorreo(String correo) {this.correo = correo;} 

    /**
     * Devuelve la contrasena del cliente.
     * @return la contrasena del cliente.
     */
    public String getContrasena() {return contrasena;}

    /**
     * Asigna la contrasena al cliente segun la 'contrasena' ingresada por parametro.
     * @param contrasena 
     */
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}   

    /**
     * Devuelve las solicitudes del cliente.
     * @return las solicitudes del cliente.
     */
    public List<SolicitudEntity> getSolicitudes() {return solicitudes;}

    /**
     * Asigna las solicitudes al cliente segun la lista de solicitudes ingresada por parametro.
     * @param solicitudes - Lista de solicitudes del cliente.
     */
    public void setSolicitudes(List<SolicitudEntity> solicitudes) {this.solicitudes = solicitudes;}
    
    /**
     * Devuelve las tarjetas de credito del cliente.
     * @return las tarjetas de credito del cliente.
     */
    public List<TarjetaCreditoEntity> getTarjetas() {return tarjetas;}
    
    /**
     * Asigna las tarjetas de credito al cliente segun la lista de tarjetas ingresadas por parametro.
     * @param tarjetas - Lista de tarjetas de credito del cliente.
     */
    public void setTarjetas(List<TarjetaCreditoEntity> tarjetas) {this.tarjetas = tarjetas;}

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClienteEntity other = (ClienteEntity) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.correo, other.correo)) {
            return false;
        }
        if (!Objects.equals(this.solicitudes, other.solicitudes)) {
            return false;
        }
        return Objects.equals(this.tarjetas, other.tarjetas);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + Objects.hashCode(this.direccion);
        hash = 79 * hash + Objects.hashCode(this.correo);
        hash = 79 * hash + Objects.hashCode(this.contrasena);
        return hash;
    }
    
    
}
