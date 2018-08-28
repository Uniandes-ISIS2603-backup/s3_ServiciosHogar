/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author María Ocampo
 */
@Stateless
public class PrestadorPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorPersistence.class.getName());
    
    @PersistenceContext(unitName = "SechUP")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la BD.
     * @param prestadorEntity Objeto prestador que se creará en la BD.
     * @return Devuelve la entidad creada con un id dado por la BD.
     */
    public PrestadorEntity create(PrestadorEntity prestadorEntity)
    {
        
        LOGGER.log(Level.INFO, "Creando un prestador nuevo");
        em.persist(prestadorEntity); //Método con comando SQL "INSERT INTO..."
        LOGGER.log(Level.INFO, "Saliendo de crear un prestador nuevo");
        return prestadorEntity;
    }
    
    /**
     * Devuelve todos los prestadores de la base de datos
     * @return Una lista con todos los prestadores que encuentre en la BD.
     */
    public List<PrestadorEntity> findAll()
    {
        
        LOGGER.log(Level.INFO, "Consultando todas las editoriales");
        TypedQuery query = em.createQuery("select u from PrestadorEntity u", PrestadorEntity.class); //Query para buscar todos los prestadores
        return query.getResultList(); //getResultList()obtiene la lista de prestadores
    }
    
    /**
     * Busca si hay una editorial con el id que se encía de argumento
     * @param prestadoresId Id correspondiente al prestador buscado
     * @return un prestador
     */
    public PrestadorEntity find(Long prestadoresId)
    {
        LOGGER.log(Level.INFO, "Consultando editorial con id={0}", prestadoresId);
        return em.find(PrestadorEntity.class, prestadoresId); //En SQL "SELECT * FROM ..."
    }
    
    /**
     * Actualiza un prestador
     * @param prestadorEntity El prestador que viene con los cambios.
     * @return Un prestador con los cambios aplicados
     */
    public PrestadorEntity update(PrestadorEntity prestadorEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando el prestador con id={0}", prestadorEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la editorial con id={0}", prestadorEntity.getId());
        
        return em.merge(prestadorEntity); //En SQL "UPDATE ..."
    }
    
    /**
     * Borra un prestador de la BD recibiendo como argumento el id del prestador.
     * @param prestadoresId Id correspondiente al prestador a borrar.
     */
    public void delete(Long prestadoresId)
    {
         LOGGER.log(Level.INFO, "Borrando prestador con id={0}", prestadoresId);
         //Busca el prestador a borrar.
         PrestadorEntity entity = em.find(PrestadorEntity.class, prestadoresId);
         em.remove(entity); //En SQL "DELETE ..."
         LOGGER.log(Level.INFO, "Saliendo de borrar el prestador con id={0}", prestadoresId);
    }
    
    public PrestadorEntity findByName(String name)
    {
        LOGGER.log(Level.INFO, "Consultando prestador por nombre", name);
        
        TypedQuery query = em.createQuery("Select e From PrestadorEntity e where e.name = :name", PrestadorEntity.class);
        
        query = query.setParameter("name", name);
        
        List<PrestadorEntity> sameName = query.getResultList();
        PrestadorEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar prestador por nombre ", name);
        return result;
    }
}