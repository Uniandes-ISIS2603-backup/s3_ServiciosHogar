/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author estudiante
 */
@Stateless
public class PrestadorPersistence {
    
    private static final Logger LOGGER =  Logger.getLogger(PrestadorPersistence.class.getName());
    
    @PersistenceContext(unitName = "SechPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la BD
     * @param prestadorEntity. Objeto Prestador que se creará en la BD.
     * @return La entidad creada (Prestador) con un id dado por la BD
     */
    public PrestadorEntity create(PrestadorEntity prestadorEntity)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo prestador");
        em.persist(prestadorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo prestador");
        
        return prestadorEntity;
    }
    
    /**
     * Devuelve el prestador con el id dado por parámetro
     * @param prestadorId. El id del prestador a buscar
     * @return La entidad encontrada (Prestador).
     */
    public PrestadorEntity find(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Consultando prestador con id={0}", prestadorId);
    
        return em.find(PrestadorEntity.class, prestadorId);
    }
    
    /**
     * Modifica el prestador con el id dado por parámetro
     * @param prestadorEntity. El prestador con los cambios que se desean realizar.
     * @return  El prestador con los cambios aplicados
     */
    public PrestadorEntity update(PrestadorEntity prestadorEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando prestador con id={0}", prestadorEntity.getId());
      
        LOGGER.log(Level.INFO, "Saliendo de actualizar el prestador con id={0}", prestadorEntity.getId());
      
        return em.merge(prestadorEntity);
    }
    
    /**
     * Borra el prestador con el id dado por parámetro
     * @param prestadorId. El id del prestador que se desea eliminar
     */
    public void delete(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Borrando prestador con id={0}", prestadorId);
        
        PrestadorEntity entity = em.find(PrestadorEntity.class, prestadorId);
        em.remove(entity);
        
        LOGGER.log(Level.INFO, "Saliendo de borrar prestador con id={0}", prestadorId);
      
    }
    
    /**
     * Retorna una lista con todos los prestador en la BD.
     * @return Una lista con todos los prestador que se encuentren en la BD.
     */
    public List<PrestadorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas los prestadores");
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = em.createQuery("select u from PrestadorEntity u", PrestadorEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de editoriales.
        return query.getResultList();
    }
	
    /**
     * Busca si existe un prestador con la cédula dada por parámetro
     * @param cedula. La cédula del prestador a buscar
     * @return El prestador con la cédula correspondiente. Null en caso de que no exista.
     */
    public PrestadorEntity findByCedula(Integer cedula)
    {
        LOGGER.log(Level.INFO, "Consultando el prestador con la cédula ", cedula);
        TypedQuery query = em.createQuery("Select e From PrestadorEntity e where e.cedula = :cedula" , PrestadorEntity.class);
        query = query.setParameter("cedula", cedula);
        
        List<PrestadorEntity> sameCedula = query.getResultList();
        PrestadorEntity buscado;
        
        if(sameCedula == null || sameCedula.isEmpty())
            buscado = null;
        else
            buscado = sameCedula.get(0);
        
        LOGGER.log(Level.INFO, "Saliendo de consultar el prestador con cédula ", cedula);
        return buscado;
    }
    
}
