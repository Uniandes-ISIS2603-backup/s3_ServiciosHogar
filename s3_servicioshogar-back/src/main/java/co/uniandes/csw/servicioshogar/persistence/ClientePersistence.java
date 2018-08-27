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
 * @author estudiante
 */
@Stateless
public class ClientePersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "ServiciosHogarPU")
    protected EntityManager em;
    
    public ClienteEntity create(ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Creando un cliente nuevo");
        em.persist(clienteEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
        return clienteEntity;       
    }
    
    public List<ClienteEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los clientes");
        /*Se crea un query para buscar todas los clientes en la base de datos.*/
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        return query.getResultList();
    }
    
    public ClienteEntity find(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Consultando cliente con id={0}", clientesId);
        return em.find(ClienteEntity.class, clientesId);
    }
    
    public ClienteEntity update(ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando cliente con id = {0}", clienteEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el cliente con id = {0}", clienteEntity.getId());
        return em.merge(clienteEntity);
    }
    
    public void delete(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Borrando cliente con id = {0}", clientesId);
        ClienteEntity entity = em.find(ClienteEntity.class, clientesId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente con id = {0}", clientesId);
    }
    
    public ClienteEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando cliente por nombre ", nombre);
        /*Se crea un query para buscar editoriales con el nombre que recibe el método como argumento.
        ":name" es un placeholder que debe ser remplazado*/
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.nombre = :name", ClienteEntity.class);
        /*Se remplaza el placeholder ":name" con el valor del argumento */
        query = query.setParameter("nombre", nombre);
        /*Se invoca el query se obtiene la lista resultado*/
        List<ClienteEntity> sameName = query.getResultList();
        ClienteEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar cliente por nombre ", nombre);
        return result;
    }
}
