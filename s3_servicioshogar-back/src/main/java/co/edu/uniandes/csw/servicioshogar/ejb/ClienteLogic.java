/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.uniandes.csw.servicioshogar.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Stateless
public class ClienteLogic 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    /*Inyecta las dependencias*/
    @Inject
    private ClientePersistence persistence;
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
   /**
    * Crea un cliente en la persistencia.
    * @param clienteEntity. Entidad que representa el cliente a presistir.
    * @return Entidad del cliente luego de ser persistida.
    * @throws BusinessLogicException . Si el cliente ya existe.
    */
    public ClienteEntity crearCliente(ClienteEntity clienteEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        /*Verifica la regla de negocio que dice no puede haber dos clientes con el mismo nombre*/
        if(persistence.findByName(clienteEntity.getNombre()) != null)
            throw new BusinessLogicException("Ya existe un Cliente con el nombre \"" + clienteEntity.getNombre()+ "\"");
        /*Invoca la persistencia para crear el cliente*/
        persistence.create(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return clienteEntity;
    }
    
    /**
     * Obtener todos los clientes existentes en la BD.
     * @return lista con todos los clientes.
     */
    public List<ClienteEntity> getClientes()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        List<ClienteEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes");
        return clientes;
    }
    
    /**
     * Obtener un cliente identificado con el 'id' ingresado por parametro.
     * @param clientesId. Id del cliente a buscar.
     * @return el cliente solicitado.
     */
    public ClienteEntity getCliente(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = persistence.find(clientesId);
        if(clienteEntity == null)        
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clientesId);
                
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", clientesId);
        return clienteEntity ;
    }
    
    /**
     * Modifica la informacion de un cliente ingresado por parametro.
     * @param clientesId. Id del cliente a modificar.
     * @param clienteEntity. Cliente con los cambios a ser modificado.
     * @return cliente con los cambios actualizados en la BD
     */
    public ClienteEntity modificarCliente(Long clientesId, ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clientesId);
        ClienteEntity nuevoCliente = persistence.update(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clienteEntity.getId());
        return nuevoCliente;
    }
    
    /**
     * Borrar un cliente
     * @param clientesId. Id del cliente a borrar.
     */
    public void deleteCliente(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clientesId);
        persistence.delete(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", clientesId);
    }
}
