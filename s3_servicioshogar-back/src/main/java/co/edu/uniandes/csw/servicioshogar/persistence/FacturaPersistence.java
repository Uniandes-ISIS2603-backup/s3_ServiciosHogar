/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adriana Trujillo
 */
@Stateless
public class FacturaPersistence {
    private static final Logger LOGGER = Logger.getLogger(FacturaPersistence.class.getName());
    
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
        //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
    /**
     * Metodo para persistir la entidad en la BD.
     * @param facturaEntity  . Objeto FacturaEntity que se creara en la BD.
     * @return la entidad creada con un id dado por la BD.
     */    
    public FacturaEntity create(FacturaEntity facturaEntity)
    {
        LOGGER.log(Level.INFO, "Creando una tarjeta de crédito nueva");
        em.persist(facturaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear tarjeta de crédito nueva");
        return facturaEntity;       
    }
    
    /**
     * Devuelve una lista con todas las facturas en la BD.
     * @return una lista con todas las facturas que se encuentren en la BD.
     */
    public List<FacturaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las facturas");
        /*Se crea un query para buscar todas las facturas en la base de datos.*/
        TypedQuery query = em.createQuery("select u from FacturaEntity u", FacturaEntity.class);
        return query.getResultList();
    }
    
    //FIXME 
    /**
     * Devuelve una factura identificada por el parametro ingresado.
     * @param facturaId  . Nuemro factura.
     * @return .
     */
    public FacturaEntity find(Long facturaId)
    {
        LOGGER.log(Level.INFO, "", facturaId);
        return em.find(FacturaEntity.class, facturaId);
    }
    
    /**
     * Modifica una tarjeta de credito identificada por el parámetro ingresado.
     * @param facturaEntity  . Tarjeta con los nuevos cambios.
     * @return tarjeta de credito con cambios aplicados.
     */
    public FacturaEntity update(FacturaEntity facturaEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando factura identificada por un numero de factura", facturaEntity.getNoFactura());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la factura", facturaEntity.getNoFactura());
        return em.merge(facturaEntity);
    }
    
    /**
     * Borra un cliente identificado con el 'id' ingresado por parametro de la BD.
     * @param tarjetaId. Titular de tarjeta de credito a borrar.
     */
    public void delete(Long tarjetaId)
    {
        LOGGER.log(Level.INFO, "Borrando cliente con id = {0}", tarjetaId);
        FacturaEntity entity = em.find(FacturaEntity.class, tarjetaId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente con id = {0}", tarjetaId);
    }
        
}
