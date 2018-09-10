/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniela Rocha Torres
 */
@Stateless
public class ReferenciaPersistence {

        private static final Logger LOGGER = Logger.getLogger(ReferenciaPersistence.class.getName());
    
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
public ReferenciaEntity create(ReferenciaEntity referenciaEntity)
{   LOGGER.log(Level.INFO, "Creando una referencia");
        em.persist(referenciaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una referencia");
        return referenciaEntity;     
}

 public List<ReferenciaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las referncias");
        /*Se crea un query para buscar todas las referencias en la base de datos.*/
        TypedQuery query = em.createQuery("select u from ReferenciaEntity u", ReferenciaEntity.class);
        return query.getResultList();
    }
    
    /**
     * Devuelve un cliente identificado con el 'id' ingresado por parametro.
     * @param clientesId. Id del cliente a buscar.
     * @return clienteEntity.
     */
    public ReferenciaEntity find(Integer idRemitente)
    {
        LOGGER.log(Level.INFO, "Consultando referencia con idRemitente={0}", idRemitente);
        return em.find(ReferenciaEntity.class, idRemitente);
    }
    
    /**
     * Modifica un cliente identificado con el 'id' ingresado por parametro
     * @param clienteEntity. Cliente que viene con los nuevos cambios.
     * @return cliente con los cambios aplicados.
     */
    public ReferenciaEntity update(ReferenciaEntity referenciaEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando referencia con idRemitente = {0}", referenciaEntity.getIdRemitente());
        LOGGER.log(Level.INFO, "Saliendo de actualizar referencia con idRemitente = {0}", referenciaEntity.getIdRemitente());
        return em.merge(referenciaEntity);
    }
    
    /**
     * Borra un cliente identificado con el 'id' ingresado por parametro de la BD.
     * @param clientesId. Id del cliente a borrar.
     */
    public void delete(Integer idRemitente)
    {
        LOGGER.log(Level.INFO, "Borrando referencia con idRemitente = {0}", idRemitente);
        ReferenciaEntity entity = em.find(ReferenciaEntity.class, idRemitente);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar referencia con idRemitente = {0}", idRemitente);
    }
   
}
