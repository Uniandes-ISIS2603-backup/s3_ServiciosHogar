/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

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
        /*Se crea un query para buscar todas los  en la base de datos.*/
        TypedQuery query = em.createQuery("select u from HojaDeVidaEntity u", HojaDeVidaEntity.class);
        return query.getResultList();
    }
   
}
