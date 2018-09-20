/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Adriana Trujillo
 */
@Stateless
public class TarjetaCreditoPersistence {
    //---------------Atributos-------------------//
    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoPersistence.class.getName());
    
     @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
    /**
     * Metodo para persistir la entidad en la BD.
     * @param tarjetaEntity . Objeto TarjetaCredito que se creara en la BD.
     * @return la entidad creada con un id dado por la BD.
     */    
    public TarjetaCreditoEntity create(TarjetaCreditoEntity tarjetaEntity)
    {
        LOGGER.log(Level.INFO, "Creando una tarjeta de crédito nueva");
        em.persist(tarjetaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear tarjeta de crédito nueva");
        return tarjetaEntity;       
    }
    
    /**
     * Devuelve una lista con todas las tarjetas de credito en la BD.
     * @return una lista con todas las tarjetas de credito que se encuentren en la BD.
     */
    public List<TarjetaCreditoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las tarjetas creditos");
        /*Se crea un query para buscar todas las tarjetas de credito en la base de datos.*/
        TypedQuery query = em.createQuery("select u from TarjetaCreditoEntity u", TarjetaCreditoEntity.class);
        return query.getResultList();
    }
    
    /**
     * Devuelve una tarjeta de crédito identificada por el parametro ingresado.
     * @param tarjetaId. Titular de la tarjeta de credito.
     * @return TarjetaCreditoEntity.
     */
    public TarjetaCreditoEntity find(Long tarjetaId)
    {
        LOGGER.log(Level.INFO, "", tarjetaId);
        return em.find(TarjetaCreditoEntity.class, tarjetaId);
    }
    
    /**
     * Modifica una tarjeta de credito identificada por el parámetro ingresado.
     * @param tarjetaEntity . Tarjeta con los nuevos cambios.
     * @return tarjeta de credito con cambios aplicados.
     */
    public TarjetaCreditoEntity update(TarjetaCreditoEntity tarjetaEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando tarjeta de credito identificada por un titular", tarjetaEntity.getTitular());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la tarjeta de credito", tarjetaEntity.getTitular());
        return em.merge(tarjetaEntity);
    }
    
    /**
     * Borra un cliente identificado con el 'id' ingresado por parametro de la BD.
     * @param tarjetaId. Titular de tarjeta de credito a borrar.
     */
    public void delete(Long tarjetaId)
    {
        LOGGER.log(Level.INFO, "Borrando cliente con id = {0}", tarjetaId);
        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, tarjetaId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente con id = {0}", tarjetaId);
    }
    
    /**
     * Busca si existe una tarjeta de credito con el numero enviado por parametro.
     * @param numero. Numero de la tarjeta de credito a buscar.
     * @return cliente con el nombre correspondiente. Null en caso de no encontrarlo
     */
    public TarjetaCreditoEntity findByNumber(Integer numero) 
    {
        LOGGER.log(Level.INFO, "Consultando tarjeta credito por numero ", numero);
        /*Se crea un query para buscar editoriales con el nombre que recibe el método como argumento.*/
        TypedQuery query = em.createQuery("Select e From TarjetaCreditoEntity e where e.numero = :numero", TarjetaCreditoEntity.class);
        
        query = query.setParameter("numero", numero);
        /*Se invoca el query se obtiene la lista resultado*/
        List<TarjetaCreditoEntity> sameName = query.getResultList();
        TarjetaCreditoEntity result;
        
        if (sameName == null)
            result = null;
        else if (sameName.isEmpty())
            result = null;
        else
            result = sameName.get(0);
        
        LOGGER.log(Level.INFO, "Saliendo de consultar tarjeta de credito por numero ", numero);
        return result;
    }
}
