/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
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
public class HojaDeVidaPersistence {
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    
       public HojaDeVidaEntity create(HojaDeVidaEntity hojaDeVidaEntity)
    {
        LOGGER.log(Level.INFO, "Creando una hoja de vida nueva");
        em.persist(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una hoja de vida nuevo");
        return hojaDeVidaEntity;       
    }
       
       public List<HojaDeVidaEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todas las hojas de vida");
        TypedQuery query = em.createQuery("select u from HojaDeVidaEntity u", HojaDeVidaEntity.class);
        return query.getResultList();
    }
   
    public HojaDeVidaEntity find(Long telPrestador)
    {
        LOGGER.log(Level.INFO, "Consultando hoja de vida del prestador con telefono={0}", telPrestador);
        return em.find(HojaDeVidaEntity.class, telPrestador);
    }
    
    /**
     * Modifica un cliente identificado con el 'id' ingresado por parametro
     * @param hojaDeVidaEntity. Cliente que viene con los nuevos cambios.
     * @return hoja de vda con los cambios aplicados.
     */
    public HojaDeVidaEntity update(HojaDeVidaEntity hojaDeVidaEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando hojaDeVida con id = {0}", hojaDeVidaEntity.getId());
       

        LOGGER.log(Level.INFO, "Saliendo de actualizar la hoja de vida asociada con telefono= {0}", hojaDeVidaEntity.getTelefono());
        
     
        return em.merge(hojaDeVidaEntity);
    }
    
    public void delete(Long telPrestador)
    {
        LOGGER.log(Level.INFO, "Borrando hoja de vida asociada con telefono= {0}", telPrestador);
        HojaDeVidaEntity entity = em.find(HojaDeVidaEntity.class, telPrestador);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la hoja de vida asociada con telefono= {0}", telPrestador);
    }
    
    /**
     * Busca si existe un cliente con el nombre enviado por parametro.
     * @param email. Nombre del cliente a buscar.
     * @return cliente con el nombre correspondiente. Null en caso de no encontrarlo
     */
    public HojaDeVidaEntity findByEmail(String email) 
    {
        LOGGER.log(Level.INFO, "Consultando hoja de vida por email ", email);
        /*Se crea un query para buscar editoriales con el nombre que recibe el método como argumento.
        ":name" es un placeholder que debe ser remplazado*/
        TypedQuery query = em.createQuery("Select e From HojaDeVidaEntity e where e.email = :email", HojaDeVidaEntity.class);
        /*Se remplaza el placeholder ":name" con el valor del argumento */
        query = query.setParameter("email", email);
        /*Se invoca el query se obtiene la lista resultado*/
        List<HojaDeVidaEntity> sameEmail = query.getResultList();
        HojaDeVidaEntity result;
        
        if (sameEmail == null)
            result = null;
        else if (sameEmail.isEmpty())
            result = null;
        else
            result = sameEmail.get(0);
        
        LOGGER.log(Level.INFO, "Saliendo de consultar hoja de vida por email ", email);
        return result;
    }
}
