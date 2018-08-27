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
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    @Inject
    private ClientePersistence persistence;
    
    public ClienteEntity crearCliente(ClienteEntity clienteEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        /*Verifica la regla de negocio que dice no puede haber dos clientes con el mismo nombre*/
        if(persistence.findByName(clienteEntity.getNombre()) != null)
        {
            throw new BusinessLogicException("Ya existe un Cliente con el nombre \"" + clienteEntity.getNombre()+ "\"");
        }
        /*Invoca la persistencia para crear el cliente*/
        persistence.create(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return clienteEntity;
    }
    
    public List<ClienteEntity> getClientes()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los clientes");
        List<ClienteEntity> clientes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los clientes");
        return clientes;
    }
    
    public ClienteEntity getCliente(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = persistence.find(clientesId);
        if(clienteEntity == null)
        {
            LOGGER.log(Level.SEVERE, "El cliente con el id = {0} no existe", clientesId);
        }        
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", clientesId);

        return clienteEntity ;
    }
    
    public ClienteEntity modificarCliente(Long clientesId, ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cliente con id = {0}", clientesId);
        ClienteEntity nuevoCliente = persistence.update(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cliente con id = {0}", clienteEntity.getId());
        return nuevoCliente;
    }
    
    public void deleteCliente(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", clientesId);
        persistence.delete(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", clientesId);
    }
}
