/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlos Eduardo Robles
 */
@Stateless
public class ClientePersistence 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
    /**
     * Metodo para persistir la entidad en la BD.
     * @param clienteEntity. Objeto Cliente que se creara en la BD.
     * @return la entidad creada(Cliente) con un id dado por la BD.
     */    
    public ClienteEntity create(ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Creando un cliente nuevo");
        em.persist(clienteEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
        return clienteEntity;       
    }
    
    /**
     * Devuelve una lista con todos los clientes en la BD.
     * @return una lista con todos los clientes que se encuentren en la BD.
     */
    public List<ClienteEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los clientes");
        /*Se crea un query para buscar todas los clientes en la base de datos.*/
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        return query.getResultList();
    }
    
    /**
     * Devuelve un cliente identificado con el 'id' ingresado por parametro.
     * @param clientesId. Id del cliente a buscar.
     * @return clienteEntity.
     */
    public ClienteEntity find(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Consultando cliente con id={0}", clientesId);
        return em.find(ClienteEntity.class, clientesId);
    }
    
    /**
     * Modifica un cliente identificado con el 'id' ingresado por parametro
     * @param clienteEntity. Cliente que viene con los nuevos cambios.
     * @return cliente con los cambios aplicados.
     */
    public ClienteEntity update(ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando cliente con id = {0}", clienteEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el cliente con id = {0}", clienteEntity.getId());
        return em.merge(clienteEntity);
    }
    
    /**
     * Borra un cliente identificado con el 'id' ingresado por parametro de la BD.
     * @param clientesId. Id del cliente a borrar.
     */
    public void delete(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Borrando cliente con id = {0}", clientesId);
        ClienteEntity entity = em.find(ClienteEntity.class, clientesId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente con id = {0}", clientesId);
    }
    
    /**
     * Busca si existe un cliente con el correo enviado por parametro.
     * @param correo. Nombre del cliente a buscar.
     * @return cliente con el correo correspondiente. Null en caso de no encontrarlo
     */
    public ClienteEntity findByCorreo(String correo) 
    {
        LOGGER.log(Level.INFO, "Consultando cliente por correo ", correo);
        /*Se crea un query para buscar editoriales con el correo que recibe el método como argumento.
        ":name" es un placeholder que debe ser remplazado*/
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.correo = :correo", ClienteEntity.class);
        /*Se remplaza el placeholder ":name" con el valor del argumento */
        query = query.setParameter("correo", correo);
        /*Se invoca el query se obtiene la lista resultado*/
        List<ClienteEntity> sameName = query.getResultList();
        ClienteEntity result;
        
        if (sameName == null)
            result = null;
        else if (sameName.isEmpty())
            result = null;
        else
            result = sameName.get(0);
        
        LOGGER.log(Level.INFO, "Saliendo de consultar cliente por correo ", correo);
        return result;
    }
}
