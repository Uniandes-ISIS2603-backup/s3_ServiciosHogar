/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Al serializarse como JSON esta clase implementa el siguiente modelo.
 * <br>
 * <pre>
 * {
 *	"nombre":"string",
 *	"direccion":"string",
 *	"correo":"string",
 *      "contrasena":"string
 * }
 * </pre>
 * Por ejemplo un cliente se representa asi:<br>
 * <pre>
 * {
 *      "id":1,
 *      "nombre": "Carlos Eduardo Robles",
 *      "direccion": "AK72 #23 24",
 *      "correo": "ce.robles@uniandes.edu.co ,
 *      "contrasena":"string"
 * }
 * </pre>
 * @author Carlos Eduardo Robles
 */
public class ClienteDTO implements Serializable 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------ 
    private Long id /*Id del Cliente*/;
    private String nombre /*Nombre del cliente*/;
    private String direccion /*Direccion del Cliente*/;
    private String correo /*Correo del Cliente*/ ;
    private String contrasena /*Contrasena del Cliente*/;
    
    //------------------------------------------
    //---------------Constructor----------------
    //------------------------------------------
    public ClienteDTO(){}
    
    public ClienteDTO(ClienteEntity clienteEntity)
    {
        if (clienteEntity != null) 
        {
            this.id = clienteEntity.getId();
            this.nombre = clienteEntity.getNombre();
            this.direccion = clienteEntity.getDireccion();
            this.correo = clienteEntity.getCorreo();   
            this.contrasena = clienteEntity.getContrasena();
        }         
    }
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------   
    /**
     * Devuelve el id del cliente.
     * @return Id del cliente.
     */
    public Long getId() {return id;}

    /**
     * Asigna el Id del cliente segun el 'id' ingresado por parametro.
     * @param id Id del cliente.
     */
    public void setId(Long id) {this.id = id;}

    /**
     * Devuelve el nombre del Cliente.
     * @return nombre del cliente.
     */
    public String getNombre() {return nombre;}

    /**
     * Asigna el nombre del cliente segun el 'nombre' ingresado por parametro.
     * @param nombre Nombre del cliente a obtener.
     */
    public void setNombre(String nombre) {this.nombre = nombre;}
    
    /**
     * Devuelve la direccion del cliente.
     * @return direccion del cliente.
     */
    public String getDireccion() {return direccion;}
    
    /**
     * Asigna la direccion del cliente segun la 'direccion' ingresada por parametro.
     * @param direccion Direccion del cliente.
     */
    public void setDireccion(String direccion) {this.direccion = direccion;}

    /**
     * Devuelve el correo del cliente.
     * @return correo del cliente.
     */
    public String getCorreo() {return correo;}

    /**
     * Asigna el correo del cliente segun el 'corre' ingresado por parametro.
     * @param correo Correo electronico del cliente.
     */
    public void setCorreo(String correo) {this.correo = correo;}     

    /**
     * Devuelve la contrasena del prestador.
     * @return la contrasena del cliente.
     */
    public String getContrasena() {return contrasena;}

    /**
     * Asigna la contrasena al prestador segun la 'contrasena' ingresada por parametro.
     * @param contrasena 
     */
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}  

    /**
     * Convierte DTO a Entity
     * @return entidad del cliente.
     */
    public ClienteEntity toEntity() 
    {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setNombre(this.nombre);
        clienteEntity.setCorreo(this.correo);
        clienteEntity.setDireccion(this.direccion);
        clienteEntity.setContrasena(this.contrasena);
        return clienteEntity;
    }    
    
    /**
     * Metodo toString
     * @return string.
     */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
